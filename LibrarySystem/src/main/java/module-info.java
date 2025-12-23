module com.mycompany.library {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.media;
    opens com.mycompany.library to javafx.fxml;
    opens com.mycompany.library.model to javafx.base;  
    exports com.mycompany.library;
    exports com.mycompany.library.model;
    
    exports com.mycompany.library.patterns.decorator;
    exports com.mycompany.library.patterns.strategy;
}
