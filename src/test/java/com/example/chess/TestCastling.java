package com.example.chess;

import com.example.chess.board.Board;
import com.example.chess.board.BoardUtils;
import com.example.chess.board.Move;
import com.example.chess.piece.Piece;
import com.example.chess.piece.Rook;
import com.example.pgn.FenUtils;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TestCastling {
    
    @Test
    public void testWhiteKingSideCastle() {
        //Given board, the white king should be able to castle king side
        final String fenString = "r3k2r/pppppppp/8/8/8/8/PPPPPPPP/R3K2R w KQkq - 0 1";
        Board board = FenUtils.createGameFromFEN(fenString);
        final Rook rook = (Rook) board.getTile(BoardUtils.getCoordinateAtPosition("h1")).getPiece();
        final Move.KingSideCastleMove castleMove = new Move.KingSideCastleMove(
                board,
                board.getWhitePlayer().getPlayerKing(),62,
                rook, rook.getPiecePosition(), 61);

        assertTrue(board.getWhitePlayer().isKingSideCastleCapable());
        assertTrue(board.getWhitePlayer().getLegalMoves().contains(castleMove));

        board = board.currentPlayer().makeMove(castleMove).getTransitionBoard();
        assertEquals(62, board.getWhitePlayer().getPlayerKing().getPiecePosition());
        assertEquals(Piece.PieceType.ROOK, board.getTile(61).getPiece().getPieceType());
        assertTrue(board.getWhitePlayer().isCastled());
        assertFalse(board.getWhitePlayer().isQueenSideCastleCapable());
        assertFalse(board.getWhitePlayer().isKingSideCastleCapable());
    }
    
    @Test
    public void testWhiteQueenSideCastle() {
        //Given board, the white king should be able to castle queen side
        final String fenString = "r3k2r/pppppppp/8/8/8/8/PPPPPPPP/R3K2R w KQkq - 0 1";
        Board board = FenUtils.createGameFromFEN(fenString);
        final Rook rook = (Rook) board.getTile(BoardUtils.getCoordinateAtPosition("a1")).getPiece();
        final Move.QueenSideCastleMove castleMove = new Move.QueenSideCastleMove(
                board,
                board.getWhitePlayer().getPlayerKing(),58,
                rook, rook.getPiecePosition(), 59);

        assertTrue(board.getWhitePlayer().isQueenSideCastleCapable());
        assertTrue(board.getWhitePlayer().getLegalMoves().contains(castleMove));

        board = board.currentPlayer().makeMove(castleMove).getTransitionBoard();
        assertEquals(58, board.getWhitePlayer().getPlayerKing().getPiecePosition());
        assertEquals(Piece.PieceType.ROOK, board.getTile(59).getPiece().getPieceType());
        assertTrue(board.getWhitePlayer().isCastled());
        assertFalse(board.getWhitePlayer().isQueenSideCastleCapable());
        assertFalse(board.getWhitePlayer().isKingSideCastleCapable());
    }

    @Test
    public void testBlackKingSideCastle() {
        //Given board, the black king should be able to castle king side
        final String fenString = "r3k2r/pppppppp/8/8/8/8/PPPPPPPP/R3K2R b KQkq - 0 1";
        Board board = FenUtils.createGameFromFEN(fenString);
        final Rook rook = (Rook) board.getTile(BoardUtils.getCoordinateAtPosition("h8")).getPiece();
        final Move.KingSideCastleMove castleMove = new Move.KingSideCastleMove(
                board,
                board.getBlackPlayer().getPlayerKing(),6,
                rook, rook.getPiecePosition(), 5);

        assertTrue(board.getBlackPlayer().isKingSideCastleCapable());
        assertTrue(board.getBlackPlayer().getLegalMoves().contains(castleMove));

        board = board.currentPlayer().makeMove(castleMove).getTransitionBoard();
        assertEquals(6, board.getBlackPlayer().getPlayerKing().getPiecePosition());
        assertEquals(Piece.PieceType.ROOK, board.getTile(5).getPiece().getPieceType());
        assertTrue(board.getBlackPlayer().isCastled());
        assertFalse(board.getBlackPlayer().isQueenSideCastleCapable());
        assertFalse(board.getBlackPlayer().isKingSideCastleCapable());
    }

    @Test
    public void testBlackQueenSideCastle() {
        //Given board, the black king should be able to castle queen side
        final String fenString = "r3k2r/pppppppp/8/8/8/8/PPPPPPPP/R3K2R b KQkq - 0 1";
        Board board = FenUtils.createGameFromFEN(fenString);
        final Rook rook = (Rook) board.getTile(BoardUtils.getCoordinateAtPosition("a8")).getPiece();
        final Move.QueenSideCastleMove castleMove = new Move.QueenSideCastleMove(
                board,
                board.getBlackPlayer().getPlayerKing(),2,
                rook, rook.getPiecePosition(), 3);

        assertTrue(board.getBlackPlayer().isQueenSideCastleCapable());
        assertTrue(board.getBlackPlayer().getLegalMoves().contains(castleMove));

        board = board.currentPlayer().makeMove(castleMove).getTransitionBoard();
        assertEquals(2, board.getBlackPlayer().getPlayerKing().getPiecePosition());
        assertEquals(Piece.PieceType.ROOK, board.getTile(3).getPiece().getPieceType());
        assertTrue(board.getBlackPlayer().isCastled());
        assertFalse(board.getBlackPlayer().isQueenSideCastleCapable());
        assertFalse(board.getBlackPlayer().isKingSideCastleCapable());
    }

    @Test
    public void testCastlingMoveAfterKingMove() {
        //Given board, both kings should not be able to castle after moving back and forth
        final String fenString = "r3k2r/pppppppp/8/8/8/8/PPPPPPPP/R3K2R b KQkq - 0 1";
        Board board = FenUtils.createGameFromFEN(fenString);

        board = board.currentPlayer().makeMove(Move.MoveFactory
                .createMove(board, BoardUtils.getCoordinateAtPosition("e8"), BoardUtils.getCoordinateAtPosition("d8"))).getTransitionBoard();
        board = board.currentPlayer().makeMove(Move.MoveFactory
                .createMove(board, BoardUtils.getCoordinateAtPosition("e1"), BoardUtils.getCoordinateAtPosition("d1"))).getTransitionBoard();
        board = board.currentPlayer().makeMove(Move.MoveFactory
                .createMove(board, BoardUtils.getCoordinateAtPosition("d8"), BoardUtils.getCoordinateAtPosition("e8"))).getTransitionBoard();
        board = board.currentPlayer().makeMove(Move.MoveFactory
                .createMove(board, BoardUtils.getCoordinateAtPosition("d1"), BoardUtils.getCoordinateAtPosition("e1"))).getTransitionBoard();

        assertFalse(board.getBlackPlayer().isQueenSideCastleCapable());
        assertFalse(board.getBlackPlayer().isKingSideCastleCapable());
        assertFalse(board.getWhitePlayer().isQueenSideCastleCapable());
        assertFalse(board.getWhitePlayer().isKingSideCastleCapable());
        assertFalse(board.getBlackPlayer().isCastled());
        assertFalse(board.getWhitePlayer().isCastled());
    }

    @Test
    public void testCastlingMoveAfterRookMoveOnKingSide() {
        //Given board, both kings should not be able to castle king side after rook moves back and forth
        final String fenString = "r3k2r/pppppppp/8/8/8/8/PPPPPPPP/R3K2R b KQkq - 0 1";
        Board board = FenUtils.createGameFromFEN(fenString);

        board = board.currentPlayer().makeMove(Move.MoveFactory
                .createMove(board, BoardUtils.getCoordinateAtPosition("h8"), BoardUtils.getCoordinateAtPosition("g8"))).getTransitionBoard();
        board = board.currentPlayer().makeMove(Move.MoveFactory
                .createMove(board, BoardUtils.getCoordinateAtPosition("h1"), BoardUtils.getCoordinateAtPosition("g1"))).getTransitionBoard();
        board = board.currentPlayer().makeMove(Move.MoveFactory
                .createMove(board, BoardUtils.getCoordinateAtPosition("g8"), BoardUtils.getCoordinateAtPosition("h8"))).getTransitionBoard();
        board = board.currentPlayer().makeMove(Move.MoveFactory
                .createMove(board, BoardUtils.getCoordinateAtPosition("g1"), BoardUtils.getCoordinateAtPosition("h1"))).getTransitionBoard();

        assertTrue(board.getBlackPlayer().isQueenSideCastleCapable());
        assertTrue(board.getWhitePlayer().isQueenSideCastleCapable());
        assertFalse(board.getBlackPlayer().isKingSideCastleCapable());
        assertFalse(board.getWhitePlayer().isKingSideCastleCapable());
        assertFalse(board.getBlackPlayer().isCastled());
        assertFalse(board.getWhitePlayer().isCastled());
    }

    @Test
    public void testCastlingMoveAfterRookMoveOnQueenSide() {
        //Given board, both kings should not be able to castle queen side after rook moves back and forth
        final String fenString = "r3k2r/pppppppp/8/8/8/8/PPPPPPPP/R3K2R b KQkq - 0 1";
        Board board = FenUtils.createGameFromFEN(fenString);

        board = board.currentPlayer().makeMove(Move.MoveFactory
                .createMove(board, BoardUtils.getCoordinateAtPosition("a8"), BoardUtils.getCoordinateAtPosition("b8"))).getTransitionBoard();
        board = board.currentPlayer().makeMove(Move.MoveFactory
                .createMove(board, BoardUtils.getCoordinateAtPosition("a1"), BoardUtils.getCoordinateAtPosition("b1"))).getTransitionBoard();
        board = board.currentPlayer().makeMove(Move.MoveFactory
                .createMove(board, BoardUtils.getCoordinateAtPosition("b8"), BoardUtils.getCoordinateAtPosition("a8"))).getTransitionBoard();
        board = board.currentPlayer().makeMove(Move.MoveFactory
                .createMove(board, BoardUtils.getCoordinateAtPosition("b1"), BoardUtils.getCoordinateAtPosition("a1"))).getTransitionBoard();

        assertTrue(board.getBlackPlayer().isKingSideCastleCapable());
        assertTrue(board.getWhitePlayer().isKingSideCastleCapable());
        assertFalse(board.getBlackPlayer().isQueenSideCastleCapable());
        assertFalse(board.getWhitePlayer().isQueenSideCastleCapable());
        assertFalse(board.getBlackPlayer().isCastled());
        assertFalse(board.getWhitePlayer().isCastled());
    }

    @Test
    public void testCastlingMoveWithPiecesInBetween() {
        //Give standard board, both kings cannot castle
        final Board board = Board.createStandardBoard();
        final Rook blackQueenSideRook = (Rook) board.getTile(0).getPiece();
        final Rook blackKingSideRook  = (Rook) board.getTile(7).getPiece();
        final Rook whiteQueenSideRook = (Rook) board.getTile(56).getPiece();
        final Rook whiteKingSideRook  = (Rook) board.getTile(63).getPiece();

        final Move.QueenSideCastleMove blackQueenSideCastleMove = new Move.QueenSideCastleMove(
                board, board.getBlackPlayer().getPlayerKing(),2,
                blackQueenSideRook, blackQueenSideRook.getPiecePosition(), 3);
        final Move.KingSideCastleMove blackKingSideCastleMove = new Move.KingSideCastleMove(
                board, board.getBlackPlayer().getPlayerKing(),6,
                blackKingSideRook, blackKingSideRook.getPiecePosition(), 5);
        final Move.QueenSideCastleMove whiteQueenSideCastleMove = new Move.QueenSideCastleMove(
                board, board.getBlackPlayer().getPlayerKing(),58,
                whiteQueenSideRook, whiteQueenSideRook.getPiecePosition(), 59);
        final Move.KingSideCastleMove whiteKingSideCastleMove = new Move.KingSideCastleMove(
                board, board.getBlackPlayer().getPlayerKing(),62,
                whiteKingSideRook, whiteKingSideRook.getPiecePosition(), 61);

        assertFalse(board.getBlackPlayer().getLegalMoves().contains(blackKingSideCastleMove));
        assertFalse(board.getBlackPlayer().getLegalMoves().contains(blackQueenSideCastleMove));
        assertFalse(board.getWhitePlayer().getLegalMoves().contains(whiteKingSideCastleMove));
        assertFalse(board.getWhitePlayer().getLegalMoves().contains(whiteQueenSideCastleMove));
    }

    @Test
    public void testCastlingMoveWhenKingIsInCheck() {
        final String fenString = "4k2r/5ppp/8/4R3/8/8/8/4K3 b k - 0 1";
        final Board board = FenUtils.createGameFromFEN(fenString);

        final Rook blackKingSideRook  = (Rook) board.getTile(7).getPiece();
        final Move.KingSideCastleMove blackKingSideCastleMove = new Move.KingSideCastleMove(
                board, board.getBlackPlayer().getPlayerKing(),6,
                blackKingSideRook, blackKingSideRook.getPiecePosition(), 5);

        assertTrue(board.getBlackPlayer().isKingSideCastleCapable());
        assertFalse(board.getBlackPlayer().getLegalMoves().contains(blackKingSideCastleMove));
        assertFalse(board.getBlackPlayer().isCastled());
    }

    @Test
    public void testCastlingMoveWithAttackOnPassingTile() {
        final String fenString = "4k2r/5ppp/8/4R3/8/8/8/4K3 b k - 0 1";
        final Board board = FenUtils.createGameFromFEN(fenString);

        final Rook blackKingSideRook  = (Rook) board.getTile(7).getPiece();
        final Move.KingSideCastleMove blackKingSideCastleMove = new Move.KingSideCastleMove(
                board, board.getBlackPlayer().getPlayerKing(),6,
                blackKingSideRook, blackKingSideRook.getPiecePosition(), 5);

        assertTrue(board.getBlackPlayer().isKingSideCastleCapable());
        assertFalse(board.getBlackPlayer().getLegalMoves().contains(blackKingSideCastleMove));
        assertFalse(board.getBlackPlayer().isCastled());
    }

    @Test
    public void testCastlingMoveWithEndingUpInCheck() {
        final String fenString = "4k2r/4pppp/7N/8/8/8/8/4K3 w k - 0 1";
        final Board board = FenUtils.createGameFromFEN(fenString);

        final Rook blackKingSideRook  = (Rook) board.getTile(7).getPiece();
        final Move.KingSideCastleMove blackKingSideCastleMove = new Move.KingSideCastleMove(
                board, board.getBlackPlayer().getPlayerKing(),6,
                blackKingSideRook, blackKingSideRook.getPiecePosition(), 5);

        assertTrue(board.getBlackPlayer().isKingSideCastleCapable());
        assertFalse(board.getBlackPlayer().getLegalMoves().contains(blackKingSideCastleMove));
        assertFalse(board.getBlackPlayer().isCastled());
    }
}
