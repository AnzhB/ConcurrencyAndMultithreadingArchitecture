package com.epam.multithreading.task6;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class Task6 {
    public static void main(String[] args) {
        System.out.println("Classic Model:");
        classicModel(1, 1, 1000, 10000);
        System.out.println("Concurrency Model:");
        concurrencyModel(1, 1, 1000, 10000);
    }

    private static void classicModel(int producersNumber, int consumersNumber, int queueCapacity, int operationsNumber) {
        Queue<Integer> queue = new LinkedList<>();
        Thread[] producerThreads = new Thread[producersNumber];
        Thread[] consumerThreads = new Thread[consumersNumber];
        long startTime;
        long endTime;

        for (int i = 0; i < producersNumber; i++) {
            producerThreads[i] = new Thread(() -> {
                Random random = new Random();
                for (int j = 0; j < operationsNumber; j++) {
                    int randomNumber = random.nextInt(100);
                    synchronized (queue) {
                        while (queue.size() >= queueCapacity) {
                            try {
                                queue.wait();
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                        queue.add(randomNumber);
                        queue.notifyAll();
                    }
                }
            });
            producerThreads[i].start();
        }

        for (int i = 0; i < consumersNumber; i++) {
            consumerThreads[i] = new Thread(() -> {
                for (int j = 0; j < operationsNumber; j++) {
                    synchronized (queue) {
                        while (queue.isEmpty()) {
                            try {
                                queue.wait();
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                        queue.poll();
                        queue.notifyAll();
                    }
                }
            });
            consumerThreads[i].start();
        }

        startTime = System.currentTimeMillis();
        for (Thread thread : producerThreads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        for (Thread thread : consumerThreads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        endTime = System.currentTimeMillis();
        long totalTime = endTime - startTime;
        double operationPerSecond = (operationsNumber * (producersNumber + consumersNumber)) / (totalTime / 1000.0);
        System.out.println("Operations per second = " + operationPerSecond);
    }

    private static void concurrencyModel(int producersNumber, int consumersNumber, int queueCapacity, int operationsNumber) {
        BlockingQueue<Integer> queue = new ArrayBlockingQueue<>(queueCapacity);
        Thread[] producerThreads = new Thread[producersNumber];
        Thread[] consumerThreads = new Thread[consumersNumber];
        long startTime;
        long endTime;

        for (int i = 0; i < producersNumber; i++) {
            producerThreads[i] = new Thread(() -> {
                Random random = new Random();
                for (int j = 0; j < operationsNumber; j++) {
                    try {
                        int randomNumber = random.nextInt(100);
                        queue.put(randomNumber);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });
            producerThreads[i].start();
        }

        for (int i = 0; i < consumersNumber; i++) {
            consumerThreads[i] = new Thread(() -> {
                for (int j = 0; j < operationsNumber; j++) {
                    try {
                        queue.take();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });
            consumerThreads[i].start();
        }

        startTime = System.currentTimeMillis();
        for (Thread thread : producerThreads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        for (Thread thread : consumerThreads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        endTime = System.currentTimeMillis();
        long totalTime = endTime - startTime;
        double operationPerSecond = (operationsNumber * (producersNumber + consumersNumber)) / (totalTime / 1000.0);
        System.out.println("Operations per second = " + operationPerSecond);
    }
}
