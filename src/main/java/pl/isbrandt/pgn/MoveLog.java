package pl.isbrandt.pgn;

import pl.isbrandt.chess.board.BoardUtils;
import pl.isbrandt.chess.board.Move;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class MoveLog {

    private final List<Entry> log;
    private String pgn;

    public MoveLog() {
        this.log = new ArrayList<>();
        this.pgn = "";
    }

    public void addMove(final Move move) {
        this.log.add(new Entry(move));
        updatePGN(move);
    }

    public void addMove(final Move move, final String fenString) {
        this.log.add(new Entry(move, fenString));
        updatePGN(move);
    }

    public boolean hasThreefoldRepetitionOccurred() {
        Map<String, Long> map = this.log.stream()
                .collect(Collectors.groupingBy(Entry::getFenString, Collectors.counting()));
        for (Map.Entry<String, Long> entry : map.entrySet()) {
            if(entry.getValue() >= 3) return true;
        }
        return false;
    }

    public List<Entry> getLog() {
        return this.log;
    }

    private void updatePGN(Move move) {
        final StringBuilder sb = new StringBuilder(this.pgn);
        int moveCount = this.log.size();
        if (moveCount % 2 != 0) {
            //white moved last
            sb.append(moveCount / 2 + 1)
                    .append(". ")
                    .append(move.toString())
                    .append(" ");
        } else {
            //black moved last
            sb.append(move.toString())
                    .append(" ");
        }
        this.pgn = sb.toString();
    }

    public String getPgn() {
        return this.pgn;
    }

    private static class Entry {

        private final String initialCoordinate;
        private final String destinationCoordinate;
        private final String movePGN;
        private final String fenString;

        public Entry(final Move move) {
            this.initialCoordinate = BoardUtils.getPositionAtCoordinate(move.getCurrentCoordinate());
            this.destinationCoordinate = BoardUtils.getPositionAtCoordinate(move.getDestinationCoordinate());
            this.movePGN = move.toString();
            this.fenString = FenUtils.createFenFromGame(move.execute());
        }

        public Entry(final Move move, final String fenString) {
            this.initialCoordinate = BoardUtils.getPositionAtCoordinate(move.getCurrentCoordinate());
            this.destinationCoordinate = BoardUtils.getPositionAtCoordinate(move.getDestinationCoordinate());
            this.movePGN = move.toString();
            this.fenString = fenString;
        }

        public String getMovePGN() {
            return this.movePGN;
        }

        public String getFenString() {
            return fenString;
        }

        public String getInitialCoordinate() {
            return initialCoordinate;
        }

        public String getDestinationCoordinate() {
            return destinationCoordinate;
        }
    }
}
