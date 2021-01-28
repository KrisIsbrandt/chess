package com.example.chess;

import com.example.chess.board.Board;
import com.example.chess.board.BoardUtils;
import com.example.chess.board.Move;
import com.example.chess.piece.*;
import com.example.chess.player.MoveTransition;
import com.example.pgn.FenUtils;
import org.junit.jupiter.api.Test;

import java.util.Collection;

import static org.junit.jupiter.api.Assertions.*;

public class TestPieces {

    @Test
    public void testRookInTheMiddleMoves() {
        //Given white rook on e5, then it has 11 legal moves, including 1 attack move
        final String fenString = "rnbqkbnr/pppppppp/8/4R3/8/8/PPPPPPPP/RNBQKBN1 w Qkq - 0 1";
        final Board board = FenUtils.createGameFromFEN(fenString);
        final Rook rook = (Rook) board.getTile(BoardUtils.getCoordinateAtPosition("e5")).getPiece();
        final Collection<Move> rookMoves = rook.calculateLegalMoves(board);

        assertTrue(rookMoves.contains(Move.MoveFactory
                .createMove(board,BoardUtils.getCoordinateAtPosition("e5"), BoardUtils.getCoordinateAtPosition("a5"))));
        assertTrue(rookMoves.contains(Move.MoveFactory
                .createMove(board,BoardUtils.getCoordinateAtPosition("e5"), BoardUtils.getCoordinateAtPosition("b5"))));
        assertTrue(rookMoves.contains(Move.MoveFactory
                .createMove(board,BoardUtils.getCoordinateAtPosition("e5"), BoardUtils.getCoordinateAtPosition("c5"))));
        assertTrue(rookMoves.contains(Move.MoveFactory
                .createMove(board,BoardUtils.getCoordinateAtPosition("e5"), BoardUtils.getCoordinateAtPosition("d5"))));
        assertTrue(rookMoves.contains(Move.MoveFactory
                .createMove(board,BoardUtils.getCoordinateAtPosition("e5"), BoardUtils.getCoordinateAtPosition("f5"))));
        assertTrue(rookMoves.contains(Move.MoveFactory
                .createMove(board,BoardUtils.getCoordinateAtPosition("e5"), BoardUtils.getCoordinateAtPosition("g5"))));
        assertTrue(rookMoves.contains(Move.MoveFactory
                .createMove(board,BoardUtils.getCoordinateAtPosition("e5"), BoardUtils.getCoordinateAtPosition("h5"))));
        assertTrue(rookMoves.contains(Move.MoveFactory
                .createMove(board,BoardUtils.getCoordinateAtPosition("e5"), BoardUtils.getCoordinateAtPosition("e4"))));
        assertTrue(rookMoves.contains(Move.MoveFactory
                .createMove(board,BoardUtils.getCoordinateAtPosition("e5"), BoardUtils.getCoordinateAtPosition("e3"))));
        assertTrue(rookMoves.contains(Move.MoveFactory
                .createMove(board,BoardUtils.getCoordinateAtPosition("e5"), BoardUtils.getCoordinateAtPosition("e6"))));
        assertTrue(rookMoves.contains(Move.MoveFactory
                .createMove(board,BoardUtils.getCoordinateAtPosition("e5"), BoardUtils.getCoordinateAtPosition("e7"))));
        assertEquals(11, rookMoves.size());
    }

