package org.example.bibliomanager.helpers;

import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXDatePicker;
import io.github.palexdev.materialfx.dialogs.MFXGenericDialog;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import org.example.bibliomanager.controller.viewController.DialogRentContentController;
import org.example.bibliomanager.model.entities.Book;
import org.example.bibliomanager.model.entities.Rent;
import org.example.bibliomanager.model.entities.User;
import org.example.bibliomanager.model.repositories.RentRepository;

import java.io.IOException;
import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Locale;

public class DialogManager {
    HandleErrors handleErrors = new HandleErrors();
    private ArrayList<MFXDatePicker> datePickers;
    private final Book book;
    private User user;
    private RentRepository rentRepository;
    private final Pane rootPane;
    private final String contentUrl;
    private String status = "rent";

    public DialogManager(Book book, User user, RentRepository rentRepository, Pane rootPane, String contentUrl) {
        this.book = book;
        this.user = user;
        this.rentRepository = rentRepository;
        this.rootPane = rootPane;
        this.contentUrl = contentUrl;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public DialogManager(Book book, Pane rootPane, String contentUrl) {
        this.book = book;
        this.rootPane = rootPane;
        this.contentUrl = contentUrl;
    }

    public void showDialog() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(contentUrl));
            Node dialogContent = loader.load();


            switch (status) {
                case "rent":
                    DialogRentContentController dialogController = loader.getController();
                    datePickers = dialogController.getDatePickers();
                    setupDatePickersValidation();
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
        dialog.setHeaderText("Rentar " + book.getTitle());
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

    private void handleEditAction(MFXGenericDialog dialog) {}

    private void handleCreateAction(MFXGenericDialog dialog) {}

    private void setupDatePickersValidation() {
        MFXDatePicker startDatePicker = datePickers.get(0);
        MFXDatePicker endDatePicker = datePickers.get(1);

        startDatePicker.valueProperty().addListener((obs, oldDate, newDate) -> {
            if (newDate != null && endDatePicker.getValue() != null && newDate.isAfter(endDatePicker.getValue())) {
                handleErrors.showSnackbar("La fecha de inicio no puede ser después de la fecha de fin", rootPane, true);
                startDatePicker.setValue(oldDate);  // Revertir al valor anterior
            }
        });

        endDatePicker.valueProperty().addListener((obs, oldDate, newDate) -> {
            if (newDate != null && startDatePicker.getValue() != null && newDate.isBefore(startDatePicker.getValue())) {
                handleErrors.showSnackbar("La fecha de fin no puede ser antes de la fecha de inicio", rootPane, true);
                endDatePicker.setValue(oldDate);  // Revertir al valor anterior
            }
        });
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

