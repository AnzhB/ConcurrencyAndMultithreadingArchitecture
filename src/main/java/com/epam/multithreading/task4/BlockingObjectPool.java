package com.epam.multithreading.task4;

import java.util.ArrayList;
import java.util.List;

public class BlockingObjectPool {
    private int size;
    private List<Object> queue = new ArrayList<>(size);

    /**
     * Creates filled pool of passed size
     *
     * @param size of pool
     */
    public BlockingObjectPool(int size) {
        this.size = size;
    }

    /**
     * Gets object from pool or blocks if pool is empty
     *
     * @return object from pool
     */
    public synchronized Object get() throws InterruptedException {
        while (queue.isEmpty()) {
            wait();
        }
        Object object = queue.remove(0);
        notifyAll();
        return object;
    }

    /**
     * Puts object to pool or blocks if pool is full
     *
     * @param object to be taken back to pool
     */
    public synchronized void take(Object object) throws InterruptedException {
        while (queue.size() == size) {
            wait();
        }
        queue.add(object);
        notifyAll();
    }

}