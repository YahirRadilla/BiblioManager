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

    public String updateAuthor(int id,Author author){
        try {
            conn = DriverManager.getConnection(URL, USER, PASSWORD);
            String selectQuery = """
            UPDATE Autores
            SET
                nombre = ?
            WHERE id = ?; 
            """;
            pstmt = conn.prepareStatement(selectQuery);
            pstmt.setString(1, author.getName());
            pstmt.setInt(2, id);
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

    public String deleteAuthor(int id){
        try {
            conn = DriverManager.getConnection(URL, USER, PASSWORD);
            String selectQuery = "DELETE FROM Autores WHERE id = ?";
            pstmt = conn.prepareStatement(selectQuery);
            pstmt.setInt(1, id);
            int rs = pstmt.executeUpdate();

            if (rs > 0) {
                return "deleted";
            }

        } catch (SQLException e) {
            return "not-deleted";
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

    public String insertAuthor(Author author){
        try {
            conn = DriverManager.getConnection(URL, USER, PASSWORD);

            String selectQuery = """
            INSERT INTO Autores (nombre) 
            VALUES (?)
            """;
            pstmt = conn.prepareStatement(selectQuery);
            pstmt.setString(1, author.getName());
            int filasInsertadas = pstmt.executeUpdate();
            if (filasInsertadas > 0) {
                return "created";
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
}
