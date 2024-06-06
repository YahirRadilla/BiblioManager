package org.example.bibliomanager.controller.datasource;

import org.example.bibliomanager.model.entities.Genre;

import java.sql.*;
import java.util.ArrayList;

public class GenreDatasource {
    static final String URL = System.getenv("SQL_URL");
    static final String USER = System.getenv("SQL_USER");
    static final String PASSWORD = System.getenv("SQL_PASSWORD");
    Connection conn = null;
    Statement stmt = null;
    PreparedStatement pstmt = null;

    public ArrayList<Genre> getGenres(){
        try {
            conn = DriverManager.getConnection(URL, USER, PASSWORD);
            stmt = conn.createStatement();
            ArrayList<Genre> genres = new ArrayList<>();
            String selectQuery = "SELECT * FROM railway.Categorias";
            ResultSet rs = stmt.executeQuery(selectQuery);
            while(rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("nombre");
                Genre newGenre = new Genre(id,name);
                genres.add(newGenre);

            }
            return genres;

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

    public String updateGenre(int id,Genre genre){
        try {
            conn = DriverManager.getConnection(URL, USER, PASSWORD);
            String selectQuery = """
            UPDATE Categorias
            SET
                nombre = ?
            WHERE id = ?; 
            """;
            pstmt = conn.prepareStatement(selectQuery);
            pstmt.setString(1, genre.getName());
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

    public String deleteGenre(int id){
        try {
            conn = DriverManager.getConnection(URL, USER, PASSWORD);
            String selectQuery = "DELETE FROM Categorias WHERE id = ?";
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

    public String insertGenre(Genre genre){
        try {
            conn = DriverManager.getConnection(URL, USER, PASSWORD);

            String selectQuery = """
            INSERT INTO Categorias (nombre) 
            VALUES (?)
            """;
            pstmt = conn.prepareStatement(selectQuery);
            pstmt.setString(1, genre.getName());
            int executedUpdate = pstmt.executeUpdate();
            if (executedUpdate > 0) {
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
