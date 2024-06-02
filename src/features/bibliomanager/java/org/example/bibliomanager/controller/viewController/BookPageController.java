package org.example.bibliomanager.controller.viewController;

import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXDatePicker;
import io.github.palexdev.materialfx.dialogs.MFXGenericDialog;
import io.github.palexdev.materialfx.dialogs.MFXGenericDialogBuilder;
import io.github.palexdev.materialfx.dialogs.MFXStageDialog;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.example.bibliomanager.controller.repositories.RentRepositoryImplements;
import org.example.bibliomanager.helpers.DialogManager;
import org.example.bibliomanager.model.entities.Book;
import org.example.bibliomanager.model.entities.Rent;
import org.example.bibliomanager.model.entities.User;
import org.example.bibliomanager.model.repositories.RentRepository;

import java.io.IOException;
import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Locale;


public class BookPageController {


    User user;
    Book book;
    RentRepository rentRepository = new RentRepositoryImplements();
    //ArrayList<MFXDatePicker> datePickers;

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

        addHeader(); 

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
        try {
            FXMLLoader loaderHeader = new FXMLLoader(getClass().getResource("/org/example/bibliomanager/shared/header.fxml"));
            AnchorPane header = loaderHeader.load();
            HeaderController controller = loaderHeader.getController();
            controller.setValues(user);

            principalPanel.getChildren().add(header);

        }catch (IOException e){
            e.printStackTrace();
        }
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

    }
}
