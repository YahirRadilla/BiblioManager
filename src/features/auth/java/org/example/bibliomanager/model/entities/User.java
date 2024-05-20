package org.example.bibliomanager.model.entities;

import java.security.Timestamp;

public class User {
    private int id;
    private String name;
    private String email;
    private String phone;
    private String direction;
    private Boolean isActiveSession = false;

    public User(int id, String name, String email, String phone, String direction, boolean b) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.direction = direction;
        this.isActiveSession = b;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", direction='" + direction + '\'' +
                ", isActiveSession=" + isActiveSession +
                '}';
    }
}
