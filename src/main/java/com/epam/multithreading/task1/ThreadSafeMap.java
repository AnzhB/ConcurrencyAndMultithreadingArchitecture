package com.epam.multithreading.task1;

import java.util.HashMap;
import java.util.Map;

public class ThreadSafeMap {
    private Map<Integer, Integer> map = new HashMap<>();
    private Object lock = new Object();

    public void put(int key, int value) {
        synchronized (lock) {
            map.put(key, value);
        }
    }

    public int getSum() {
        synchronized (lock) {
             int sum = map.values().stream().reduce(0, Integer::sum);
            return sum;
        }
    }
}


