package com.statkevich.receipttask.cache;


import java.util.HashMap;
import java.util.Map;

/**
 * Cache
 *
 * @see com.statkevich.receipttask.cache.LruCache
 */

public class LfuCache implements Cache {
    private final int capacity;
    private final Map<Object, Node> pointerMap;

    public LfuCache(int capacity) {
        this.capacity = capacity;
        this.pointerMap = new HashMap<>();
    }

    @Override
    public void put(Object key, Object value) {
        if (pointerMap.size() == capacity) {
            Object leastFrequentlyKey = findLFU();
            pointerMap.remove(leastFrequentlyKey);
        }
        Node newNode = new Node(value, 1);
        pointerMap.put(key, newNode);
    }

    @Override
    public Object get(Object key) {
        Node node = pointerMap.get(key);
        if (node != null) {
            node.increment();
            return node.value;
        }
        return null;
    }

    @Override
    public void evict(Object key) {
        pointerMap.remove(key);
    }

    private Object findLFU() {
        Object key = null;
        int minFrequency = Integer.MAX_VALUE;
        for (Map.Entry<Object, Node> entry : pointerMap.entrySet()) {
            if (entry.getValue().frequency < minFrequency) {
                minFrequency = entry.getValue().frequency;
                key = entry.getKey();
            }
        }
        return key;
    }


    private static class Node {
        Object value;
        int frequency;

        public Node(Object value, int frequency) {
            this.value = value;
            this.frequency = frequency;
        }

        public void increment() {
            frequency += 1;
        }
    }
}

