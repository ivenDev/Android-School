package com.cloniamix.lesson_3_engurazov;

public class User {

    private long id;
    private String name;
    private String surname;
    private String email;
    private String login;
    private String region;
    private String position;

    public User(String name, String surname, String email, String login, String region, String position) {
        id = System.currentTimeMillis();
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.login = login;
        this.region = region;
        this.position = position;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public String getEmail() {
        return email;
    }

    public String getLogin() {
        return login;
    }

    public String getRegion() {
        return region;
    }

    public String getPosition() {
        return position;
    }
}
