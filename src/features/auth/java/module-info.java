module org.example.bibliomanager {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires java.desktop;
    requires com.jfoenix;
    requires MaterialFX;

    exports org.example.bibliomanager.model.entities to javafx.fxml;

    exports org.example.bibliomanager.controller.viewController.login to javafx.fxml;
    opens org.example.bibliomanager.controller.viewController.login to javafx.fxml;

    exports org.example.bibliomanager.controller.viewController.register to javafx.fxml;
    opens org.example.bibliomanager.controller.viewController.register to javafx.fxml;

    exports org.example.bibliomanager.controller.viewController to javafx.fxml;
    opens org.example.bibliomanager.controller.viewController to javafx.fxml;


    opens org.example.bibliomanager to javafx.fxml;
    exports org.example.bibliomanager;
}