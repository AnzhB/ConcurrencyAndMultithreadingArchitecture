package com.epam.multithreading.task1;

public class Task1_2 {
    public static void main(String[] args) throws InterruptedException {
        ThreadSafeMap threadSafeMap = new ThreadSafeMap();
        long startTime = System.currentTimeMillis();
        Thread addThread = new Thread(new AddElementsIntoTheMap2(threadSafeMap));
        Thread sumThread = new Thread(new SumValues2(threadSafeMap));
        addThread.start();
        sumThread.start();
        addThread.join();
        sumThread.join();
        long endTime = System.currentTimeMillis();
        System.out.println("Total execution time = " + (endTime - startTime));
        System.exit(0);
    }
}

class AddElementsIntoTheMap2 implements Runnable {
    private ThreadSafeMap threadSafeMap;

    public AddElementsIntoTheMap2(ThreadSafeMap threadSafeMap) {
        this.threadSafeMap = threadSafeMap;
    }

    public void add() throws InterruptedException {
        for (int i = 1; i <= 10; i++) {
            int b = i;
            threadSafeMap.put(i, b);
            System.out.println("key = " + i + ", value = " + b);
            Thread.sleep(1000);
        }
    }

    @Override
    public void run() {
        try {
            add();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

class SumValues2 implements Runnable {
    private ThreadSafeMap threadSafeMap;
    private volatile boolean isSumPrintedForLastKey = false;

    public SumValues2(ThreadSafeMap threadSafeMap) {
        this.threadSafeMap = threadSafeMap;
    }

    public void sum() throws InterruptedException {
        while (!isSumPrintedForLastKey) {
            int sum = threadSafeMap.getSum();
            if (!isSumPrintedForLastKey) {
                System.out.println("Sum of values = " + sum);
                if (threadSafeMap.containsKey(10)) {
                    isSumPrintedForLastKey = true;
                }
            }
            Thread.sleep(5000);
        }
    }

    @Override
    public void run() {
        try {
            sum();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
