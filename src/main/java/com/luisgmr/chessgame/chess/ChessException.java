package com.luisgmr.chessgame.chess;

import com.luisgmr.chessgame.boardgame.BoardException;
import javafx.scene.control.Alert;

public class ChessException extends BoardException {

    public ChessException(String message) {
        super(message);
    }

}
