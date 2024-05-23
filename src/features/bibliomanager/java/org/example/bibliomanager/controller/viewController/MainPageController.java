package org.example.bibliomanager.controller.viewController;

import com.jfoenix.controls.JFXMasonryPane;

import io.github.palexdev.materialfx.controls.MFXProgressSpinner;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;

import javafx.scene.layout.AnchorPane;

import org.example.bibliomanager.controller.repositories.BookRepositoryImplements;
import org.example.bibliomanager.helpers.DatabaseQueryTask;
import org.example.bibliomanager.model.entities.Book;
import org.example.bibliomanager.model.entities.User;


import java.io.IOException;
import java.util.ArrayList;

public class MainPageController{
    //BookRepositoryImplements bookRepository = new BookRepositoryImplements();

    User user;
    @FXML
    private AnchorPane principalPanel;
    @FXML
    private JFXMasonryPane masonryPane;
    @FXML
    private MFXProgressSpinner loader;



    @FXML
    public void initialize() {
        //ArrayList<Book> books = bookRepository.getBooks();
        addHeader();
        loader.setVisible(true);
        DatabaseQueryTask task = new DatabaseQueryTask();
        task.setOnSucceeded(event -> {

            // Obtiene los resultados de la tarea
            ArrayList<Book> books = task.getValue();

            // Actualiza la interfaz de usuario con los resultados
            addItemsToMasonryPane(books);
            loader.setVisible(false);
        });
        task.setOnFailed(event -> {
            // Maneja el error
            loader.setVisible(false);
            Throwable throwable = task.getException();
            System.out.println(throwable);
        });

        new Thread(task).start();

    }

    public void setValues(User user) {
        this.user = user;
        System.out.println(user);
    }

    private void addHeader(){
        try {
            FXMLLoader loaderHeader = new FXMLLoader(getClass().getResource("/org/example/bibliomanager/mainPage/shared/header.fxml"));
            AnchorPane header = loaderHeader.load();
            HeaderController controller = loaderHeader.getController();
            controller.setValues(user);

            principalPanel.getChildren().add(header);

        }catch (IOException e){
            e.printStackTrace();
        }
    }

    private void addItemsToMasonryPane(ArrayList<Book> books) {
        masonryPane.setPadding(new Insets(10, 10, 50, 70));
        masonryPane.setHSpacing(100);
        masonryPane.setVSpacing(60);

        for (Book book : books) {
            try {
                FXMLLoader loaderCard = new FXMLLoader(getClass().getResource("/org/example/bibliomanager/mainPage/shared/bookCard.fxml"));
                AnchorPane card = loaderCard.load();
                BookController controller = loaderCard.getController();
                controller.setValues(book);

                masonryPane.getChildren().add(card);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }



}
