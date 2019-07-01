package com.example.demo;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

public class MySet<E> implements Set<E> {

    private HashMap<E, Object> hashMap;

    public MySet() {
        this.hashMap = new HashMap<>();
    }

    @Override
    public int size() {
        return hashMap.size();
    }

    @Override
    public boolean isEmpty() {
        return hashMap.isEmpty();
    }

    @Override
    public boolean contains(Object o) {
        return hashMap.containsKey(o);
    }

    @Override
    public Iterator<E> iterator() {
        return hashMap.keySet().iterator();
    }

    @Override
    public Object[] toArray() {
        return hashMap.keySet().toArray();
    }

    @Override
    public <T> T[] toArray(T[] a) {
        return hashMap.keySet().toArray(a);
    }

    @Override
    public boolean add(E e) {
        return hashMap.put(e, 1) == null;
    }

    @Override
    public boolean remove(Object o) {
        return hashMap.remove(o) == "1";
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        return hashMap.keySet().containsAll(c);
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        c.forEach(e -> add(e));
        return false;
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        return hashMap.keySet().retainAll(c);
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        return hashMap.keySet().retainAll(c);
    }

    @Override
    public void clear() {
        hashMap.clear();
    }

    public String toString() {
        return hashMap.keySet().toString();
    }
}
