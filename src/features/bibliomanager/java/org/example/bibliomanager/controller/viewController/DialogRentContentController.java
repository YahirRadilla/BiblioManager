package org.example.bibliomanager.controller.viewController;

import io.github.palexdev.materialfx.controls.MFXDatePicker;
import javafx.fxml.FXML;
import java.util.ArrayList;


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
