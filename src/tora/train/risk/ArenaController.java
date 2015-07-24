package tora.train.risk;

import java.awt.*;
import java.util.*;
import java.util.List;
import java.util.Queue;

import static tora.train.risk.ArenaCommandValidator.isReinforceValid;

public class ArenaController {
    private final Arena arena;
    private CombatStrategy strategy;
    private BonusStrategy bonusComputer;
    private List<Player> players;           //all players
    private Queue<Player> playersQueue;     //used to decide which player turn is
    private boolean isCurrentPlayerTurn;    //indicates whether current player started his turn

    /**
     * Default constructor
     */
    public ArenaController() {
        players = new ArrayList<>();
        players.add(Player.CPU_MAP_PLAYER);
        this.arena = new Arena();
        this.bonusComputer = new RiskDefaultBonus(arena);
        this.strategy = new RiskCombatStrategy();
        playersQueue = new LinkedList<>();
        isCurrentPlayerTurn = false;
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
        this.bonusComputer = new RiskDefaultBonus(arena);
        this.strategy = strategy;
        playersQueue = new LinkedList<>();
        isCurrentPlayerTurn = false;
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
        this.bonusComputer = new RiskDefaultBonus(arena);
        this.strategy = new RiskCombatStrategy();
        playersQueue = new LinkedList<>();
        isCurrentPlayerTurn = false;
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
        this.bonusComputer = new RiskDefaultBonus(arena);
        this.strategy = strategy;
        playersQueue = new LinkedList<>();
        isCurrentPlayerTurn = false;
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
            transferUnits(nrOfAttackingUnits, init, dest);
        }
        return true;
    }

    /**
     * Moves units from a territory to another. (Both territories must belong to the same player)
     *
     * @param nrOfUnits how many units to move (value > 0)
     * @param from      the territory's coordinates from which the player moves (belongs to player)
     * @param to        the territory's coordinates to which the player moves (belongs to player)
     */
    private void transferUnits(int nrOfUnits, Point from, Point to) {
        Territory fromTerritory = arena.getTerritoryAtCoordinate(from.x, from.y);
        Territory toTerritory = arena.getTerritoryAtCoordinate(to.x, to.y);

        fromTerritory.setMovableUnits(fromTerritory.getUnitNr() - nrOfUnits);
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

        List<Territory> distributableTerritories = arena.getDistributableTerritories();

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
                distributableTerritories.get(territoryID).setMovableUnits(1);
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
        boolean isValid = isReinforceValid(nrOfUnits, territory, player);
        if (!isValid)
            return false;

        int units = territory.getUnitNr();
        territory.setUnitNr(units + nrOfUnits);
        territory.setMovableUnits(units + nrOfUnits);
        player.setReinforcements(player.getReinforcements() - nrOfUnits);
        return true;
    }

    /**
     * Set the units on territories to be movable again, at the start of the territory
     *
     */
    public void resetMovableUnits(Player player) {
        List<Territory> territories = arena.getOwnedTerritories(player);
        for (Territory territory : territories) {
            int units = territory.getUnitNr();
            territory.setMovableUnits(units);
        }
    }

    /**
     * Method that computes  and gives the bonus reinforcements a player should get at the start of the
     * turn
     *
     * @param player player which receives the reinforcements
     */
    public void givePlayerBonus(Player player) {
        int bonus = bonusComputer.computePlayerBonus(player);
        player.setReinforcements(bonus);
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

    /**
     * Builds the queue of players.
     */
    private void generateOrderOfPlayers() {
        int nrOfPlayers = getNumberOfPlayers();
        if (players.contains(Player.CPU_MAP_PLAYER))
            nrOfPlayers--;

        List<Player> playersToDistribute = new ArrayList<>(players);
        playersToDistribute.remove(Player.CPU_MAP_PLAYER);
        Random generator = new Random();
        for (int i = 1; i <= nrOfPlayers; i++) {
            Player generatedPlayer = playersToDistribute.get(generator.nextInt(playersToDistribute.size()));
            playersToDistribute.remove(generatedPlayer);
            playersQueue.add(generatedPlayer);
        }
    }

    /**
     * @return player that has to move next
     * Sets isCurrentPlayerTurn to true.
     * If playersQueue is empty, this method will reconstruct the queue and then return the first player.
     */
    public Player getCurrentPlayer() {
        if (playersQueue.isEmpty())
            generateOrderOfPlayers();
        isCurrentPlayerTurn = true;
        return playersQueue.peek();
    }

    /**
     * Call this method when a player ends his turn. This will remove the currentPlayer from the playersQueue
     * and set isCurrentPlayerTurn to false.
     * @return false if playersQueue was empty before calling this method or
     *                  getCurrentPlayer was not called before this method.
     */
    public boolean endCurrentPlayerTurn() {
        if (isCurrentPlayerTurn) {
            isCurrentPlayerTurn = false;
            if (playersQueue.isEmpty())
                return false;
            playersQueue.remove();
            return true;
        }
        return false;
    }

    public Queue<Player> getPlayersQueue() {
        if (playersQueue.isEmpty())
            generateOrderOfPlayers();
        return playersQueue;
    }

    public Player getPlayerByName(String name){
        for (Player p: players){
            if (p.getName().equals(name)){
                return p;
            }
        }
        return null;
    }

    public boolean isPlayersQueueEmpty(){
        return playersQueue.isEmpty();
    }
}
