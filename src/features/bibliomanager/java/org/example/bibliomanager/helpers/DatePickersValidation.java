package org.example.bibliomanager.helpers;

import io.github.palexdev.materialfx.controls.MFXDatePicker;
import javafx.scene.layout.Pane;

public class DatePickersValidation {
    public void setupDatePickersValidation(MFXDatePicker startDatePicker, MFXDatePicker endDatePicker, Pane rootPane) {
        HandleErrors handleErrors = new HandleErrors();

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
}
