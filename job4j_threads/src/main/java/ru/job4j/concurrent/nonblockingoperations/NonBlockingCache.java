package ru.job4j.concurrent.nonblockingoperations;

import net.jcip.annotations.ThreadSafe;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@ThreadSafe
public class NonBlockingCache {
    private final Map<Integer, Base> memory = new ConcurrentHashMap<>();

    public boolean add(Base model) {
        return memory.putIfAbsent(model.getId(), model) == null;
    }

    public Base get(int id) {
        return memory.get(id);
    }

    public boolean update(Base model) {
        return memory.computeIfPresent(model.getId(), (key, value) -> {
            if (value.getVersion() != model.getVersion()) {
                throw new OptimisticException("Versions are not equal");
            }
            return updateValue(model);
        }) != null;
    }

    public boolean delete(Base model) {
        return memory.remove(model.getId(), model);
    }

    private Base updateValue(Base model) {
        Base base = new Base(model.getId(), model.getVersion() + 1);
        base.setName(model.getName());
        return base;
    }
}
