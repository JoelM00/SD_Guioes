package G4.EX3;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        Agreement b = new Agreement(3);
        AgreementV2 b2 = new AgreementV2(3);

        for (int i = 0; i<3; i++) {
            Thread.sleep(500);
            new Thread(() -> {
                try {
                    int x = b2.propoeV2(1);
                    System.out.println(Thread.currentThread().getId()+" -> Done: "+x);
                } catch (Exception ignored) { }
            }).start();
            Thread.sleep(500);

            new Thread(() -> {
                try {
                    int x = b2.propoeV2(54);
                    System.out.println(Thread.currentThread().getId()+" -> Done: "+x);
                } catch (Exception ignored) { }
            }).start();
            Thread.sleep(500);

            new Thread(() -> {
                try {
                    int x = b2.propoeV2(2);
                    System.out.println(Thread.currentThread().getId()+" -> Done: "+x);
                } catch (Exception ignored) { }
            }).start();
        }
    }
}
