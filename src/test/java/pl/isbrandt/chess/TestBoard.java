package pl.isbrandt.chess;

import pl.isbrandt.chess.Alliance;
import pl.isbrandt.chess.board.Board;
import pl.isbrandt.chess.board.BoardUtils;
import pl.isbrandt.chess.board.Move;
import pl.isbrandt.chess.piece.Piece;
import com.google.common.collect.Iterables;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TestBoard {

    @Test
    public void testInitialBoard() {

        final Board board = Board.createStandardBoard();
        assertEquals(board.currentPlayer().getLegalMoves().size(), 20);
        assertEquals(board.currentPlayer().getOpponent().getLegalMoves().size(), 20);
        assertFalse(board.currentPlayer().isInCheck());
        assertFalse(board.currentPlayer().isInCheckMate());
        assertFalse(board.currentPlayer().isCastled());
        assertTrue(board.currentPlayer().isKingSideCastleCapable());
        assertTrue(board.currentPlayer().isQueenSideCastleCapable());
        assertEquals(board.currentPlayer(), board.getWhitePlayer());
        assertEquals(board.currentPlayer().getOpponent(), board.getBlackPlayer());
        assertFalse(board.currentPlayer().getOpponent().isInCheck());
        assertFalse(board.currentPlayer().getOpponent().isInCheckMate());
        assertFalse(board.currentPlayer().getOpponent().isCastled());
        assertTrue(board.currentPlayer().getOpponent().isKingSideCastleCapable());
        assertTrue(board.currentPlayer().getOpponent().isQueenSideCastleCapable());

        final Iterable<Piece> allPieces = board.getAllPieces();
        final Iterable<Move> allMoves = Iterables.concat(board.getWhitePlayer().getLegalMoves(), board.getBlackPlayer().getLegalMoves());
        for(final Move move : allMoves) {
            assertFalse(move.isAttack());
            assertFalse(move.isCastlingMove());
        }

        assertEquals(Iterables.size(allMoves), 40);
        assertEquals(Iterables.size(allPieces), 32);
        assertFalse(BoardUtils.isEndGame(board));
    }

    @Test
    public void testInvalidBoard() {
        //Given empty board, then should be thrown RuntimeException
        assertThrows(RuntimeException.class, ()-> {
            final Board.Builder builder = new Board.Builder();
            builder.setMoveMaker (Alliance.WHITE);
            builder.build();
        });
    }
}
