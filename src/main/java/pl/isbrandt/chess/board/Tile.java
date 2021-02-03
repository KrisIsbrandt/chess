package pl.isbrandt.chess.board;

import pl.isbrandt.chess.piece.Piece;
import com.google.common.collect.ImmutableMap;

import java.util.HashMap;
import java.util.Map;

/**
 * Immutable abstract Tile class with two subclasses, EmptyTile and OccupiedTile.
 * Classes are immutable, so they variables cannot be changed after initialization.
 * Tile class provide static factory for creating new tiles, both occupied and empty.
 * EMPTY_TILES is map holds all empty tiles at the beginning of the game.
 */

public abstract class Tile {

    protected final int tileCoordinate;
    private static final Map<Integer, EmptyTile> EMPTY_TILES_CACHE = createAllPossibleEmptyTiles();

    private Tile(final int tileCoordinate) {
        this.tileCoordinate = tileCoordinate;
    }

    //static Tile Factory
    public static Tile createTile(final int tileCoordinate, final Piece piece) {
        return piece != null ? new OccupiedTile(tileCoordinate, piece) : EMPTY_TILES_CACHE.get(tileCoordinate);
    }

    public abstract boolean isTileOccupied();

    public abstract Piece getPiece();

    public int getTileCoordinate() {
        return tileCoordinate;
    }

    private static Map<Integer, EmptyTile> createAllPossibleEmptyTiles() {
        final Map<Integer, EmptyTile> emptyTileMap = new HashMap<>();
        for (int i = 0; i < BoardUtils.NUM_TILES; i++) {
            emptyTileMap.put(i, new EmptyTile(i));
        }
        //creates immutable map via Guava, that prevents any modification to the map
        return ImmutableMap.copyOf(emptyTileMap);
    }

    //Subclasses
    public static final class EmptyTile extends Tile {

        private EmptyTile(final int tileCoordinate) {
            super(tileCoordinate);
        }

        @Override
        public boolean isTileOccupied() {
            return false;
        }

        @Override
        public Piece getPiece() {
            return null;
        }

        @Override
        public String toString() {
            return "-";
        }
    }

    public static final class OccupiedTile extends Tile {
        private final Piece pieceOnTile;

        private OccupiedTile(final int tileCoordinate, Piece piece) {
            super(tileCoordinate);
            this.pieceOnTile = piece;
        }

        @Override
        public boolean isTileOccupied() {
            return true;
        }

        @Override
        public Piece getPiece() {
            return this.pieceOnTile;
        }

        @Override
        public String toString() {
            return this.getPiece().getPieceAlliance().isBlack() ? getPiece().toString().toLowerCase() :
                    getPiece().toString();
        }
    }
}
