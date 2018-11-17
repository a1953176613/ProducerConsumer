

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Semaphore;

public class Consumer implements Runnable {

    //阻塞队列
    private BlockingQueue<DataBuffer> queue;
    private Semaphore full;
    private  Semaphore empty;
    private Semaphore mutex;
    int length;
    int nextout;

    private Thread t;

    /**
     * 构造器
     */
    public Consumer() {

    }

    public Consumer(BlockingQueue<DataBuffer> queue, Semaphore full, Semaphore empty, Semaphore mutex, int length, int nextout) {
        this.queue = queue;
        this.full = full;
        this.empty = empty;
        this.mutex = mutex;
        this.length = length;
        this.nextout = nextout;
        t = new Thread(this);
        t.start();
    }

    @Override
    public void run() {
        long start = System.currentTimeMillis();
        while(true) {
            DataBuffer data = null;

            try {
                full.acquire();
                mutex.acquire();

                data = queue.take();
                System.out.println("消费者" + t.getName() + "消耗了" + data.getData() + ", 还剩" + queue.size() + "个数据, 时间: " + (System.currentTimeMillis()-start)/100 + "(100ms)");
                nextout = (nextout+1) % length;
                Thread.sleep(400);

                mutex.release();
                empty.release();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
