package org.example.bibliomanager.model.repositories;

import org.example.bibliomanager.model.entities.Book;

import java.util.ArrayList;

public abstract class BookRepository {

    public abstract ArrayList<Book> getBooks();
    public abstract Book getBooksById(int id);
    public abstract ArrayList<Book> getBooksByGenre(String genre);
    public abstract ArrayList<Book> getBooksByQuery(String query);
    public abstract ArrayList<String> getGenres();

}
