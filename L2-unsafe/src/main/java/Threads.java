import java.util.concurrent.atomic.AtomicLong;

/**
 *
 */
public class Threads {

    static boolean flag = false;

    static AtomicLong t1Ts = new AtomicLong();
    static AtomicLong t2Ts = new AtomicLong();

    public static void main(String[] args) throws Exception {

        final Object lock = new Object();
        Thread t1 = new Thread(() -> {
            while (true) {
                synchronized (lock) {
                    while (!flag) {
                        try {
                            lock.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    t1Ts.set(System.nanoTime());
                    System.out.println(System.nanoTime() - t2Ts.get());
                }
            }
        });

        Thread t2 = new Thread(() -> {
            while (true) {
                synchronized (lock) {
                    flag = true;
                    lock.notifyAll();
                    t2Ts.set(System.nanoTime());
                }
            }
        });

        t1.start();
        t2.start();

        t1.join();
        t2.join();

        System.out.println(t1Ts.get() - t2Ts.get());
    }
}
