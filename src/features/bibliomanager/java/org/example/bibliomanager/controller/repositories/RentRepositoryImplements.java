package org.example.bibliomanager.controller.repositories;


import org.example.bibliomanager.controller.datasource.RentDatasourceImplements;
import org.example.bibliomanager.model.datasources.RentDatasource;
import org.example.bibliomanager.model.entities.Rent;
import org.example.bibliomanager.model.repositories.RentRepository;

import java.util.ArrayList;

public class RentRepositoryImplements extends RentRepository {

    RentDatasource datasource = new RentDatasourceImplements();

    @Override
    public ArrayList<Rent> getRents() {
        return datasource.getRents();
    }

    @Override
    public Rent getRent(int id) {
        return datasource.getRent(id);
    }

    @Override
    public String deleteRent(int id) {
        return datasource.deleteRent(id);
    }

    @Override
    public String updateRent(Rent rent) {
        return datasource.updateRent(rent);
    }

    @Override
    public String insertRent(Rent rent) {
        return datasource.insertRent(rent);
    }
}
