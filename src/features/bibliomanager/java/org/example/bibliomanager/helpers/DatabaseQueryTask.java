package org.example.bibliomanager.helpers;

import javafx.concurrent.Task;
import org.example.bibliomanager.controller.repositories.BookRepositoryImplements;
import org.example.bibliomanager.model.entities.Book;

import java.util.ArrayList;

public class DatabaseQueryTask extends Task<ArrayList<Book>> {
    private BookRepositoryImplements bookRepository;
    private String genre;
    private String query;
    private String method = "get";

    // Constructor que recibe un parámetro
    public DatabaseQueryTask() {
        this.bookRepository = new BookRepositoryImplements();
    }

    public void setMethod(String method) {
        this.method = method;
    }
    public void setGenre(String genre) {
        this.genre = genre;
    }
    public void setQuery(String query) {
        this.query = query;
    }


    @Override
    protected ArrayList<Book> call() throws Exception {
        switch (method){
            case "get":
                return bookRepository.getBooks();
            case "genre":
                return bookRepository.getBooksByGenre(genre);
            case "query":
                return bookRepository.getBooksByQuery(query);
        }

        return null;
    }
}