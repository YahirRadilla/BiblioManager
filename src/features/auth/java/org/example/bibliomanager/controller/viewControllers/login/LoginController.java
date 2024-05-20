package org.example.bibliomanager.controller.viewControllers.login;

import com.jfoenix.controls.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.example.bibliomanager.controller.datasources.AuthDatasourceImplements;
import org.example.bibliomanager.controller.repositories.AuthRepositoryImplements;
import org.example.bibliomanager.helpers.HandleErrors;
import org.example.bibliomanager.helpers.SeePassword;
import org.example.bibliomanager.model.datasources.AuthDatasource;
import org.example.bibliomanager.model.entities.User;
import org.example.bibliomanager.model.repositories.AuthRepository;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

public class LoginController {


    AuthRepositoryImplements authRepository = new AuthRepositoryImplements();
    HandleErrors handleErrors = new HandleErrors();
    SeePassword seePassword = new SeePassword();
    Timer timer;
    Color normalColor = new Color(0.30196, 0.30196, 0.30196,1);
    Color errorColor = new Color(0.8588, 0.1882, 0.3373,1);

    private boolean isEyeOpen = true;
    @FXML
    private AnchorPane loginContainer;
    @FXML
    private Button createAccountButton;
    @FXML
    private Button loginButton;
    @FXML
    private JFXTextField emailInput;
    @FXML
    private JFXPasswordField passwordInput;
    @FXML
    private JFXTextField passwordAuxInput;
    @FXML
    private AnchorPane errorAlert;
    @FXML
    private Text errorText;
    @FXML
    private ImageView eyeImage;
    @FXML
    private Button togglePasswordButton;

    @FXML
    protected void onCreateAccountClick() {

        try {
            Stage previousStage = (Stage) createAccountButton.getScene().getWindow();
            previousStage.close();
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/org/example/bibliomanager/register/register.fxml"));
            Parent root = fxmlLoader.load();
            Stage stage = new Stage();
            stage.setTitle("Registro de usuario");
            stage.setResizable(false);
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    protected void seePassword(){
        if(passwordInput.getText().equals("")) return;
        seePassword.seePassword(passwordInput, passwordAuxInput, isEyeOpen, eyeImage, togglePasswordButton);
        if(isEyeOpen){
            isEyeOpen = false;
            return;
        }
        isEyeOpen = true;
    }

    ArrayList<JFXTextField> textFields = new ArrayList<>();
    ArrayList<JFXPasswordField> passwordFields = new ArrayList<>();
    @FXML
    private void initialize() {
        textFields.add(emailInput);
        textFields.add(passwordAuxInput);
        passwordFields.add(passwordInput);
    }


    @FXML
    protected void onLoginClick() {


        String email = emailInput.getText();
        String password;

        if(isEyeOpen){
            password = passwordInput.getText();
        }else{
            password = passwordAuxInput.getText();
        }

        boolean areInputsFilled = handleInputs(email,password);
        if(!areInputsFilled){
            handleErrors.handleErrors("inputs-not-filled",textFields,passwordFields, loginContainer);
            return;
        }

        User user = authRepository.login(email, password);
        System.out.println(user);
        if(user != null){
            try {
                Stage previousStage = (Stage) createAccountButton.getScene().getWindow();
                previousStage.close();
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/org/example/bibliomanager/mainPage/mainPage.fxml"));
                Parent root = fxmlLoader.load();
                Stage stage = new Stage();
                stage.setTitle("Registro de usuario");
                stage.setResizable(false);
                stage.setScene(new Scene(root));
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        handleErrors.handleErrors("not-authorized",textFields,passwordFields, loginContainer);

    }

    private boolean handleInputs(String email, String password) {
        if(!Objects.equals(email, "") && !Objects.equals(password, "")){
            return true;
        }
        return false;
    }




}
