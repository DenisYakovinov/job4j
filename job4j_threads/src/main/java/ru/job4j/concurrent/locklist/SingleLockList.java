package ru.job4j.concurrent.locklist;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

@ThreadSafe
public final class SingleLockList<T> implements Iterable<T> {

    @GuardedBy("this")
    private final List<T> list;

    public SingleLockList(List<T> list) {
        this.list = copy(list);
    }

    public synchronized void add(T value) {
        list.add(value);
    }

    public synchronized T get(int index) {
        return list.get(index);
    }

    public synchronized T delete(int index) {
        return list.remove(index);
    }

    @Override
    public synchronized Iterator<T> iterator() {
        return copy(list).iterator();
    }

    private synchronized List<T> copy(List<T> list) {
        return list.stream().collect(Collectors.toList());
    }
}
