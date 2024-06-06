package org.example.bibliomanager.controller.viewController;

import io.github.palexdev.materialfx.beans.NumberRange;
import io.github.palexdev.materialfx.controls.MFXComboBox;
import io.github.palexdev.materialfx.controls.MFXDatePicker;
import io.github.palexdev.materialfx.controls.MFXTextField;
import io.github.palexdev.materialfx.controls.MFXToggleButton;
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

import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Locale;
import java.util.function.Function;
import java.util.function.Supplier;

public class DialogEditContentController {
    BookDatasourceImplements bookDatasource = new BookDatasourceImplements();
    AuthRepositoryImplements authRepository = new AuthRepositoryImplements();
    AuthorDatasource authorDatasource = new AuthorDatasource();
    ArrayList<String> authorNames = new ArrayList<>();
    ArrayList<Author> authors;
    User user;
    Book book;
    Rent rent;
    Author author;
    Genre genre;
    Pane rootPane;

    @FXML
    private AnchorPane principalForm;
    @FXML
    private AnchorPane bookForm;
    @FXML
    private AnchorPane userForm;
    @FXML
    private AnchorPane rentForm;
    @FXML
    private AnchorPane authorAndGenreForm;
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
    //user
    @FXML
    private MFXTextField nameInput;
    @FXML
    private MFXTextField emailInput;
    @FXML
    private MFXTextField phoneInput;
    @FXML
    private MFXTextField directionInput;
    //Rent
    @FXML
    private MFXComboBox<String> bookCombo;
    @FXML
    private MFXComboBox<String> userCombo;
    @FXML
    private MFXDatePicker pickUpPicker;
    @FXML
    private MFXDatePicker returnPicker;
    @FXML
    private MFXToggleButton isReturnedToggle;
    //Author | Genre
    @FXML
    private MFXTextField nameEntityInput;


    @FXML
    public void initialize() {
        fillItemsCombos();
        datePicker.setYearsRange(new NumberRange<>(500,2100));
        datePicker.setConverterSupplier(getSupplier());
        pickUpPicker.setConverterSupplier(getSupplier());
        returnPicker.setConverterSupplier(getSupplier());
    }

    public void setValues(Book book){
        this.book = book;
        bookForm.setVisible(true);
        fillBookInputs();
        fillBookCombos();
    }

    public void setValues(User user){
        this.user = user;
        userForm.setVisible(true);
        fillUserInputs();
    }

    public void setValues(Rent rent, Pane rootPane){
        this.rent = rent;
        this.rootPane = rootPane;
        rentForm.setVisible(true);
        fillRentItemsCombos();
        fillRentInputs();
        setValidationPickers();
    }

    public void setValues(Author author){
        System.out.println(author.getName());
        this.author = author;
        authorAndGenreForm.setVisible(true);
        nameEntityInput.setText(author.getName());
    }

    public void setValues(Genre genre){
        this.genre = genre;
        authorAndGenreForm.setVisible(true);
        nameEntityInput.setText(genre.getName());
    }


    public void setValidationPickers(){
        DatePickersValidation datePickersValidation = new DatePickersValidation();
        datePickersValidation.setupDatePickersValidation(pickUpPicker,returnPicker, rootPane);
    }



    public Author getUpdatedAuthor(){
        return new Author(1000, nameEntityInput.getText());
    }

    public Genre getUpdatedGenre(){
        return new Genre(1000, nameEntityInput.getText());
    }

    public Rent getUpdatedRent(){
        Date pickUpSqlDate = Date.valueOf(pickUpPicker.getText());
        Date returnSqlDate = Date.valueOf(returnPicker.getText());
        return new Rent(1000, pickUpSqlDate, null, returnSqlDate, isReturnedToggle.isSelected(), bookCombo.getText(), userCombo.getText());
    }

    public User getUpdatedUser(){
        return new User(1000, nameInput.getText(), emailInput.getText(), phoneInput.getText(), directionInput.getText());
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

    private void fillRentItemsCombos(){

        ArrayList<Book> books = bookDatasource.getBooks();
        ArrayList<String> bookNames = new ArrayList<>();
        ArrayList<User> users = authRepository.getUsers();
        ArrayList<String> userEmail = new ArrayList<>();
        for(Book book: books){
            bookNames.add(book.getTitle());
        }
        for(User user: users){
            userEmail.add(user.getEmail());
        }
        bookCombo.setItems(FXCollections.observableArrayList(bookNames));
        userCombo.setItems(FXCollections.observableArrayList(userEmail));

    }

    private void fillBookInputs(){
        titleInput.setText(book.getTitle());
        ratingInput.setText(book.getRating()+"");
        datePicker.setText(book.getDate());
        isbnInput.setText(book.getIsbn());
    }

    private void fillBookCombos(){
        authorCombo.selectItem(book.getAuthor());
        genreCombo.selectItem(book.getCategory());
    }

    private void fillUserInputs(){
        nameInput.setText(user.getName());
        emailInput.setText(user.getEmail());
        phoneInput.setText(user.getPhone());
        directionInput.setText(user.getDirection());
    }

    private void fillRentInputs(){
        bookCombo.selectItem(rent.getBookName());
        userCombo.selectItem(rent.getUserEmail());
        pickUpPicker.setText(rent.getPickUpDate().toString());
        returnPicker.setText(rent.getReturnDate().toString());
        isReturnedToggle.setSelected(rent.isReturned());
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
