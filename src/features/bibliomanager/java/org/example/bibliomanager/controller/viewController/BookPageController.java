package org.example.bibliomanager.controller.viewController;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;

import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.example.bibliomanager.controller.repositories.RentRepositoryImplements;
import org.example.bibliomanager.helpers.DialogManager;
import org.example.bibliomanager.helpers.Header;
import org.example.bibliomanager.model.entities.Book;
import org.example.bibliomanager.model.entities.User;
import org.example.bibliomanager.model.repositories.RentRepository;

import java.io.IOException;



public class BookPageController {


    RentRepository rentRepository = new RentRepositoryImplements();
    Header header = new Header();
    User user;
    Book book;

    @FXML
    private StackPane rootPane;
    @FXML
    private AnchorPane principalPanel;
    @FXML
    private Text title;
    @FXML
    private Text author;
    @FXML
    private Text rating;
    @FXML
    private Text genre;
    @FXML
    private Text synopsis;
    @FXML
    private ImageView image;
    @FXML
    private Text date;


    @FXML
    public void initialize() {



    }

    @FXML
    protected void onRent(){
        showDialog();
    }

    public void showDialog(){
        DialogManager dialogManager = new DialogManager(book, user, rentRepository, rootPane, "/org/example/bibliomanager/shared/dialogRentContent.fxml");
        dialogManager.showDialog();
    }

    @FXML
    protected void onGoBack(){
        try {
            Stage previousStage = (Stage) title.getScene().getWindow();
            previousStage.close();
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/org/example/bibliomanager/mainPage/mainPage.fxml"));
            Parent root = fxmlLoader.load();
            MainPageController controller = fxmlLoader.getController();
            controller.setValues(user);
            Stage stage = new Stage();
            stage.setTitle("Main");
            stage.setResizable(false);
            stage.setScene(new Scene(root));
            stage.show();

        }catch (IOException e){
            e.printStackTrace();
        }
    }

    private void addHeader(){
        header.addHeader("/org/example/bibliomanager/shared/header.fxml",user,principalPanel);
    }



    public void setValues(Book book, User user) {
        title.setText(book.getTitle());
        author.setText(book.getAuthor());
        rating.setText(String.valueOf(book.getRating()));
        genre.setText(book.getCategory());
        synopsis.setText(book.getSynopsis());
        image.setImage(new Image(book.getImg()));
        date.setText(book.getDate());
        this.book = book;
        this.user = user;
        addHeader();
    }


    @FXML
    protected void onSceneClick(){
        header.closeListViewSearch();
    }

}
