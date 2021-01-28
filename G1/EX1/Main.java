package G1.EX1;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        int N = 10;

        Thread[] ths = new Thread[N];

        for (int i = 0; i<N; i++) {
            ths[i] = new Thread(() -> {
               for (int j = 0; j<100; j++) {
                   System.out.println(Thread.currentThread().getId()+" -> "+(j+1));
               }
            });
        }

        for (Thread t :ths) {
            t.start();
        }

        for (Thread t : ths) {
            t.join();
        }
    }
}
