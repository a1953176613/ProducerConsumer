import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Semaphore;

public class ProducerConsumer {


    public static void main(String[] args) {
        final int length = 10;      //缓冲区大小
        Semaphore full = new Semaphore(0);
        Semaphore empty = new Semaphore(length);
        Semaphore mutex = new Semaphore(1);     //互斥信号量
        int nextin = 0;
        int nextout = 0;

        //生产者
        List<Producer> producers = new ArrayList<>();
        //消费者
        List<Consumer> consumers = new ArrayList<>();

        BlockingQueue<DataBuffer> queue = new ArrayBlockingQueue<>(length);   //缓冲区

        System.out.println("缓冲区的数据个数: " + queue.size());


        for(int i = 0; i < 10; ++i) {
            producers.add(new Producer(queue, full, empty, mutex, length, nextout));
        }

        for(int i = 0; i < 10; ++i) {
            consumers.add(new Consumer(queue, full, empty, mutex, length, nextout));
        }
    }
}
