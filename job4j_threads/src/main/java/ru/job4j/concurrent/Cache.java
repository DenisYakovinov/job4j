package ru.job4j.concurrent;
/*
 Если две нити пробуют выполнить один и тот же синхронизированный метод,
 то одна из нитей переходит в режим блокировки до тех пор пока первая нить
 не закончить работу с этим методом.
 */
public final class Cache {
    private static Cache cache;

    public static synchronized Cache instOf() {
        if (cache == null) {
            cache = new Cache();
        }
        return cache;
    }
}
