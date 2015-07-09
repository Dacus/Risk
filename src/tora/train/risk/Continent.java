package tora.train.risk;

import java.util.ArrayList;
import java.util.List;


public class Continent {
    private ContinentType type;
    private List<Player> currentPlayers;

    public Continent(ContinentType type) {
        this.type = type;
        currentPlayers = new ArrayList<>();
    }

    public Player getOwnerIfExists() {
        if (currentPlayers.size() == 1)
            return currentPlayers.get(0);
        else return null;
    }

    /**
     * @param player player to be added as owning a territory on this continent
     * @return true if the addition was successful, false otherwise
     */
    public boolean addPlayer(Player player) {
        return currentPlayers.add(player);
    }

    /**
     * @param player player to be removed because it no longer owns a territory on this continent
     * @return true if the addition was successful, false otherwise
     */
    public boolean removePlayer(Player player) {
        return currentPlayers.remove(player);
    }

    /**
     * @return the type of the continent
     */
    public ContinentType getType() {
        return type;
    }
}
