module ec.edu.espol.vehitrade {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.base;
    requires java.mail;

    opens ec.edu.espol.vehitrade to javafx.fxml;
    exports ec.edu.espol.vehitrade;
    
    opens ec.edu.espol.vehitrade.controllers to javafx.fxml;
    exports ec.edu.espol.vehitrade.controllers;
    
    opens ec.edu.espol.vehitrade.model to javafx.fxml;
    exports ec.edu.espol.vehitrade.model;
}
