package org.example.bibliomanager.model.entities;

public class Author {
    private final int id;
    private final String nombre;

    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public Author(int id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }

}
