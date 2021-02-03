package pl.isbrandt.chess.board;

import pl.isbrandt.chess.board.BoardUtils;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TestBoardUtils {

    @Test
    public void testIsWhiteTileMethod() {
        assertTrue(BoardUtils.isWhiteTile(0));
        assertTrue(BoardUtils.isWhiteTile(2));
        assertTrue(BoardUtils.isWhiteTile(9));
        assertTrue(BoardUtils.isWhiteTile(16));
        assertTrue(BoardUtils.isWhiteTile(18));
        assertTrue(BoardUtils.isWhiteTile(63));
        assertFalse(BoardUtils.isWhiteTile(1));
        assertFalse(BoardUtils.isWhiteTile(3));
        assertFalse(BoardUtils.isWhiteTile(8));
        assertFalse(BoardUtils.isWhiteTile(10));
        assertFalse(BoardUtils.isWhiteTile(62));
    }
}