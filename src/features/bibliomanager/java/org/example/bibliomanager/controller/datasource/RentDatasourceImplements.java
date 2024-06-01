package org.example.bibliomanager.controller.datasource;

import org.example.bibliomanager.model.datasources.RentDatasource;
import org.example.bibliomanager.model.entities.Book;
import org.example.bibliomanager.model.entities.Rent;

import java.sql.*;
import java.util.ArrayList;

public class RentDatasourceImplements extends RentDatasource {
    static final String URL = "jdbc:mysql://localhost:3306/GestionBiblioteca";
    static final String USER = "root";
    static final String PASSWORD = "A1M0ST_3ASYavgsql";
    Connection conn = null;
    Statement stmt = null;
    PreparedStatement pstmt = null;


    @Override
    public ArrayList<Rent> getRents() {
        return null;
    }

    @Override
    public Rent getRent(int id) {
        return null;
    }

    @Override
    public String deleteRent(int id) {
        return "";
    }

    @Override
    public String updateRent(Rent rent) {
        return "";
    }

    @Override
    public String insertRent(Rent rent) {
        try {
            conn = DriverManager.getConnection(URL, USER, PASSWORD);

            String selectQuery = "INSERT INTO Prestamos (libro_id, usuario_id, fecha_recogida, fecha_devolucion) " +
                    "VALUES (?, ?, ?, ?)";
            pstmt = conn.prepareStatement(selectQuery);
            pstmt.setInt(1, rent.getBook().getId()); // ID del libro
            pstmt.setInt(2, rent.getUser().getId()); // ID del usuario
            pstmt.setString(3, rent.getPickUpDate()); // Fecha de recogida
            pstmt.setString(4, rent.getReturnDate()); // Fecha de devolución
            int filasInsertadas = pstmt.executeUpdate();
            if (filasInsertadas > 0) {
                System.out.println("El préstamo se insertó correctamente en la base de datos.");
            } else {
                System.out.println("No se pudo insertar el préstamo en la base de datos.");
            }
            return null;

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
