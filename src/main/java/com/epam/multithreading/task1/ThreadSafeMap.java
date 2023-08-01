package com.epam.multithreading.task1;

import java.util.HashMap;
import java.util.Map;

public class ThreadSafeMap {
    private Map<Integer, Integer> map = new HashMap<>();

    public synchronized void put(int key, int value) {
        map.put(key, value);
    }

    public synchronized int getSum() {
        int sum = map.values().stream().reduce(0, Integer::sum);
        return sum;
    }

    public synchronized boolean containsKey(int key) {
        return map.containsKey(key);
    }
}


