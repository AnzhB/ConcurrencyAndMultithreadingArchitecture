package com.epam.multithreading.task2;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Task2 {

    public static void main(String[] args) throws InterruptedException {
        List<Integer> list = new ArrayList<>();
        Random random = new Random();
        Object lock = new Object();

        Thread addThread = new Thread(new AddElementsIntoTheMap(list, random, lock));
        Thread sumThread = new Thread(new SumNumbers(list, lock));
        Thread squareThread = new Thread(new Square(list, lock));

        addThread.start();
        sumThread.start();
        squareThread.start();
        addThread.join();
        sumThread.join();
        squareThread.join();
    }
}

class AddElementsIntoTheMap implements Runnable {
    private List<Integer> list;
    private Random random;
    private Object lock;

    public AddElementsIntoTheMap(List<Integer> list, Random random, Object lock) {
        this.list = list;
        this.random = random;
        this.lock = lock;
    }

    public void add() throws InterruptedException {
        synchronized (lock) {
            list.add(random.nextInt(9));
            System.out.println("writing random number " + list.get(list.size() - 1));
            Thread.sleep(1000);
        }
    }

    @Override
    public void run() {
        while (true) {
            try {
                add();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

class SumNumbers implements Runnable {
    private List<Integer> list;
    private Object lock;

    public SumNumbers(List<Integer> list, Object lock) {
        this.list = list;
        this.lock = lock;
    }

    public void printSum() throws InterruptedException {
        synchronized (lock) {
            int sum = list.stream().reduce(0, Integer::sum);
            System.out.println("printing sum of the numbers " + sum);
            Thread.sleep(5000);
        }
    }

    @Override
    public void run() {
        while (true) {
            try {
                printSum();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

class Square implements Runnable {
    private List<Integer> list;
    private Object lock;

    public Square(List<Integer> list, Object lock) {
        this.list = list;
        this.lock = lock;
    }

    public void printSquare() throws InterruptedException {
        synchronized (lock) {
            int sumOfSquares = list.stream().map(x -> x * x).reduce(0, Integer::sum);
            double squareRoot = Math.sqrt(sumOfSquares);
            System.out.println("printing square root of sum of squares of all numbers " + squareRoot);
            Thread.sleep(10000);
        }
    }

    @Override
    public void run() {
        while (true) {
            try {
                printSquare();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}