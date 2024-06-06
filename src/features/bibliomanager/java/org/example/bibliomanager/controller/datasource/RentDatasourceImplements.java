package org.example.bibliomanager.controller.datasource;

import org.example.bibliomanager.model.datasources.RentDatasource;
import org.example.bibliomanager.model.entities.Book;
import org.example.bibliomanager.model.entities.Rent;

import java.sql.*;
import java.util.ArrayList;

public class RentDatasourceImplements extends RentDatasource {
    static final String URL = System.getenv("SQL_URL");
    static final String USER = System.getenv("SQL_USER");
    static final String PASSWORD = System.getenv("SQL_PASSWORD");
    Connection conn = null;
    Statement stmt = null;
    PreparedStatement pstmt = null;


    @Override
    public ArrayList<Rent> getRents() {
        try {
            conn = DriverManager.getConnection(URL, USER, PASSWORD);
            stmt = conn.createStatement();
            ArrayList<Rent> rents = new ArrayList<>();
            String selectQuery = """
            SELECT p.id, l.titulo AS nombre_libro, u.email AS correo_usuario, 
                   p.fecha_prestamo, p.fecha_recogida, p.fecha_devolucion, p.devuelto 
            FROM Prestamos p
            JOIN Libros l ON p.libro_id = l.id
            JOIN Usuarios u ON p.usuario_id = u.id;
            """;
            ResultSet rs = stmt.executeQuery(selectQuery);
            while(rs.next()) {
                int id = rs.getInt("id");
                String title = rs.getString("nombre_libro");
                String email = rs.getString("correo_usuario");
                Date rentDate = rs.getDate("fecha_prestamo");
                Date pickUpDate = rs.getDate("fecha_recogida");
                Date returnDate = rs.getDate("fecha_devolucion");
                boolean isReturned = rs.getBoolean("devuelto");
                Rent newRent = new Rent(id,pickUpDate,rentDate,returnDate,isReturned,title,email);
                rents.add(newRent);

            }
            return rents;

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
    public Rent getRent(int id) {
        return null;
    }

    @Override
    public String deleteRent(int id) {
        try {
            conn = DriverManager.getConnection(URL, USER, PASSWORD);
            String selectQuery = "DELETE FROM prestamos WHERE id = ?";
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

    @Override
    public String updateRent(int id, Rent rent) {
        try {
            conn = DriverManager.getConnection(URL, USER, PASSWORD);
            String selectQuery = """
            UPDATE Prestamos
            SET
                libro_id = (SELECT id FROM Libros WHERE titulo = ?),
                usuario_id = (SELECT id FROM Usuarios WHERE email = ?),
                fecha_recogida = ?,
                fecha_devolucion = ?,
                devuelto = ?
            WHERE id = ?; 
            """;
            pstmt = conn.prepareStatement(selectQuery);
            pstmt.setString(1, rent.getBookName());
            pstmt.setString(2, rent.getUserEmail());
            pstmt.setDate(3, rent.getPickUpDate());
            pstmt.setDate(4, rent.getReturnDate());
            pstmt.setBoolean(5, rent.isReturned());
            pstmt.setInt(6, id);
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
    public String insertRent(Rent rent) {
        try {
            conn = DriverManager.getConnection(URL, USER, PASSWORD);

            String selectQuery = "INSERT INTO Prestamos (libro_id, usuario_id, fecha_recogida, fecha_devolucion) " +
                    "VALUES (?, ?, ?, ?)";
            pstmt = conn.prepareStatement(selectQuery);
            pstmt.setInt(1, rent.getBook().getId());
            pstmt.setInt(2, rent.getUser().getId());
            pstmt.setDate(3, rent.getPickUpDate());
            pstmt.setDate(4, rent.getReturnDate());
            int filasInsertadas = pstmt.executeUpdate();
            if (filasInsertadas > 0) {
                System.out.println("El préstamo se insertó correctamente en la base de datos.");
            } else {
                System.out.println("No se pudo insertar el préstamo en la base de datos.");
            }
            return "created";

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
