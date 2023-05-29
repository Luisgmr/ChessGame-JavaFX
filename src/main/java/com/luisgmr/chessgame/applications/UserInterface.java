package com.luisgmr.chessgame.applications;

import com.luisgmr.chessgame.chess.ChessMatch;
import com.luisgmr.chessgame.chess.ChessPiece;
import com.luisgmr.chessgame.chess.ChessPosition;
import com.luisgmr.chessgame.chess.Color;

import java.util.*;
import java.util.stream.Collectors;

public class UserInterface {

    // https://stackoverflow.com/questions/5762491/how-to-print-color-in-console-using-system-out-println

    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_BLACK = "\u001B[30m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_CYAN = "\u001B[36m";
    public static final String ANSI_WHITE = "\u001B[37m";

    public static final String ANSI_BLACK_BACKGROUND = "\u001B[40m";
    public static final String ANSI_RED_BACKGROUND = "\u001B[41m";
    public static final String ANSI_GREEN_BACKGROUND = "\u001B[42m";
    public static final String ANSI_YELLOW_BACKGROUND = "\u001B[43m";
    public static final String ANSI_BLUE_BACKGROUND = "\u001B[44m";
    public static final String ANSI_PURPLE_BACKGROUND = "\u001B[45m";
    public static final String ANSI_CYAN_BACKGROUND = "\u001B[46m";
    public static final String ANSI_WHITE_BACKGROUND = "\u001B[47m";

    public static void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    public static ChessPosition readChessPosition(Scanner sc) {
        try {
            String s = sc.nextLine();
            char column = s.charAt(0);
            int row = Integer.parseInt(s.substring(1));
            return new ChessPosition(column, row);
        } catch (RuntimeException e) {
            throw new InputMismatchException("Erro lendo ChessPosition. Use somente valores de a1 até h8.");
        }
    }

    public static void printMatch(ChessMatch chessMatch, List<ChessPiece> captured) {
        printBoard(chessMatch.getPieces());
        System.out.println();
        printCapturedPieces(captured);
        System.out.println();
        System.out.println("Turno: " + chessMatch.getTurn());
        if (!chessMatch.isCheckMate()) {
            System.out.println("Esperando jogador: " + chessMatch.getCurrentPlayer() + ANSI_RESET);
            if (chessMatch.isCheck()) {
                System.out.println("Você está em xeque!");
            }
        } else {
            System.out.println("Xeque-mate!");
            System.out.println("Vencedor: " + chessMatch.getCurrentPlayer());
        }
    }

    public static void printBoard(ChessPiece[][] pieces) {
        System.out.println();
        System.out.println(ANSI_BLACK_BACKGROUND + "                     " + ANSI_RESET);
        for (int i = 0; i < pieces.length; i++) {
            System.out.print(ANSI_BLACK_BACKGROUND + (8 - i) + " " + ANSI_RESET + " ");
            for (int j = 0; j < pieces.length; j++) {
                printPiece(pieces[i][j], false);
                if (j == 7) {
                    System.out.print(ANSI_BLACK_BACKGROUND + "  " + ANSI_RESET);
                }
            }
            System.out.println();
        }
        System.out.println(ANSI_BLACK_BACKGROUND + "   a b c d e f g h   " + ANSI_RESET);
    }

    public static void printBoard(ChessPiece[][] pieces, boolean[][] possibleMoves) {
        System.out.println();
        System.out.println(ANSI_BLACK_BACKGROUND + "                     " + ANSI_RESET);
        for (int i = 0; i < pieces.length; i++) {
            System.out.print(ANSI_BLACK_BACKGROUND + (8 - i) + " " + ANSI_RESET + " ");
            for (int j = 0; j < pieces.length; j++) {
                printPiece(pieces[i][j], possibleMoves[i][j]);
                if (j == 7) {
                    System.out.print(ANSI_BLACK_BACKGROUND + "  " + ANSI_RESET);
                }
            }
            System.out.println();
        }
        System.out.println(ANSI_BLACK_BACKGROUND + "   a b c d e f g h   " + ANSI_RESET);
    }

    private static void printPiece(ChessPiece piece, boolean background) {
        if (background) {
            System.out.print(ANSI_RED_BACKGROUND);
        }
        if (piece == null) {
            System.out.print("-" + ANSI_RESET);
        } else {
            if (piece.getColor() == Color.WHITE) {
                System.out.print((background ? "" : ANSI_RESET) + piece + ANSI_RESET);
            } else {
                System.out.print((background ? "" : ANSI_GREEN) + piece + ANSI_RESET);
            }
        }
        System.out.print(" ");
    }

    private static void printCapturedPieces(List<ChessPiece> captured) {
        List<ChessPiece> white = captured.stream().filter(x -> x.getColor() == Color.WHITE).collect(Collectors.toList());
        List<ChessPiece> black = captured.stream().filter(x -> x.getColor() == Color.BLACK).collect(Collectors.toList());
        System.out.println("Peças capturadas:");
        System.out.print(Color.WHITE + ": ");
        System.out.println(Arrays.toString(white.toArray()) + ANSI_RESET);
        System.out.print(Color.BLACK + ": ");
        System.out.println(Arrays.toString(black.toArray()) + ANSI_RESET);
    }

}
