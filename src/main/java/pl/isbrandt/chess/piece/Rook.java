package pl.isbrandt.chess.piece;

import pl.isbrandt.chess.Alliance;
import pl.isbrandt.chess.board.Board;
import pl.isbrandt.chess.board.Move;
import pl.isbrandt.chess.board.Tile;
import com.google.common.collect.ImmutableList;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static pl.isbrandt.chess.board.BoardUtils.*;
import static pl.isbrandt.chess.board.Move.*;

public class Rook extends Piece {

    private final static int[] CANDIDATE_MOVE_VECTOR_COORDINATES = {-8, -1, 1, 8};

    public Rook(final int piecePosition, final Alliance pieceAlliance) {
        super(piecePosition, pieceAlliance, PieceType.ROOK, true);
    }

    public Rook(final int piecePosition, final Alliance pieceAlliance, final boolean isFirstMove) {
        super(piecePosition, pieceAlliance, PieceType.ROOK, isFirstMove);
    }

    @Override
    public Rook movePiece(final Move move) {
        return new Rook(move.getDestinationCoordinate(), move.getMovedPiece().getPieceAlliance());
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
                            legalMoves.add(new MajorAttackMove(board, this, candidateDestinationCoordinate, pieceAtDestination));
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
        return PieceType.ROOK.toString();
    }

    private static boolean isFirstColumnExclusion(final int currentPosition, final int candidateOffset) {
        return FIRST_COLUMN[currentPosition] && candidateOffset == 1;
    }

    private static boolean isEighthColumnExclusion(final int currentPosition, final int candidateOffset) {
        return EIGHTH_COLUMN[currentPosition] && candidateOffset == -1;
    }
}
