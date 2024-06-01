package org.example.bibliomanager;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;


import java.io.IOException;

public class App extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        //FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("/org/example/bibliomanager/bookPage/bookPage.fxml"));
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("/org/example/bibliomanager/mainPage/mainPage.fxml"));
        //FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("login/login.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Inicio de Sesión");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.sizeToScene();
        stage.show();

    }

    public static void main(String[] args) {
        launch();
    }
}