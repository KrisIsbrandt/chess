package pl.isbrandt.chess.player;

import pl.isbrandt.chess.Alliance;
import pl.isbrandt.chess.board.Board;
import pl.isbrandt.chess.board.Move;
import pl.isbrandt.chess.board.Tile;
import pl.isbrandt.chess.piece.Piece;
import pl.isbrandt.chess.piece.Rook;
import com.google.common.collect.ImmutableList;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static pl.isbrandt.chess.board.Move.*;

public class BlackPlayer extends Player {

    public BlackPlayer(final Board board,
                       final Collection<Move> whiteStandardLegalMoves,
                       final Collection<Move> blackStandardLegalMoves) {
        super(board, blackStandardLegalMoves, whiteStandardLegalMoves);
    }

    @Override
    public Collection<Piece> getActivePieces() {
        return this.board.getBlackPieces();
    }

    @Override
    public Alliance getAlliance() {
        return Alliance.BLACK;
    }

    @Override
    public Player getOpponent() {
        return this.board.getWhitePlayer();
    }

    @Override
    public Collection<Move> calculateKingCastles(final Collection<Move> playerLegalMoves,
                                                 final Collection<Move> opponentsLegalMoves) {
        final List<Move> kingCastles = new ArrayList<>();
        if (this.playerKing.isFirstMove() && !this.isInCheck()) {
            //Black king side castle check
            if (!this.board.getTile(5).isTileOccupied() &&
                !this.board.getTile(6).isTileOccupied()) {
                if (calculateAttacksOnTile(5, opponentsLegalMoves).isEmpty() &&
                    calculateAttacksOnTile(6, opponentsLegalMoves).isEmpty()) {

                    final Tile rookTile = this.board.getTile(7);
                    if (rookTile.isTileOccupied() && rookTile.getPiece().isFirstMove() &&
                        rookTile.getPiece().getPieceType().equals(Piece.PieceType.ROOK)) {
                        kingCastles.add(new KingSideCastleMove(this.board, this.playerKing, 6,
                                (Rook) rookTile.getPiece(), rookTile.getTileCoordinate(), 5));
                    }
                }
            }
            //Black queen side castle check
            if (!this.board.getTile(1).isTileOccupied() &&
                !this.board.getTile(2).isTileOccupied() &&
                !this.board.getTile(3).isTileOccupied()) {
                if (calculateAttacksOnTile(2, opponentsLegalMoves).isEmpty() &&
                    calculateAttacksOnTile(3, opponentsLegalMoves).isEmpty()) {

                    final Tile rookTile = this.board.getTile(0);
                    if (rookTile.isTileOccupied() && rookTile.getPiece().isFirstMove() &&
                        rookTile.getPiece().getPieceType().equals(Piece.PieceType.ROOK)) {
                        kingCastles.add(new QueenSideCastleMove(this.board, this.playerKing, 2,
                                (Rook) rookTile.getPiece(), rookTile.getTileCoordinate(), 3));
                    }
                }
            }
        }
        return ImmutableList.copyOf(kingCastles);
    }
}
