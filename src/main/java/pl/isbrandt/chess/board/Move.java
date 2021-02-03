package pl.isbrandt.chess.board;

import pl.isbrandt.chess.piece.Pawn;
import pl.isbrandt.chess.piece.Piece;
import pl.isbrandt.chess.piece.Rook;

import java.util.ArrayList;
import java.util.List;

/**
 * Move class implements different type of moves. This is needed for PGN chess notation.
 *
 * move is executed by creating a new transition board with moved piece at different tile.
 */
public abstract class Move {

    protected final Board board;
    protected final Piece movedPiece;
    protected final int destinationCoordinate;
    protected final boolean isFirstMove;

    public static final Move NULL_MOVE = new NullMove();

    public static class MoveFactory {

        private MoveFactory() {
            throw new RuntimeException("Not instantiable");
        }

        public static Move createMove(final Board board,
                                      final int currentCoordinate,
                                      final int destinationCoordinate) {
            for (final Move move : board.getAllLegalMoves()) {
                if (move.getCurrentCoordinate() == currentCoordinate &&
                        move.getDestinationCoordinate() == destinationCoordinate) {
                    return move;
                }
            }
            return NULL_MOVE;
        }
    }

    private Move(final Board board,
                 final Piece movedPiece,
                 final int destinationCoordinate) {
        this.board = board;
        this.movedPiece = movedPiece;
        this.destinationCoordinate = destinationCoordinate;
        this.isFirstMove = movedPiece.isFirstMove();
    }

