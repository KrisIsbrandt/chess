package pl.isbrandt.server.model;

import pl.isbrandt.chess.Alliance;
import pl.isbrandt.chess.board.Board;
import pl.isbrandt.chess.board.BoardUtils;
import pl.isbrandt.chess.board.Move;
import pl.isbrandt.pgn.MoveLog;
import pl.isbrandt.chess.player.MoveTransition;
import pl.isbrandt.pgn.FenUtils;

import java.util.Random;

public class Game {

    private final String INITIAL_FEN_STRING = "rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1";

    private Long id;
    private String boardName;
    private String player1Id;
    private String player2Id;
    private String player1SessionId;
    private String player2SessionId;
    private String whitePlayer;
    private String blackPlayer;
    private Board board;
    private String fen;
    private GameState state;
    private MoveLog moveLog;

    public Game() {
    }

    public Game(String player1Id, String boardName) {
        this.boardName = boardName;
        this.player1Id = player1Id;
        this.player2Id = null;
        if (playerColorRandomizer()) {
            this.whitePlayer = player1Id;
        } else {
            this.blackPlayer = player1Id;
        }
        this.state = GameState.PENDING;
        this.board = Board.createStandardBoard();
        this.fen = INITIAL_FEN_STRING;
        this.moveLog = new MoveLog();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBoardName() {
        return boardName;
    }

    public String getWhitePlayer() {
        return whitePlayer;
    }

    public String getBlackPlayer() {
        return blackPlayer;
    }

    public Board getBoard() {
        return board;
    }

    public void setBoard(Board board) {
        this.board = board;
    }

    public GameState getState() {
        return this.state;
    }

    public void setState(GameState state) {
        this.state = state;
    }

    public String getFen() {
        return fen;
    }

    public boolean playerColorRandomizer() {
        return new Random().nextBoolean();
    }

    public void join(String player2Id) {
        if (this.player2Id == null) {
            this.player2Id = player2Id;
            if (this.whitePlayer == null) {
                this.whitePlayer = player2Id;
            } else {
                this.blackPlayer = player2Id;
            }
            this.state = GameState.WHITE_TO_MOVE;
        }
    }

    public void websocketJoin(String playerId, String sessionId) {
        if (playerId.equals(player1Id)) {
            player1SessionId = sessionId;
        } else if (playerId.equals(player2Id)) {
            player2SessionId = sessionId;
        }
    }

    public boolean isValidPlayerId(String playerId) {
        return playerId.equals(this.player1Id) || playerId.equals(this.player2Id);
    }

    public boolean isValidPlayerSessionId(String playerId) {
        return playerId.equals(this.player1SessionId) || playerId.equals(this.player2SessionId);
    }

    public void makeMove(String playerId, String from, String to) {
        if (!isValidPlayerId(playerId)) return;
        if (!this.state.canMove()) return;
        Alliance playerAlliance = determineAlliance(playerId);

        if (playerAlliance.equals(this.getBoard().currentPlayer().getAlliance())) {
            try {
                Move move = Move.MoveFactory.createMove(this.getBoard(),
                        BoardUtils.getCoordinateAtPosition(from),
                        BoardUtils.getCoordinateAtPosition(to));
                MoveTransition moveTransition = this.getBoard().currentPlayer().makeMove(move);
                if (moveTransition.getMoveStatus().isDone()) {
                    this.board = moveTransition.getTransitionBoard();
                    this.fen = FenUtils.createFenFromGame(this.board);
                    this.moveLog.addMove(move, this.fen);
                    this.state = nextPlayerToPlay(playerAlliance);
                    if (BoardUtils.isEndGame(this.board)) {
                        if (this.board.currentPlayer().isInDraw()) this.state = GameState.DRAW;
                        if (this.board.getWhitePlayer().isInCheckMate()) this.state = GameState.BLACK_WON;
                        if (this.board.getBlackPlayer().isInCheckMate()) this.state = GameState.WHITE_WON;
                    }
                }
            } catch (Exception e) {
                return;
            }
        }
    }

    private GameState nextPlayerToPlay(Alliance playerAlliance) {
        if (playerAlliance.isWhite()) {
            return GameState.BLACK_TO_MOVE;
        } else {
            return GameState.WHITE_TO_MOVE;
        }
    }

    public Alliance determineAlliance(String playerId) {
        if (this.whitePlayer.equals(playerId)) {
            return Alliance.WHITE;
        } else if (this.blackPlayer.equals(playerId)) {
            return Alliance.BLACK;
        }
        return null;
    }

    public String getPlayerIdBasedOnSessionId(String playerSessionId) {
        if (playerSessionId.equals(this.player1SessionId)) {
            return this.player1Id;
        } else if (playerSessionId.equals(this.player2SessionId)) {
            return this.player2Id;
        }
        return null;
    }

    public MoveLog getMoveLog() {
        return this.moveLog;
    }

    public enum GameState {
        PENDING {
            @Override
            public boolean isPending() {
                return true;
            }

            @Override
            public boolean hasEnded() {
                return false;
            }

            @Override
            public boolean canMove() {
                return false;
            }
        },
        WHITE_TO_MOVE {
            @Override
            public boolean isPending() {
                return false;
            }

            @Override
            public boolean hasEnded() {
                return false;
            }

            @Override
            public boolean canMove() {
                return true;
            }
        },
        BLACK_TO_MOVE {
            @Override
            public boolean isPending() {
                return false;
            }

            @Override
            public boolean hasEnded() {
                return false;
            }

            @Override
            public boolean canMove() {
                return true;
            }
        },
        BLACK_WON {
            @Override
            public boolean isPending() {
                return false;
            }

            @Override
            public boolean hasEnded() {
                return true;
            }

            @Override
            public boolean canMove() {
                return false;
            }
        },
        WHITE_WON {
            @Override
            public boolean isPending() {
                return false;
            }

            @Override
            public boolean hasEnded() {
                return true;
            }

            @Override
            public boolean canMove() {
                return false;
            }
        },
        DRAW {
            @Override
            public boolean isPending() {
                return false;
            }

            @Override
            public boolean hasEnded() {
                return true;
            }

            @Override
            public boolean canMove() {
                return false;
            }
        };

        GameState() {}

        public abstract boolean isPending();

        public abstract boolean hasEnded();

        public abstract boolean canMove();
    }
}
