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
    private CombatStrategy strategy;
    private List<Player> players;

    /**
     * Default constructor
     */
    public ArenaController() {
        players = new ArrayList<Player>();
        players.add(Player.CPU_MAP_PLAYER);
        this.arena = new Arena();
        this.strategy = new RiskCombatStrategy();
    }

    /**
     * Constructor with given strategy, on the default map
     *
     * @param strategy the strategy logic of attacking
     */
    public ArenaController(CombatStrategy strategy) {
        players = new ArrayList<Player>();
        players.add(Player.CPU_MAP_PLAYER);
        this.arena = new Arena();
        this.strategy = strategy;
    }

    /**
     * Constructor with specific arena and default combat logic
     *
     * @param arena the arena the game is played on
     */
    public ArenaController(Arena arena) {
        players = new ArrayList<Player>();
        players.add(Player.CPU_MAP_PLAYER);
        this.arena = arena;
        this.strategy = new RiskCombatStrategy();
    }

    /**Constructor with specified arena and attacking strategy
     *
     * @param arena the arena used for playing
     * @param strategy combat rules
     */
    public ArenaController(Arena arena, CombatStrategy strategy) {
        players = new ArrayList<Player>();
        players.add(Player.CPU_MAP_PLAYER);
        this.arena = arena;
        this.strategy = strategy;
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
     * @param init               the territory's coordinates from which the player moves (territory must belong to player)
     * @param dest               the territory's coordinates to which the player moves
     *                           (if territory does not belong to player = attack)
     * @param player             the player that want to move
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
     * @param to        the territory's coordinates to which the player moves (belongs to player)
     * @param player    the player that want to move
     */
    private void transferUnits(int nrOfUnits, Point from, Point to, Player player) {
        Territory fromTerritory = arena.getTerritoryAtCoordinate(from.x, from.y);
        Territory toTerritory = arena.getTerritoryAtCoordinate(to.x, to.y);

        fromTerritory.setUnitNr(fromTerritory.getUnitNr() - nrOfUnits);
        toTerritory.setUnitNr(toTerritory.getUnitNr() + nrOfUnits);
    }

    /**
     * Initiates an attack.
     *
     * @param nrOfAttackingUnits how many units does player use to attack an enemy territory.
     *                           (0 < value <= nr of units available)
     * @param init               the territory's coordinates from which the player initiates the attack. (belongs to player)
     * @param dest               the territory's coordinates that player wants to attack. (does not belong to player)
     * @param player             the player that initiates the attack
     */
    private void resolveAttack(int nrOfAttackingUnits, Point init, Point dest, Player player) {
        Territory destination = arena.getTerritoryAtCoordinate(dest);
        Territory source = arena.getTerritoryAtCoordinate(init);
        Player initialOwner = source.getOwner();
        boolean changedOwner = strategy.solveAttack(nrOfAttackingUnits, source, destination);
        if (changedOwner) {
            verifyContinentAfterTerritoryLoss(destination, initialOwner);
        }
    }

    /**
     * Verifies if a player does not have any territories on the continent after it lost a territory
     *
     * @param territoryLost territory that is lost
     */
    private void verifyContinentAfterTerritoryLoss(Territory territoryLost, Player owner) {
        List<Territory> ownedTerritories = arena.getOwnedTerritories(owner);
        Continent continent = territoryLost.getContinent();
        if (ownedTerritories.size() == 0) {
            players.remove(owner);
            continent.removePlayer(owner);
            return;
        }

        for (Territory territory : ownedTerritories) {
            if (territory.getContinent().equals(continent) && !territory.equals(territoryLost))
                return;
        }

        continent.removePlayer(owner);
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
     * <p/>
     * (players list must be complete before running this method)
     *
     * @param territoriesPerPlayer how many territories to distribute for each player (value >= 0)
     * @param unitsPerTerritory    how many units should each territory have (value >= 0)
     * @return true if distribution succeeded /
     * false if distribution cannot be done because there are not enough territories on the map to fulfill the request
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
                distributableTerritories.get(territoryID).getContinent().addPlayer(currentPlayer);
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
        if (player.getReinforcements() < nrOfUnits || territory.getOwner() != player)
            return false;

        territory.setUnitNr(territory.getUnitNr() + nrOfUnits);
        player.setReinforcements(player.getReinforcements() - nrOfUnits);
        return true;
    }

    public void givePlayerBonus(Player player) {
        player.setReinforcements(computePlayerBonus(player));
    }

    /**Method that computes the bonus reinforcements a player should get at the start of the
     * turn
     * @param player player for which we are computing the reinforcements
     * @return the amount of units he can reinforce now
     */
    private int computePlayerBonus(Player player) {
        int bonus = DEFAULT_BONUS, territories = 0;
        List<Continent> continents = arena.getContinents();
        int N = arena.getXSize(), M = arena.getYSize();
        for (Continent continent : continents) {
            try {
                if (continent.getOwnerIfExists().equals(player))
                    bonus += continent.getType().getBonus();
            } catch (NullPointerException e) {
            }
        }
        System.out.println("Continent bonus + 5 = " + bonus);
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < M; j++) {
                Territory territory = arena.getTerritoryAtCoordinate(i, j);
                if (territory.getOwner().equals(player))
                    territories++;
            }
        }
        bonus += (territories / GROUP_SIZE) * BONUS_FOR_GROUP;
        System.out.println("Territories : " + territories);
        System.out.println("Bonus : " + bonus);
        return bonus;
    }

    /**
     * @return The number of players involved in the game
     */
    public int getNumberOfPlayers() {
        return players.size();
    }

    /**
     * @param index
     * @return the player at position "index"
     */
    public Player getPlayerByIndex(int index) {
        return players.get(index);
    }

    public Arena getArena() {
        return arena;
    }
}
