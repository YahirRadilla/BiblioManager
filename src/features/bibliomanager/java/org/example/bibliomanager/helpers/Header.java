package org.example.bibliomanager.helpers;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import org.example.bibliomanager.controller.viewController.HeaderController;
import org.example.bibliomanager.model.entities.User;

import java.io.IOException;

public class Header {
    HeaderController headerController;

    public void addHeader(String fxmlUrl, User user, AnchorPane principalPanel){
        try {
            FXMLLoader loaderHeader = new FXMLLoader(getClass().getResource("/org/example/bibliomanager/shared/header.fxml"));
            AnchorPane header = loaderHeader.load();
            headerController = loaderHeader.getController();

            headerController.setValues(user);

            principalPanel.getChildren().add(header);
            principalPanel.getChildren().add(headerController.getListView());
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public void closeListViewSearch(){
        headerController.closeListViewSearch();
    }
}
