package com.epam.multithreading.task2;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
public class Task2 {

    public static void main(String[] args) throws InterruptedException {
        List<Integer> list = new ArrayList<>();
        Random random = new Random();
        Thread addThread = new Thread(new AddElementsIntoTheMap(list, random));
        Thread sumThread = new Thread(new SumNumbers(list));
        Thread squareThread = new Thread(new Square(list));
        addThread.start();
        sumThread.start();
        squareThread.start();
        addThread.join();
        sumThread.join();
        squareThread.join();
    }
}

class AddElementsIntoTheMap implements Runnable {
    private List list;
    private Random random;

    public AddElementsIntoTheMap(List list, Random random) {
        this.list = list;
        this.random = random;
    }

    public synchronized void add(List<Integer> list, Random random) throws InterruptedException {
        list.add(random.nextInt((9)));
        System.out.println("writing random number " + random.nextInt((9)));
        Thread.sleep(1000);
    }

    @Override
    public void run() {
        while (true) {
            try {
                add(list, random);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

class SumNumbers implements Runnable {
    private List list;

    public SumNumbers(List list) {
        this.list = list;
    }

    public synchronized void printSum(List<Integer> list) throws InterruptedException {
        System.out.println("printing sum of the numbers " + list.stream().reduce(0, Integer::sum));
        Thread.sleep(5000);
    }

    @Override
    public void run() {
        while (true) {
            try {
                printSum(list);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

class Square implements Runnable {
    private List list;

    public Square(List list) {
        this.list = list;
    }

    public synchronized void printSquare (List<Integer> list) throws InterruptedException {
        System.out.println("printing square root of sum of squares of all numbers " + Math.sqrt(list.stream().reduce(0, Integer::sum)));
        Thread.sleep(10000);
    }

    @Override
    public void run() {
        while (true) {
            try {
                printSquare(list);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}