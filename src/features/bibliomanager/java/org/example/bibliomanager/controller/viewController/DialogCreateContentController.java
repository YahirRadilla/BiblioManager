package org.example.bibliomanager.controller.viewController;

import io.github.palexdev.materialfx.beans.NumberRange;
import io.github.palexdev.materialfx.controls.MFXComboBox;
import io.github.palexdev.materialfx.controls.MFXDatePicker;
import io.github.palexdev.materialfx.controls.MFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.util.StringConverter;
import org.example.bibliomanager.controller.datasource.AuthorDatasource;
import org.example.bibliomanager.controller.datasource.BookDatasourceImplements;
import org.example.bibliomanager.controller.repositories.AuthRepositoryImplements;
import org.example.bibliomanager.helpers.DatePickersValidation;
import org.example.bibliomanager.model.entities.*;
import org.example.bibliomanager.model.repositories.AuthRepository;

import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.function.Supplier;

public class DialogCreateContentController {

    BookDatasourceImplements bookDatasource = new BookDatasourceImplements();
    AuthRepository authRepository = new AuthRepositoryImplements();
    AuthorDatasource authorDatasource = new AuthorDatasource();
    ArrayList<String> authorNames = new ArrayList<>();
    ArrayList<Author> authors;
    ArrayList<String> imageName;

    User user;
    Book book;
    Rent rent;
    Author author;
    Genre genre;

    @FXML
    private AnchorPane createBookContent;
    @FXML
    private AnchorPane createUserContent;
    @FXML
    private AnchorPane createRentContent;
    @FXML
    private AnchorPane createAuthorOrGenreContent;
    //book
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
    private MFXTextField description;
    @FXML
    private MFXComboBox<String> imageCombo;
    //user
    @FXML
    private MFXTextField nameUserInput;
    @FXML
    private MFXTextField emailUserInput;
    @FXML
    private MFXTextField phoneUserInput;
    @FXML
    private MFXTextField directionUserInput;
    @FXML
    private MFXTextField passwordUserInput;
    //rent
    @FXML
    private MFXComboBox<String> bookRentCombo;
    @FXML
    private MFXComboBox<String> emailRentCombo;
    @FXML
    private MFXDatePicker pickUpPicker;
    @FXML
    private MFXDatePicker returnPicker;
    //author | genre
    @FXML
    private MFXTextField nameEntityInput;

    @FXML
    public void initialize() {
        fillItemsCombos();
        datePicker.setYearsRange(new NumberRange<>(500,2100));
        datePicker.setConverterSupplier(getSupplier());

    }

    public void setContent(String content, Pane rootPane){
        switch (content){
            case "book":
                createBookContent.setVisible(true);
                break;
            case "user":
                createUserContent.setVisible(true);
                break;
            case "rentAd":
                createRentContent.setVisible(true);
                DatePickersValidation datePickersValidation = new DatePickersValidation();
                fillRentItemsCombos();
                pickUpPicker.setConverterSupplier(getSupplier());
                returnPicker.setConverterSupplier(getSupplier());
                datePickersValidation.setupDatePickersValidation(pickUpPicker,returnPicker, rootPane);
                break;
            case "author":
                createAuthorOrGenreContent.setVisible(true);
                break;
            case "genre":
                createAuthorOrGenreContent.setVisible(true);
                break;
        }
    }

    public Book getCreatedBook(){
        return new Book(titleInput.getText(), description.getText(),authorCombo.getText(),genreCombo.getText(),isbnInput.getText(),datePicker.getText(),Float.parseFloat(ratingInput.getText()),0,imageCombo.getText(), "");
    }

    public User getCreatedUser(){
        return new User( nameUserInput.getText(),1, emailUserInput.getText(), phoneUserInput.getText(),directionUserInput.getText(), passwordUserInput.getText());

    }

    public Rent getCreatedRent(){
        return new Rent(1, Date.valueOf(pickUpPicker.getText()),Date.valueOf(returnPicker.getText()),bookRentCombo.getText(),emailRentCombo.getText());
    }

    public Author getCreatedAuthor(){
        return new Author( 1, nameEntityInput.getText());
    }

    public Genre getCreatedGenre(){
        return new Genre( 1, nameEntityInput.getText());
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

        ObservableList<String> imageList = FXCollections.observableArrayList(bookDatasource.getImages());
        imageCombo.setItems(imageList);
    }

    private void fillRentItemsCombos(){

        ArrayList<String> books = new ArrayList<>();
        for(Book book: bookDatasource.getBooks()){
            books.add(book.getTitle());
        }
        ArrayList<String> users = new ArrayList<>();
        for(User user: authRepository.getUsers()){
            users.add(user.getEmail());
        }
        ObservableList<String> bookList = FXCollections.observableArrayList(books);
        bookRentCombo.setItems(bookList);


        ObservableList<String> userList = FXCollections.observableArrayList(users);
        emailRentCombo.setItems(userList);

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
