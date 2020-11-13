package com.example.chess.piece;

import com.example.chess.Alliance;
import com.example.chess.board.Board;
import com.example.chess.board.Move;

import java.util.Collection;

public abstract class Piece {

    protected final int piecePosition;
    protected final Alliance pieceAlliance;

    Piece(final int piecePosition, final Alliance pieceAlliance) {
        this.piecePosition = piecePosition;
        this.pieceAlliance = pieceAlliance;
    }

    public abstract Collection<Move> calculateLegalMoves(final Board board);

    public Alliance getAlliance() {
        return this.pieceAlliance;
    }
}
