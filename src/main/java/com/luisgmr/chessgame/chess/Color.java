package com.luisgmr.chessgame.chess;

import com.luisgmr.chessgame.applications.UserInterface;

public enum Color {

    BLACK,
    WHITE;

    @Override
    public String toString() {
        if (this == WHITE) {
            return UserInterface.ANSI_RESET + "BRANCAS";
        } else {
            return UserInterface.ANSI_GREEN + "PRETAS";
        }
    }
}
