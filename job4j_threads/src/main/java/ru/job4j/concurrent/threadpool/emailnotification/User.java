package ru.job4j.concurrent.threadpool.emailnotification;

public class User {
    private String name;
    private String email;

    public User(String userName, String email) {
        this.name = userName;
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
