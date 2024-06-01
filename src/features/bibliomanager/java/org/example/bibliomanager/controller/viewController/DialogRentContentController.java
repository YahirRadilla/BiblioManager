package org.example.bibliomanager.controller.viewController;

import io.github.palexdev.materialfx.controls.MFXDatePicker;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.DateCell;
import javafx.scene.control.ListCell;


import javax.security.auth.callback.Callback;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class DialogRentContentController {

    @FXML
    private MFXDatePicker pickUpPicker;
    @FXML
    private MFXDatePicker returnPicker;

    public ArrayList<MFXDatePicker> getDatePickers(){
        ArrayList<MFXDatePicker> datePickers = new ArrayList<>();
        datePickers.add(pickUpPicker);
        datePickers.add(returnPicker);
        return datePickers;
    }




}
