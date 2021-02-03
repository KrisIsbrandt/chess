package pl.isbrandt.chess;

import pl.isbrandt.chess.board.Board;
import pl.isbrandt.chess.board.BoardUtils;
import pl.isbrandt.chess.board.Move;
import pl.isbrandt.chess.player.MoveStatus;
import pl.isbrandt.pgn.FenUtils;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TestKingSafety {

    @Test
    public void testCheckingKing() {
        //Given board, after white queen move to e6, then black is in check and black king should have 3 legal moves that does not leave him in check.
        final String fenString = "8/4k3/8/8/8/1Q2K3/8/8 w - - 0 1";
        Board board = FenUtils.createGameFromFEN(fenString);

        //white queen moves and checks black king
        board = board.currentPlayer().makeMove(Move.MoveFactory
                .createMove(board, BoardUtils.getCoordinateAtPosition("b3"), BoardUtils.getCoordinateAtPosition("e6"))).getTransitionBoard();

        //escape check moves
        final Move move1 = Move.MoveFactory.createMove(board,BoardUtils.getCoordinateAtPosition("e7"), BoardUtils.getCoordinateAtPosition("d8"));
        final Move move2 = Move.MoveFactory.createMove(board,BoardUtils.getCoordinateAtPosition("e7"), BoardUtils.getCoordinateAtPosition("f8"));
        final Move move3 = Move.MoveFactory.createMove(board,BoardUtils.getCoordinateAtPosition("e7"), BoardUtils.getCoordinateAtPosition("e6"));
        //leaves player in check moves
        final Move move4 = Move.MoveFactory.createMove(board,BoardUtils.getCoordinateAtPosition("e7"), BoardUtils.getCoordinateAtPosition("e8"));
        final Move move5 = Move.MoveFactory.createMove(board,BoardUtils.getCoordinateAtPosition("e7"), BoardUtils.getCoordinateAtPosition("d7"));
        final Move move6 = Move.MoveFactory.createMove(board,BoardUtils.getCoordinateAtPosition("e7"), BoardUtils.getCoordinateAtPosition("f7"));
        final Move move7 = Move.MoveFactory.createMove(board,BoardUtils.getCoordinateAtPosition("e7"), BoardUtils.getCoordinateAtPosition("d6"));
        final Move move8 = Move.MoveFactory.createMove(board,BoardUtils.getCoordinateAtPosition("e7"), BoardUtils.getCoordinateAtPosition("f6"));

        assertTrue(board.getBlackPlayer().isInCheck());
        assertEquals(MoveStatus.DONE, board.getBlackPlayer().makeMove(move1).getMoveStatus());
        assertEquals(MoveStatus.DONE, board.getBlackPlayer().makeMove(move2).getMoveStatus());
        assertEquals(MoveStatus.DONE, board.getBlackPlayer().makeMove(move3).getMoveStatus());
        assertEquals(MoveStatus.LEAVES_PLAYER_IN_CHECK, board.getBlackPlayer().makeMove(move4).getMoveStatus());
        assertEquals(MoveStatus.LEAVES_PLAYER_IN_CHECK, board.getBlackPlayer().makeMove(move5).getMoveStatus());
        assertEquals(MoveStatus.LEAVES_PLAYER_IN_CHECK, board.getBlackPlayer().makeMove(move6).getMoveStatus());
        assertEquals(MoveStatus.LEAVES_PLAYER_IN_CHECK, board.getBlackPlayer().makeMove(move7).getMoveStatus());
        assertEquals(MoveStatus.LEAVES_PLAYER_IN_CHECK, board.getBlackPlayer().makeMove(move8).getMoveStatus());
    }

    @Test
    public void testDiscoveredCheck() {
        //Given board, after white knight move to g4, then black is in check by white queen
        final String fenString = "8/1b2k3/8/4N3/4Q3/4K3/8/8 w - - 0 1";
        Board board = FenUtils.createGameFromFEN(fenString);

        //white knight moves and queen checks black king
        board = board.currentPlayer().makeMove(Move.MoveFactory
                .createMove(board, BoardUtils.getCoordinateAtPosition("e5"), BoardUtils.getCoordinateAtPosition("g4"))).getTransitionBoard();

        //escape check moves
        final Move move1 = Move.MoveFactory.createMove(board,BoardUtils.getCoordinateAtPosition("e7"), BoardUtils.getCoordinateAtPosition("d8"));
        final Move move2 = Move.MoveFactory.createMove(board,BoardUtils.getCoordinateAtPosition("e7"), BoardUtils.getCoordinateAtPosition("f8"));
        final Move move3 = Move.MoveFactory.createMove(board,BoardUtils.getCoordinateAtPosition("e7"), BoardUtils.getCoordinateAtPosition("d7"));
        final Move move4 = Move.MoveFactory.createMove(board,BoardUtils.getCoordinateAtPosition("e7"), BoardUtils.getCoordinateAtPosition("f7"));
        final Move move5 = Move.MoveFactory.createMove(board,BoardUtils.getCoordinateAtPosition("e7"), BoardUtils.getCoordinateAtPosition("d6"));
        final Move move6 = Move.MoveFactory.createMove(board,BoardUtils.getCoordinateAtPosition("b7"), BoardUtils.getCoordinateAtPosition("e4"));
        //leaves player in check moves
        final Move move7 = Move.MoveFactory.createMove(board,BoardUtils.getCoordinateAtPosition("e7"), BoardUtils.getCoordinateAtPosition("e6"));
        final Move move8 = Move.MoveFactory.createMove(board,BoardUtils.getCoordinateAtPosition("e7"), BoardUtils.getCoordinateAtPosition("e8"));
        final Move move9 = Move.MoveFactory.createMove(board,BoardUtils.getCoordinateAtPosition("e7"), BoardUtils.getCoordinateAtPosition("f6"));

        assertTrue(board.getBlackPlayer().isInCheck());
        assertEquals(MoveStatus.DONE, board.getBlackPlayer().makeMove(move1).getMoveStatus());
        assertEquals(MoveStatus.DONE, board.getBlackPlayer().makeMove(move2).getMoveStatus());
        assertEquals(MoveStatus.DONE, board.getBlackPlayer().makeMove(move3).getMoveStatus());
        assertEquals(MoveStatus.DONE, board.getBlackPlayer().makeMove(move4).getMoveStatus());
        assertEquals(MoveStatus.DONE, board.getBlackPlayer().makeMove(move5).getMoveStatus());
        assertEquals(MoveStatus.DONE, board.getBlackPlayer().makeMove(move6).getMoveStatus());
        assertEquals(MoveStatus.LEAVES_PLAYER_IN_CHECK, board.getBlackPlayer().makeMove(move7).getMoveStatus());
        assertEquals(MoveStatus.LEAVES_PLAYER_IN_CHECK, board.getBlackPlayer().makeMove(move8).getMoveStatus());
        assertEquals(MoveStatus.LEAVES_PLAYER_IN_CHECK, board.getBlackPlayer().makeMove(move9).getMoveStatus());
    }

    @Test
    public void testDoubleCheck() {
        //Given board, after white knight move to g6, then black is in double check by white queen and white knight
        final String fenString = "8/1b2k3/8/4N3/4Q3/4K3/8/8 w - - 0 1";
        Board board = FenUtils.createGameFromFEN(fenString);

        //white knight moves and queen checks black king
        board = board.currentPlayer().makeMove(Move.MoveFactory
                .createMove(board, BoardUtils.getCoordinateAtPosition("e5"), BoardUtils.getCoordinateAtPosition("g6"))).getTransitionBoard();
        assertTrue(board.getBlackPlayer().isInCheck());

        //escape check moves
        final Move move1 = Move.MoveFactory.createMove(board,BoardUtils.getCoordinateAtPosition("e7"), BoardUtils.getCoordinateAtPosition("d8"));
        final Move move2 = Move.MoveFactory.createMove(board,BoardUtils.getCoordinateAtPosition("e7"), BoardUtils.getCoordinateAtPosition("d7"));
        final Move move3 = Move.MoveFactory.createMove(board,BoardUtils.getCoordinateAtPosition("e7"), BoardUtils.getCoordinateAtPosition("f7"));
        final Move move4 = Move.MoveFactory.createMove(board,BoardUtils.getCoordinateAtPosition("e7"), BoardUtils.getCoordinateAtPosition("d6"));
        final Move move5 = Move.MoveFactory.createMove(board,BoardUtils.getCoordinateAtPosition("e7"), BoardUtils.getCoordinateAtPosition("f6"));
        //leaves player in check moves
        final Move move6 = Move.MoveFactory.createMove(board,BoardUtils.getCoordinateAtPosition("e7"), BoardUtils.getCoordinateAtPosition("f8"));
        final Move move7 = Move.MoveFactory.createMove(board,BoardUtils.getCoordinateAtPosition("b7"), BoardUtils.getCoordinateAtPosition("e4"));
        final Move move8 = Move.MoveFactory.createMove(board,BoardUtils.getCoordinateAtPosition("e7"), BoardUtils.getCoordinateAtPosition("e6"));
        final Move move9 = Move.MoveFactory.createMove(board,BoardUtils.getCoordinateAtPosition("e7"), BoardUtils.getCoordinateAtPosition("e8"));

        assertTrue(board.getBlackPlayer().isInCheck());
        assertEquals(MoveStatus.DONE, board.getBlackPlayer().makeMove(move1).getMoveStatus());
        assertEquals(MoveStatus.DONE, board.getBlackPlayer().makeMove(move2).getMoveStatus());
        assertEquals(MoveStatus.DONE, board.getBlackPlayer().makeMove(move3).getMoveStatus());
        assertEquals(MoveStatus.DONE, board.getBlackPlayer().makeMove(move4).getMoveStatus());
        assertEquals(MoveStatus.DONE, board.getBlackPlayer().makeMove(move5).getMoveStatus());
        assertEquals(MoveStatus.LEAVES_PLAYER_IN_CHECK, board.getBlackPlayer().makeMove(move6).getMoveStatus());
        assertEquals(MoveStatus.LEAVES_PLAYER_IN_CHECK, board.getBlackPlayer().makeMove(move7).getMoveStatus());
        assertEquals(MoveStatus.LEAVES_PLAYER_IN_CHECK, board.getBlackPlayer().makeMove(move8).getMoveStatus());
        assertEquals(MoveStatus.LEAVES_PLAYER_IN_CHECK, board.getBlackPlayer().makeMove(move9).getMoveStatus());
    }

    @Test
    public void testCheckmate() {
        //Given board, black is in checkmate and the game has ended
        final String fenString = "7k/6Q1/5K2/8/8/8/8/8 b - - 0 1";
        final Board board = FenUtils.createGameFromFEN(fenString);

        assertTrue(board.getBlackPlayer().isInCheckMate());
        assertFalse(board.getBlackPlayer().isInStaleMate());
        assertTrue(BoardUtils.isEndGame(board));
    }

    @Test
    public void testFoolsMate() {
        Board board = Board.createStandardBoard();
        board = board.getWhitePlayer().makeMove(Move.MoveFactory.createMove(board, BoardUtils.getCoordinateAtPosition("f2"), BoardUtils.getCoordinateAtPosition("f3"))).getTransitionBoard();
        board = board.getBlackPlayer().makeMove(Move.MoveFactory.createMove(board, BoardUtils.getCoordinateAtPosition("e7"), BoardUtils.getCoordinateAtPosition("e5"))).getTransitionBoard();
        board = board.getWhitePlayer().makeMove(Move.MoveFactory.createMove(board, BoardUtils.getCoordinateAtPosition("g2"), BoardUtils.getCoordinateAtPosition("g4"))).getTransitionBoard();
        board = board.getBlackPlayer().makeMove(Move.MoveFactory.createMove(board, BoardUtils.getCoordinateAtPosition("d8"), BoardUtils.getCoordinateAtPosition("h4"))).getTransitionBoard();
        assertTrue(board.getWhitePlayer().isInCheckMate());
        assertTrue(BoardUtils.isEndGame(board));
    }

    @Test
    public void testScholarsMate() {
        Board board = Board.createStandardBoard();
        board = board.getWhitePlayer().makeMove(Move.MoveFactory.createMove(board, BoardUtils.getCoordinateAtPosition("e2"), BoardUtils.getCoordinateAtPosition("e4"))).getTransitionBoard();
        board = board.getBlackPlayer().makeMove(Move.MoveFactory.createMove(board, BoardUtils.getCoordinateAtPosition("e7"), BoardUtils.getCoordinateAtPosition("e5"))).getTransitionBoard();
        board = board.getWhitePlayer().makeMove(Move.MoveFactory.createMove(board, BoardUtils.getCoordinateAtPosition("d1"), BoardUtils.getCoordinateAtPosition("f3"))).getTransitionBoard();
        board = board.getBlackPlayer().makeMove(Move.MoveFactory.createMove(board, BoardUtils.getCoordinateAtPosition("b8"), BoardUtils.getCoordinateAtPosition("c6"))).getTransitionBoard();
        board = board.getWhitePlayer().makeMove(Move.MoveFactory.createMove(board, BoardUtils.getCoordinateAtPosition("f1"), BoardUtils.getCoordinateAtPosition("c4"))).getTransitionBoard();
        board = board.getBlackPlayer().makeMove(Move.MoveFactory.createMove(board, BoardUtils.getCoordinateAtPosition("h7"), BoardUtils.getCoordinateAtPosition("h6"))).getTransitionBoard();
        board = board.getWhitePlayer().makeMove(Move.MoveFactory.createMove(board, BoardUtils.getCoordinateAtPosition("f3"), BoardUtils.getCoordinateAtPosition("f7"))).getTransitionBoard();

        assertTrue(board.getBlackPlayer().isInCheckMate());
        assertTrue(BoardUtils.isEndGame(board));
    }

    @Test
    public void testSmotheredMate() {
        //Given board, white should mate in 1 with knight to f7
        final String fenString = "6rk/6pp/8/6N1/8/8/5K2/8 w - - 0 1";
        Board board = FenUtils.createGameFromFEN(fenString);
        board = board.getWhitePlayer().makeMove(Move.MoveFactory.createMove(board, BoardUtils.getCoordinateAtPosition("g5"), BoardUtils.getCoordinateAtPosition("f7"))).getTransitionBoard();
        assertTrue(board.getBlackPlayer().isInCheckMate());
        assertTrue(BoardUtils.isEndGame(board));
    }

    @Test
    public void testIllegalKingMoveNextToOpponentsKing() {
        //Given board, when white king moves to black king, then it should leave him in check and thus its illegal move
        final String fenString = "8/8/8/4k3/8/P3K3/8/8 w - - 0 1";
        final Board board = FenUtils.createGameFromFEN(fenString);

        final Move move1 = Move.MoveFactory.createMove(board,BoardUtils.getCoordinateAtPosition("e3"), BoardUtils.getCoordinateAtPosition("d4"));
        final Move move2 = Move.MoveFactory.createMove(board,BoardUtils.getCoordinateAtPosition("e3"), BoardUtils.getCoordinateAtPosition("e4"));
        final Move move3 = Move.MoveFactory.createMove(board,BoardUtils.getCoordinateAtPosition("e3"), BoardUtils.getCoordinateAtPosition("f4"));

        assertEquals(MoveStatus.LEAVES_PLAYER_IN_CHECK, board.getWhitePlayer().makeMove(move1).getMoveStatus());
        assertEquals(MoveStatus.LEAVES_PLAYER_IN_CHECK, board.getWhitePlayer().makeMove(move2).getMoveStatus());
        assertEquals(MoveStatus.LEAVES_PLAYER_IN_CHECK, board.getWhitePlayer().makeMove(move3).getMoveStatus());
    }
}
