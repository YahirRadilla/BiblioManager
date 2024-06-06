package org.example.bibliomanager.controller.viewController;

import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXListView;
import io.github.palexdev.materialfx.controls.MFXTextField;
import io.github.palexdev.materialfx.font.MFXFontIcon;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.example.bibliomanager.App;
import org.example.bibliomanager.controller.repositories.AuthRepositoryImplements;
import org.example.bibliomanager.controller.repositories.BookRepositoryImplements;
import org.example.bibliomanager.model.entities.Book;
import org.example.bibliomanager.model.entities.User;

import java.awt.event.KeyEvent;
import java.io.IOException;
import java.util.ArrayList;

public class HeaderController{
    AuthRepositoryImplements authRepository = new AuthRepositoryImplements();
    BookRepositoryImplements bookRepository = new BookRepositoryImplements();
    ArrayList<Book> books;
    User user;
    String previousQuery = "";

    private MFXListView<String> listView;
    private ObservableList<String> items;


    @FXML
    private MFXButton logOutButton;
    @FXML
    private MFXTextField searchTextField;
    @FXML
    private AnchorPane header;


    public void setValues(User user) {
        this.user = user;

    }

    public MFXListView<String> getListView() {
        return listView;
    }

    @FXML
    public void initialize() {
        listView = new MFXListView<>();
        listView.setLayoutX(searchTextField.getLayoutX());
        listView.setLayoutY(searchTextField.getLayoutY()+30);
        listView.setPrefSize(230.0, 0.0);
        listView.setMaxHeight(500.0);

    }

    public void closeListViewSearch(){
        listView.setPrefSize(230.0, 0);
    }

    @FXML
    protected void onSearchChange(){
        String query = searchTextField.getText().trim();

        if(previousQuery.equals(query)){
            return;
        }
        if(query.length() < 3){
            listView.setPrefSize(230.0, 0);
            return;
        }
        books = bookRepository.getBooksByQuery("%"+query+"%");
        ArrayList<String> titles = new ArrayList<>();
        for(Book book : books){
            titles.add(book.getTitle());
        }
        items = FXCollections.observableArrayList(
                titles
        );
        listView.setItems(items);
        listView.setPrefSize(230.0, 32.0*items.size());

        previousQuery = query;
        setOnMouseItem();
    }

    @FXML
    protected void goToAdministration(){
        try {
            Stage previousStage = (Stage) logOutButton.getScene().getWindow();
            previousStage.close();
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/org/example/bibliomanager/administratorPage/administratorPage.fxml"));
            Parent root = fxmlLoader.load();
            Stage stage = new Stage();
            stage.setTitle("Administrador");
            stage.setResizable(false);
            stage.setScene(new Scene(root));

            AdministrationPageController controller = fxmlLoader.getController();
            controller.setValues(user);

            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    protected void goToExplorer(){
        try {
            Stage previousStage = (Stage) logOutButton.getScene().getWindow();
            previousStage.close();
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/org/example/bibliomanager/mainPage/mainPage.fxml"));
            Parent root = fxmlLoader.load();
            Stage stage = new Stage();
            stage.setTitle("Página Principal");
            stage.setResizable(false);
            stage.setScene(new Scene(root));

            MainPageController controller = fxmlLoader.getController();
            controller.setValues(user);

            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void setOnMouseItem() {
        listView.setOnMouseClicked(event -> {
            String selectedItem = listView.getSelectionModel().getSelectedValues().get(0);
            if (selectedItem != null) {
                for (Book book : books) {
                    if (book.getTitle().equals(selectedItem)) {
                        try {
                            Stage previousStage = (Stage) header.getScene().getWindow();
                            previousStage.close();
                            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/org/example/bibliomanager/bookPage/bookPage.fxml"));
                            Parent root = fxmlLoader.load();
                            Stage stage = new Stage();
                            stage.setTitle(book.getTitle());
                            stage.setResizable(false);
                            stage.setScene(new Scene(root));

                            BookPageController controller = fxmlLoader.getController();
                            controller.setValues(book,user);

                            stage.show();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }

            }
        });
    }

    @FXML
    protected void onLogOut() {
        try {
            Stage previousStage = (Stage) logOutButton.getScene().getWindow();
            previousStage.close();
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/org/example/bibliomanager/login/login.fxml"));
            Parent root = fxmlLoader.load();
            Stage stage = new Stage();
            stage.setTitle("Inicio de Sesión");
            stage.setResizable(false);
            stage.setScene(new Scene(root));
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