    @Test
    public void testRookInTheCornerMoves() {
        //Given white rook on h1, then it has 14 legal moves
        final String fenString = "8/8/k7/8/K7/8/8/7R w - - 0 1";
        final Board board = FenUtils.createGameFromFEN(fenString);
        final Rook rook = (Rook) board.getTile(BoardUtils.getCoordinateAtPosition("h1")).getPiece();
        final Collection<Move> rookMoves = rook.calculateLegalMoves(board);

        assertTrue(rookMoves.contains(Move.MoveFactory
                .createMove(board,BoardUtils.getCoordinateAtPosition("h1"), BoardUtils.getCoordinateAtPosition("a1"))));
        assertTrue(rookMoves.contains(Move.MoveFactory
                .createMove(board,BoardUtils.getCoordinateAtPosition("h1"), BoardUtils.getCoordinateAtPosition("b1"))));
        assertTrue(rookMoves.contains(Move.MoveFactory
                .createMove(board,BoardUtils.getCoordinateAtPosition("h1"), BoardUtils.getCoordinateAtPosition("c1"))));
        assertTrue(rookMoves.contains(Move.MoveFactory
                .createMove(board,BoardUtils.getCoordinateAtPosition("h1"), BoardUtils.getCoordinateAtPosition("d1"))));
        assertTrue(rookMoves.contains(Move.MoveFactory
                .createMove(board,BoardUtils.getCoordinateAtPosition("h1"), BoardUtils.getCoordinateAtPosition("e1"))));
        assertTrue(rookMoves.contains(Move.MoveFactory
                .createMove(board,BoardUtils.getCoordinateAtPosition("h1"), BoardUtils.getCoordinateAtPosition("f1"))));
        assertTrue(rookMoves.contains(Move.MoveFactory
                .createMove(board,BoardUtils.getCoordinateAtPosition("h1"), BoardUtils.getCoordinateAtPosition("g1"))));
        assertTrue(rookMoves.contains(Move.MoveFactory
                .createMove(board,BoardUtils.getCoordinateAtPosition("h1"), BoardUtils.getCoordinateAtPosition("h2"))));
        assertTrue(rookMoves.contains(Move.MoveFactory
                .createMove(board,BoardUtils.getCoordinateAtPosition("h1"), BoardUtils.getCoordinateAtPosition("h3"))));
        assertTrue(rookMoves.contains(Move.MoveFactory
                .createMove(board,BoardUtils.getCoordinateAtPosition("h1"), BoardUtils.getCoordinateAtPosition("h4"))));
        assertTrue(rookMoves.contains(Move.MoveFactory
                .createMove(board,BoardUtils.getCoordinateAtPosition("h1"), BoardUtils.getCoordinateAtPosition("h5"))));
        assertTrue(rookMoves.contains(Move.MoveFactory
                .createMove(board,BoardUtils.getCoordinateAtPosition("h1"), BoardUtils.getCoordinateAtPosition("h6"))));
        assertTrue(rookMoves.contains(Move.MoveFactory
                .createMove(board,BoardUtils.getCoordinateAtPosition("h1"), BoardUtils.getCoordinateAtPosition("h7"))));
        assertTrue(rookMoves.contains(Move.MoveFactory
                .createMove(board,BoardUtils.getCoordinateAtPosition("h1"), BoardUtils.getCoordinateAtPosition("h8"))));
        assertEquals(14, rookMoves.size());
    }

    @Test
    public void testKnightInTheMiddleMoves() {
        //Given white knight on d5, then it has 8 legal moves
        final String fenString = "k7/8/8/3n4/8/8/8/K6N w - - 0 1";
        final Board board = FenUtils.createGameFromFEN(fenString);
        final Knight knight = (Knight) board.getTile(BoardUtils.getCoordinateAtPosition("d5")).getPiece();
        final Collection<Move> knightMoves = knight.calculateLegalMoves(board);
        assertTrue(knightMoves.contains(Move.MoveFactory
                .createMove(board,BoardUtils.getCoordinateAtPosition("d5"), BoardUtils.getCoordinateAtPosition("b4"))));
        assertTrue(knightMoves.contains(Move.MoveFactory
                .createMove(board,BoardUtils.getCoordinateAtPosition("d5"), BoardUtils.getCoordinateAtPosition("b6"))));
        assertTrue(knightMoves.contains(Move.MoveFactory
                .createMove(board,BoardUtils.getCoordinateAtPosition("d5"), BoardUtils.getCoordinateAtPosition("c3"))));
        assertTrue(knightMoves.contains(Move.MoveFactory
                .createMove(board,BoardUtils.getCoordinateAtPosition("d5"), BoardUtils.getCoordinateAtPosition("c7"))));
        assertTrue(knightMoves.contains(Move.MoveFactory
                .createMove(board,BoardUtils.getCoordinateAtPosition("d5"), BoardUtils.getCoordinateAtPosition("e3"))));
        assertTrue(knightMoves.contains(Move.MoveFactory
                .createMove(board,BoardUtils.getCoordinateAtPosition("d5"), BoardUtils.getCoordinateAtPosition("e7"))));
        assertTrue(knightMoves.contains(Move.MoveFactory
                .createMove(board,BoardUtils.getCoordinateAtPosition("d5"), BoardUtils.getCoordinateAtPosition("f4"))));
        assertTrue(knightMoves.contains(Move.MoveFactory
                .createMove(board,BoardUtils.getCoordinateAtPosition("d5"), BoardUtils.getCoordinateAtPosition("f6"))));
        assertEquals(8, knightMoves.size());
    }

