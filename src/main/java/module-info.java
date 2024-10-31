module com.berly.unsa.cryptography {
    requires javafx.controls;
    requires javafx.fxml;
    requires transitive javafx.graphics;
    
    opens com.berly.unsa.cryptography.controller to javafx.fxml;
    opens com.berly.unsa.cryptography.gui to javafx.fxml;
     
    exports com.berly.unsa.cryptography;
    exports com.berly.unsa.cryptography.gui;
}
