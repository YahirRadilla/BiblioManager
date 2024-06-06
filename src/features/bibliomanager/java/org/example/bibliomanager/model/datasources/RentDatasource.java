package org.example.bibliomanager.model.datasources;

import org.example.bibliomanager.model.entities.Rent;

import java.util.ArrayList;

abstract public class RentDatasource {
    abstract public ArrayList<Rent> getRents();
    abstract public Rent getRent(int id);
    abstract public String deleteRent(int id);
    abstract public String updateRent(int id,Rent rent);
    abstract public String insertRent(Rent rent);
    abstract public String insertRentAdmin(Rent rent);
}
