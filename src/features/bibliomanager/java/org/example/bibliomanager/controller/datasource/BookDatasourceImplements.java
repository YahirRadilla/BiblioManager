package org.example.bibliomanager.controller.datasource;

import org.example.bibliomanager.model.datasources.BookDatasource;
import org.example.bibliomanager.model.entities.Book;
import org.example.bibliomanager.model.entities.User;

import java.sql.*;
import java.util.ArrayList;

public class BookDatasourceImplements extends BookDatasource {
    static final String URL = System.getenv("SQL_URL");
    static final String USER = System.getenv("SQL_USER");
    static final String PASSWORD = System.getenv("SQL_PASSWORD");
    Connection conn = null;
    Statement stmt = null;


    @Override
    public ArrayList<Book> getBooks() {
        try {
            conn = DriverManager.getConnection(URL, USER, PASSWORD);
            stmt = conn.createStatement();
            ArrayList<Book> books = new ArrayList<>();
            String selectQuery = "SELECT \n" +
                    "    L.id,\n" +
                    "    L.titulo,\n" +
                    "    L.sinopsis,\n" +
                    "    A.nombre AS autor_nombre,\n" +
                    "    C.nombre AS categoria_nombre,\n" +
                    "    L.isbn,\n" +
                    "    L.fecha_publicacion,\n" +
                    "    I.ruta_archivo AS imagen_url,\n" +
                    "    L.cantidad_disponible,\n" +
                    "    L.calificacion\n" +
                    "FROM \n" +
                    "    Libros L\n" +
                    "JOIN \n" +
                    "    Autores A ON L.autor_id = A.id\n" +
                    "JOIN \n" +
                    "    Categorias C ON L.categoria_id = C.id\n" +
                    "LEFT JOIN \n" +
                    "    Imagenes I ON L.imagen_id = I.id;";
            ResultSet rs = stmt.executeQuery(selectQuery);
            while(rs.next()) {
                int id = rs.getInt("id");
                String title = rs.getString("titulo");
                String synopsis = rs.getString("sinopsis");
                String authorName = rs.getString("autor_nombre");
                String categoryName = rs.getString("categoria_nombre");
                String isbn = rs.getString("isbn");
                String publicationDate = rs.getString("fecha_publicacion");
                String imageUrl = rs.getString("imagen_url");
                int quantityAvailable = rs.getInt("cantidad_disponible");
                float rating = rs.getFloat("calificacion");
                Book newBook = new Book(id,title,synopsis,authorName,categoryName,isbn,publicationDate,rating,quantityAvailable,imageUrl);
                books.add(newBook);

            }
            return books;

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
    public Book getBooksById(int id) {
        return null;
    }

    @Override
    public ArrayList<Book> getBooksByGenre(String genre) {
        return null;
    }

    @Override
    public ArrayList<Book> getBooksByQuery(String query) {
        return null;
    }
}