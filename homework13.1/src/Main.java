
public class Main {
    public static void main(String[] args) {

        long millisLinStart = System.currentTimeMillis();
        new StringsTask().run();
        new CalculationTask().run();
        new SleepyTask().run();
        long millisLinFinish = System.currentTimeMillis();

        Thread t1 = new Thread(new StringsTask());
        Thread t2 = new Thread(new CalculationTask());
        Thread t3 = new Thread(new SleepyTask());

        long millisParStart = System.currentTimeMillis();
        t1.start();
        t2.start();
        t3.start();
        try {
            t1.join();
        } catch (InterruptedException ex) {
            System.out.println("Поток t1 прерван");
        }

        try {
            t2.join();
        } catch (InterruptedException ex) {
            System.out.println("Поток t2 прерван");
        }

        try {
            t3.join();
        } catch (InterruptedException ex) {
            System.out.println("Поток t3 прерван");
        }
        long millisParFinish = System.currentTimeMillis();


        System.out.println("Линейное выполнение : " + (millisLinFinish - millisLinStart));
        System.out.println("Параллельное выполнение: " + (millisParFinish - millisParStart));
        System.out.println("All tasks completed! Time: ");

    }
}