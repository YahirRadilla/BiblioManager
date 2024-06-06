package org.example.bibliomanager.helpers;

import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXDatePicker;
import io.github.palexdev.materialfx.controls.MFXTableView;
import io.github.palexdev.materialfx.dialogs.MFXGenericDialog;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import org.example.bibliomanager.controller.repositories.AuthRepositoryImplements;
import org.example.bibliomanager.controller.repositories.BookRepositoryImplements;
import org.example.bibliomanager.controller.repositories.RentRepositoryImplements;
import org.example.bibliomanager.controller.viewController.DialogCreateContentController;
import org.example.bibliomanager.controller.viewController.DialogEditContentController;
import org.example.bibliomanager.controller.viewController.DialogRentContentController;
import org.example.bibliomanager.model.entities.*;
import org.example.bibliomanager.model.repositories.RentRepository;

import java.io.IOException;
import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Locale;

public class DialogManager {
    BookRepositoryImplements bookRepository = new BookRepositoryImplements();
    AuthRepositoryImplements authRepository = new AuthRepositoryImplements();
    private RentRepository rentRepository = new RentRepositoryImplements();
    DialogEditContentController dialogEditController;
    DialogCreateContentController dialogCreateContentController;
    private MFXTableView<MyItem> table;
    private MyItem selectedItem;
    HandleErrors handleErrors = new HandleErrors();
    private ArrayList<MFXDatePicker> datePickers;
    private Book book;
    private User user;
    private Rent rent;
    private Author author;
    private Genre genre;
    private final Pane rootPane;
    private final String contentUrl;
    private String status = "rent";
    private String currentContent = "book";
    DatePickersValidation datePickersValidation = new DatePickersValidation();

    public DialogManager(Book book, User user, RentRepository rentRepository, Pane rootPane, String contentUrl) {
        this.book = book;
        this.user = user;
        this.rentRepository = rentRepository;
        this.rootPane = rootPane;
        this.contentUrl = contentUrl;
    }

    public DialogManager(Book book, Pane rootPane, String contentUrl, MFXTableView<MyItem> table, MyItem selectedItem) {
        this.book = book;
        this.rootPane = rootPane;
        this.contentUrl = contentUrl;
        this.table = table;
        this.selectedItem = selectedItem;
    }

    public DialogManager(User user, Pane rootPane, String contentUrl, MFXTableView<MyItem> table, MyItem selectedItem) {
        this.user = user;
        this.rootPane = rootPane;
        this.contentUrl = contentUrl;
        this.table = table;
        this.selectedItem = selectedItem;
    }

    public DialogManager(Rent rent, Pane rootPane, String contentUrl, MFXTableView<MyItem> table, MyItem selectedItem) {
        this.rent = rent;
        this.rootPane = rootPane;
        this.contentUrl = contentUrl;
        this.table = table;
        this.selectedItem = selectedItem;
    }

    public DialogManager(Author author, Pane rootPane, String contentUrl, MFXTableView<MyItem> table, MyItem selectedItem) {
        this.author = author;
        this.rootPane = rootPane;
        this.contentUrl = contentUrl;
        this.table = table;
        this.selectedItem = selectedItem;
    }

    public DialogManager(Genre genre, Pane rootPane, String contentUrl, MFXTableView<MyItem> table, MyItem selectedItem) {
        this.genre = genre;
        this.rootPane = rootPane;
        this.contentUrl = contentUrl;
        this.table = table;
        this.selectedItem = selectedItem;
    }

    public DialogManager(Pane rootPane, String contentUrl, MFXTableView<MyItem> table) {
        this.rootPane = rootPane;
        this.contentUrl = contentUrl;
        this.table = table;

    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setCurrentContent(String currentContent) {
        this.currentContent = currentContent;
    }

    public void showDialog() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(contentUrl));
            Node dialogContent = loader.load();

