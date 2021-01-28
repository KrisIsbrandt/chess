package com.example.chess.player;

import com.example.chess.Alliance;
import com.example.chess.board.Board;
import com.example.chess.board.Move;
import com.example.chess.board.Tile;
import com.example.chess.piece.Piece;
import com.example.chess.piece.Rook;
import com.google.common.collect.ImmutableList;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static com.example.chess.board.Move.*;

public class WhitePlayer extends Player {

    public WhitePlayer(final Board board,
                       final Collection<Move> whiteStandardLegalMoves,
                       final Collection<Move> blackStandardLegalMoves) {
        super(board, whiteStandardLegalMoves, blackStandardLegalMoves);
    }

    @Override
    public Collection<Piece> getActivePieces() {
        return this.board.getWhitePieces();
    }

    @Override
    public Alliance getAlliance() {
        return Alliance.WHITE;
    }

    @Override
    public Player getOpponent() {
        return this.board.getBlackPlayer();
    }

    @Override
    public Collection<Move> calculateKingCastles(final Collection<Move> playerLegalMoves,
                                                 final Collection<Move> opponentsLegalMoves) {
        final List<Move> kingCastles = new ArrayList<>();
        if (this.playerKing.isFirstMove() && !this.isInCheck()) {
            //White king side castle check
            if (!this.board.getTile(61).isTileOccupied() &&
                !this.board.getTile(62).isTileOccupied()) {
                if (Player.calculateAttacksOnTile(61, opponentsLegalMoves).isEmpty() &&
                    Player.calculateAttacksOnTile(62, opponentsLegalMoves).isEmpty()) {

                    final Tile rookTile = this.board.getTile(63);
                    if (rookTile.isTileOccupied() && rookTile.getPiece().isFirstMove() &&
                        rookTile.getPiece().getPieceType().equals(Piece.PieceType.ROOK)) {
                        kingCastles.add(new KingSideCastleMove(this.board, this.playerKing,62,
                                (Rook) rookTile.getPiece(), rookTile.getTileCoordinate(), 61));
                    }
                }
            }
            //White queen side castle check
            if (!this.board.getTile(57).isTileOccupied() &&
                !this.board.getTile(58).isTileOccupied() &&
                !this.board.getTile(59).isTileOccupied()) {
                if (Player.calculateAttacksOnTile(58, opponentsLegalMoves).isEmpty() &&
                    Player.calculateAttacksOnTile(59, opponentsLegalMoves).isEmpty()) {

                    final Tile rookTile = this.board.getTile(56);
                    if (rookTile.isTileOccupied() && rookTile.getPiece().isFirstMove() &&
                        rookTile.getPiece().getPieceType().equals(Piece.PieceType.ROOK)) {
                        kingCastles.add(new QueenSideCastleMove(this.board, this.playerKing, 58,
                                (Rook) rookTile.getPiece(), rookTile.getTileCoordinate(), 59));
                    }
                }
            }
        }
        return  ImmutableList.copyOf(kingCastles);
    }
}


