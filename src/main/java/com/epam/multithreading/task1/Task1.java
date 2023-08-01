package com.epam.multithreading.task1;


import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class Task1 {

    public static void main(String[] args) throws InterruptedException {

//        Map<Integer, Integer> map = new ConcurrentHashMap<>();
//        Map<Integer, Integer> map = new HashMap<>();
        Map<Integer, Integer> map =  Collections.synchronizedMap(new HashMap<>());

        Thread addThread = new Thread(new AddElementsIntoTheMap(map));
        Thread sumThread = new Thread(new SumValues(map));
        addThread.start();
        sumThread.start();
        addThread.join();
        sumThread.join();
    }
}

 class AddElementsIntoTheMap implements Runnable {
    private Map map;

    public AddElementsIntoTheMap(Map map) {
        this.map = map;
    }

    public void add(Map map) throws InterruptedException {
        for (int i = 1; i <= 100; i++) {
            int b = i;
            map.put(i, b);
            System.out.println("key = " + i + ", value = " + b);
            Thread.sleep(1000);
        }
    }

    @Override
    public void run() {
        try {
            add(map);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

 class SumValues implements Runnable {
    private Map map;

    public SumValues(Map map) {
        this.map = map;
    }

    public void sum(Map<Integer, Integer> map) throws InterruptedException {
        while (true) {
            int sum = map.values().stream().reduce(0, Integer::sum);
            System.out.println("Sum of values = " + sum);
            Thread.sleep(5000);
        }
    }

    @Override
    public void run() {
        try {
            sum(map);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}