package com.luisgmr.chessgame.controller;

import com.luisgmr.chessgame.boardgame.Piece;
import com.luisgmr.chessgame.boardgame.Position;
import com.luisgmr.chessgame.chess.ChessMatch;
import com.luisgmr.chessgame.chess.ChessPiece;
import com.luisgmr.chessgame.chess.ChessPosition;
import com.luisgmr.chessgame.chess.Color;
import com.luisgmr.chessgame.chess.pieces.Queen;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;

import java.io.File;
import java.net.URL;
import java.util.*;

public class BoardController implements Initializable {

    @FXML GridPane board;
    @FXML GridPane whiteCaptured;
    @FXML GridPane blackCaptured;
    @FXML Label currentColor;
    @FXML Label whiteLastMove;
    @FXML Label blackLastMove;

    boolean blackCapturedLayout[][] = new boolean[4][4];
    boolean whiteCapturedLayout[][] = new boolean[4][4];

    ChessMatch chessMatch;

    ChessPiece clickedPiece = null;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        String imagePath = "src/main/resources/com/luisgmr/chessgame/images/tabuleiro.png";
        File imageFile = new File(imagePath);
        String imageUrl = imageFile.toURI().toString();
        Image backgroundImage = new Image(imageUrl);
        board.setStyle("-fx-background-image: url('" + backgroundImage.getUrl() + "');");

        chessMatch = new ChessMatch();
        currentColor.setText("VEZ DAS " + chessMatch.getCurrentPlayer() + " JOGAREM");
        List<ChessPiece> captured = new ArrayList<>();

        sendPiecesOnBoard(chessMatch.getPieces());

