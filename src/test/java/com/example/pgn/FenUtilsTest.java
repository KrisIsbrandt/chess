package com.example.pgn;

import com.example.chess.Alliance;
import com.example.chess.board.Board;
import com.example.chess.board.BoardUtils;
import com.example.chess.board.Move;
import com.example.chess.piece.*;
import com.example.chess.player.BlackPlayer;
import com.example.chess.player.MoveTransition;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
class FenUtilsTest {

    @Test
    public void testStandardBoardFEN() {
        final Board board = Board.createStandardBoard();
        final String fenString = FenUtils.createFenFromGame(board);
        assertEquals(fenString, "rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1");
    }

    @Test
    public void testBoardFEN1() {
        final Board board = Board.createStandardBoard();
        final MoveTransition transition = board.currentPlayer()
                .makeMove(Move.MoveFactory.createMove(board,
                        BoardUtils.getCoordinateAtPosition("e2"),
                        BoardUtils.getCoordinateAtPosition("e4")));
        assertTrue(transition.getMoveStatus().isDone());
        final String fenString = FenUtils.createFenFromGame(transition.getTransitionBoard());
        assertEquals(fenString, "rnbqkbnr/pppppppp/8/8/4P3/8/PPPP1PPP/RNBQKBNR b KQkq e3 0 1");
    }

    @Test
    public void testFenParser() {
        final String fenString = "4k3/8/8/8/8/8/4P3/R3K3 w Q - 0 1";
        final Board board = FenUtils.createGameFromFEN(fenString);
        assertEquals(new King(4, Alliance.BLACK, false, false),
                board.getTile(4).getPiece());
        assertEquals(new Pawn(52, Alliance.WHITE),
                board.getTile(52).getPiece());
        assertEquals(new Rook(56, Alliance.WHITE),
                board.getTile(56).getPiece());
        assertEquals(new King(60, Alliance.WHITE, false, true),
                board.getTile(60).getPiece());
    }
}