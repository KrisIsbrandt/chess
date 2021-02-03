package pl.isbrandt.chess.player;

public enum MoveStatus {
    DONE {
        @Override
        public boolean isDone() {
            return true;
        }

        @Override
        public boolean isIllegalMove() {
            return false;
        }

        @Override
        public boolean leavesPlayerInCheck() {
            return false;
        }
    },
    ILLEGAL_MOVE {
        @Override
        public boolean isDone() {
            return false;
        }

        @Override
        public boolean isIllegalMove() {
            return true;
        }

        @Override
        public boolean leavesPlayerInCheck() {
            return false;
        }
    },
    LEAVES_PLAYER_IN_CHECK {
        @Override
        public boolean isDone() {
            return false;
        }

        @Override
        public boolean isIllegalMove() {
            return false;
        }

        @Override
        public boolean leavesPlayerInCheck() {
            return true;
        }
    };
    public abstract boolean isDone();
    public abstract boolean isIllegalMove();
    public abstract boolean leavesPlayerInCheck();


}
