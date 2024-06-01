package org.example.bibliomanager.controller.viewController;

import io.github.palexdev.materialfx.controls.MFXButton;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.example.bibliomanager.model.entities.Book;
import org.example.bibliomanager.model.entities.User;

import java.io.IOException;

public class BookController {

    Book book;
    User user;
    @FXML
    private Text title;
    @FXML
    private Text author;
    @FXML
    private Text rating;
    @FXML
    private Text synopsis;
    @FXML
    private ImageView image;
    @FXML
    private MFXButton goToBookButton;

    @FXML
    public void initialize() {

    }

    @FXML
    protected void onGoToBook(){
        try {
            Stage previousStage = (Stage) goToBookButton.getScene().getWindow();
            previousStage.close();
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/org/example/bibliomanager/bookPage/bookPage.fxml"));
            Parent root = fxmlLoader.load();
            Stage stage = new Stage();
            stage.setTitle(title.getText());
            stage.setResizable(false);
            stage.setScene(new Scene(root));

            BookPageController controller = fxmlLoader.getController();
            controller.setValues(book,user);

            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setValues(Book book, User user) {
        title.setText(book.getTitle());
        author.setText(book.getAuthor());
        rating.setText(String.valueOf(book.getRating()));
        synopsis.setText(book.getSynopsis().substring(0, 100)+"...");
        image.setImage(new Image(book.getImg()));
        this.book = book;
        this.user = user;
    }
}
