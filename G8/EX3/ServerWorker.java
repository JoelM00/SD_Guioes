package Guiao8.EX3;

public class ServerWorker implements Runnable {
    private final TaggedConnection t;

    public ServerWorker(TaggedConnection t) {
        this.t = t;
    }

    @Override
    public void run() {
        try (t) {
            while (true) {
                TaggedConnection.Frame f = t.receive();
                String mensagem = new String(f.dados);

                if (f.tag == 0) {

                    System.out.println("Recebi: "+mensagem);

                } else if (f.tag % 2 == 1) {

                    String res = mensagem.toUpperCase();
                    System.out.println("Reenviando: "+res);
                    t.send(f.tag,res.getBytes());

                } else {
                    for (int i = 0; i < mensagem.length(); i++) {
                        String str = mensagem.substring(i,i+1);
                        System.out.println("Enviando: "+str);
                        t.send(f.tag,str.getBytes());
                        Thread.sleep(100);
                    }
                    t.send(f.tag,new byte[0]);
                }
            }
        } catch (Exception e) { }
    }
}
