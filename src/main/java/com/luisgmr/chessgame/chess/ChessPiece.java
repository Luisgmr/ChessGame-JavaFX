package com.luisgmr.chessgame.chess;

import com.luisgmr.chessgame.boardgame.Board;
import com.luisgmr.chessgame.boardgame.Piece;
import com.luisgmr.chessgame.boardgame.Position;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.File;

public abstract class ChessPiece extends Piece {

    private Color color;
    private int moveCount;

    private ImageView icon;

    public ChessPiece(Board board, Color color) {
        super(board);
        this.color = color;
        String imagePath = "src/main/resources/com/luisgmr/chessgame/images/" + fileName() + ".png";
        File imageFile = new File(imagePath);
        String imageUrl = imageFile.toURI().toString();
        ImageView view = new ImageView(imageUrl);
        view.setFitWidth(100);
        view.setFitHeight(100);
        icon = view;
    }

    public Color getColor() {
        return color;
    }

    public void increaseMoveCount() {
        moveCount++;
    }

    public void decreaseMoveCount() {
        moveCount--;
    }

    public int getMoveCount() {
        return moveCount;
    }

    public ChessPosition getChessPosition() {
        return ChessPosition.fromPosition(position);
    }

    protected boolean isThereOpponentPiece(Position position) {
        ChessPiece p = (ChessPiece) getBoard().piece(position);
        return p != null && p.getColor() != color;
    }

    public ImageView getIcon() {
        return icon;
    }
}
