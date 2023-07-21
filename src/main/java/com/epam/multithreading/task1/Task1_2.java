package com.epam.multithreading.task1;

public class Task1_2 {
    public static void main(String[] args) throws InterruptedException {
        ThreadSafeMap threadSafeMap = new ThreadSafeMap();
        Thread addThread = new Thread(new AddElementsIntoTheMap2(threadSafeMap));
        Thread sumThread = new Thread(new SumValues2(threadSafeMap));
        addThread.start();
        sumThread.start();
        addThread.join();
        sumThread.join();
    }
}

class AddElementsIntoTheMap2 implements Runnable {
    private ThreadSafeMap threadSafeMap;

    public AddElementsIntoTheMap2(ThreadSafeMap threadSafeMap) {
        this.threadSafeMap = threadSafeMap;
    }

    public void add() throws InterruptedException {
        for (int i = 1; i <= 100; i++) {
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

    public SumValues2(ThreadSafeMap threadSafeMap) {
        this.threadSafeMap = threadSafeMap;
    }

    public void sum() throws InterruptedException {
        while (true) {
            int sum = threadSafeMap.getSum();
            System.out.println("Sum of values = " + sum);
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
