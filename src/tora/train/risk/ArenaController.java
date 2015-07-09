package tora.train.risk;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class ArenaController {
	private Arena arena;
	private List<Player> players;
	
	public ArenaController(){
		//TODO
		players=new ArrayList<Player>();
		initArena();
	}

	public int getReinforcements(Player p){
        /**
         * Returns the number of units that p is allowed to use for reinforcement.
         */

		//TODO
		return 0;
	}

	public void addPlayer(Player p){
        /**
         *  Adds a player to the game.
         */

		players.add(p);
	}
	
	public boolean moveUnits(int nrOfUnits, Point init, Point dest, Player player){
        /**
         * Moves player's nrOfUnits from point init to point dest.
         * Returns true if operation is successful.
         * If dest belongs to another player, moveUnits method initiates an attack.
         */


		//TODO
		//TODO
		return false;
	}
	
	private void initArena(){
        /**
         * Initializes territories and places players on the map.
         */

		//TODO
	}

	private boolean reinforce(int nrOfUnits, Point dest,Player player){
        /**
         * Puts player's units on specified territory.
         */

		//TODO
		return false;
	}

	private void resolveAttack(){

	}
	
}
