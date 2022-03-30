package ru.job4j.concurrent.withoutsharedresource;

public class ShareNotSafe {

    /*
    при непотокобезопасном UserCache (с ощим ресуром user)
    Последний оператор может напечатать name или rename
     */
    public static void main(String[] args) throws InterruptedException {
        UserCache cache = new UserCache();
        User user = User.of("name");
        cache.add(user);
        Thread first = new Thread(
                () -> {
                    user.setName("rename");
                }
        );
        first.start();
        first.join();
        System.out.println(cache.findById(1).getName());
    }
}
