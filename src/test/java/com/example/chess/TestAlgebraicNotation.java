package com.example.chess;

import com.example.chess.board.Board;
import com.example.chess.board.BoardUtils;
import com.example.chess.board.Move;
import com.example.pgn.FenUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TestAlgebraicNotation {

    @Test
    public void testPawnMovesNotation() {
        //Given board, if a specific pawn moves, then it should match expected notation
        final String fenString = "k6n/2P3P1/1P6/4Pp2/8/4p3/PP1P4/3K4 w - f6 0 1";
        Board board = FenUtils.createGameFromFEN(fenString);
        String moveNotation = Move.MoveFactory.createMove(board, BoardUtils.getCoordinateAtPosition("a2"),BoardUtils.getCoordinateAtPosition("a3")).toString(); //a3
        String expected = "a3";
        assertEquals(expected, moveNotation);

        moveNotation = Move.MoveFactory.createMove(board, BoardUtils.getCoordinateAtPosition("b2"),BoardUtils.getCoordinateAtPosition("b4")).toString(); //b4
        expected = "b4";
        assertEquals(expected, moveNotation);

        moveNotation = Move.MoveFactory.createMove(board, BoardUtils.getCoordinateAtPosition("d2"),BoardUtils.getCoordinateAtPosition("e3")).toString(); //dxe3
        expected = "dxe3";
        assertEquals(expected, moveNotation);

        moveNotation = Move.MoveFactory.createMove(board, BoardUtils.getCoordinateAtPosition("b6"),BoardUtils.getCoordinateAtPosition("b7")).toString(); //b7+
        expected = "b7+";
        assertEquals(expected, moveNotation);

        moveNotation = Move.MoveFactory.createMove(board, BoardUtils.getCoordinateAtPosition("c7"),BoardUtils.getCoordinateAtPosition("c8")).toString(); //c8=Q#
        expected = "c8=Q#";
        assertEquals(expected, moveNotation);

        moveNotation = Move.MoveFactory.createMove(board, BoardUtils.getCoordinateAtPosition("g7"),BoardUtils.getCoordinateAtPosition("g8")).toString(); //g8=Q+
        expected = "g8=Q+";
        assertEquals(expected, moveNotation);

        moveNotation = Move.MoveFactory.createMove(board, BoardUtils.getCoordinateAtPosition("g7"),BoardUtils.getCoordinateAtPosition("h8")).toString(); //gxh8=Q+
        expected = "gxh8=Q+";
        assertEquals(expected, moveNotation);

        moveNotation = Move.MoveFactory.createMove(board, BoardUtils.getCoordinateAtPosition("e5"),BoardUtils.getCoordinateAtPosition("f6")).toString(); //exf6
        expected = "exf6";
        assertEquals(expected, moveNotation);
    }

}
