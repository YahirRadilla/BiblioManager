package org.example.bibliomanager.model.datasources;

import org.example.bibliomanager.model.entities.Book;

import java.util.ArrayList;

public abstract class BookDatasource{

    public abstract ArrayList<Book> getBooks();
    public abstract Book getBooksById(int id);
    public abstract ArrayList<Book> getBooksByGenre(String genre);
    public abstract ArrayList<Book> getBooksByQuery(String query);
    public abstract ArrayList<String> getGenres();
    public abstract String deleteBookById(int id);
    public abstract String updateBookById(int id, Book book);
    public abstract String addBook(Book book);
    public abstract ArrayList<String> getImages();

}
