package com.statkevich.receipttask.cache;

public interface Cache {
    void put(Object key, Object value);

    Object get(Object key);

    void evict(Object key);
}
