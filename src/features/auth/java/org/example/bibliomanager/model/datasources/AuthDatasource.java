package org.example.bibliomanager.model.datasources;


import org.example.bibliomanager.model.entities.User;

abstract public class AuthDatasource {

    public abstract User login(String email, String password);
    public abstract String register(String email, String password,String name, String phone ,String direction);
    public abstract void logOut(User user);
    //public abstract User checkAuthentication(Boolean isActiveSession);
}
