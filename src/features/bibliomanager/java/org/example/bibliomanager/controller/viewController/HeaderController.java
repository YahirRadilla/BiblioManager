package org.example.bibliomanager.controller.viewController;

import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXTextField;
import io.github.palexdev.materialfx.font.MFXFontIcon;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.example.bibliomanager.controller.repositories.AuthRepositoryImplements;
import org.example.bibliomanager.model.entities.User;

import java.io.IOException;

public class HeaderController{
    AuthRepositoryImplements authRepository = new AuthRepositoryImplements();
    User user;
    @FXML
    private MFXButton logOutButton;
    @FXML
    private MFXTextField searchTextField;

    public void setValues(User user) {
        this.user = user;

    }

    @FXML
    public void initialize() {

        //searchTextField.setTrailingIcon(new MFXFontIcon("mfx-info-circle-filled",18));


    }

    @FXML
    protected void onLogOut() {
        try {
            Stage previousStage = (Stage) logOutButton.getScene().getWindow();
            previousStage.close();
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/org/example/bibliomanager/login/login.fxml"));
            Parent root = fxmlLoader.load();
            Stage stage = new Stage();
            stage.setTitle("Inicio de Sesión");
            stage.setResizable(false);
            stage.setScene(new Scene(root));
            authRepository.logOut(user);
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
