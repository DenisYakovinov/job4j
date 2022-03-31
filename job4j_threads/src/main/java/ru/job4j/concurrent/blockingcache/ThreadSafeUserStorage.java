package ru.job4j.concurrent.blockingcache;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import java.util.HashMap;
import java.util.Map;

@ThreadSafe
public final class ThreadSafeUserStorage {

    @GuardedBy("this")
    private final Map<Integer, User> keysToUsers = new HashMap<>();

    public synchronized boolean add(User user) {
        return keysToUsers.putIfAbsent(user.getId(), user) == null;
    }

    public synchronized User getById(int id) {
        return keysToUsers.get(id);
    }

    public synchronized boolean delete(User user) {
        return keysToUsers.remove(user.getId(), user);
    }

    public synchronized boolean update(User user) {
        return keysToUsers.replace(user.getId(), user) != null;
    }

    public synchronized boolean transfer(int fromId, int toId, int amount) {
        User fromUser = keysToUsers.get(fromId);
        User toUser = keysToUsers.get(toId);
        if (fromUser == null || toUser == null || fromUser.getAmount() < amount) {
            return false;
        }
        fromUser.setAmount(fromUser.getAmount() - amount);
        toUser.setAmount(amount + toUser.getAmount());
        return true;
    }
}

