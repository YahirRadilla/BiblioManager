package org.example.bibliomanager.model.entities;

public class Genre {
    private int id;
    private String name;

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Genre(int id, String nombre) {
        this.id = id;
        this.name = nombre;
    }
}
