package pl.isbrandt.chess.piece;

import pl.isbrandt.chess.Alliance;
import pl.isbrandt.chess.board.Board;
import pl.isbrandt.chess.board.Move;

import java.util.Collection;

/**
 * Piece provides base for implementation for specific piece types.
 * Each piece defines its legal moves that can be played by a player.
 */
public abstract class Piece {

    protected final int piecePosition;
    protected final Alliance pieceAlliance;
    protected boolean isFirstMove;
    protected final PieceType pieceType;
    private final int cashedHashCode;

    Piece(final int piecePosition,
          final Alliance pieceAlliance,
          final PieceType pieceType,
          final boolean isFirstMove) {
        this.piecePosition = piecePosition;
        this.pieceAlliance = pieceAlliance;
        this.pieceType = pieceType;
        this.isFirstMove = isFirstMove;
        this.cashedHashCode = commuteHashCode();
    }

    private int commuteHashCode() {
        int result = pieceType.hashCode();
        result = 31 * result + pieceAlliance.hashCode();
        result = 31 * result + piecePosition;
        result = 31 * result + (isFirstMove ? 1 : 0);
        return result;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }

        if (!(o instanceof Piece)) {
            return false;
        }
        final Piece otherPiece = (Piece) o;
        return this.piecePosition == otherPiece.getPiecePosition() &&
                this.pieceType == otherPiece.getPieceType() &&
                this.getPieceAlliance() == otherPiece.getPieceAlliance() &&
                this.isFirstMove == otherPiece.isFirstMove;
    }

    @Override
    public int hashCode() {
        return this.cashedHashCode;
    }

    public abstract Collection<Move> calculateLegalMoves(final Board board);

    public abstract Piece movePiece(Move move);

    public boolean isFirstMove() {
        return this.isFirstMove;
    }

    public Alliance getPieceAlliance() {
        return this.pieceAlliance;
    }

    public int getPiecePosition() {
        return this.piecePosition;
    }

    public PieceType getPieceType() {
        return this.pieceType;
    }

    public void setFirstMove(boolean firstMove) {
        this.isFirstMove = firstMove;
    }

    public enum PieceType {
        PAWN("P"),
        KNIGHT("N"),
        BISHOP("B"),
        ROOK("R"),
        QUEEN("Q"),
        KING("K");

        private String pieceName;

        PieceType(final String pieceName) {
            this.pieceName = pieceName;
        }

        @Override
        public String toString() {
            return this.pieceName;
        }
    }
}
