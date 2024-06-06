package org.example.bibliomanager.model.repositories;


import org.example.bibliomanager.model.entities.User;

import java.util.ArrayList;

public abstract class AuthRepository {

    public abstract User login(String email, String password);
    public abstract String register(String email, String password,String name, String phone ,String direction);
    public abstract void logOut(User user);
    public abstract ArrayList<User> getUsers();
    public abstract String updateUser(int id,User user);
    public abstract String deleteUser(int id);
    //public abstract User checkAuthentication(Boolean isActiveSession);

}
