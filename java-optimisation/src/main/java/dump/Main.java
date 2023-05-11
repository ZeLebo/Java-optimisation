package dump;

public class Main {
    public static void main(String[] args) {
        Runnable target = () -> {
            Singleton singleton = Singleton.getInstance();
            Bean bean = new Bean();
            System.out.println(singleton.toString() + bean.toString());
            try {
                Thread.sleep(1000000);
            } catch (InterruptedException exc) {
                System.out.println("Thread has been killed");
            }
        };

        for (int i = 0; i < 10; ++i) {
            Thread thread = new Thread(target);
            thread.start();
        }
    }
}