        board.setOnMouseClicked(e -> {
            if (chessMatch.isCheckMate()) {
                return;
            }
            double cellWidth = board.getWidth() / 8;
            double cellHeight = board.getHeight() / 8;
            int clickedRow = (int) (e.getY() / cellHeight);
            int clickedCol = (int) (e.getX() / cellWidth);

            Position position = new Position(clickedRow, clickedCol);
            if (getObjectFromGridPane(board, clickedRow, clickedCol, "action") != null) {
                if (getObjectFromGridPane(board, clickedRow, clickedCol, "action").getStyle().contains("-fx-opacity")) {
                    // Moving display
                    ChessPosition sourcePosition = clickedPiece.getChessPosition();
                    if (getObjectFromGridPane(board, clickedRow, clickedCol, "action").getStyle().contains("-fx-opacity: 30%")) {
                        clearDisplay();
                        ChessPiece capturedPiece = chessMatch.performChessMove(sourcePosition, ChessPosition.fromPosition(position));
                        board.getChildren().remove(clickedPiece.getIcon());
                        board.add(clickedPiece.getIcon(), clickedCol, clickedRow);
                        if (chessMatch.enPassantCaptured != null) {
                            ImageView pieceNode = chessMatch.enPassantCaptured.getIcon();
                            pieceNode.setFitHeight(80);
                            pieceNode.setFitWidth(80);
                            addCapturedInterface(chessMatch.enPassantCaptured, pieceNode);
                            chessMatch.enPassantCaptured = null;
                        }
                        updateBoard();
                        if (clickedPiece.getColor() == Color.WHITE) {
                            whiteLastMove.setText(clickedPiece + " de " + sourcePosition + " em " + ChessPosition.fromPosition(position));
                        } else {
                            blackLastMove.setText(clickedPiece + " de " + sourcePosition + " em " + ChessPosition.fromPosition(position));
                        }
                        return;
                    } else if (getObjectFromGridPane(board, clickedRow, clickedCol, "action").getStyle().contains("-fx-opacity: 25%")) {
                        clearDisplay();
                        ChessPiece capturedPiece = chessMatch.performChessMove(sourcePosition, ChessPosition.fromPosition(position));
                        ImageView pieceNode = (ImageView) getObjectFromGridPane(board, position.getRow(), position.getColumn(), "piece");
                        pieceNode.setFitWidth(80);
                        pieceNode.setFitHeight(80);
                        addCapturedInterface(capturedPiece, pieceNode);
                        Position capturedPostion = new Position(clickedRow, clickedCol);
                        board.getChildren().remove(clickedPiece.getIcon());
                        board.add(clickedPiece.getIcon(), clickedCol, clickedRow);
                        updateBoard();
                        if (clickedPiece.getColor() == Color.WHITE) {
                            whiteLastMove.setText(clickedPiece + " de " + sourcePosition + " capturando " + capturedPiece.toString().toLowerCase() + " de " + ChessPosition.fromPosition(position));
                        } else {
                            blackLastMove.setText(clickedPiece + " de " + sourcePosition + " capturando " + capturedPiece.toString().toLowerCase() + " de " + ChessPosition.fromPosition(position));
                        }
                        return;
                    }
                }
            }
            if (chessMatch.getBoard().isPiece(position)) {
                ChessPiece piece = (ChessPiece) chessMatch.getBoard().piece(position);
                if (chessMatch.opponent(chessMatch.getCurrentPlayer()) != piece.getColor()) {
                    clearDisplay();
                    showPossibleMoves(piece);
                    clickedPiece = piece;
                    return;
                } else {
                    clearDisplay();
                }
            } else {
                clearDisplay();
            }
        });
    }

    private Node getObjectFromGridPane(GridPane gridPane, int row, int col, String description) {
        for (int i = 0; i < gridPane.getChildren().size(); i++) {
            Node cell = gridPane.getChildren().get(i);
            int cellRow = GridPane.getRowIndex(cell);
            int cellCol = GridPane.getColumnIndex(cell);

            if (cellRow == row && cellCol == col && cell.getAccessibleRoleDescription().equals(description)) {
                return cell;
            }
        }
        return null;
    }

    private void sendCapturingDisplay(Position position) {
        String imagePath = "src/main/resources/com/luisgmr/chessgame/images/acao_captura.png";
        File imageFile = new File(imagePath);
        String imageUrl = imageFile.toURI().toString();
        ImageView image = new ImageView(imageUrl);
        image.setFitHeight(85);
        image.setFitWidth(85);
        image.setStyle("-fx-opacity: 25%;");
        image.setAccessibleRoleDescription("action");
        board.add(image, position.getColumn(), position.getRow());
    }

    private void sendMovingDisplay(Position position) {
        String imagePath = "src/main/resources/com/luisgmr/chessgame/images/acao_mover.png";
        File imageFile = new File(imagePath);
        String imageUrl = imageFile.toURI().toString();
        ImageView image = new ImageView(imageUrl);
        image.setFitHeight(30);
        image.setFitWidth(30);
        image.setStyle("-fx-opacity: 30%;");
        image.setAccessibleRoleDescription("action");
        board.add(image, position.getColumn(), position.getRow());
    }

    private void clearDisplay() {
        // Executar a limpa 3 vezes (em algumas limpas, displays não são removidos corretamente)
        for (int j = 0; j < 3; j++) {
            for (int i = 0; i < board.getChildren().size(); i++) {
                Node child = board.getChildren().get(i);
                if (child instanceof ImageView) {
                    if (child.getStyle().contains("-fx-opacity")) {
                        board.getChildren().remove(child);
                    }
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

    public void sendPiecesOnBoard(List<Piece> pieces) {
        for (ChessPiece piece : pieces.stream().map(x -> (ChessPiece)x).toList()) {
            sendPieceOnBoard(piece);
        }
    }

    public void updateBoard() {
        board.getChildren().clear();
        sendPiecesOnBoard(chessMatch.getPiecesOnTheBoard());
        currentColor.setText("VEZ DAS " + chessMatch.getCurrentPlayer() + " JOGAREM");
    }

    private void sendPieceOnBoard(ChessPiece piece) {
        Position position = piece.getChessPosition().toPosition();
        ImageView icon = piece.getIcon();
        icon.setAccessibleRoleDescription("piece");
        board.add(piece.getIcon(), position.getColumn(), position.getRow());
    }

    public void showPossibleMoves(ChessPiece piece) {
        if (chessMatch.getCurrentPlayer() == piece.getColor()) {
            for (int row = 0; row < chessMatch.getBoard().getColumns(); row++) {
                for (int col = 0; col < chessMatch.getBoard().getRows(); col++) {
                    if (piece.possibleMoves()[row][col]) {
                        Position position = new Position(row, col);
                        if (chessMatch.getBoard().isPiece(position)) {
                            sendCapturingDisplay(position);
                        } else {
                            sendMovingDisplay(position);
                        }
                    }
                }
            }
        }
    }

    public boolean containsImageView(GridPane gridPane, int rowIndex, int columnIndex) {
        boolean containsImageView = false;

        for (Node node : gridPane.getChildren()) {
            Integer nodeRowIndex = GridPane.getRowIndex(node);
            Integer nodeColumnIndex = GridPane.getColumnIndex(node);

            if (nodeRowIndex != null && nodeRowIndex == rowIndex && nodeColumnIndex != null && nodeColumnIndex == columnIndex && node instanceof ImageView) {
                containsImageView = true;
                break;
            }
        }
        return containsImageView;
    }

    public void addCapturedInterface(ChessPiece capturedPiece, Node pieceNode) {
        if (capturedPiece.getColor() == Color.WHITE) {
            boolean pieceAdded = false;
            for (int column = 0; column < 4; column++) {
                for (int row = 0; row < 4; row++) {
                    if (!whiteCapturedLayout[column][row]) {
                        whiteCaptured.add(pieceNode, row, column);
                        whiteCapturedLayout[column][row] = true;
                        pieceAdded = true;
                        break;
                    }
                }
                if (pieceAdded) {
                    break;
                }
            }
        } else if (capturedPiece.getColor() == Color.BLACK) {
            boolean pieceAdded = false;
            for (int column = 0; column < 4; column++) {
                for (int row = 0; row < 4; row++) {
                    if (!blackCapturedLayout[column][row]) {
                        blackCaptured.add(pieceNode, row, column);
                        blackCapturedLayout[column][row] = true;
                        pieceAdded = true;
                        break;
                    }
                }
                if (pieceAdded) {
                    break;
                }
            }
        }
    }

}
