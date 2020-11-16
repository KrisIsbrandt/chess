package com.example.chess.piece;

import com.example.chess.Alliance;
import com.example.chess.board.Board;
import com.example.chess.board.Move;
import com.example.chess.board.Tile;
import com.google.common.collect.ImmutableList;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static com.example.chess.board.BoardUtils.*;
import static com.example.chess.board.BoardUtils.isValidCoordinate;
import static com.example.chess.board.Move.*;

public class Bishop extends Piece {

    private final static int[] CANDIDATE_MOVE_VECTOR_COORDINATES = {-9, -7, 7, 9};

    public Bishop(final int piecePosition, final Alliance pieceAlliance) {
        super(piecePosition, pieceAlliance);
    }

    @Override
    public Collection<Move> calculateLegalMoves(final Board board) {

        final List<Move> legalMoves = new ArrayList<>();

        for(final int currentCandidateOffset : CANDIDATE_MOVE_VECTOR_COORDINATES) {
            int candidateDestinationCoordinate = this.piecePosition;
            //loop through all tiles in a vector, break if tile occupied or out of board
            while (isValidCoordinate(candidateDestinationCoordinate)) {
                candidateDestinationCoordinate += currentCandidateOffset;

                if (isValidCoordinate(candidateDestinationCoordinate)) {
                    if (isFirstColumnExclusion(candidateDestinationCoordinate, currentCandidateOffset) ||
                        isEighthColumnExclusion(candidateDestinationCoordinate, currentCandidateOffset)) {
                        break;
                    }
                    final Tile candidateDestinationTile = board.getTile(candidateDestinationCoordinate);
                    if (!candidateDestinationTile.isTileOccupied()) {
                        legalMoves.add(new MajorMove(board, this, candidateDestinationCoordinate));
                    } else {
                        //If occupied tile, check if it is occupied by enemy piece
                        final Piece pieceAtDestination = candidateDestinationTile.getPiece();
                        final Alliance pieceAlliance = pieceAtDestination.getPieceAlliance();
                        if (this.pieceAlliance != pieceAlliance) {
                            legalMoves.add(new AttackMove(board, this, candidateDestinationCoordinate, pieceAtDestination));
                        }
                        //If occupied, then do not check further tiles on the vector, by breaking loop
                        break;
                    }
                }
            }
        }

        return ImmutableList.copyOf(legalMoves);
    }

    @Override
    public String toString() {
        return PieceType.BISHOP.toString();
    }

    private static boolean isFirstColumnExclusion(final int currentPosition, final int candidateOffset) {
        return FIRST_COLUMN[currentPosition] &&
                (candidateOffset == 7 || candidateOffset == -9);
    }

    private static boolean isEighthColumnExclusion(final int currentPosition, final int candidateOffset) {
        return EIGHTH_COLUMN[currentPosition] &&
                (candidateOffset == -7 || candidateOffset == 9);
    }
}
