package pl.isbrandt.chess.board;

import com.google.common.collect.ImmutableMap;

import java.util.HashMap;
import java.util.Map;

/**
 * BoardUtils class provides static data used by other classes
 * the constructor is private and even if it is called it will throw RunTimeException
 */
public class BoardUtils {

    public static final int NUM_TILES = 64;
    public static final int NUM_TILES_PER_ROW = 8;
    public static final int NUM_TILES_PER_COLUMN = 8;

    public static final boolean[] FIRST_COLUMN = initColumn(0);
    public static final boolean[] SECOND_COLUMN = initColumn(1);
    public static final boolean[] SEVENTH_COLUMN = initColumn(6);
    public static final boolean[] EIGHTH_COLUMN = initColumn(7);

    public static final boolean[] FIRST_RANK = initRow(0);
    public static final boolean[] SECOND_RANK = initRow(8);
    public static final boolean[] THIRD_RANK = initRow(16);
    public static final boolean[] FOURTH_RANK = initRow(24);
    public static final boolean[] FIFTH_RANK = initRow(32);
    public static final boolean[] SIX_RANK = initRow(40);
    public static final boolean[] SEVENTH_RANK = initRow(48);
    public static final boolean[] EIGHT_RANK = initRow(56);

    public static final String[] ALGEBRAIC_NOTATION = initializeAlgebraicNotation();

    private static String[] initializeAlgebraicNotation() {
        String[] algebraicNotation = new String[NUM_TILES];
        int counter = 0;
        for (int row = NUM_TILES_PER_ROW; row > 0 ; row--) {
            for (int column = 'a'; column <= 'h'; column++) {
                algebraicNotation[counter] = String.valueOf((char) column) + row;
                counter++;
            }
        }
        return algebraicNotation;
    }
    public static final Map<String, Integer> POSITION_TO_COORDINATE = initializePositionToCoordinateMap();

    private static Map<String, Integer> initializePositionToCoordinateMap() {
        Map<String, Integer> positionToCoordinateMap = new HashMap<>();
        for (int i = 0; i < NUM_TILES; i++) {
            positionToCoordinateMap.put(ALGEBRAIC_NOTATION[i], i);
        }
        return ImmutableMap.copyOf(positionToCoordinateMap);
    }


    private BoardUtils() {
        throw new RuntimeException("You cannot instantiate BoardUtils class");
    }

    private static boolean[] initColumn(int columnNumber) {
        final boolean[] column = new boolean[NUM_TILES];
        do {
            column[columnNumber] = true;
            columnNumber += NUM_TILES_PER_ROW;
        } while (columnNumber < NUM_TILES);
        return column;
    }

    private static boolean[] initRow(int rowNumber) {
        final boolean[] row = new boolean[NUM_TILES];
        do {
            row[rowNumber] = true;
            rowNumber++;
        } while (rowNumber % NUM_TILES_PER_ROW != 0);
        return row;
    }

    public static boolean isValidCoordinate(final int coordinate) {
        return coordinate >= 0 && coordinate < NUM_TILES;
    }

    public static int getCoordinateAtPosition(final String position) {
        return POSITION_TO_COORDINATE.get(position);
    }

    public static String getPositionAtCoordinate(final int coordinate) {
        return ALGEBRAIC_NOTATION[coordinate];
    }

    public static boolean isEndGame(final Board board) {
        return board.currentPlayer().isInCheckMate() || board.currentPlayer().isInDraw();
    }

    public static boolean isWhiteTile(final int coordinate) {
        if (FIRST_RANK[coordinate] || THIRD_RANK[coordinate] || FIFTH_RANK[coordinate] || SEVENTH_RANK[coordinate]) {
            return coordinate % 2 == 0;
        } else {
            return coordinate % 2 == 1;
        }
    }

    public static boolean isSameRank(int coordinate1, int coordinate2) {
        return BoardUtils.getPositionAtCoordinate(coordinate1).substring(1,2).equals(BoardUtils.getPositionAtCoordinate(coordinate2).substring(1,2));
    }

    public static boolean isSameColumn(int coordinate1, int coordinate2) {

        return BoardUtils.getPositionAtCoordinate(coordinate1).substring(0,1).equals(BoardUtils.getPositionAtCoordinate(coordinate2).substring(0,1));
    }
}
