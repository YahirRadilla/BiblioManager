package org.example.bibliomanager.controller.viewController;

import com.jfoenix.controls.JFXMasonryPane;

import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXProgressSpinner;
import io.github.palexdev.materialfx.controls.MFXScrollPane;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;

import javafx.scene.layout.AnchorPane;

import org.example.bibliomanager.controller.repositories.BookRepositoryImplements;
import org.example.bibliomanager.helpers.DatabaseQueryTask;
import org.example.bibliomanager.helpers.Header;
import org.example.bibliomanager.model.entities.Book;
import org.example.bibliomanager.model.entities.User;


import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

public class MainPageController{
    BookRepositoryImplements bookRepository = new BookRepositoryImplements();
    Header header = new Header();
    String currentGenre = "Todos los géneros";
    ArrayList<String> genres = new ArrayList<>();
    DatabaseQueryTask task = new DatabaseQueryTask();
    HeaderController headerController;
    User user;

    @FXML
    private AnchorPane principalPanel;
    @FXML
    private AnchorPane genderMenuPanel;
    @FXML
    private JFXMasonryPane masonryPane;
    @FXML
    private MFXProgressSpinner loader;
    @FXML
    private MFXScrollPane scrollPane;
    @FXML
    private MFXButton allGenresButton;




    @FXML
    public void initialize() {
        /*addHeader();
        ArrayList<Book> books = bookRepository.getBooksByGenre("Épica");
        addItemsToMasonryPane(books);*/


        genres = bookRepository.getGenres();

        addGenreButtons(genres);

        setTask("get", "Todos los géneros");


    }

    @FXML
    protected void onAllGenderClick(){
        if(Objects.equals(currentGenre, "Todos los géneros")){
            return;
        }

        masonryPane.getChildren().clear();

        setTask("get", "Todos los géneros");
    }

    @FXML
    protected void onSceneClick(){
        header.closeListViewSearch();
    }

    public void setValues(User user) {
        this.user = user;
        addHeader();
    }

    private void addHeader(){
        header.addHeader("/org/example/bibliomanager/shared/header.fxml",user,principalPanel);
    }



    private void addItemsToMasonryPane(ArrayList<Book> books) {
        masonryPane.setPadding(new Insets(10, 10, 50, 70));
        masonryPane.setHSpacing(100);
        masonryPane.setVSpacing(60);

        for (Book book : books) {
            try {
                FXMLLoader loaderCard = new FXMLLoader(getClass().getResource("/org/example/bibliomanager/shared/bookCard.fxml"));
                AnchorPane card = loaderCard.load();
                BookController controller = loaderCard.getController();
                controller.setValues(book,user);

                masonryPane.getChildren().add(card);

            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

    private void setTask(String method, String genre){

        loader.setVisible(true);
        task = new DatabaseQueryTask();
        task.setMethod(method);
        task.setGenre(genre);
        scrollPane.setVvalue(0.0);
        task.setOnSucceeded(event -> {
            loader.setVisible(false);
            ArrayList<Book> books = task.getValue();
            addItemsToMasonryPane(books);
            currentGenre = genre;
        });
        task.setOnFailed(event -> {
            loader.setVisible(false);
            Throwable throwable = task.getException();
            System.out.println(throwable);
        });
        new Thread(task).start();
    }

    private void addGenreButtons(ArrayList<String> genres){
        int buttonSpaces = 30;
        for (String genre: genres){

            MFXButton newGenreButton = new MFXButton();
            newGenreButton.getStyleClass().add("aside-menu-buttons");
            newGenreButton.setVisible(true);
            newGenreButton.setLayoutX(allGenresButton.getLayoutX());
            newGenreButton.setLayoutY(allGenresButton.getLayoutY() + buttonSpaces);
            newGenreButton.setText(genre);
            buttonSpaces+=30;
            newGenreButton.setOnAction(actionEvent -> onGenderMenuClick(genre));
            genderMenuPanel.getChildren().add(newGenreButton);
        }
    }


    private void onGenderMenuClick(String genre){
        if(Objects.equals(currentGenre, genre)){
            return;
        }

        masonryPane.getChildren().clear();

        setTask("genre", genre);
    }


}
