package pl.isbrandt.server.model;

public class GameDTO {

    private Long id;
    private String boardName;
    private String fen;
    private String whitePlayerId;
    private String blackPlayerId;
    private Game.GameState state;

    public GameDTO() {}

    public GameDTO(Long id, String boardName) {
        this.id = id;
        this.boardName = boardName;
    }

    public GameDTO(Game game) {
        this.id = game.getId();
        this.boardName = game.getBoardName();
        this.fen = game.getFen();
        this.whitePlayerId = game.getWhitePlayer();
        this.blackPlayerId = game.getBlackPlayer();
        this.state = game.getState();
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

    public void setBoardName(String boardName) {
        this.boardName = boardName;
    }

    public String getFen() {
        return fen;
    }

    public void setFen(String fen) {
        this.fen = fen;
    }

    public Game.GameState getState() {
        return state;
    }

    public void setState(Game.GameState state) {
        this.state = state;
    }

    public String getWhitePlayerId() {
        return whitePlayerId;
    }

    public void setWhitePlayerId(String whitePlayerId) {
        this.whitePlayerId = whitePlayerId;
    }

    public String getBlackPlayerId() {
        return blackPlayerId;
    }

    public void setBlackPlayerId(String blackPlayerId) {
        this.blackPlayerId = blackPlayerId;
    }
}
