package com.luisgmr.chessgame.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

public class BoardController implements Initializable {
    
    @FXML GridPane board;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        String imagePath = "src/main/resources/com/luisgmr/chessgame/images/tabuleiro.png";
        File imageFile = new File(imagePath);
        String imageUrl = imageFile.toURI().toString();
        Image backgroundImage = new Image(imageUrl);
        System.out.println(imageUrl);
        board.setStyle("-fx-background-image: url('" + backgroundImage.getUrl() + "');");
    }

}
