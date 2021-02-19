package pl.isbrandt.server.model;

public class PlayerJoinMessage {

    private String playerId;

    public PlayerJoinMessage() {
    }

    public PlayerJoinMessage(String playerId) {
        this.playerId = playerId;
    }

    public String getPlayerId() {
        return playerId;
    }
}
