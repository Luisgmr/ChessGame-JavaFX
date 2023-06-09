package com.luisgmr.chessgame.chess;

import com.luisgmr.chessgame.applications.UserInterface;

public enum Color {

    BLACK,
    WHITE;

    @Override
    public String toString() {
        if (this == WHITE) {
            return "BRANCAS";
        } else {
            return "PRETAS";
        }
    }

    public String getFileString() {
        if (this == WHITE) {
            return "brancas_";
        } else {
            return "pretas_";
        }
    }

}