    @Test
    public void testKnightInTheCornerMoves() {
        //Given white knight on h1, then it has 2 legal moves
        final String fenString = "k7/8/8/3n4/8/8/8/K6N w - - 0 1";
        final Board board = FenUtils.createGameFromFEN(fenString);
        final Knight knight = (Knight) board.getTile(BoardUtils.getCoordinateAtPosition("h1")).getPiece();
        final Collection<Move> knightMoves = knight.calculateLegalMoves(board);
        assertTrue(knightMoves.contains(Move.MoveFactory
                .createMove(board,BoardUtils.getCoordinateAtPosition("h1"), BoardUtils.getCoordinateAtPosition("f2"))));
        assertTrue(knightMoves.contains(Move.MoveFactory
                .createMove(board,BoardUtils.getCoordinateAtPosition("h1"), BoardUtils.getCoordinateAtPosition("g3"))));
        assertEquals(2, knightMoves.size());
    }

    @Test
    public void testBishopInTheMiddleMoves() {
        //Given black bishop on d5, then it has 12 legal moves
        final String fenString = "k7/8/8/3b4/8/8/5P2/K5b1 b - - 0 1";
        final Board board = FenUtils.createGameFromFEN(fenString);
        final Bishop bishop = (Bishop) board.getTile(BoardUtils.getCoordinateAtPosition("d5")).getPiece();
        final Collection<Move> bishopMoves = bishop.calculateLegalMoves(board);

        assertTrue(bishopMoves.contains(Move.MoveFactory
                .createMove(board,BoardUtils.getCoordinateAtPosition("d5"), BoardUtils.getCoordinateAtPosition("b7"))));
        assertTrue(bishopMoves.contains(Move.MoveFactory
                .createMove(board,BoardUtils.getCoordinateAtPosition("d5"), BoardUtils.getCoordinateAtPosition("c6"))));
        assertTrue(bishopMoves.contains(Move.MoveFactory
                .createMove(board,BoardUtils.getCoordinateAtPosition("d5"), BoardUtils.getCoordinateAtPosition("e4"))));
        assertTrue(bishopMoves.contains(Move.MoveFactory
                .createMove(board,BoardUtils.getCoordinateAtPosition("d5"), BoardUtils.getCoordinateAtPosition("f3"))));
        assertTrue(bishopMoves.contains(Move.MoveFactory
                .createMove(board,BoardUtils.getCoordinateAtPosition("d5"), BoardUtils.getCoordinateAtPosition("g2"))));
        assertTrue(bishopMoves.contains(Move.MoveFactory
                .createMove(board,BoardUtils.getCoordinateAtPosition("d5"), BoardUtils.getCoordinateAtPosition("h1"))));
        assertTrue(bishopMoves.contains(Move.MoveFactory
                .createMove(board,BoardUtils.getCoordinateAtPosition("d5"), BoardUtils.getCoordinateAtPosition("a2"))));
        assertTrue(bishopMoves.contains(Move.MoveFactory
                .createMove(board,BoardUtils.getCoordinateAtPosition("d5"), BoardUtils.getCoordinateAtPosition("b3"))));
        assertTrue(bishopMoves.contains(Move.MoveFactory
                .createMove(board,BoardUtils.getCoordinateAtPosition("d5"), BoardUtils.getCoordinateAtPosition("c4"))));
        assertTrue(bishopMoves.contains(Move.MoveFactory
                .createMove(board,BoardUtils.getCoordinateAtPosition("d5"), BoardUtils.getCoordinateAtPosition("e6"))));
        assertTrue(bishopMoves.contains(Move.MoveFactory
                .createMove(board,BoardUtils.getCoordinateAtPosition("d5"), BoardUtils.getCoordinateAtPosition("f7"))));
        assertTrue(bishopMoves.contains(Move.MoveFactory
                .createMove(board,BoardUtils.getCoordinateAtPosition("d5"), BoardUtils.getCoordinateAtPosition("g8"))));
        assertEquals(12, bishopMoves.size());
    }

