package org.example.bibliomanager.helpers;

import javafx.concurrent.Task;
import org.example.bibliomanager.controller.repositories.BookRepositoryImplements;
import org.example.bibliomanager.model.entities.Book;

import java.util.ArrayList;

public class DatabaseQueryTask extends Task<ArrayList<Book>> {
    BookRepositoryImplements bookRepository = new BookRepositoryImplements();

    @Override
    protected ArrayList<Book> call() throws Exception {

        return bookRepository.getBooks();
    }
}