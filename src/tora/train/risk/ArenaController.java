package tora.train.risk;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

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
        this.arena = new Arena();
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
     * @param nrOfUnits how many units to move (value > 0)
     * @param from      the territory's coordinates from which the player moves (belongs to player)
     * @param to      the territory's coordinates to which the player moves (belongs to player)
     * @param player    the player that want to move
     */
    private void transferUnits(int nrOfUnits, Point from, Point to, Player player) {
        Territory fromTerritory=arena.getTerritoryAtCoordinate(from.x, from.y);
        Territory toTerritory=arena.getTerritoryAtCoordinate(to.x, to.y);

        fromTerritory.unitNr-=nrOfUnits;
        toTerritory.unitNr+=nrOfUnits;
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
        int attackingKills = calculateKills(CombatType.ATTACK, nrOfAttackingUnits);
        int defendingKills = calculateKills(CombatType.DEFEND, defendingUnits);

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
        arena.getTerritoryAtCoordinate(dest).continent.addPlayer(player);
    }

    /**
     * Method that randomizes the number of units that will be killed
     * in a fight, depending of they either defend or attack
     *
     * @param type          the combat type the units are engaged in
     * @param numberOfUnits the number of units that are involved
     * @return the number of kills it delivered
     */
    private int calculateKills(CombatType type, int numberOfUnits) {
        Random randomizer = new Random();
        int kills = 0;
        for (int i = 0; i < numberOfUnits; i++) {
            int chance = randomizer.nextInt(100);
            if (chance < type.winChance)
                kills++;
        }

        return kills;
    }

    /**
     * Initializes territories and places players on the map.
     */
    private Arena defaultArena() {
        //TODO
        return null;
    }

    /**
     * Distributes players on arena's map.
     *
     * (players list must be complete before running this method)
     * @param territoriesPerPlayer how many territories to distribute for each player (value >= 0)
     * @param unitsPerTerritory how many units should each territory have (value >= 0)
     * @return true if distribution succeeded /
     *   false if distribution cannot be done because there are not enough territories on the map to fulfill the request
     */
    private boolean distributePlayers(int territoriesPerPlayer, int unitsPerTerritory) {
        if ((territoriesPerPlayer < 0) || (unitsPerTerritory < 0))
            return false;

        ArrayList<Territory> distributableTerritories = new ArrayList<>();

        //search distributable territories in arena and put them in the list
        for (int i = 0; i < arena.getXSize(); i++) {
            for (int j = 0; j < arena.getYSize(); j++) {
                Territory currentTerritory = arena.getTerritoryAtCoordinate(i, j);
                if (currentTerritory.getContinent().getType().isDistributable())
                    distributableTerritories.add(currentTerritory);
            }
        }

        //territories cannot be distributed if they are not enough
        if (territoriesPerPlayer * (players.size() - 1) > distributableTerritories.size())
            return false;

        for (int i = 0; i < territoriesPerPlayer; i++) {
            //for each player, distribute a random territory
            for (int playerID = 0; playerID < players.size(); playerID++) {
                //if current player is computer, skip
                if (players.get(playerID) == Player.CPU_MAP_PLAYER)
                    continue;

                Random generator = new Random();
                int territoryID = generator.nextInt(distributableTerritories.size());
                distributableTerritories.get(territoryID).owner = players.get(playerID);

                //mark current territory as distributed
                distributableTerritories.remove(territoryID);
            }
        }
        return true;
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

    /**
     * Enum with the type of combats we encounter on the map
     */
    private static enum CombatType {
        DEFEND(70),
        ATTACK(60);

        private final int winChance;

        /**
         * @param winChance  < 100% percentage that represents what is the chance of one unit killing
         * another in a fight of this type
         */
        private CombatType(int winChance) {
            this.winChance = winChance;
        }
    }
}
