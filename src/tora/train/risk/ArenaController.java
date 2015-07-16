package tora.train.risk;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ArenaController {
    private static final int BONUS_FOR_GROUP = 1;
    private static final int GROUP_SIZE = 3;
    private static final int DEFAULT_BONUS = 5;
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

        if (!arena.getTerritoryAtCoordinate(dest).getOwner().equals(player)) {
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

        fromTerritory.setUnitNr(fromTerritory.getUnitNr() - nrOfUnits);
        toTerritory.setUnitNr(toTerritory.getUnitNr() + nrOfUnits);
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
        int defendingUnits = arena.getTerritoryAtCoordinate(dest).getUnitNr();
        int attackingKills = calculateKills(CombatType.ATTACK, nrOfAttackingUnits);
        int defendingKills = calculateKills(CombatType.DEFEND, defendingUnits);
        int unitsOnAttackingTerritory = arena.getTerritoryAtCoordinate(init).getUnitNr();

        if (attackingKills >= defendingUnits) {
            if (defendingKills < nrOfAttackingUnits) {
                changeOwnershipOfTerritory(nrOfAttackingUnits, dest, player, defendingKills);
                arena.getTerritoryAtCoordinate(init).setUnitNr(unitsOnAttackingTerritory - nrOfAttackingUnits);
            } else {
                makeTerritoryNeutral(dest);
                arena.getTerritoryAtCoordinate(init).setUnitNr(unitsOnAttackingTerritory - nrOfAttackingUnits);
            }
        } else {
            arena.getTerritoryAtCoordinate(dest).setUnitNr(defendingUnits - attackingKills);
            if (defendingKills > nrOfAttackingUnits)
                defendingKills = nrOfAttackingUnits;
            arena.getTerritoryAtCoordinate(init).setUnitNr(unitsOnAttackingTerritory - defendingKills);
        }
    }

    /**
     * Marginal case when the territory becomes neutral as the attacked could not
     * conquer it but it killed all the units inside it
     *
     * @param dest the territory coordinate on which this happened
     */
    private void makeTerritoryNeutral(Point dest) {
        verifyContinentAfterTerritoryLoss(dest);

        arena.getTerritoryAtCoordinate(dest).setUnitNr(0);
        arena.getTerritoryAtCoordinate(dest).setOwner(Player.CPU_MAP_PLAYER);
        arena.getTerritoryAtCoordinate(dest).getContinent().addPlayer(Player.CPU_MAP_PLAYER);
    }

    /**
     * Changes owner of a territory, after an attack with all his consequences.
     *
     * @param nrOfAttackingUnits how many units attacked the territory
     * @param dest               the territory that was attacked
     * @param player             the player that won the territory
     * @param defendingKills     how many units were on the territory before the attack
     */
    private void changeOwnershipOfTerritory(int nrOfAttackingUnits, Point dest, Player player, int defendingKills) {
        verifyContinentAfterTerritoryLoss(dest);

        arena.getTerritoryAtCoordinate(dest).setOwner(player);
        arena.getTerritoryAtCoordinate(dest).setUnitNr(nrOfAttackingUnits - defendingKills);
        arena.getTerritoryAtCoordinate(dest).getContinent().addPlayer(player);
    }

    /**
     * Verify if a player is still on a continent after he will loose the territory at given coordinate
     * If it still has other territory(ies) on it ,it does nothing, otherwise it removes it from it
     *
     * @param coordinate the coordinate of the territory in cause
     */
    private void verifyContinentAfterTerritoryLoss(Point coordinate) {
        Player owner = arena.getTerritoryAtCoordinate(coordinate).getOwner();
        List<Territory> ownedTerritories = arena.getOwnedTerritories(owner);
        Continent continent = arena.getTerritoryAtCoordinate(coordinate).getContinent();

        for (Territory territory : ownedTerritories) {
            if (territory.getContinent().equals(continent) && !territory.equals(arena.getTerritoryAtCoordinate(coordinate)))
                return;
        }

        continent.removePlayer(owner);
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
    public boolean distributePlayers(int territoriesPerPlayer, int unitsPerTerritory) {
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
                Player currentPlayer = players.get(playerID);

                //if current player is computer, skip
                if (currentPlayer == Player.CPU_MAP_PLAYER)
                    continue;

                Random generator = new Random();
                int territoryID = generator.nextInt(distributableTerritories.size());
                distributableTerritories.get(territoryID).setOwner(currentPlayer);
                distributableTerritories.get(territoryID).setUnitNr(1);
                currentPlayer.setReinforcements(currentPlayer.getReinforcements() - 1);

                //mark current territory as distributed
                distributableTerritories.remove(territoryID);
                distributableTerritories.get(territoryID).getContinent().addPlayer(currentPlayer);
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
    public boolean reinforce(int nrOfUnits, Point dest, Player player) {
        Territory territory = arena.getTerritoryAtCoordinate(dest);
        return reinforce(nrOfUnits, territory, player);
    }

    /**
     * Puts player's units on specified territory. Overload
     *
     * @param nrOfUnits how many units to place on the territory (value > 0)
     * @param territory the territory where the units will be placed (territory must belong to player)
     * @param player    the player that puts his reinforcements
     * @return true if player can put his reinforcements on the specified territory
     */
    public boolean reinforce(int nrOfUnits, Territory territory, Player player) {
        if (player.getReinforcements()<nrOfUnits || territory.getOwner()!=player)
            return false;

        territory.setUnitNr(territory.getUnitNr() + nrOfUnits);
        player.setReinforcements(player.getReinforcements()-nrOfUnits);
        return true;
    }

    private int computePlayerBonus(Player player) {
        int bonus = DEFAULT_BONUS, territories = 0;
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
     *
     * @return The number of players involved in the game
     */
    public int getNumberOfPlayers() {
        return players.size();
    }

    /**
     *
     * @param index
     * @return the player at position "index"
     */
    public Player getPlayerByIndex(int index) {
        return players.get(index);
    }

    public Arena getArena() {
        return arena;
    }

    /**
     * Enum with the type of combats we encounter on the map
     */
    private static enum CombatType {
        DEFEND(70),
        ATTACK(60);

        private final int winChance;

        /**
         * @param winChance < 100% percentage that represents what is the chance of one unit killing
         *                  another in a fight of this type
         */
        private CombatType(int winChance) {
            this.winChance = winChance;
        }
    }
}
