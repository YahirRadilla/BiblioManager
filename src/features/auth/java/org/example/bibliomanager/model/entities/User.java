package org.example.bibliomanager.model.entities;

import java.security.Timestamp;

public class User {
    private final int id;
    private final String name;
    private final String email;
    private final String phone;
    private final String direction;
    private String registerDate;

    public String getRegisterDate() {
        return registerDate;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    public String getDirection() {
        return direction;
    }

    public User(int id, String name, String email, String phone, String direction) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.direction = direction;
    }

    public User(int id, String name, String email, String phone, String direction, String registerDate) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.direction = direction;
        this.registerDate = registerDate;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", direction='" + direction + '\'' +
                '}';
    }
}
