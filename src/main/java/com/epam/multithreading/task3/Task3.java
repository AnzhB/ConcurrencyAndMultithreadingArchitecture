package com.epam.multithreading.task3;

import java.util.*;

public class Task3 {

    public static void main(String[] args) throws InterruptedException {
        Queue<String> queue = new LinkedList<>();
        Random random = new Random();
        Thread producerThread = new Thread(new Producer(queue, random));
        Thread consumerThread = new Thread(new Consumer(queue, random));
        producerThread.start();
        consumerThread.start();
        producerThread.join();
        consumerThread.join();
    }
}

class Producer implements Runnable {
    private Queue<String> queue;
    private Random random;

    public Producer(Queue<String> queue, Random random) {
        this.queue = queue;
        this.random = random;
    }

    public void generate(Queue<String> queue, Random random) throws InterruptedException {
        Thread.sleep(random.nextInt(1000));
        synchronized (queue) {
            String message = "Message " + random.nextInt(10);
            queue.add(message);
            System.out.println("Posted message " + message + " to the queue");
            System.out.println("Number of generated Messages = " + queue.size());
            queue.notify();
            queue.wait();
        }
    }

    @Override
    public void run() {
        while (true) {
            try {
                generate(queue, random);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

class Consumer implements Runnable {
    private Queue<String> queue;
    private Random random;

    public Consumer(Queue<String> queue, Random random) {
        this.queue = queue;
        this.random = random;
    }

    public void consume (Queue<String> queue, Random random) throws InterruptedException {
        Thread.sleep(random.nextInt(3000));
        synchronized (queue) {
            for (String message : queue) {
                System.out.println("Consuming message " + message);
            }
            queue.clear();
            System.out.println("Number of Messages after Consuming = " + queue.size());
            queue.notify();
            queue.wait();
        }
    }

    @Override
    public void run() {
        while (true) {
            try {
                consume(queue, random);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