            switch (status) {
                case "rent":
                    DialogRentContentController dialogController = loader.getController();
                    datePickers = dialogController.getDatePickers();
                    datePickersValidation.setupDatePickersValidation(datePickers.get(0), datePickers.get(1),rootPane);
                    break;
                case "edit":
                    dialogEditController = loader.getController();
                    if(currentContent.equals("book")){
                        dialogEditController.setValues(book);
                    }
                    if(currentContent.equals("user")){
                        dialogEditController.setValues(user);
                    }
                    if(currentContent.equals("rentAd")){
                        dialogEditController.setValues(rent, rootPane);
                    }
                    if(currentContent.equals("author")){
                        dialogEditController.setValues(author);

                    }
                    if(currentContent.equals("genre")){
                        dialogEditController.setValues(genre);
                    }
                    break;
                case "create":
                    dialogCreateContentController = loader.getController();
                    break;

            }

            MFXGenericDialog dialog = createDialog(dialogContent);
            addActionsToDialog(dialog);
            setupDialogCloseHandler(dialog);

            Rectangle overlay = createOverlay();
            addOverlayResizeListeners(overlay);
            setupOverlayClickHandler(dialog, overlay);

            showOverlayAndDialog(dialog, overlay);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private Node loadDialogContent(FXMLLoader loader) throws IOException {
        return loader.load();
    }

    private MFXGenericDialog createDialog(Node dialogContent) {
        MFXGenericDialog dialog = new MFXGenericDialog();
        switch (status) {
            case "rent":
                dialog.setHeaderText("Rentar " + book.getTitle());
                break;
            case "edit":
                if(currentContent.equals("book")){
                    dialog.setHeaderText("Editar " + book.getTitle());
                }
                if(currentContent.equals("user")){
                    dialog.setHeaderText("Editar " + user.getName());
                }
                if(currentContent.equals("author")){
                    dialog.setHeaderText("Editar " + author.getName());
                }
                if(currentContent.equals("genre")){
                    dialog.setHeaderText("Editar " + genre.getName());
                }
                break;
            case "create":
                dialog.setHeaderText("Crear entidad");
                break;
        }
        dialog.setShowMinimize(false);
        dialog.setShowAlwaysOnTop(false);
        dialog.setContent(dialogContent);
        return dialog;
    }

    private void addActionsToDialog(MFXGenericDialog dialog) {
        MFXButton okButton = new MFXButton();
        okButton.setOnAction(event -> handleOkAction(dialog));

        switch (status) {
            case "rent":
                okButton.setText("Guardar Renta");
                break;
            case "edit":
                okButton.setText("Editar");
                okButton.setOnAction(event -> handleEditAction(dialog));
                break;
            case "create":
                okButton.setText("Crear");
                okButton.setOnAction(event -> handleCreateAction(dialog));
                break;
        }


        MFXButton cancelButton = new MFXButton("Cancelar");
        cancelButton.setOnAction(event -> handleCancelAction(dialog));

        dialog.addActions(okButton,cancelButton);
    }

