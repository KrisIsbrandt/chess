package com.example.chess.board;

import com.example.chess.piece.Piece;
import com.google.common.collect.ImmutableMap;

import java.util.HashMap;
import java.util.Map;

/**
Immutable abstract Tile class with two subclasses, EmptyTile and OccupiedTile.
Classes are immutable, so they variables cannot be changed after initialization.
Tile class provide static factory for creating new tiles, both occupied and empty.
EMPTY_TILES is map holds all empty tiles at the beginning of the game.
 */

public abstract class Tile {

    protected final int tileCoordinate;
    
    private static final Map<Integer, EmptyTile> EMPTY_TILES = createAllPossibleEmptyTiles();

    private Tile(int tileCoordinate) {
        this.tileCoordinate = tileCoordinate;
    }

    //static Tile Factory
    public static Tile createTile(final int tileCoordinate, final Piece piece) {
        return piece != null ? new OccupiedTile(tileCoordinate, piece) : EMPTY_TILES.get(tileCoordinate);
    }

    public abstract boolean isTileOccupied();

    public abstract Piece getPiece();

    private static Map<Integer, EmptyTile> createAllPossibleEmptyTiles() {
        final Map<Integer, EmptyTile> emptyTileMap = new HashMap<>();
        for (int i = 0; i < 64; i++) {
            emptyTileMap.put(i, new EmptyTile(i));
        }
        //creates immutable map via Guava, that prevents any modification to the map
        return ImmutableMap.copyOf(emptyTileMap);
    }

    public static final class EmptyTile extends Tile {

        EmptyTile(int tileCoordinate) {
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
    }

    public static final class OccupiedTile extends Tile {
        private final Piece pieceOnTile;

        OccupiedTile(int tileCoordinate, Piece piece) {
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
    }
}
