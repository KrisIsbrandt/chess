package pl.isbrandt.chess;

import org.junit.jupiter.api.Test;
import pl.isbrandt.chess.board.Board;
import pl.isbrandt.chess.board.BoardUtils;
import pl.isbrandt.chess.board.Move;
import pl.isbrandt.pgn.MoveLog;
import pl.isbrandt.pgn.FenUtils;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestMoveLog {

    @Test
    public void testMoveLog() {

        MoveLog moveLog = new MoveLog();

        Board board = Board.createStandardBoard();
        Move move = Move.MoveFactory.createMove(board, BoardUtils.getCoordinateAtPosition("a2"), BoardUtils.getCoordinateAtPosition("a4"));
        board = board.currentPlayer().makeMove(move).getTransitionBoard();
        moveLog.addMove(move);

        move = Move.MoveFactory.createMove(board, BoardUtils.getCoordinateAtPosition("a7"), BoardUtils.getCoordinateAtPosition("a5"));
        board = board.currentPlayer().makeMove(move).getTransitionBoard();
        moveLog.addMove(move);

        move = Move.MoveFactory.createMove(board, BoardUtils.getCoordinateAtPosition("b2"), BoardUtils.getCoordinateAtPosition("b4"));
        board = board.currentPlayer().makeMove(move).getTransitionBoard();
        moveLog.addMove(move);

        move = Move.MoveFactory.createMove(board, BoardUtils.getCoordinateAtPosition("b7"), BoardUtils.getCoordinateAtPosition("b5"));
        board = board.currentPlayer().makeMove(move).getTransitionBoard();
        moveLog.addMove(move);

        move = Move.MoveFactory.createMove(board, BoardUtils.getCoordinateAtPosition("c2"), BoardUtils.getCoordinateAtPosition("c4"));
        board = board.currentPlayer().makeMove(move).getTransitionBoard();
        moveLog.addMove(move);

        move = Move.MoveFactory.createMove(board, BoardUtils.getCoordinateAtPosition("c7"), BoardUtils.getCoordinateAtPosition("c5"));
        board = board.currentPlayer().makeMove(move).getTransitionBoard();
        moveLog.addMove(move);

        move = Move.MoveFactory.createMove(board, BoardUtils.getCoordinateAtPosition("d2"), BoardUtils.getCoordinateAtPosition("d4"));
        board = board.currentPlayer().makeMove(move).getTransitionBoard();
        moveLog.addMove(move);

        move = Move.MoveFactory.createMove(board, BoardUtils.getCoordinateAtPosition("d7"), BoardUtils.getCoordinateAtPosition("d5"));
        board = board.currentPlayer().makeMove(move).getTransitionBoard();
        moveLog.addMove(move);

        move = Move.MoveFactory.createMove(board, BoardUtils.getCoordinateAtPosition("e2"), BoardUtils.getCoordinateAtPosition("e4"));
        board = board.currentPlayer().makeMove(move).getTransitionBoard();
        moveLog.addMove(move);

        move = Move.MoveFactory.createMove(board, BoardUtils.getCoordinateAtPosition("e7"), BoardUtils.getCoordinateAtPosition("e5"));
        board = board.currentPlayer().makeMove(move).getTransitionBoard();
        moveLog.addMove(move);

        move = Move.MoveFactory.createMove(board, BoardUtils.getCoordinateAtPosition("a4"), BoardUtils.getCoordinateAtPosition("b5"));
        board = board.currentPlayer().makeMove(move).getTransitionBoard();
        moveLog.addMove(move);


        String expectedBoard = "rnbqkbnr/5ppp/8/pPppp3/1PPPP3/8/5PPP/RNBQKBNR";
        String actualBoard = FenUtils.createFenFromGame(board).split(" ")[0];
        assertEquals(expectedBoard,actualBoard);

        String expectedPGN = "1. a4 a5 2. b4 b5 3. c4 c5 4. d4 d5 5. e4 e5 6. axb5 ";
        String actualPGN = moveLog.getPgn();
        assertEquals(expectedPGN, actualPGN);
    }


}