    @Test
    public void testBishopInTheCornerMoves() {
        //Given black bishop g1, then it has 2 legal moves
        final String fenString = "k7/8/8/3b4/8/8/5P2/K5b1 b - - 0 1";
        final Board board = FenUtils.createGameFromFEN(fenString);
        final Bishop bishop = (Bishop) board.getTile(BoardUtils.getCoordinateAtPosition("g1")).getPiece();
        final Collection<Move> bishopMoves = bishop.calculateLegalMoves(board);

        assertTrue(bishopMoves.contains(Move.MoveFactory
                .createMove(board,BoardUtils.getCoordinateAtPosition("g1"), BoardUtils.getCoordinateAtPosition("f2"))));
        assertTrue(bishopMoves.contains(Move.MoveFactory
                .createMove(board,BoardUtils.getCoordinateAtPosition("g1"), BoardUtils.getCoordinateAtPosition("h2"))));
        assertEquals(2, bishopMoves.size());
    }
    
    @Test
    public void testQueenInTheMiddleMoves() {
        //Given black bishop on d5, then it has 26 legal moves
        final String fenString = "k7/8/8/3q4/8/8/5P2/K5b1 b - - 0 1";
        final Board board = FenUtils.createGameFromFEN(fenString);
        final Queen queen = (Queen) board.getTile(BoardUtils.getCoordinateAtPosition("d5")).getPiece();
        final Collection<Move> queenMoves = queen.calculateLegalMoves(board);

        assertTrue(queenMoves.contains(Move.MoveFactory
                .createMove(board,BoardUtils.getCoordinateAtPosition("d5"), BoardUtils.getCoordinateAtPosition("b7"))));
        assertTrue(queenMoves.contains(Move.MoveFactory
                .createMove(board,BoardUtils.getCoordinateAtPosition("d5"), BoardUtils.getCoordinateAtPosition("c6"))));
        assertTrue(queenMoves.contains(Move.MoveFactory
                .createMove(board,BoardUtils.getCoordinateAtPosition("d5"), BoardUtils.getCoordinateAtPosition("e4"))));
        assertTrue(queenMoves.contains(Move.MoveFactory
                .createMove(board,BoardUtils.getCoordinateAtPosition("d5"), BoardUtils.getCoordinateAtPosition("f3"))));
        assertTrue(queenMoves.contains(Move.MoveFactory
                .createMove(board,BoardUtils.getCoordinateAtPosition("d5"), BoardUtils.getCoordinateAtPosition("g2"))));
        assertTrue(queenMoves.contains(Move.MoveFactory
                .createMove(board,BoardUtils.getCoordinateAtPosition("d5"), BoardUtils.getCoordinateAtPosition("h1"))));
        assertTrue(queenMoves.contains(Move.MoveFactory
                .createMove(board,BoardUtils.getCoordinateAtPosition("d5"), BoardUtils.getCoordinateAtPosition("a2"))));
        assertTrue(queenMoves.contains(Move.MoveFactory
                .createMove(board,BoardUtils.getCoordinateAtPosition("d5"), BoardUtils.getCoordinateAtPosition("b3"))));
        assertTrue(queenMoves.contains(Move.MoveFactory
                .createMove(board,BoardUtils.getCoordinateAtPosition("d5"), BoardUtils.getCoordinateAtPosition("c4"))));
        assertTrue(queenMoves.contains(Move.MoveFactory
                .createMove(board,BoardUtils.getCoordinateAtPosition("d5"), BoardUtils.getCoordinateAtPosition("e6"))));
        assertTrue(queenMoves.contains(Move.MoveFactory
                .createMove(board,BoardUtils.getCoordinateAtPosition("d5"), BoardUtils.getCoordinateAtPosition("f7"))));
        assertTrue(queenMoves.contains(Move.MoveFactory
                .createMove(board,BoardUtils.getCoordinateAtPosition("d5"), BoardUtils.getCoordinateAtPosition("g8"))));

        assertTrue(queenMoves.contains(Move.MoveFactory
                .createMove(board,BoardUtils.getCoordinateAtPosition("d5"), BoardUtils.getCoordinateAtPosition("a5"))));
        assertTrue(queenMoves.contains(Move.MoveFactory
                .createMove(board,BoardUtils.getCoordinateAtPosition("d5"), BoardUtils.getCoordinateAtPosition("b5"))));
        assertTrue(queenMoves.contains(Move.MoveFactory
                .createMove(board,BoardUtils.getCoordinateAtPosition("d5"), BoardUtils.getCoordinateAtPosition("c5"))));
        assertTrue(queenMoves.contains(Move.MoveFactory
                .createMove(board,BoardUtils.getCoordinateAtPosition("d5"), BoardUtils.getCoordinateAtPosition("e5"))));
        assertTrue(queenMoves.contains(Move.MoveFactory
                .createMove(board,BoardUtils.getCoordinateAtPosition("d5"), BoardUtils.getCoordinateAtPosition("f5"))));
        assertTrue(queenMoves.contains(Move.MoveFactory
                .createMove(board,BoardUtils.getCoordinateAtPosition("d5"), BoardUtils.getCoordinateAtPosition("g5"))));
        assertTrue(queenMoves.contains(Move.MoveFactory
                .createMove(board,BoardUtils.getCoordinateAtPosition("d5"), BoardUtils.getCoordinateAtPosition("h5"))));
        assertTrue(queenMoves.contains(Move.MoveFactory
                .createMove(board,BoardUtils.getCoordinateAtPosition("d5"), BoardUtils.getCoordinateAtPosition("d6"))));
        assertTrue(queenMoves.contains(Move.MoveFactory
                .createMove(board,BoardUtils.getCoordinateAtPosition("d5"), BoardUtils.getCoordinateAtPosition("d7"))));
        assertTrue(queenMoves.contains(Move.MoveFactory
                .createMove(board,BoardUtils.getCoordinateAtPosition("d5"), BoardUtils.getCoordinateAtPosition("d8"))));
        assertTrue(queenMoves.contains(Move.MoveFactory
                .createMove(board,BoardUtils.getCoordinateAtPosition("d5"), BoardUtils.getCoordinateAtPosition("d4"))));
        assertTrue(queenMoves.contains(Move.MoveFactory
                .createMove(board,BoardUtils.getCoordinateAtPosition("d5"), BoardUtils.getCoordinateAtPosition("d3"))));
        assertTrue(queenMoves.contains(Move.MoveFactory
                .createMove(board,BoardUtils.getCoordinateAtPosition("d5"), BoardUtils.getCoordinateAtPosition("d2"))));
        assertTrue(queenMoves.contains(Move.MoveFactory
                .createMove(board,BoardUtils.getCoordinateAtPosition("d5"), BoardUtils.getCoordinateAtPosition("d1"))));

        assertEquals(26, queenMoves.size());
    }

