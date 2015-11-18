package yangzhang_assignment6;

import java.util.concurrent.atomic.AtomicInteger;
/*
 * 2.Create two threads, let the first thread print out number 1 to 52, another thread print out A to Z. 
 *   The result shows on the screen should be: “12A34B56C…”.
 */

public class PrintNumChar {
    public static void main(String args[]) throws InterruptedException {
        AtomicInteger synInt = new AtomicInteger(0);
        printNumThread a = new printNumThread(synInt);
        printCharThread b = new printCharThread(synInt);
        a.start();
        b.start();
    }
}

//thread to print numbers
class printNumThread extends Thread {         
    private AtomicInteger synInt;
    private int count = 1;
    public printNumThread(AtomicInteger synInt) {
        this.synInt = synInt;
    }
    @Override
    public void run() {
        synchronized (synInt) {
            for (int i = 1; i < 53; i++) {
                System.out.println(count);
                count = count + 1;
                if (i % 2 == 0) {
                    synInt.notifyAll();
                    try {
                        synInt.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }   
}

//thread to print characters
class printCharThread extends Thread {
    private AtomicInteger synInt;
    public printCharThread(AtomicInteger synInt) {
        this.synInt = synInt;
    }
    @Override
    public void run() {
        synchronized (synInt) {
            for (char i = 0; i < 27; i++){                       
                    System.out.println((char)('A' + i));
                    synInt.notifyAll();                  
                    try {
                        synInt.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }                   
            }
        }
    } 
}