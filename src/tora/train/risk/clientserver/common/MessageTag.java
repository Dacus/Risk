package tora.train.risk.clientserver.common;

/**
 * Type of message
 */

/**
 * Un tag este o bucatica de informatie folosita pentru a specifica o categorie. Un obiect poate avea atasate mai
 * multe tag-uri. Un mesaj poate avea atasate mai multe MessageTag-uri?
 */
public enum MessageTag {
	USER, GLOBAL, IDENTITY, READY, START, CONNECT, STOP, PLAYER_DISCONNECTED, NEW_PLAYER_CONNECTED,
    RESTRICT_CLIENT, RESTRICT_CONNECTION, ONLINE_PLAYERS
}
