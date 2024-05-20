module org.example.bibliomanager {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires java.desktop;
    requires com.jfoenix;

    exports org.example.bibliomanager.controller.viewControllers.login to javafx.fxml;
    opens org.example.bibliomanager.controller.viewControllers.login to javafx.fxml;

    exports org.example.bibliomanager.controller.viewControllers.register to javafx.fxml;
    opens org.example.bibliomanager.controller.viewControllers.register to javafx.fxml;


    opens org.example.bibliomanager to javafx.fxml;
    exports org.example.bibliomanager;
}