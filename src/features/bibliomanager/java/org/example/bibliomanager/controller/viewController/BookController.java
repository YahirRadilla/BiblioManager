package org.example.bibliomanager.controller.viewController;

import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import org.example.bibliomanager.model.entities.Book;
import org.example.bibliomanager.model.entities.User;

public class BookController {

    Book book;
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
    public void initialize() {



    }

    public void setValues(Book book) {
        title.setText(book.getTitle());
        author.setText(book.getAuthor());
        rating.setText(String.valueOf(book.getRating()));
        synopsis.setText(book.getSynopsis().substring(0, 100)+"...");
        image.setImage(new Image(book.getImg()));

    }
}
