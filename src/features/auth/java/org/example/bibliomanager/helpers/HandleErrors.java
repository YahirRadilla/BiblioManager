package org.example.bibliomanager.helpers;

import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXSnackbar;
import com.jfoenix.controls.JFXSnackbarLayout;
import com.jfoenix.controls.JFXTextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.util.Duration;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class HandleErrors {
    Timer timer;
    Color normalColor = new Color(0.30196, 0.30196, 0.30196,1);
    Color errorColor = new Color(0.8588, 0.1882, 0.3373,1);


    public void showSnackbar(String message, Pane loginContainer, boolean error) {
        JFXSnackbar snackbar = new JFXSnackbar(loginContainer);
        snackbar.setPrefWidth(loginContainer.getWidth());
        if(error){
            snackbar.getStylesheets().add(getClass().getResource("/org/example/bibliomanager/shared/error-snackbar.css").toExternalForm());
        }else{
            snackbar.getStylesheets().add(getClass().getResource("/org/example/bibliomanager/shared/succesfull-snackbar.css").toExternalForm());
        }
        snackbar.enqueue(new JFXSnackbar.SnackbarEvent(new JFXSnackbarLayout(message), Duration.millis(2500)));
    }

    public void showInputErrors(Color color, ArrayList<JFXTextField> textFields, ArrayList<JFXPasswordField> passwordFields) {
        for(JFXTextField textField : textFields){
            textField.setUnFocusColor(color);
        }
        for(JFXPasswordField passwordField : passwordFields){
            passwordField.setUnFocusColor(color);
        }
    }


    public void showErrors(String message,AnchorPane loginContainer, ArrayList<JFXTextField> textFields, ArrayList<JFXPasswordField> passwordFields){
        showSnackbar(message, loginContainer, true);
        showInputErrors(errorColor, textFields, passwordFields);

        if (timer != null && timer.isRunning()) {
            timer.stop();
        }
        timer = new Timer(2500, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showInputErrors(normalColor, textFields, passwordFields);
            }
        });
        timer.start();
    }


    public void handleErrors(String error, ArrayList<JFXTextField> textFields, ArrayList<JFXPasswordField> passwordFields, AnchorPane loginContainer) {
        switch (error){
            case "not-authorized":
                showErrors("Credenciales Incorrectas", loginContainer, textFields, passwordFields);
                break;
            case "inputs-not-filled":
                showErrors("No se han rellenado todos los campos", loginContainer, textFields, passwordFields);
                break;
            case "email-duplicated":
                showErrors("Correo Electrónico en uso", loginContainer, textFields, passwordFields);
                break;
            case "phone-duplicated":
                showErrors("Teléfono en uso", loginContainer, textFields, passwordFields);
                break;
            case "password-is-not-same":
                showErrors("Contraseñas no coinciden", loginContainer, textFields, passwordFields);
                break;
            case "email-format":
                showErrors("Formato de correo electrónico incorrecto", loginContainer, textFields, passwordFields);
                break;
            case "phone-format":
                showErrors("Formato de teléfono incorrecto", loginContainer, textFields, passwordFields);
                break;
        }
    }
}
