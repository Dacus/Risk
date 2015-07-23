package tora.train.risk.clientserver.singleclient.logic;

public enum Status {
    CONNECTED("Player Connected"),
    NOT_CONNECTED("Player not connected"),
    READY("Player is ready"),
    PLAYING("Currently playing");

    private final String message;

    private Status(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return message;
    }
}