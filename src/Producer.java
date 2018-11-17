import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Semaphore;

public class Producer implements Runnable {

    //阻塞队列
    private BlockingQueue queue;
    private Semaphore full;
    private  Semaphore empty;
    private Semaphore mutex;
    private int length;
    private int nextin;

    private Thread t;

    /**
     * 构造器
     */
    public Producer() {

    }

    public Producer(BlockingQueue queue, Semaphore full, Semaphore empty, Semaphore mutex, int length, int nextin) {
        this.queue = queue;
        this.full = full;
        this.empty = empty;
        this.mutex = mutex;
        this.length = length;
        this.nextin = nextin;
        t = new Thread(this);
        t.start();
    }

    @Override
    public void run() {
        long start = System.currentTimeMillis();
        while(true) {
            DataBuffer data = new DataBuffer((int)(100*Math.random()));
            try {
                empty.acquire();
                mutex.acquire();

                queue.put(data);
                System.out.println("生产者" + t.getName() + "生产了" + data.getData() + ", 还有" + queue.size() + "个数据, 时间: " + (System.currentTimeMillis()-start)/100 + "(100ms)");
                nextin = (nextin+1) % length;
                Thread.sleep(400);

                mutex.release();
                full.release();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