    @Test
    public void testPawnMove() {
        Board board = Board.createStandardBoard();
        Pawn whitePawn = (Pawn) board.getTile(BoardUtils.getCoordinateAtPosition("a2")).getPiece();
        final Collection<Move> pawnMoves = whitePawn.calculateLegalMoves(board);
        assertTrue(pawnMoves.contains(Move.MoveFactory
                .createMove(board,BoardUtils.getCoordinateAtPosition("a2"), BoardUtils.getCoordinateAtPosition("a3"))));
        assertTrue(pawnMoves.contains(Move.MoveFactory
                .createMove(board,BoardUtils.getCoordinateAtPosition("a2"), BoardUtils.getCoordinateAtPosition("a4"))));
        assertEquals(2, pawnMoves.size());

        //Move a2 white pawn to a4, should have 1 move
        board = board.currentPlayer().makeMove(Move.MoveFactory.createMove(board, BoardUtils.getCoordinateAtPosition("a2"), BoardUtils.getCoordinateAtPosition("a4"))).getTransitionBoard();
        whitePawn = (Pawn) board.getTile(BoardUtils.getCoordinateAtPosition("a4")).getPiece();
        assertEquals(1, whitePawn.calculateLegalMoves(board).size());

        //Move a7 black pawn to a5, should have 0 move
        board = board.currentPlayer().makeMove(Move.MoveFactory.createMove(board, BoardUtils.getCoordinateAtPosition("a7"), BoardUtils.getCoordinateAtPosition("a5"))).getTransitionBoard();
        Pawn blackPawn = (Pawn) board.getTile(BoardUtils.getCoordinateAtPosition("a5")).getPiece();
        assertEquals(0, blackPawn.calculateLegalMoves(board).size());

        //Pawn at a4 should have 0 legal moves
        assertEquals(0, whitePawn.calculateLegalMoves(board).size());
    }

