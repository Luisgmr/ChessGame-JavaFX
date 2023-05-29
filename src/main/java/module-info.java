module com.luisgmr.chessgame {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires net.synedra.validatorfx;

    opens com.luisgmr.chessgame to javafx.fxml;
    exports com.luisgmr.chessgame.applications;
    opens com.luisgmr.chessgame.applications to javafx.fxml;
    exports com.luisgmr.chessgame;
    exports com.luisgmr.chessgame.controller;
    opens com.luisgmr.chessgame.controller to javafx.fxml;
}