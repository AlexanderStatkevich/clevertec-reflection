package com.statkevich.receipttask.dao.api;


import java.util.List;

public interface BaseDao<E, K> {

    E get(K key);

    List<E> getByKeys(List<K> keys);
}
