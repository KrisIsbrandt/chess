package pl.isbrandt.server.model;

public class MoveMessage {

    private String playerId;
    private String from;
    private String to;

    public MoveMessage() {
    }

    public MoveMessage(String playerId, String from, String to) {
        this.playerId = playerId;
        this.from = from;
        this.to = to;
    }

    public String getPlayerId() {
        return playerId;
    }

    public String getFrom() {
        return from;
    }

    public String getTo() {
        return to;
    }
}
