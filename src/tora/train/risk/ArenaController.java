package tora.train.risk;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class ArenaController {
    private static final int BONUS_FOR_GROUP = 1;
    private static final int GROUP_SIZE = 3;
    private final Arena arena;
    private List<Player> players;

    /**
     * Default constructor
     */
    public ArenaController() {
        //TODO
        players = new ArrayList<Player>();
        players.add(Player.CPU_MAP_PLAYER);
        this.arena = defaultArena();
    }

    /**
     * Constructor with given arena
     *
     * @param arena the arena the game is played on
     */
    public ArenaController(Arena arena) {
        players = new ArrayList<Player>();
        players.add(Player.CPU_MAP_PLAYER);
        this.arena = arena;
    }

    /**
     * @param p player that wants to get the reinforcements
     * @return the number of units that p is allowed to use for reinforcement
     */
    public int getReinforcements(Player p) {
        //TODO
        return 0;
    }

    /**
     * Adds a player to the game.
     *
     * @param p player to add
     */
    public void addPlayer(Player p) {
        players.add(p);
    }

    /**
     * Moves units from a territory to another.
     *
     * @param nrOfAttackingUnits how many units to move (value > 0)
     * @param init      the territory's coordinates from which the player moves (territory must belong to player)
     * @param dest      the territory's coordinates to which the player moves
     *                  (if territory does not belong to player = attack)
     * @param player    the player that want to move
     * @return true if player can move the specified units from init to dest
     */
    public boolean moveUnits(int nrOfAttackingUnits, Point init, Point dest, Player player) {
        if (!ArenaCommandValidator.validateMove(arena, nrOfAttackingUnits, init, dest, player)) {
            return false;
        }

        if (arena.getTerritoryAtCoordinate(dest).owner != player) {
            resolveAttack(nrOfAttackingUnits, init, dest, player);
        } else {
            transferUnits(nrOfAttackingUnits, init, dest, player);
        }
        return true;
    }

    /**
     * Moves units from a territory to another. (Both territories must belong to the same player)
     *
     * @param nrOfAttackingUnits how many units to move (value > 0)
     * @param init      the territory's coordinates from which the player moves (belongs to player)
     * @param dest      the territory's coordinates to which the player moves (belongs to player)
     * @param player    the player that want to move
     */
    private void transferUnits(int nrOfAttackingUnits, Point init, Point dest, Player player) {
        //TODO
    }

    /**
     * Initiates an attack.
     *
     * @param nrOfAttackingUnits how many units does player use to attack an enemy territory.
     *                           (0 < value <= nr of units available)
     * @param init      the territory's coordinates from which the player initiates the attack. (belongs to player)
     * @param dest      the territory's coordinates that player wants to attack. (does not belong to player)
     * @param player    the player that initiates the attack
     */
    private void resolveAttack(int nrOfAttackingUnits, Point init, Point dest, Player player) {
        int defendingUnits = arena.getTerritoryAtCoordinate(dest).unitNr;
        int attackingKills = calculateKills("attack");
        int defendingKills = calculateKills("defend");

        if (attackingKills >= defendingUnits) {
            changeOwnershipOfTerritory(nrOfAttackingUnits, dest, player, defendingKills);
        } else {
            arena.getTerritoryAtCoordinate(dest).unitNr = defendingUnits - attackingKills;
        }
    }

    /**
     * Changes owner of a territory, after an attack.
     *
     * @param nrOfAttackingUnits how many units attacked the territory
     * @param dest               the territory that was attacked
     * @param player             the player that won the territory
     * @param defendingKills     how many units were on the territory before the attack
     */
    private void changeOwnershipOfTerritory(int nrOfAttackingUnits, Point dest, Player player, int defendingKills) {
        arena.getTerritoryAtCoordinate(dest).owner = player;
        arena.getTerritoryAtCoordinate(dest).unitNr = nrOfAttackingUnits - defendingKills;
        //TODO complete method
    }

    private int calculateKills(String typeOfCombat) {
        //TODO create method for both cases attacking and defending
        return 0;
    }

    /**
     * Initializes territories and places players on the map.
     */
    private Arena defaultArena() {
        //TODO
        return null;
    }

    /**
     * Puts player's units on specified territory.
     *
     * @param nrOfUnits how many units to place on the territory (value > 0)
     * @param dest      the territory's coordinates where the units will be placed (territory must belong to player)
     * @param player    the player that puts his reinforcements
     * @return true if player can put his reinforcements on the specified territory
     */
    private boolean reinforce(int nrOfUnits, Point dest, Player player) {
        if (getReinforcements(player)<nrOfUnits || arena.getTerritoryAtCoordinate(dest).getOwner()!=player)
            return false;
        arena.getTerritoryAtCoordinate(dest).unitNr+=nrOfUnits;
        player.setReinforcements(player.getReinforcements()-nrOfUnits);
        return true;
    }

    private int computePlayerBonus(Player player) {
        int bonus = 0, territories = 0;
        List<Continent> continents = arena.getContinents();
        int N = arena.getXSize(), M = arena.getYSize();
        for (Continent continent : continents) {
            if (continent.getOwnerIfExists().equals(player))
                bonus += continent.getType().getBonus();
        }
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < M; j++) {
                Territory territory = arena.getTerritoryAtCoordinate(i, j);
                if (territory.getOwner().equals(player))
                    territories++;
            }
        }
        bonus += (territories / GROUP_SIZE) * BONUS_FOR_GROUP;
        return bonus;
    }
}
