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
    ArrayList<MFXDatePicker> datePickers;

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
        try {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/bibliomanager/shared/dialogRentContent.fxml"));
            Node dialogContent = loader.load();
            DialogRentContentController dialogController = loader.getController();
            datePickers = dialogController.getDatePickers();

            MFXGenericDialog dialog = new MFXGenericDialog();
            dialog.setHeaderText("Rentar " + book.getTitle());
            dialog.setShowMinimize(false);
            dialog.setShowAlwaysOnTop(false);
            dialog.setContent(dialogContent);


            MFXButton okButton = new MFXButton("Guardar Renta");
            okButton.setOnAction(event -> {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d MMM yyyy", Locale.forLanguageTag("es"));
                LocalDate firstLocalDate = LocalDate.parse(datePickers.getFirst().getText(), formatter);
                LocalDate lastLocalDate = LocalDate.parse(datePickers.getLast().getText(), formatter);

                Date firstSqlDate = Date.valueOf(firstLocalDate);
                Date lastSqlDate = Date.valueOf(lastLocalDate);

                rentRepository.insertRent(new Rent(book, user,firstSqlDate, lastSqlDate));
                rootPane.getChildren().remove(dialog);
                rootPane.getChildren().removeIf(node -> node instanceof Rectangle); // Eliminar el fondo opaco
            });

            MFXButton cancelButton = new MFXButton("Cancelar");
            cancelButton.setOnAction(event -> {
                rootPane.getChildren().remove(dialog);
                rootPane.getChildren().removeIf(node -> node instanceof Rectangle); // Eliminar el fondo opaco
            });

            dialog.addActions(okButton, cancelButton);

            dialog.onCloseProperty().set(event -> {
                rootPane.getChildren().remove(dialog);
                rootPane.getChildren().removeIf(node -> node instanceof Rectangle); // Eliminar el fondo opaco
            });


            Rectangle overlay = new Rectangle(rootPane.getWidth(), rootPane.getHeight());
            overlay.setFill(Color.rgb(0, 0, 0, 0.5));


            rootPane.widthProperty().addListener((obs, oldVal, newVal) -> overlay.setWidth(newVal.doubleValue()));
            rootPane.heightProperty().addListener((obs, oldVal, newVal) -> overlay.setHeight(newVal.doubleValue()));


            overlay.setOnMouseClicked(event -> {
                rootPane.getChildren().remove(dialog);
                rootPane.getChildren().remove(overlay); // Eliminar el fondo opaco
            });


            rootPane.getChildren().add(overlay);
            rootPane.getChildren().add(dialog);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    protected void onGoBack(){
        try {
            Stage previousStage = (Stage) title.getScene().getWindow();
            previousStage.close();
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/org/example/bibliomanager/mainPage/mainPage.fxml"));
            Parent root = fxmlLoader.load();
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