    @Test
    public void testPawnAttackMove() {
        /*
        Given board from fen, following pawns should have attack moves
        a5 - 1 attack move
        b4 - 2 attack moves and 1 pawn move
        c5 - 1 attack move and 1 pawn move
        */
        final String fenString = "rnbqkbnr/1p1ppppp/8/p1p5/PP5P/8/2PPPPP1/RNBQKBNR w KQkq - 0 1";
        final Board board = FenUtils.createGameFromFEN(fenString);
        final Pawn pawnA5 = (Pawn) board.getTile(BoardUtils.getCoordinateAtPosition("a5")).getPiece();
        final Pawn pawnB4 = (Pawn) board.getTile(BoardUtils.getCoordinateAtPosition("b4")).getPiece();
        final Pawn pawnC5 = (Pawn) board.getTile(BoardUtils.getCoordinateAtPosition("c5")).getPiece();

        assertTrue(pawnA5.calculateLegalMoves(board).contains(Move.MoveFactory
                .createMove(board,BoardUtils.getCoordinateAtPosition("a5"), BoardUtils.getCoordinateAtPosition("b4"))));
        assertTrue(pawnB4.calculateLegalMoves(board).contains(Move.MoveFactory
                .createMove(board,BoardUtils.getCoordinateAtPosition("b4"), BoardUtils.getCoordinateAtPosition("a5"))));
        assertTrue(pawnB4.calculateLegalMoves(board).contains(Move.MoveFactory
                .createMove(board,BoardUtils.getCoordinateAtPosition("b4"), BoardUtils.getCoordinateAtPosition("c5"))));
        assertTrue(pawnC5.calculateLegalMoves(board).contains(Move.MoveFactory
                .createMove(board,BoardUtils.getCoordinateAtPosition("c5"), BoardUtils.getCoordinateAtPosition("b4"))));
    }

