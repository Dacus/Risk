package tora.train.risk.clientserver.common;

/**
 * Type of message
 */
public enum MessageType {
	USER, GLOBAL, IDENTITY, READY, START, CONNECT, STOP, PLAYER_DISCONNECTED, NEW_PLAYER_CONNECTED,
    RESTRICT_CLIENT, RESTRICT_CONNECTION, ONLINE_PLAYERS
}