    private Move(final Board board,
                 final int destinationCoordinate) {
        this.board = board;
        this.destinationCoordinate = destinationCoordinate;
        this.movedPiece = null;
        this.isFirstMove = false;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Move)) return false;
        final Move move = (Move) o;
        return this.getCurrentCoordinate() == move.getCurrentCoordinate() &&
               this.getDestinationCoordinate() == move.getDestinationCoordinate() &&
               this.getMovedPiece().equals(((Move) o).getMovedPiece());
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + this.destinationCoordinate;
        result = prime * result + this.movedPiece.hashCode();
        result = prime * result + this.movedPiece.getPiecePosition();
        return result;
    }

    public Piece getMovedPiece() {
        return this.movedPiece;
    }

    public Board getBoard() {
        return board;
    }

    public int getDestinationCoordinate() {
        return this.destinationCoordinate;
    }

    public int getCurrentCoordinate() {
        return this.movedPiece.getPiecePosition();
    }

    public boolean isAttack() {
        return false;
    }

    public boolean isCastlingMove() {
        return false;
    }

    public Piece getAttackedPiece() {
        return null;
    }

    public Board execute() {
        final Board.Builder builder = new Board.Builder();
        for (final Piece piece : this.board.currentPlayer().getActivePieces()) {
            //copy not-moved piece of the current player on the new board
            if (!this.movedPiece.equals(piece)) {
                builder.setPiece(piece);
            }
        }
        //copy opponents pieces
        for (final Piece piece : this.board.currentPlayer().getOpponent().getActivePieces()) {
            builder.setPiece(piece);
        }
        //check if rook moved
        if (this.movedPiece.getPieceType().equals(Piece.PieceType.ROOK) && this.movedPiece.isFirstMove()) {
            //If true, then its queen side rook
            if (BoardUtils.FIRST_COLUMN[this.movedPiece.getPiecePosition()]) {
                this.board.currentPlayer().getPlayerKing().setQueenSideCastleCapable(false);
            } else if (BoardUtils.EIGHTH_COLUMN[this.movedPiece.getPiecePosition()]) {
                this.board.currentPlayer().getPlayerKing().setKingSideCastleCapable(false);
            }
        }
        this.movedPiece.setFirstMove(false);
        builder.setPiece(this.movedPiece.movePiece(this));
        builder.setMoveMaker(this.board.currentPlayer().getOpponent().getAlliance());
        return builder.build();
    }

    String ambiguitySolver() {
        //Check if the same piece type can end up on the same destination coordinate.
        //If yes then provide column letter or row number or both to determine unique move. Otherwise blank string.
        if (this.movedPiece.getPieceType().equals(Piece.PieceType.PAWN)) {
            return BoardUtils.getPositionAtCoordinate(this.movedPiece.getPiecePosition()).substring(0,1);
        }
        List<Move> otherMovesWithSameDestinationAndPieceType = new ArrayList<>();
        for (final Move move : this.board.currentPlayer().getLegalMoves()) {
            if (this.movedPiece.getPieceType().equals(move.getMovedPiece().getPieceType()) &&
                    move.getDestinationCoordinate() == this.destinationCoordinate && !this.equals(move)) {
                otherMovesWithSameDestinationAndPieceType.add(move);
            }
        }
        if (otherMovesWithSameDestinationAndPieceType.size() == 1) {
            if (BoardUtils.isSameColumn(otherMovesWithSameDestinationAndPieceType.get(0).getCurrentCoordinate(), this.getCurrentCoordinate())) {
                return BoardUtils.getPositionAtCoordinate(this.movedPiece.getPiecePosition()).substring(1, 2);
            } else {
                return BoardUtils.getPositionAtCoordinate(this.movedPiece.getPiecePosition()).substring(0, 1);
            }
        } else if (otherMovesWithSameDestinationAndPieceType.size() > 1) {
            return BoardUtils.getPositionAtCoordinate(this.movedPiece.getPiecePosition());
        }
        return "";
    }

    String getPieceColumn() {
        return BoardUtils.getPositionAtCoordinate(this.movedPiece.getPiecePosition()).substring(0,1);
    }

    String isCheckOrMateMove() {
        Board board = getBoard().currentPlayer().makeMove(this).getTransitionBoard();
        if (board.currentPlayer().isInCheckMate()) {
            return "#";
        } else if (board.currentPlayer().isInCheck()) {
            return "+";
        }
        return "";
    }

    //Major piece move
    public static final class MajorMove extends Move {

        public MajorMove(final Board board,
                         final Piece movedPiece,
                         final int destinationCoordinate) {
            super(board, movedPiece, destinationCoordinate);
        }

        @Override
        public boolean equals(final Object other) {
            return this == other || other instanceof MajorMove && super.equals(other);
        }

        @Override
        public String toString() {
            return new StringBuilder()
                    .append(this.movedPiece.getPieceType())
                    .append(ambiguitySolver())
                    .append(BoardUtils.getPositionAtCoordinate(this.destinationCoordinate))
                    .append(isCheckOrMateMove())
                    .toString();
        }
    }

    //Attack moves
    public static class AttackMove extends Move {

        final Piece attackedPiece;

        public AttackMove(final Board board,
                          final Piece movedPiece,
                          final int destinationCoordinate,
                          final Piece attackedPiece) {
            super(board, movedPiece, destinationCoordinate);
            this.attackedPiece = attackedPiece;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof AttackMove)) return false;
            final AttackMove attackMove = (AttackMove) o;
            return super.equals(attackMove) && this.getAttackedPiece() == attackMove.getAttackedPiece();
        }

        @Override
        public int hashCode() {
            return this.attackedPiece.hashCode() + super.hashCode();
        }

        @Override
        public boolean isAttack() {
            return true;
        }

        @Override
        public Piece getAttackedPiece() {
            return this.attackedPiece;
        }
    }

    public static class MajorAttackMove extends AttackMove {

        public MajorAttackMove(final Board board,
                               final Piece movedPiece,
                               final int destinationCoordinate,
                               final Piece attackedPiece) {
            super(board, movedPiece, destinationCoordinate, attackedPiece);
        }

        @Override
        public String toString() {
            return new StringBuilder().append(movedPiece.getPieceType())
                    .append(ambiguitySolver())
                    .append("x")
                    .append(BoardUtils.getPositionAtCoordinate(this.destinationCoordinate))
                    .append(isCheckOrMateMove())
                    .toString();
        }
    }

    public static class PawnAttackMove extends AttackMove {

        public PawnAttackMove(final Board board,
                              final Piece movedPiece,
                              final int destinationCoordinate,
                              final Piece attackedPiece) {
            super(board, movedPiece, destinationCoordinate, attackedPiece);
        }

        @Override
        public String toString() {
            return new StringBuilder()
                    .append(getPieceColumn())
                    .append("x")
                    .append(BoardUtils.getPositionAtCoordinate(this.destinationCoordinate))
                    .append(isCheckOrMateMove())
                    .toString();
        }

    }

    public static final class PawnEnPassantAttackMove extends PawnAttackMove {

        public PawnEnPassantAttackMove(final Board board,
                                       final Piece movedPiece,
                                       final int destinationCoordinate,
                                       final Piece attackedPiece) {
            super(board, movedPiece, destinationCoordinate, attackedPiece);
        }
    @Override
    public String toString() {
        return new StringBuilder()
                .append(getPieceColumn())
                .append("x")
                .append(BoardUtils.getPositionAtCoordinate(this.destinationCoordinate))
                .append(isCheckOrMateMove())
                .toString();
    }
    }

    //Pawn moves
    public static final class PawnMove extends Move {

        public PawnMove(final Board board,
                        final Piece movedPiece,
                        final int destinationCoordinate) {
            super(board, movedPiece, destinationCoordinate);
        }

        @Override
        public String toString() {
            return new StringBuilder()
                    .append(BoardUtils.getPositionAtCoordinate(this.destinationCoordinate))
                    .append(isCheckOrMateMove())
                    .toString();
        }
    }

    public static final class PawnJump extends Move {

        public PawnJump(final Board board,
                            final Piece movedPiece,
                            final int destinationCoordinate) {
            super(board, movedPiece, destinationCoordinate);
        }

        @Override
        public Board execute() {
            final Board.Builder builder = new Board.Builder();
            for (final Piece piece : this.board.currentPlayer().getActivePieces()) {
                if (!this.movedPiece.equals(piece)) {
                    builder.setPiece(piece);
                }
            }
            for (final Piece piece : this.board.currentPlayer().getOpponent().getActivePieces()) {
                builder.setPiece(piece);
            }
            final Pawn movedPawn = (Pawn) this.movedPiece.movePiece(this);
            builder.setPiece(movedPawn);
            builder.setEnPassantPawn(movedPawn);
            builder.setMoveMaker(this.board.currentPlayer().getOpponent().getAlliance());
            return builder.build();
        }

        @Override
        public String toString() {
            return BoardUtils.getPositionAtCoordinate(this.destinationCoordinate);
        }
    }

    public static final class PawnPromotion extends Move {

        final Move decoratedMove;
        final Pawn promotedPawn;
        final Piece promotionPiece;

        public PawnPromotion(final Move decoratedMove,
                             final Piece promotionPiece) {
            super(decoratedMove.getBoard(), decoratedMove.getMovedPiece(), decoratedMove.getDestinationCoordinate());
            this.decoratedMove = decoratedMove;
            this.promotedPawn = (Pawn) decoratedMove.getMovedPiece().movePiece(this);
            this.promotionPiece = promotionPiece;
        }

        @Override
        public Board execute() {
            final Board pawnMovedBoard = this.decoratedMove.execute();
            final Board.Builder builder = new Board.Builder();
            for (final Piece piece : pawnMovedBoard.currentPlayer().getActivePieces()) {
                if (!this.promotedPawn.equals(piece)) {
                    builder.setPiece(piece);
                }
            }
            for (final Piece piece : pawnMovedBoard.currentPlayer().getOpponent().getActivePieces()) {
                builder.setPiece(piece);
            }
            builder.setPiece(this.promotionPiece.movePiece(this));
            builder.setMoveMaker(pawnMovedBoard.currentPlayer().getAlliance());
            return builder.build();
        }

        @Override
        public boolean isAttack() {
            return this.decoratedMove.isAttack();
        }

        @Override
        public Piece getAttackedPiece() {
            return this.decoratedMove.getAttackedPiece();
        }

        @Override
        public String toString() {
            return new StringBuilder()
                    .append(isAttack() ? getPieceColumn() + "x" : "")
                    .append(BoardUtils.getPositionAtCoordinate(this.destinationCoordinate))
                    .append("=")
                    .append(this.promotionPiece.getPieceType())
                    .append(isCheckOrMateMove())
                    .toString();
        }

        @Override
        public int hashCode() {
            return decoratedMove.hashCode() + (31 * promotedPawn.hashCode());
        }

        @Override
        public boolean equals(final Object other) {
            return this == other || other instanceof PawnPromotion && (this.decoratedMove.equals(other));
        }
    }

    //Castle moves
    static class CastleMove extends Move {

        protected final Rook castleRook;
        protected final int castleRookStart;
        protected final int castleRookDestination;

        public CastleMove(final Board board,
                          final Piece movedPiece,
                          final int destinationCoordinate,
                          final Rook castleRook,
                          final int castleRookStart,
                          final int castleRookDestination) {
            super(board, movedPiece, destinationCoordinate);
            this.castleRook = castleRook;
            this.castleRookStart = castleRookStart;
            this.castleRookDestination = castleRookDestination;
        }

        public Rook getCastleRook() {
            return castleRook;
        }

        public int getCastleRookStart() {
            return castleRookStart;
        }

        public int getCastleRookDestination() {
            return castleRookDestination;
        }

        @Override
        public boolean isCastlingMove() {
            return true;
        }

        @Override
        public Board execute() {
            final Board.Builder builder = new Board.Builder();
            for (final Piece piece : this.board.currentPlayer().getActivePieces()) {
                //copy not-moved piece of the current player on the new board
                if (!this.movedPiece.equals(piece) && !this.castleRook.equals(piece)) {
                    builder.setPiece(piece);
                }
            }
            //copy opponents pieces
            for (final Piece piece : this.board.currentPlayer().getOpponent().getActivePieces()) {
                builder.setPiece(piece);
            }
            builder.setPiece(this.movedPiece.movePiece(this));
            builder.setPiece(new Rook(this.castleRookDestination, this.castleRook.getPieceAlliance()));
            builder.setMoveMaker(this.board.currentPlayer().getOpponent().getAlliance());
            return builder.build();
        }
    }

    public static final class KingSideCastleMove extends CastleMove {

        public KingSideCastleMove(final Board board,
                                  final Piece movedPiece,
                                  final int destinationCoordinate,
                                  final Rook castleRook,
                                  final int castleRookStart,
                                  final int castleRookDestination) {
            super(board, movedPiece, destinationCoordinate, castleRook, castleRookStart, castleRookDestination);
        }

        @Override
        public String toString() {
            return "O-O";
        }

        @Override
        public boolean isCastlingMove() {
            return true;
        }
    }

    public static final class QueenSideCastleMove extends CastleMove {

        public QueenSideCastleMove(final Board board,
                                   final Piece movedPiece,
                                   final int destinationCoordinate,
                                   final Rook castleRook,
                                   final int castleRookStart,
                                   final int castleRookDestination) {
            super(board, movedPiece, destinationCoordinate, castleRook, castleRookStart, castleRookDestination);
        }

        @Override
        public String toString() {
            return "O-O-O";
        }

        @Override
        public boolean isCastlingMove() {
            return true;
        }
    }

    //Invalid move
    public static final class NullMove extends Move {

        public NullMove() {
            super(null, -1);
        }

        @Override
        public Board execute() {
            throw new RuntimeException("cannot execute the null move!");
        }
    }
}
