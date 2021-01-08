package com.example.pgn;

import com.example.chess.Alliance;
import com.example.chess.board.Board;
import com.example.chess.board.BoardUtils;
import com.example.chess.piece.*;

import static com.example.chess.board.Board.Builder;

public class FenUtils {

    private FenUtils() {
        throw new RuntimeException("Not instantiable!");
    }

    public static Board createGameFromFEN(final String fenString) {
        return parseFen(fenString);
    }

    public static String createFenFromGame(final Board board) {
        return calculateBoardText(board) + " " +
               calculateCurrentPlayerText(board) + " " +
               calculateCastleText(board) + " " +
               calculateEnPassantSquare(board) + " " +
               //TODO clock implementation
               "0 1";
    }

    private static String calculateEnPassantSquare(final Board board) {
        final Pawn enPassantPawn = board.getEnPassantPawn();
        if (enPassantPawn != null) {
            return BoardUtils.getPositionAtCoordinate(enPassantPawn.getPiecePosition() +
                    (8) * enPassantPawn.getPieceAlliance().getOppositeDirection());
        }
        return "-";
    }

    private static String calculateCastleText(final Board board) {
        final StringBuilder builder = new StringBuilder();
        if (board.getWhitePlayer().isKingSideCastleCapable()) {
            builder.append("K");
        }
        if (board.getWhitePlayer().isQueenSideCastleCapable()) {
            builder.append("Q");
        }
        if (board.getBlackPlayer().isKingSideCastleCapable()) {
            builder.append("k");
        }
        if (board.getBlackPlayer().isQueenSideCastleCapable()) {
            builder.append("q");
        }

        final String result = builder.toString();
        return result.isEmpty() ? "-" : result;
    }

    private static String calculateCurrentPlayerText(final Board board) {
        return board.currentPlayer().getAlliance().toString().substring(0,1).toLowerCase();
    }

    private static String calculateBoardText(final Board board) {
        final StringBuilder builder = new StringBuilder();
        for (int i = 0; i < BoardUtils.NUM_TILES; i++) {
            final String tileText = board.getTile(i).toString();
            builder.append(tileText);
        }
        builder.insert(8,"/");
        builder.insert(17,"/");
        builder.insert(26,"/");
        builder.insert(35,"/");
        builder.insert(44,"/");
        builder.insert(53,"/");
        builder.insert(62,"/");

        return builder.toString().replaceAll("--------", "8")
                .replaceAll("-------", "8")
                .replaceAll("------", "6")
                .replaceAll("-----", "5")
                .replaceAll("----", "4")
                .replaceAll("---", "3")
                .replaceAll("--", "2")
                .replaceAll("-", "1");
    }

    private static Board parseFen(final String fenString) {
        final String[] parsedFen = fenString.split(" ");
        final Builder builder = new Builder();

        builder.setMoveMaker(determineMoveMaker(parsedFen[1]));

        final boolean whiteKingSideCastle = parsedFen[2].contains("K");
        final boolean whiteQueenSideCastle = parsedFen[2].contains("Q");
        final boolean blackKingSideCastle = parsedFen[2].contains("k");
        final boolean blackQueenSideCastle = parsedFen[2].contains("q");

        final int enPassantPawnCoordination = determineEnPassantPawnCoordinate(parsedFen[3]);

        //TODO clock implementation

        final String boardConfig = parsedFen[0];
        final char[]boardTiles = boardConfig.replaceAll("/", "")
                .replaceAll("8", "--------")
                .replaceAll("7", "-------")
                .replaceAll("6", "------")
                .replaceAll("5", "-----")
                .replaceAll("4", "----")
                .replaceAll("3", "---")
                .replaceAll("2", "--")
                .replaceAll("1", "-")
                .toCharArray();

        //rnbqkp
        for (int i = 0; i < boardTiles.length; i++) {
            switch (boardTiles[i]) {
                case 'r':
                    builder.setPiece(new Rook(i, Alliance.BLACK));
                    break;
                case 'n':
                    builder.setPiece(new Knight(i, Alliance.BLACK));
                    break;
                case 'b':
                    builder.setPiece(new Bishop(i, Alliance.BLACK));
                    break;
                case 'q':
                    builder.setPiece(new Queen(i, Alliance.BLACK));
                    break;
                case 'k':
                    builder.setPiece(new King(i, Alliance.BLACK, blackKingSideCastle, blackQueenSideCastle));
                    break;
                case 'p':
                    if (i == enPassantPawnCoordination) {
                        Pawn pawn = new Pawn(i, Alliance.BLACK);
                        builder.setPiece(pawn);
                        builder.setEnPassantPawn(pawn);
                    } else {
                        builder.setPiece(new Pawn(i, Alliance.BLACK));
                    }
                    break;
                case 'R':
                    builder.setPiece(new Rook(i, Alliance.WHITE));
                    break;
                case 'N':
                    builder.setPiece(new Knight(i, Alliance.WHITE));
                    break;
                case 'B':
                    builder.setPiece(new Bishop(i, Alliance.WHITE));
                    break;
                case 'Q':
                    builder.setPiece(new Queen(i, Alliance.WHITE));
                    break;
                case 'K':
                    builder.setPiece(new King(i, Alliance.WHITE, whiteKingSideCastle, whiteQueenSideCastle));
                    break;
                case 'P':
                    if (i == enPassantPawnCoordination) {
                        Pawn pawn = new Pawn(i, Alliance.WHITE);
                        builder.setPiece(pawn);
                        builder.setEnPassantPawn(pawn);
                    } else {
                        builder.setPiece(new Pawn(i, Alliance.WHITE));
                    }
                    break;
                case '-':
                    continue;
                default:
                    throw new RuntimeException("Invalid FEN String " +boardConfig);
            }
        }
        return builder.build();
    }

    private static Alliance determineMoveMaker(final String moveMakerString) {
        if (moveMakerString.equals("w")) return Alliance.WHITE;
        if (moveMakerString.equals("b")) return Alliance.BLACK;
        //if no move maker not provided, return white to move
        return Alliance.WHITE;
    }

    private static int determineEnPassantPawnCoordinate(final String enPassantPawnString) {
        int coordinate = -1;
        try {
            coordinate = BoardUtils.getCoordinateAtPosition(enPassantPawnString);
            if (BoardUtils.SECOND_RANK[coordinate]) {
                coordinate =+ 8;
                return coordinate;
            } else if (BoardUtils.SEVENTH_RANK[coordinate]) {
                coordinate =- 8;
                return coordinate;
            }
        } catch (Exception ignored) {}
        return coordinate;
    }
}
