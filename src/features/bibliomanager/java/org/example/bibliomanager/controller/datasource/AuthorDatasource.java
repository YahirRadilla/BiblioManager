package org.example.bibliomanager.controller.datasource;

import javafx.fxml.FXML;
import org.example.bibliomanager.model.entities.Author;
import org.example.bibliomanager.model.entities.Book;

import java.sql.*;
import java.util.ArrayList;

public class AuthorDatasource {
    static final String URL = System.getenv("SQL_URL");
    static final String USER = System.getenv("SQL_USER");
    static final String PASSWORD = System.getenv("SQL_PASSWORD");
    Connection conn = null;
    Statement stmt = null;
    PreparedStatement pstmt = null;



    public ArrayList<Author> getAuthors(){
        try {
            conn = DriverManager.getConnection(URL, USER, PASSWORD);
            stmt = conn.createStatement();
            ArrayList<Author> authors = new ArrayList<>();
            String selectQuery = "SELECT * FROM Autores";
            ResultSet rs = stmt.executeQuery(selectQuery);
            while(rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("nombre");
                Author newAuthor = new Author(id,name);
                authors.add(newAuthor);

            }
            return authors;

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
}
