package org.example.bibliomanager.controller.viewController;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import org.example.bibliomanager.model.entities.User;

import java.io.IOException;

public class BookPageController {


    User user;
    @FXML
    private AnchorPane principalPanel;

    @FXML
    public void initialize() {

        addHeader();


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
}