    @Test
    public void testPawnPromotion() {
        //Given a pawn at c7, then after a move to c8 it should be promoted to a queen
        final String fenString = "k7/2P5/8/8/8/8/8/K7 w - - 0 1";
        Board board = FenUtils.createGameFromFEN(fenString);
        MoveTransition transition = board.currentPlayer()
                .makeMove(Move.MoveFactory.createMove(board, BoardUtils.getCoordinateAtPosition("c7"), BoardUtils.getCoordinateAtPosition("c8")));
        board = transition.getTransitionBoard();
        Piece piece = board.getTile(BoardUtils.getCoordinateAtPosition("c8")).getPiece();
        assertEquals(Piece.PieceType.QUEEN, piece.getPieceType());
    }

    @Test
    public void testPawnPromotionWithAttack() {
        //Given a white pawn at c7 and black knight at d8, then after a move to d8 it should be promoted to a queen
        final String fenString = "k2n4/2P5/8/8/8/8/8/K7 w - - 0 1";
        Board board = FenUtils.createGameFromFEN(fenString);
        MoveTransition transition = board.currentPlayer()
                .makeMove(Move.MoveFactory.createMove(board, BoardUtils.getCoordinateAtPosition("c7"), BoardUtils.getCoordinateAtPosition("d8")));

        board = transition.getTransitionBoard();
        Piece piece = board.getTile(BoardUtils.getCoordinateAtPosition("d8")).getPiece();
        assertEquals(Piece.PieceType.QUEEN, piece.getPieceType());
    }

    @Test
    public void testEnPassant() {
        //Given black to move and a white pawn at e5, then a move to d5 would allow enPassant move for white
        final String fenString = "rnbqkbnr/pppppppp/8/4P3/8/8/PPPP1PPP/RNBQKBNR b KQkq - 0 1";
        Board board = FenUtils.createGameFromFEN(fenString);
        MoveTransition transition = board.currentPlayer().makeMove(
                Move.MoveFactory.createMove(board, BoardUtils.getCoordinateAtPosition("d7"), BoardUtils.getCoordinateAtPosition("d5")));
        board = transition.getTransitionBoard();

        Pawn pawn = (Pawn) board.getTile(BoardUtils.getCoordinateAtPosition("e5")).getPiece();
        Collection<Move> pawnMoves = pawn.calculateLegalMoves(board);
        Move expectedEnPassantMove = new Move.PawnEnPassantAttackMove(board,pawn, BoardUtils.getCoordinateAtPosition("d6"), board.getEnPassantPawn());

        assertEquals(board.getTile(BoardUtils.getCoordinateAtPosition("d5")).getPiece(), board.getEnPassantPawn());
        assertTrue(pawnMoves.contains(expectedEnPassantMove));
    }

    @Test
    public void testEnPassantInCornerPotentialError() {
        //Given black to move and a white pawn at a5, then a move to h5 would not allow enPassant move for white
        final String fenString = "rnbqkbnr/pppppppp/8/P7/8/8/1PPPPPPP/RNBQKBNR b KQkq - 0 1";
        Board board = FenUtils.createGameFromFEN(fenString);
        MoveTransition transition = board.currentPlayer().makeMove(
                Move.MoveFactory.createMove(board, BoardUtils.getCoordinateAtPosition("h7"), BoardUtils.getCoordinateAtPosition("h5")));
        board = transition.getTransitionBoard();

        Pawn pawn = (Pawn) board.getTile(BoardUtils.getCoordinateAtPosition("a5")).getPiece();
        Collection<Move> pawnMoves = pawn.calculateLegalMoves(board);
        Move expectedEnPassantMove = new Move.PawnEnPassantAttackMove(board,pawn, BoardUtils.getCoordinateAtPosition("a5"), board.getEnPassantPawn());

        assertEquals(board.getTile(BoardUtils.getCoordinateAtPosition("h5")).getPiece(), board.getEnPassantPawn());
        assertFalse(pawnMoves.contains(expectedEnPassantMove));
    }
}
