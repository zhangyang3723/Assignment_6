package yangzhang_assignment6;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/*
 * producer-consumer problem
 */
public class ProducerConsumer {
    public static void main(String[] args) throws Exception {
        BlockingQueue<String> queue = new ArrayBlockingQueue<String>(3);
        Producer producer = new Producer(queue);
        Consumer consumer = new Consumer(queue);
        new Thread(producer).start();
        new Thread(consumer).start();
        System.out.println("Start!");
    }
}


class Producer implements Runnable {
    protected BlockingQueue<String> queue = null;

    public Producer(BlockingQueue<String> queue) {
        this.queue = queue;
    }

    public void run() {
        for (int i = 0; i < 10; i++) {
            String msg = new String("" + i);
            try {
                Thread.sleep(5);
                queue.put(msg);
                System.out.println("Produced " + msg);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        String msg = "exit";
        try {
            queue.put(msg);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}


class Consumer implements Runnable {
    protected BlockingQueue<String> queue = null;

    public Consumer(BlockingQueue<String> queue) {
        this.queue = queue;
    }

    public void run() {
        try {
            String msg;
            while ((msg = queue.take()) != "exit") {
                Thread.sleep(10);
                System.out.println("Consumed " + msg);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
