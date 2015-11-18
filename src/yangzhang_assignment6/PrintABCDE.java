package yangzhang_assignment6;

import java.util.concurrent.atomic.AtomicInteger;

public class PrintABCDE {
    public static void main(String args[]) throws InterruptedException {
        AtomicInteger synInt = new AtomicInteger(0);
        PrintThread a = new PrintThread(synInt, "A", 0);
        PrintThread b = new PrintThread(synInt, "B", 1);
        PrintThread c = new PrintThread(synInt, "C", 2);
        PrintThread d = new PrintThread(synInt, "D", 3);
        PrintThread e = new PrintThread(synInt, "E", 4);
        a.start();
        b.start();
        c.start();
        d.start();
        e.start();
    } 
}

class PrintThread extends Thread{
    private String name;                  // A or B or C or D or E
    private AtomicInteger synInt;         // Synchronized integer 
    private int flag;                     //indicator to run when synInt == flag 
    private int count = 0;                //print times
    
    public PrintThread (AtomicInteger synInt, String name, int flag) {
        this.synInt = synInt;
        this.name = name;
        this.flag = flag;
    }
    
    @Override
    public void run() {
        while (count < 5) {
            synchronized (synInt) {
                if (synInt.get() % 5 == flag) {
                    synInt.set(synInt.get() + 1);
                    System.out.println(name);
                    count ++;
                    synInt.notifyAll();
                } else{
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