    private void handleOkAction(MFXGenericDialog dialog) {
        if (datePickers.get(0).getText().isEmpty() || datePickers.get(1).getText().isEmpty()) {
            handleErrors.showSnackbar("Introduzca las dos fechas", rootPane, true);
            return;
        }

        String patternString = "d MMM yyyy";
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern(patternString, Locale.forLanguageTag("es"));
            LocalDate startDate = LocalDate.parse(datePickers.get(0).getText(), formatter);
            LocalDate endDate = LocalDate.parse(datePickers.get(1).getText(), formatter);

            if (startDate.isAfter(endDate)) {
                handleErrors.showSnackbar("La fecha de inicio no puede ser después de la fecha de fin", rootPane, true);
                return;
            }

            Date firstSqlDate = Date.valueOf(startDate);
            Date lastSqlDate = Date.valueOf(endDate);

            String insertStatus = rentRepository.insertRent(new Rent(book, user, firstSqlDate, lastSqlDate));
            if (insertStatus.equals("created")) {
                handleErrors.showSnackbar("Renta creada satisfactoriamente", rootPane, false);
                closeDialog(dialog);
            }
        } catch (DateTimeParseException e) {
            handleErrors.showSnackbar("Formato incorrecto", rootPane, true);
        }
    }

    private void handleEditAction(MFXGenericDialog dialog) {

        if(currentContent.equals("user")){
            User updatedUser = dialogEditController.getUpdatedUser();
            String updateStatus = authRepository.updateUser(user.getId(), updatedUser);

            if(updateStatus.equals("updated")){
                MyItem item = new MyItem(user.getId(), updatedUser.getName(), updatedUser.getEmail(), updatedUser.getPhone(), updatedUser.getDirection(), user.getRegisterDate());
                table.getItems().remove(selectedItem);
                table.getItems().addFirst(item);
                table.getSelectionModel().replaceSelection(item);
                handleErrors.showSnackbar("Usuario Actualizado", rootPane, false);
                closeDialog(dialog);
            }
        }
        if(currentContent.equals("book")){
            Book updatedBook = dialogEditController.getUpdatedBook();
            String updateStatus = bookRepository.updateBookById(book.getId(), updatedBook);

            if(updateStatus.equals("updated")){
                MyItem item = new MyItem(book.getId(), updatedBook.getTitle(), updatedBook.getAuthor(), updatedBook.getRating(), updatedBook.getCategory(), updatedBook.getDate(), updatedBook.getIsbn());
                table.getItems().remove(selectedItem);
                table.getItems().addFirst(item);
                table.getSelectionModel().replaceSelection(item);
                handleErrors.showSnackbar("Libro Actualizado", rootPane, false);
                closeDialog(dialog);
            }
        }
        if(currentContent.equals("rentAd")){
            Rent updatedRent = dialogEditController.getUpdatedRent();
            String updateStatus = rentRepository.updateRent(rent.getId(), updatedRent);
            if(updateStatus.equals("updated")){
                MyItem item = new MyItem(rent.getId(), updatedRent.getBookName(), updatedRent.getUserEmail(),rent.getRentDate(), updatedRent.getPickUpDate(), updatedRent.getReturnDate(), updatedRent.isReturned());
                table.getItems().remove(selectedItem);
                table.getItems().addFirst(item);
                table.getSelectionModel().replaceSelection(item);
                handleErrors.showSnackbar("Renta Actualizado", rootPane, false);
                closeDialog(dialog);
            }
        }
        if(currentContent.equals("author")){
            dialog.setHeaderText("Editar " + author.getName());
        }
        if(currentContent.equals("genre")){
            dialog.setHeaderText("Editar " + genre.getName());
        }



    }

    private void handleCreateAction(MFXGenericDialog dialog) {
        Book newBook = dialogCreateContentController.getCreatedBook();
        String createStatus = bookRepository.addBook(newBook);
        System.out.println(createStatus);
        if(createStatus.equals("created")){
            MyItem item = new MyItem(newBook.getId(), newBook.getTitle(), newBook.getAuthor(), newBook.getRating(), newBook.getCategory(), newBook.getDate(), newBook.getIsbn());
            table.getItems().addFirst(item);
            table.getSelectionModel().replaceSelection(item);
            handleErrors.showSnackbar("Libro creado", rootPane, false);
            closeDialog(dialog);
        }
    }


    private void handleCancelAction(MFXGenericDialog dialog) {
        closeDialog(dialog);
    }

    private void setupDialogCloseHandler(MFXGenericDialog dialog) {
        dialog.onCloseProperty().set(event -> closeDialog(dialog));
    }

    private Rectangle createOverlay() {
        Rectangle overlay = new Rectangle(rootPane.getWidth(), rootPane.getHeight());
        overlay.setFill(Color.rgb(0, 0, 0, 0.5));
        return overlay;
    }

    private void addOverlayResizeListeners(Rectangle overlay) {
        rootPane.widthProperty().addListener((obs, oldVal, newVal) -> overlay.setWidth(newVal.doubleValue()));
        rootPane.heightProperty().addListener((obs, oldVal, newVal) -> overlay.setHeight(newVal.doubleValue()));
    }

    private void setupOverlayClickHandler(MFXGenericDialog dialog, Rectangle overlay) {
        overlay.setOnMouseClicked(event -> closeOverlayAndDialog(dialog, overlay));
    }

    private void showOverlayAndDialog(MFXGenericDialog dialog, Rectangle overlay) {
        rootPane.getChildren().add(overlay);
        rootPane.getChildren().add(dialog);
    }

    private void closeDialog(MFXGenericDialog dialog) {
        rootPane.getChildren().remove(dialog);
        rootPane.getChildren().removeIf(node -> node instanceof Rectangle);
    }

    private void closeOverlayAndDialog(MFXGenericDialog dialog, Rectangle overlay) {
        rootPane.getChildren().remove(dialog);
        rootPane.getChildren().remove(overlay);
    }
}

