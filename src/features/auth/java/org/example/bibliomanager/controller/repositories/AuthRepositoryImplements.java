package org.example.bibliomanager.controller.repositories;


import org.example.bibliomanager.controller.datasources.AuthDatasourceImplements;
import org.example.bibliomanager.model.datasources.AuthDatasource;
import org.example.bibliomanager.model.entities.User;
import org.example.bibliomanager.model.repositories.AuthRepository;

import java.util.ArrayList;

public class AuthRepositoryImplements extends AuthRepository {

    AuthDatasource datasource = new AuthDatasourceImplements();

    @Override
    public User login(String email, String password ) {
        return datasource.login(email,password);
    }

    @Override
    public String register(String email, String password, String name, String phone, String direction) {
        return datasource.register(email,password,name,phone,direction);
    }

    @Override
    public void logOut(User user) {
        datasource.logOut(user);
    }

    @Override
    public ArrayList<User> getUsers() {
        return datasource.getUsers();
    }

    @Override
    public String updateUser(int id,User user) {
        return datasource.updateUser(id,user);
    }

    @Override
    public String deleteUser(int id) {
        return datasource.deleteUser(id);
    }


}
