package G5.EX1;

import G5.EX1.Warehouse;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        Warehouse w = new Warehouse();

        new Thread(() -> {
            w.supply("serra",3);
        }).start();

        Thread.sleep(1000);

        new Thread(() -> {
            String[] nomes = {"serra","martelo"};
            try {
                w.consume(nomes);
                System.out.println(Thread.currentThread().getId()+" -> Done!");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();

        Thread.sleep(1000);

        new Thread(() -> {
            String[] nomes = {"martelo"};
            try {
                w.supply("martelo",1);
                w.consume(nomes);
                System.out.println(Thread.currentThread().getId()+" -> Done!");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();

        Thread.sleep(1000);

        new Thread(() -> {
            String[] nomes = {"serra"};
            try {
                w.consume(nomes);
                System.out.println(Thread.currentThread().getId()+" -> Done!");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();

        Thread.sleep(1000);

        new Thread(() -> {
            String[] nomes = {"martelo"};
            try {
                w.supply("martelo",1);
                w.consume(nomes);
                System.out.println(Thread.currentThread().getId()+" -> Done!");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();

        Thread.sleep(1000);

        new Thread(() -> {
            String[] nomes = {"serra"};
            try {
                w.consume(nomes);
                System.out.println(Thread.currentThread().getId()+" -> Done!");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();

        Thread.sleep(1000);

        new Thread(() -> {
            String[] nomes = {"serra","martelo"};
            try {
                w.supply("martelo",1);
                w.consume(nomes);
                System.out.println(Thread.currentThread().getId()+" -> Done!");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }
}
