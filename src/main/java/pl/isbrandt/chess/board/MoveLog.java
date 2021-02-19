package pl.isbrandt.chess.board;

import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Set;

public class MoveLog {

    private final Set<ExecutedMove> log;

    public MoveLog() {
        this.log = new LinkedHashSet<>();
    }

    public void logMove(final Move move) {
        log.add(new ExecutedMove(move));
    }

    public String logToPGN(final Board board) {
        StringBuilder sb = new StringBuilder(log.size());
        Iterator iterator = log.iterator();

        int moveCounter = 1;
        boolean whiteToPlay = true;
        while(iterator.hasNext()) {
            ExecutedMove move = (ExecutedMove) iterator.next();
            if (whiteToPlay) {
                sb.append(moveCounter++)
                        .append(". ")
                        .append(move.getMovePGN())
                        .append(" ");
                whiteToPlay = false;
            } else {
                sb.append(move.getMovePGN())
                        .append(" ");
                whiteToPlay = true;
            }
        }
        //TODO insert board info into pgn file
        return sb.toString();
    }

    private class ExecutedMove {

        private final String initialPosition;
        private final String destinationPosition;
        private final String movePGN;

        public ExecutedMove(final Move move) {
            this.initialPosition = BoardUtils.getPositionAtCoordinate(
                    move.getMovedPiece().getPiecePosition());
            this.destinationPosition = BoardUtils.getPositionAtCoordinate(
                    move.getDestinationCoordinate());
            this.movePGN = move.toString();
        }

        public String getInitialPosition() {
            return this.initialPosition;
        }

        public String getDestinationPosition() {
            return this.destinationPosition;
        }

        public String getMovePGN() {
            return this.movePGN;
        }
    }
}
