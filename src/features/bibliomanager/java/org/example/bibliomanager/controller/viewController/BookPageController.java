package org.example.bibliomanager.controller.viewController;

import io.github.palexdev.materialfx.controls.MFXButton;
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


public class BookPageController {


    User user;
    Book book;
    RentRepository rentRepository = new RentRepositoryImplements();


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
            // Cargar el contenido del FXML
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/bibliomanager/shared/dialogRentContent.fxml"));
            Node dialogContent = loader.load();

            // Crear el diálogo
            MFXGenericDialog dialog = new MFXGenericDialog();
            dialog.setHeaderText("Rentar " + book.getTitle());
            dialog.setContent(dialogContent);

            // Crear botones
            MFXButton okButton = new MFXButton("Guardar Renta");
            okButton.setOnAction(event -> {
                rentRepository.insertRent(new Rent(1, book, user,"2024-05-31", "2024-06-31"));
                rootPane.getChildren().remove(dialog);
                rootPane.getChildren().removeIf(node -> node instanceof Rectangle); // Eliminar el fondo opaco
            });

            MFXButton cancelButton = new MFXButton("Cancelar");
            cancelButton.setOnAction(event -> {
                rootPane.getChildren().remove(dialog);
                rootPane.getChildren().removeIf(node -> node instanceof Rectangle); // Eliminar el fondo opaco
            });

            dialog.addActions(okButton, cancelButton);

            // Crear el fondo opaco
            Rectangle overlay = new Rectangle(rootPane.getWidth(), rootPane.getHeight());
            overlay.setFill(Color.rgb(0, 0, 0, 0.5)); // Color negro con 50% de opacidad

            // Ajustar tamaño del overlay cuando se cambie el tamaño del rootPane
            rootPane.widthProperty().addListener((obs, oldVal, newVal) -> overlay.setWidth(newVal.doubleValue()));
            rootPane.heightProperty().addListener((obs, oldVal, newVal) -> overlay.setHeight(newVal.doubleValue()));

            // Agregar manejador de eventos para cerrar el diálogo al hacer clic en el overlay
            overlay.setOnMouseClicked(event -> {
                rootPane.getChildren().remove(dialog);
                rootPane.getChildren().remove(overlay); // Eliminar el fondo opaco
            });

            // Agregar el fondo opaco y el diálogo al rootPane
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
