package org.example.bibliomanager.model.repositories;

import org.example.bibliomanager.model.entities.Rent;

import java.util.ArrayList;

abstract public class RentRepository {
    abstract public ArrayList<Rent> getRents();
    abstract public Rent getRent(int id);
    abstract public String deleteRent(int id);
    abstract public String updateRent(Rent rent);
    abstract public String insertRent(Rent rent);
}
