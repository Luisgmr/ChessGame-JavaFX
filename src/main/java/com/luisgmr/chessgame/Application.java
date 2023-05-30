package com.luisgmr.chessgame;

import com.luisgmr.chessgame.controller.BoardController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Application extends javafx.application.Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Application.class.getResource("board.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1280, 960);
        stage.setTitle("Xadrez");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
        new BoardController();
    }
}