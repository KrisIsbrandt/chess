package com.example.chess.piece;

import com.example.chess.Alliance;
import com.example.chess.board.Board;
import com.example.chess.board.BoardUtils;
import com.example.chess.board.Move;
import com.example.chess.board.Tile;
import com.google.common.collect.ImmutableList;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static com.example.chess.board.BoardUtils.*;
import static com.example.chess.board.Move.*;

public class King extends Piece {

    private static final int[] CANDIDATE_MOVE_COORDINATES = {1, 7, 8, 9, -1, -7, -8, -9};

    public King(final int piecePosition, final Alliance pieceAlliance) {
        super(piecePosition, pieceAlliance, PieceType.KING, true);
    }

    public King(final int piecePosition, final Alliance pieceAlliance, final boolean isFirstMove) {
        super(piecePosition, pieceAlliance, PieceType.KING, isFirstMove);
    }

    @Override
    public King movePiece(final Move move) {
        return new King(move.getDestinationCoordinate(), move.getMovedPiece().getPieceAlliance());
    }


    @Override
    public Collection<Move> calculateLegalMoves(final Board board) {

        final List<Move> legalMoves = new ArrayList<>();

        for (final int currentCandidateOffset : CANDIDATE_MOVE_COORDINATES) {
            final int candidateDestinationCoordinate = this.piecePosition + currentCandidateOffset;
            if (isValidCoordinate(candidateDestinationCoordinate)) {
                if (isFirstColumnExclusion(this.piecePosition, currentCandidateOffset) ||
                    isEighthColumnExclusion(this.piecePosition, currentCandidateOffset)) {
                    //If exclusion applies, go back to the loop
                    continue;
                }

                final Tile candidateDestinationTile = board.getTile(candidateDestinationCoordinate);
                if (!candidateDestinationTile.isTileOccupied()) {
                    legalMoves.add(new MajorMove(board, this, candidateDestinationCoordinate));
                } else {
                    //If occupied tile, check if it is occupied by enemy piece
                    final Piece pieceAtDestination = candidateDestinationTile.getPiece();
                    final Alliance pieceAlliance = pieceAtDestination.getPieceAlliance();
                    if (this.pieceAlliance != pieceAlliance) {
                        legalMoves.add(new MajorAttackMove(board, this, candidateDestinationCoordinate, pieceAtDestination));
                    }
                }
            }
        }
        return ImmutableList.copyOf(legalMoves);
    }

    @Override
    public String toString() {
        return PieceType.KING.toString();
    }

    private static boolean isFirstColumnExclusion(final int currentPosition, final int candidateOffset) {
        return BoardUtils.FIRST_COLUMN[currentPosition] &&
                (candidateOffset == -1 || candidateOffset == -9 || candidateOffset == 7);
    }

    private static boolean isEighthColumnExclusion(final int currentPosition, final int candidateOffset) {
        return BoardUtils.EIGHTH_COLUMN[currentPosition] &&
                (candidateOffset == 1 || candidateOffset == 9 || candidateOffset == -7);
    }
}
