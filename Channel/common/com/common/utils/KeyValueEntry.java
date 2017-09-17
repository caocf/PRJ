package com.common.utils;

/**
 * Created by 25019 on 2015/11/10.
 */
public class KeyValueEntry<K, V> {
    private K key;
    private V value;

    public KeyValueEntry() {
    }

    public KeyValueEntry(K key) {
        this.key = key;
    }

    public KeyValueEntry(K key, V value) {
        this.key = key;
        this.value = value;
    }

    public K getKey() {
        return key;
    }

    public void setKey(K key) {
        this.key = key;
    }

    public V getValue() {
        return value;
    }

    public void setValue(V value) {
        this.value = value;
    }
}
