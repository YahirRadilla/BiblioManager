package org.example.bibliomanager.controller.datasources;



import org.example.bibliomanager.helpers.PasswordHashing;
import org.example.bibliomanager.model.datasources.AuthDatasource;
import org.example.bibliomanager.model.entities.Book;
import org.example.bibliomanager.model.entities.User;

import java.sql.*;
import java.util.ArrayList;

public class AuthDatasourceImplements extends AuthDatasource {
    static final String URL = System.getenv("SQL_URL");
    static final String USER = System.getenv("SQL_USER");
    static final String PASSWORD = System.getenv("SQL_PASSWORD");
    Connection conn = null;
    Statement stmt = null;
    PreparedStatement pstmt = null;
    PasswordHashing passwordHashing = new PasswordHashing();


    @Override
    public User login(String email, String password) {

        try {

            conn = DriverManager.getConnection(URL, USER, PASSWORD);
            stmt = conn.createStatement();


            String selectQuery = "SELECT * FROM Usuarios WHERE email = '"+ email + "' AND password = '" + passwordHashing.hashPassword(password) +"'" ;
            ResultSet rs = stmt.executeQuery(selectQuery);
            if (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("nombre");
                String phone = rs.getString("telefono");
                String direction = rs.getString("direccion");

                return new User(id,name, email, phone, direction);
            }


        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // Paso 4: Cerrar la conexión y los recursos
            try {
                if (stmt != null) stmt.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    @Override
    public String register(String email, String password, String name, String phone, String direction) {
        try {

            conn = DriverManager.getConnection(URL, USER, PASSWORD);
            stmt = conn.createStatement();

            String updateQuery = "INSERT INTO Usuarios (nombre, email, password, telefono, direccion) values ('" + name + "','" + email + "','" + passwordHashing.hashPassword(password) + "','" + phone + "','" + direction + "')";

            int rs = stmt.executeUpdate(updateQuery);
            if (rs!=0) {
                return "succesfully";
            }

        } catch (SQLException e) {
            if(e.getMessage().contains("email")){
                return "email-duplicated";
            }
            if(e.getMessage().contains("telefono")){
                return "phone-duplicated";
            }
        } finally {
            // Paso 4: Cerrar la conexión y los recursos
            try {
                if (stmt != null) stmt.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return "not-controlled";
    }

    @Override
    public void logOut(User user) {
        user = null;
    }

    @Override
    public ArrayList<User> getUsers() {
        try {
            conn = DriverManager.getConnection(URL, USER, PASSWORD);
            stmt = conn.createStatement();
            ArrayList<User> users = new ArrayList<>();
            String selectQuery = "SELECT * FROM gestionbiblioteca.usuarios";
            ResultSet rs = stmt.executeQuery(selectQuery);

            while(rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("nombre");
                String email = rs.getString("email");
                String password = rs.getString("password");
                String phone = rs.getString("telefono");
                String direction = rs.getString("direccion");
                String registerDate = rs.getString("fecha_registro");
                User newUser = new User(id,name,email,phone,direction,registerDate);
                users.add(newUser);
            }
            return users;

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // Paso 4: Cerrar la conexión y los recursos
            try {
                if (stmt != null) stmt.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    @Override
    public String updateUser(int id,User user) {
        try {
            conn = DriverManager.getConnection(URL, USER, PASSWORD);
            String selectQuery = """
            
                    UPDATE Usuarios
                    SET
                        nombre = ?,
                        email = ?,
                        telefono = ?,
                        direccion = ?
                    WHERE id = ?;
            """;
            pstmt = conn.prepareStatement(selectQuery);
            pstmt.setString(1, user.getName());
            pstmt.setString(2, user.getEmail());
            pstmt.setString(3, user.getPhone());
            pstmt.setString(4, user.getDirection());
            pstmt.setInt(5, id);

            int rs = pstmt.executeUpdate();

            if (rs > 0) {

                return "updated";
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return "not-updated";
        } finally {
            // Paso 4: Cerrar la conexión y los recursos
            try {
                if (stmt != null) stmt.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    @Override
    public String deleteUser(int id) {
        return "";
    }
}
