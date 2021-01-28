package com.example.chess.player;

import com.example.chess.Alliance;
import com.example.chess.board.Board;
import com.example.chess.board.BoardUtils;
import com.example.chess.board.Move;
import com.example.chess.piece.King;
import com.example.chess.piece.Piece;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Iterables;
import com.google.common.collect.Streams;

import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static com.example.chess.board.BoardUtils.*;

/**
 * Player is used to determine king status and player's legal move.
 * Player has two subclasses for each alliance to implement castle moves.
 *
 * Player makes a move that creates a transition board, which is evaluated based on game principles.
 */
public abstract class Player {

    protected final Board board;
    protected final King playerKing;
    protected final Collection<Move> legalMoves;
    private final boolean isInCheck;

    Player(final Board board,
           final Collection<Move> legalMoves,
           final Collection<Move> opponentMoves) {
        this.board = board;
        this.playerKing = establishKing();
        //if at least one opponent's move has a destinationCoordinate of player's king position, then player is in check
        this.isInCheck = !Player.calculateAttacksOnTile(this.playerKing.getPiecePosition(), opponentMoves).isEmpty();
        legalMoves.addAll(calculateKingCastles(legalMoves, opponentMoves));
        this.legalMoves = Collections.unmodifiableCollection(legalMoves);
    }

    public abstract Collection<Piece> getActivePieces();

    public abstract Alliance getAlliance();

    public abstract Player getOpponent();

    public abstract Collection<Move> calculateKingCastles(Collection<Move> playerLegalMoves, Collection<Move> opponentsLegalMoves);

    public boolean isKingSideCastleCapable() {
        return this.playerKing.isKingSideCastleCapable();
    }

    public boolean isQueenSideCastleCapable() {
        return this.playerKing.isQueenSideCastleCapable();
    }

    public boolean isMoveLegal(final Move move) {
        return this.legalMoves.contains(move);
    }

    public boolean isInCheck() {
        return this.isInCheck;
    }

    public boolean isInCheckMate() {
        return this.isInCheck && !hasEscapeMoves();
    }

    public boolean isInStaleMate() {
        return !this.isInCheck && !hasEscapeMoves();
    }

    public boolean isInDraw() {
        //TODO threefold repetition rule and 50-move rule
        return (!this.isInCheck && !isInCheckMate()) && (isInStaleMate() || !isPossibleToCheckmate());
    }

    public boolean isCastled() {
        return this.playerKing.isCastled();
    }

    public MoveTransition makeMove(final Move move) {
        if (!isMoveLegal(move)) {
            return new MoveTransition(this.board, move, MoveStatus.ILLEGAL_MOVE);
        }
        final Board transitionBoard = move.execute();
        return transitionBoard.currentPlayer().getOpponent().isInCheck ?
                new MoveTransition(this.board, move, MoveStatus.LEAVES_PLAYER_IN_CHECK) :
                new MoveTransition(transitionBoard, move, MoveStatus.DONE);
    }

    public King getPlayerKing() {
        return this.playerKing;
    }

    public Collection<Move> getLegalMoves() {
        return this.legalMoves;
    }

    @Override
    public String toString() {
        return this.getAlliance().toString();
    }

    protected boolean hasEscapeMoves() {
        for(final Move move : this.legalMoves) {
            final MoveTransition transition = makeMove(move);
            if (transition.getMoveStatus().isDone()) {
                return true;
            }
        }
        return false;
    }

    protected static Collection<Move> calculateAttacksOnTile(int piecePosition, Collection<Move> moves) {
        final List<Move> attackMoves = new ArrayList<>();
        for (final Move move : moves) {
            if (piecePosition == move.getDestinationCoordinate()) {
                attackMoves.add(move);
            }
        }
        return ImmutableList.copyOf(attackMoves);
    }

    protected boolean isPossibleToCheckmate() {
        /* Not possible to mate with following combinations:
        -king versus king
        -king and bishop versus king
        -king and knight versus king
        -king and bishop versus king and bishop with the bishops on the same color.
        */
        List<Piece> pieces = Streams.stream(this.board.getAllPieces()).collect(Collectors.toList());
        if (pieces.size() > 4) {
            return true;
        } else if (pieces.size() == 4) {
            Piece blackBishop = null, whiteBishop = null;
            for (Piece piece : pieces) {
                if (piece.getPieceType().equals(Piece.PieceType.BISHOP)) {
                    if (piece.getPieceAlliance().isWhite()) {
                        whiteBishop = piece;
                    } else {
                        blackBishop = piece;
                    }
                }
            }
            return !(whiteBishop != null && blackBishop != null &&
                    (isWhiteTile(whiteBishop.getPiecePosition()) == isWhiteTile(blackBishop.getPiecePosition())));
        } else if (pieces.size() == 3) {
            for (Piece piece : pieces) {
                if (piece.getPieceType().equals(Piece.PieceType.KING)) {
                    continue;
                } else {
                    return !piece.getPieceType().equals(Piece.PieceType.BISHOP) &&
                           !piece.getPieceType().equals(Piece.PieceType.KNIGHT);
                }
            }
        }
        return false;
    }

    private King establishKing() {
        for (final Piece piece : getActivePieces()) {
            if (piece.getPieceType() == Piece.PieceType.KING) {
                return (King) piece;
            }
        }
        throw new RuntimeException("Not a valid board");
    }
}
