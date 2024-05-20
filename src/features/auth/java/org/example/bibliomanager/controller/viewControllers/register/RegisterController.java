package org.example.bibliomanager.controller.viewControllers.register;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.example.bibliomanager.controller.repositories.AuthRepositoryImplements;
import org.example.bibliomanager.helpers.HandleErrors;
import org.example.bibliomanager.helpers.SeePassword;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegisterController {
    AuthRepositoryImplements authRepository = new AuthRepositoryImplements();
    HandleErrors handleErrors = new HandleErrors();
    SeePassword seePassword = new SeePassword();
    private boolean isEyeOpen = true;
    private boolean isEyeConfirmOpen = true;
    String emailRegex = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$";
    String phoneRegex = "^\\d{10}$|^(\\d{3}-){2}\\d{4}$";
    Pattern emailPattern = Pattern.compile(emailRegex);
    Pattern phonePattern = Pattern.compile(phoneRegex);

    @FXML
    private JFXButton moveToLoginButton;
    @FXML
    private AnchorPane registerContainer;
    @FXML
    private JFXTextField nameInputRegister;
    @FXML
    private JFXTextField emailInputRegister;
    @FXML
    private JFXTextField phoneInputRegister;
    @FXML
    private JFXTextField directionInputRegister;
    @FXML
    private JFXPasswordField passwordInputRegister;
    @FXML
    private JFXPasswordField confirmPasswordInputRegister;
    @FXML
    private JFXTextField auxPasswordInputRegister;
    @FXML
    private JFXTextField auxConfirmPasswordInputRegister;
    @FXML
    private ImageView imageEyePassword;
    @FXML
    private ImageView imageEyeConfirmPassword;
    @FXML
    private Button togglePasswordButton;
    @FXML
    private Button toggleConfirmPasswordButton;

    String name;
    String email;
    String phone;
    String direction;
    String password;
    String confirmPassword;

    ArrayList<JFXTextField> textFields = new ArrayList<>();
    ArrayList<JFXPasswordField> passwordFields = new ArrayList<>();
    @FXML
    private void initialize() {
        textFields.add(nameInputRegister);
        textFields.add(emailInputRegister);
        textFields.add(phoneInputRegister);
        textFields.add(directionInputRegister);
        textFields.add(auxPasswordInputRegister);
        textFields.add(auxConfirmPasswordInputRegister);
        passwordFields.add(passwordInputRegister);
        passwordFields.add(confirmPasswordInputRegister);

    }

    @FXML
    protected void seePassword(){
        if(passwordInputRegister.getText().equals("")) return;
        seePassword.seePassword(passwordInputRegister, auxPasswordInputRegister, isEyeOpen, imageEyePassword, togglePasswordButton);
        if(isEyeOpen){
            isEyeOpen = false;
            return;
        }
        isEyeOpen = true;
    }

    @FXML
    protected void seeConfirmPassword(){
        if(confirmPasswordInputRegister.getText().equals("")) return;
        seePassword.seePassword(confirmPasswordInputRegister, auxConfirmPasswordInputRegister, isEyeConfirmOpen, imageEyeConfirmPassword, toggleConfirmPasswordButton);
        if(isEyeConfirmOpen){
            isEyeConfirmOpen = false;
            return;
        }
        isEyeConfirmOpen = true;
    }

    @FXML
    protected void onMoveToLogin() {
        try {
            Stage previousStage = (Stage) moveToLoginButton.getScene().getWindow();
            previousStage.close();
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/org/example/bibliomanager/login/login.fxml"));
            Parent root = fxmlLoader.load();
            Stage stage = new Stage();
            stage.setTitle("Inicio de Sesión");
            stage.setResizable(false);
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    protected void onRegister() {

        boolean areInputsFilled = getValuesFromInputs();
        if (!areInputsFilled) {
            handleErrors.handleErrors("inputs-not-filled",textFields,passwordFields,registerContainer);
            return;
        }
        Matcher emailMatcher = emailPattern.matcher(email);
        Matcher phoneMatcher = phonePattern.matcher(phone);
        if (!emailMatcher.matches()) {
            handleErrors.handleErrors("email-format",textFields,passwordFields,registerContainer);
            return;
        }
        if (!phoneMatcher.matches()) {
            handleErrors.handleErrors("phone-format",textFields,passwordFields,registerContainer);
            return;
        }
        if(!password.equals(confirmPassword)){
            handleErrors.handleErrors("password-is-not-same",textFields,passwordFields,registerContainer);
            return;
        }

        String isRegistered = authRepository.register(email, password,name,phone,direction);
        if (isRegistered == "succesfully") {
            try {
                Stage previousStage = (Stage) registerContainer.getScene().getWindow();
                previousStage.close();
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/org/example/bibliomanager/login/login.fxml"));
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
        handleErrors.handleErrors(isRegistered,textFields,passwordFields,registerContainer);

    }

    private boolean getValuesFromInputs(){
        name = nameInputRegister.getText();
        email = emailInputRegister.getText();
        phone = phoneInputRegister.getText();
        direction = directionInputRegister.getText();
        if(isEyeOpen){
            password = passwordInputRegister.getText();
        }else{
            password = auxPasswordInputRegister.getText();
        }
        if(isEyeConfirmOpen){
            confirmPassword = confirmPasswordInputRegister.getText();
        }else{
            confirmPassword = auxConfirmPasswordInputRegister.getText();
        }
        boolean areInputsFilled = handleInputs(name, email, phone, direction, password, confirmPassword);
        return areInputsFilled;
    }

    private boolean handleInputs(String name, String email, String phone, String direction, String password, String confirmPassword ) {
        if(!Objects.equals(name, "") && !Objects.equals(email, "" ) && !Objects.equals(phone, "" ) && !Objects.equals(direction, "") && !Objects.equals(password, "") && !Objects.equals(confirmPassword, "")){
            return true;
        }
        return false;
    }
}
