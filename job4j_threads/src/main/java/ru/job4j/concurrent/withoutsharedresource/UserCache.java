package ru.job4j.concurrent.withoutsharedresource;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

/*
Локальные переменные видны только своей нити.
если добавляем локальный объект в общее хранилище,
то ссылка на непотокобезопасный объект становится доступна всем,
но если методы будут работать с копиями объекта User,
изменение этого объекта другими нитями не сможет произвести изменений в самом кеше.
 */
public class UserCache {
    private final ConcurrentHashMap<Integer, User> users = new ConcurrentHashMap<>();
    private final AtomicInteger id = new AtomicInteger();

    public void add(User user) {
        users.put(id.incrementAndGet(), User.of(user.getName()));
    }

    public User findById(int id) {
        return User.of(users.get(id).getName());
    }

    public List<User> findAll() {
        List<User> resultUsers = new ArrayList<>();
        users.values().forEach(u -> resultUsers.add(User.of(u.getName())));
        return resultUsers;
    }
}
