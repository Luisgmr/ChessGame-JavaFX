package com.luisgmr.chessgame.controller;

import com.luisgmr.chessgame.boardgame.Position;
import com.luisgmr.chessgame.chess.ChessMatch;
import com.luisgmr.chessgame.chess.ChessPiece;
import com.luisgmr.chessgame.chess.Color;
import com.luisgmr.chessgame.chess.pieces.Queen;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
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

        ChessMatch chessMatch = new ChessMatch();
        List<ChessPiece> captured = new ArrayList<>();

        sendPiecesOnBoard(chessMatch.getPieces());

        board.setOnMouseClicked(e -> {

            double cellWidth = board.getWidth() / 8;
            double cellHeight = board.getHeight() / 8;
            int clickedRow = (int) (e.getY() / cellHeight);
            int clickedCol = (int) (e.getX() / cellWidth);

            Position position = new Position(clickedRow, clickedCol);
            if (chessMatch.getBoard().isPiece(position)) {
                ChessPiece piece = (ChessPiece) chessMatch.getBoard().piece(position);
                clearDisplay();
                clearDisplay();
                showPossibleMoves(piece);
            }
        });

    }

    private void sendCapturingDisplay(Position position) {
        String imagePath = "src/main/resources/com/luisgmr/chessgame/images/acao_captura.png";
        File imageFile = new File(imagePath);
        String imageUrl = imageFile.toURI().toString();
        ImageView image = new ImageView(imageUrl);
        image.setFitHeight(100);
        image.setFitWidth(100);
        image.setStyle("-fx-opacity: 50%;");
        board.add(image, position.getColumn(), position.getRow());
    }

    private void sendMovingDisplay(Position position) {
        String imagePath = "src/main/resources/com/luisgmr/chessgame/images/acao_mover.png";
        File imageFile = new File(imagePath);
        String imageUrl = imageFile.toURI().toString();
        ImageView image = new ImageView(imageUrl);
        image.setFitHeight(100);
        image.setFitWidth(100);
        image.setStyle("-fx-opacity: 50%;");
        board.add(image, position.getRow(), position.getColumn());
    }

    private void clearDisplay() {
        for (int i = 0; i < board.getChildren().size(); i++) {
            Node child = board.getChildren().get(i);
            if (child instanceof ImageView) {
                if (child.getStyle().contains("opacity: 50%")) {
                    board.getChildren().remove(child);
                }
            }
        }
    }

    public void sendPiecesOnBoard(ChessPiece[][] pieces) {
        for (int i = 0; i < pieces.length; i++) {
            for (int j = 0; j < pieces.length; j++) {
                if (pieces[j][j] != null) {
                    sendPieceOnBoard(pieces[j][i]);
                }
            }
        }
    }

    private void sendPieceOnBoard(ChessPiece piece) {
        Position position = piece.getChessPosition().toPosition();
        ImageView icon = piece.getIcon();
        board.add(piece.getIcon(), position.getColumn(), position.getRow());
    }

    public void showPossibleMoves(ChessPiece piece) {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (piece.possibleMoves()[j][i]) {
                    Position position = new Position(i, j);
                    System.out.println(position);
                    sendMovingDisplay(position);
                }
            }
        }
    }

}
