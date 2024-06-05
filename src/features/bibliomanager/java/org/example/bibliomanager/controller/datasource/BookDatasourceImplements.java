package org.example.bibliomanager.controller.datasource;

import org.example.bibliomanager.model.datasources.BookDatasource;
import org.example.bibliomanager.model.entities.Author;
import org.example.bibliomanager.model.entities.Book;

import java.sql.*;
import java.util.ArrayList;

public class BookDatasourceImplements extends BookDatasource {
    static final String URL = System.getenv("SQL_URL");
    static final String USER = System.getenv("SQL_USER");
    static final String PASSWORD = System.getenv("SQL_PASSWORD");
    Connection conn = null;
    Statement stmt = null;
    PreparedStatement pstmt = null;


    @Override
    public ArrayList<Book> getBooks() {
        try {
            conn = DriverManager.getConnection(URL, USER, PASSWORD);
            stmt = conn.createStatement();
            ArrayList<Book> books = new ArrayList<>();
            String selectQuery = """
                    SELECT\s
                        L.id,
                        L.titulo,
                        L.sinopsis,
                        A.nombre AS autor_nombre,
                        C.nombre AS categoria_nombre,
                        L.isbn,
                        L.fecha_publicacion,
                        I.ruta_archivo AS imagen_url,
                        L.cantidad_disponible,
                        L.calificacion
                    FROM\s
                        Libros L
                    JOIN\s
                        Autores A ON L.autor_id = A.id
                    JOIN\s
                        Categorias C ON L.categoria_id = C.id
                    LEFT JOIN\s
                        Imagenes I ON L.imagen_id = I.id;""";
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
        try {
            conn = DriverManager.getConnection(URL, USER, PASSWORD);
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
                    "    Imagenes I ON L.imagen_id = I.id\n" +
                    "WHERE \n" +
                    "    C.nombre = ?;";

            pstmt = conn.prepareStatement(selectQuery);
            pstmt.setString(1, genre);
            ResultSet rs = pstmt.executeQuery();
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
                if (pstmt != null) pstmt.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    @Override
    public ArrayList<Book> getBooksByQuery(String query) {
        try {

            conn = DriverManager.getConnection(URL, USER, PASSWORD);
            ArrayList<Book> books = new ArrayList<>();
            String selectQuery = """
            SELECT   
                Libros.id,
                Libros.titulo,
                Libros.sinopsis,
                Autores.nombre AS autor_nombre,
                Categorias.nombre AS categoria_nombre,
                Libros.isbn,
                Libros.fecha_publicacion,
                Imagenes.ruta_archivo AS imagen_url,
                Libros.cantidad_disponible,
                Libros.calificacion
            FROM 
                Libros
            INNER JOIN 
                Autores ON Libros.autor_id = Autores.id
            INNER JOIN 
                Categorias ON Libros.categoria_id = Categorias.id
            LEFT JOIN 
                Imagenes ON Libros.imagen_id = Imagenes.id
            WHERE 
                Libros.titulo LIKE ?
                OR Libros.sinopsis LIKE ?
                OR Autores.nombre LIKE ?
                ;
            """;


            pstmt = conn.prepareStatement(selectQuery);
            pstmt.setString(1, query);
            pstmt.setString(2, query);
            pstmt.setString(3, query);
            ResultSet rs = pstmt.executeQuery();
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
    public ArrayList<String> getGenres() {
        try {
            conn = DriverManager.getConnection(URL, USER, PASSWORD);
            stmt = conn.createStatement();
            ArrayList<String> genres = new ArrayList<>();
            String genre;
            String selectQuery = "SELECT * FROM gestionbiblioteca.categorias;";
            ResultSet rs = stmt.executeQuery(selectQuery);
            while(rs.next()) {
                genre = rs.getString("nombre");
                genres.add(genre);
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

    @Override
    public String deleteBookById(int id) {
        try {
            conn = DriverManager.getConnection(URL, USER, PASSWORD);
            ArrayList<Author> authors = new ArrayList<>();
            String selectQuery = "DELETE FROM libros WHERE id = ?";
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
    public String updateBookById(int id, Book book) {
        try {
            conn = DriverManager.getConnection(URL, USER, PASSWORD);
            String selectQuery = """
            UPDATE Libros
            SET
                titulo = ?,
                autor_id = (SELECT id FROM Autores WHERE nombre = ?),
                calificacion = ?,
                categoria_id = (SELECT id FROM Categorias WHERE nombre = ?),
                fecha_publicacion = ?,
                isbn = ?
            WHERE id = ?; -- Asegúrate de cambiar el id al del libro que deseas actualizar
            """;
            pstmt = conn.prepareStatement(selectQuery);
            pstmt.setString(1, book.getTitle());
            pstmt.setString(2, book.getAuthor());
            pstmt.setFloat(3, book.getRating());
            pstmt.setString(4, book.getCategory());
            pstmt.setString(5, book.getDate());
            pstmt.setString(6, book.getIsbn());
            pstmt.setInt(7, id);
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


}
