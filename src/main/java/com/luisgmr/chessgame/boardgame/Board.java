package com.luisgmr.chessgame.boardgame;

public class Board {

    private int rows;
    private int columns;
    private Piece[][] pieces;

    public Board(int rows, int columns) {
        if (rows < 1 || columns < 1) {
            throw new BoardException("Erro ao criar um tabuleiro, é necessário que haja pelo menos 1 linha e 1 coluna.");
        }
        this.rows = rows;
        this.columns = columns;
        pieces = new Piece[rows][columns];
    }

    public int getRows() {
        return rows;
    }

    public int getColumns() {
        return columns;
    }

    public Piece piece(int row, int column) {
        if (!positionExists(row, column)) {
            throw new BoardException("Essa posição (" + row + ", " + column + ") não existe no tabuleiro.");
        }
        return pieces[row][column];
    }

    public Piece piece(Position position) {
        if (!positionExists(position)) {
            throw new BoardException("Essa posição (" + position + ") não existe no tabuleiro.");
        }
        return pieces[position.getRow()][position.getColumn()];
    }

    public void placePiece(Piece piece, Position position) {
        if (isPiece(position)) {
            throw new BoardException("Já existe uma peça nessa posição (" + position + ").");
        }
        pieces[position.getRow()][position.getColumn()] = piece;
        piece.position = position;
    }

    public Piece removePiece(Position position) {
        if (!positionExists(position)) {
            throw new BoardException("Essa posição (" + position + ") não existe no tabuleiro.");
        }
        if (piece(position) == null) {
            return null;
        }
        Piece p = piece(position);
        p.position = null;
        pieces[position.getRow()][position.getColumn()] = null;
        return p;
    }

    private boolean positionExists(int row, int column) {
        return row >= 0 && row < rows && column >= 0 && column < columns;
    }

    public boolean positionExists(Position position) {
        return positionExists(position.getRow(), position.getColumn());
    }

    public boolean isPiece(Position position) {
        if (!positionExists(position)) {
            throw new BoardException("Essa posição (" + position + ") não existe no tabuleiro.");
        }
        return piece(position) != null;
    }

}
