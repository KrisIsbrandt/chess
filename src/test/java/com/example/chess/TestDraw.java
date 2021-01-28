package com.example.chess;

import com.example.chess.board.Board;
import com.example.chess.board.BoardUtils;
import com.example.chess.board.Move;
import com.example.chess.piece.*;
import com.example.pgn.FenUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TestDraw {

    @Test
    public void testDrawOnlyKingsLeft() {
        //Given board with only kings, then its impossible to checkmate and its a draw
        Board.Builder builder = new Board.Builder();
        builder.setMoveMaker(Alliance.WHITE);
        builder.setPiece(new King(0, Alliance.WHITE, false, false));
        builder.setPiece(new King(63, Alliance.BLACK, false, false));
        Board board = builder.build();

        assertTrue(board.getBlackPlayer().isInDraw());
        assertTrue(board.getWhitePlayer().isInDraw());
        assertTrue(BoardUtils.isEndGame(board));
    }

    @Test
    public void testDrawNotAbleToCheckmateWithRemainingPieces1() {
        //Given board with kings and black knight, then its impossible to checkmate and its a draw
        Board.Builder builder = new Board.Builder();
        builder.setMoveMaker(Alliance.WHITE);
        builder.setPiece(new King(0, Alliance.WHITE, false, false));
        builder.setPiece(new King(63, Alliance.BLACK, false, false));
        builder.setPiece(new Knight(5, Alliance.BLACK));
        Board board = builder.build();

        assertTrue(board.getBlackPlayer().isInDraw());
        assertTrue(board.getWhitePlayer().isInDraw());
        assertTrue(BoardUtils.isEndGame(board));
    }

    @Test
    public void testDrawNotAbleToCheckmateWithRemainingPieces2() {
        //Given board with kings and black bishop, then its impossible to checkmate and its a draw
        Board.Builder builder = new Board.Builder();
        builder.setMoveMaker(Alliance.WHITE);
        builder.setPiece(new King(0, Alliance.WHITE, false, false));
        builder.setPiece(new King(63, Alliance.BLACK, false, false));
        builder.setPiece(new Bishop(5, Alliance.BLACK));
        Board board = builder.build();

        assertTrue(board.getBlackPlayer().isInDraw());
        assertTrue(board.getWhitePlayer().isInDraw());
        assertTrue(BoardUtils.isEndGame(board));
    }

    @Test
    public void testDrawNotAbleToCheckmateWithRemainingPieces3() {
        //Given board with kings and opposing bishops on the same color square, then its impossible to checkmate and its a draw
        Board.Builder builder = new Board.Builder();
        builder.setMoveMaker(Alliance.WHITE);
        builder.setPiece(new King(0, Alliance.WHITE, false, false));
        builder.setPiece(new King(63, Alliance.BLACK, false, false));
        builder.setPiece(new Bishop(5, Alliance.BLACK));
        builder.setPiece(new Bishop(7, Alliance.WHITE));
        Board board = builder.build();

        assertTrue(board.getBlackPlayer().isInDraw());
        assertTrue(board.getWhitePlayer().isInDraw());
        assertTrue(BoardUtils.isEndGame(board));
    }

    @Test
    public void testStalemate() {
        //Given board, when white bishop moves to f7 then black is in stalemate
        final String fenString = "5k2/8/5K2/8/8/8/B7/8 w - - 0 1";
        Board board = FenUtils.createGameFromFEN(fenString);
        board = board.getWhitePlayer().makeMove(Move.MoveFactory.createMove(board, BoardUtils.getCoordinateAtPosition("a2"), BoardUtils.getCoordinateAtPosition("f7"))).getTransitionBoard();
        assertFalse(board.getBlackPlayer().isInCheckMate());
        assertTrue(board.getBlackPlayer().isInStaleMate());
        assertTrue(board.currentPlayer().isInDraw());
        assertTrue(BoardUtils.isEndGame(board));
    }
}
