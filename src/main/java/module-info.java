module com.luisgmr.chessgame {
    requires javafx.controls;
    requires javafx.fxml;
            
        requires org.controlsfx.controls;
                requires net.synedra.validatorfx;
                    
    opens com.luisgmr.chessgame to javafx.fxml;
    exports com.luisgmr.chessgame;
}