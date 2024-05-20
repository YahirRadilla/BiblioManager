package org.example.bibliomanager.helpers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class SeePassword {

    public void seePassword(JFXPasswordField passwordInput, JFXTextField passwordAuxInput, boolean isEyeOpen, ImageView eyeImage, Button togglePasswordButton){

        if(passwordInput.getText().equals("")) return;

        if(isEyeOpen){
            eyeImage.setImage(new Image(getClass().getResourceAsStream("/org/example/bibliomanager/images/ojo_raya.png")));
            passwordAuxInput.setText(passwordInput.getText());
            passwordAuxInput.setVisible(true);
            passwordAuxInput.setManaged(true);
            passwordInput.setVisible(false);
            passwordInput.setManaged(false);
            togglePasswordButton.toFront();

            return;
        }
        eyeImage.setImage(new Image(getClass().getResourceAsStream("/org/example/bibliomanager/images/ojo.png")));
        passwordInput.setText(passwordAuxInput.getText());
        passwordInput.setVisible(true);
        passwordInput.setManaged(true);
        passwordAuxInput.setVisible(false);
        passwordAuxInput.setManaged(false);
        togglePasswordButton.toFront();


    }
}
