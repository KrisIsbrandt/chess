package pl.isbrandt.chess;

import pl.isbrandt.chess.board.Board;
import pl.isbrandt.chess.board.BoardUtils;
import pl.isbrandt.chess.board.Move;
import pl.isbrandt.pgn.FenUtils;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

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

    @Test
    public void testMajorPiecesMovesNotation() {
        final String fenString = "7k/bR6/8/8/1R6/5B2/1R1R1N2/K4Q2 w - - 0 1";
        Board board = FenUtils.createGameFromFEN(fenString);
        String moveNotation = Move.MoveFactory.createMove(board, BoardUtils.getCoordinateAtPosition("f3"),
                BoardUtils.getCoordinateAtPosition("e4")).toString(); //Be4
        String expected = "Be4";
        assertEquals(expected, moveNotation);

        moveNotation = Move.MoveFactory.createMove(board, BoardUtils.getCoordinateAtPosition("f2"),
                BoardUtils.getCoordinateAtPosition("e4")).toString(); //Ne4
        expected = "Ne4";
        assertEquals(expected, moveNotation);

        moveNotation = Move.MoveFactory.createMove(board, BoardUtils.getCoordinateAtPosition("f1"),
                BoardUtils.getCoordinateAtPosition("h1")).toString(); //Qh1+
        expected = "Qh1+";
        assertEquals(expected, moveNotation);

        moveNotation = Move.MoveFactory.createMove(board, BoardUtils.getCoordinateAtPosition("d2"),
                BoardUtils.getCoordinateAtPosition("d8")).toString(); //Rd8#
        expected = "Rd8#";
        assertEquals(expected, moveNotation);

        moveNotation = Move.MoveFactory.createMove(board, BoardUtils.getCoordinateAtPosition("d2"),
                BoardUtils.getCoordinateAtPosition("c2")).toString(); //Rdc2
        expected = "Rdc2";
        assertEquals(expected, moveNotation);

        moveNotation = Move.MoveFactory.createMove(board, BoardUtils.getCoordinateAtPosition("b2"),
                BoardUtils.getCoordinateAtPosition("b3")).toString(); //R2b3
        expected = "R2b3";
        assertEquals(expected, moveNotation);

        moveNotation = Move.MoveFactory.createMove(board, BoardUtils.getCoordinateAtPosition("b4"),
                BoardUtils.getCoordinateAtPosition("d4")).toString(); //Rbd4
        expected = "Rbd4";
        assertEquals(expected, moveNotation);

        moveNotation = Move.MoveFactory.createMove(board, BoardUtils.getCoordinateAtPosition("a1"),
                BoardUtils.getCoordinateAtPosition("a2")).toString(); //Ka2
        expected = "Ka2";
        assertEquals(expected, moveNotation);

        moveNotation = Move.MoveFactory.createMove(board, BoardUtils.getCoordinateAtPosition("b7"),
                BoardUtils.getCoordinateAtPosition("a7")).toString(); //Rxa7
        expected = "Rxa7";
        assertEquals(expected, moveNotation);
    }

    @Test
    public void testAmbiuguitySolver() {
        final String fenString = "k7/1p6/2Q1b1Q1/8/8/8/2Q3Q1/K7 w - - 0 1";
        Board board = FenUtils.createGameFromFEN(fenString);

        String moveNotation = Move.MoveFactory.createMove(board, BoardUtils.getCoordinateAtPosition("c2"),
                BoardUtils.getCoordinateAtPosition("e4")).toString(); //Qc2e4
        String expected = "Qc2e4";
        assertEquals(expected, moveNotation);

        moveNotation = Move.MoveFactory.createMove(board, BoardUtils.getCoordinateAtPosition("g2"),
                BoardUtils.getCoordinateAtPosition("f3")).toString(); //Qgf3
        expected = "Qgf3";
        assertEquals(expected, moveNotation);

        moveNotation = Move.MoveFactory.createMove(board, BoardUtils.getCoordinateAtPosition("g6"),
                BoardUtils.getCoordinateAtPosition("g4")).toString(); //Q6g4
        expected = "Q6g4";
        assertEquals(expected, moveNotation);

        moveNotation = Move.MoveFactory.createMove(board, BoardUtils.getCoordinateAtPosition("c6"),
                BoardUtils.getCoordinateAtPosition("e6")).toString(); //Qcxe6
        expected = "Qcxe6";
        assertEquals(expected, moveNotation);
    }
}
