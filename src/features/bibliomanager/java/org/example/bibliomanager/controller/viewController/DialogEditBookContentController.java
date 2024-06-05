package org.example.bibliomanager.controller.viewController;

import io.github.palexdev.materialfx.beans.NumberRange;
import io.github.palexdev.materialfx.controls.MFXComboBox;
import io.github.palexdev.materialfx.controls.MFXDatePicker;
import io.github.palexdev.materialfx.controls.MFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableArray;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.util.StringConverter;
import org.example.bibliomanager.controller.datasource.AuthorDatasource;
import org.example.bibliomanager.controller.datasource.BookDatasourceImplements;
import org.example.bibliomanager.model.entities.Author;
import org.example.bibliomanager.model.entities.Book;

import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Locale;
import java.util.function.Supplier;

public class DialogEditBookContentController {
    BookDatasourceImplements bookDatasource = new BookDatasourceImplements();
    AuthorDatasource authorDatasource = new AuthorDatasource();
    ArrayList<String> authorNames = new ArrayList<>();
    ArrayList<Author> authors;
    Book book;

    @FXML
    private MFXTextField titleInput;
    @FXML
    private MFXComboBox<String> authorCombo;
    @FXML
    private MFXTextField ratingInput;
    @FXML
    private MFXComboBox<String> genreCombo;
    @FXML
    private MFXDatePicker datePicker;
    @FXML
    private MFXTextField isbnInput;

    @FXML
    public void initialize() {
        fillItemsCombos();
        datePicker.setYearsRange(new NumberRange<>(500,2100));

        datePicker.setConverterSupplier(getSupplier());
    }

    public void setValues(Book book){
        this.book = book;
        fillInputs();
        fillCombos();
    }

    public Book getUpdatedBook(){
        return new Book(titleInput.getText(), "",authorCombo.getText(),genreCombo.getText(),isbnInput.getText(),datePicker.getText(),Float.parseFloat(ratingInput.getText()),0,"");
    }

    private void fillItemsCombos(){

        authors = authorDatasource.getAuthors();
        for(Author author: authors){
            authorNames.add(author.getName());
        }
        ObservableList<String> authorList = FXCollections.observableArrayList(authorNames);
        authorCombo.setItems(authorList);


        ObservableList<String> genreList = FXCollections.observableArrayList(bookDatasource.getGenres());
        genreCombo.setItems(genreList);

    }

    private void fillInputs(){
        titleInput.setText(book.getTitle());
        ratingInput.setText(book.getRating()+"");
        datePicker.setText(book.getDate());
        isbnInput.setText(book.getIsbn());
    }

    private void fillCombos(){
        authorCombo.selectItem(book.getAuthor());
        genreCombo.selectItem(book.getCategory());
    }


    private Supplier<StringConverter<LocalDate>> getSupplier(){
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        Supplier<StringConverter<LocalDate>> converterSupplier = () -> new StringConverter<>() {
            @Override
            public String toString(LocalDate date) {
                if (date != null) {
                    return dateFormatter.format(date);
                } else {
                    return "";
                }
            }

            @Override
            public LocalDate fromString(String string) {
                if (string != null && !string.isEmpty()) {
                    return LocalDate.parse(string, dateFormatter);
                } else {
                    return null;
                }
            }
        };
        return converterSupplier;
    }

}
