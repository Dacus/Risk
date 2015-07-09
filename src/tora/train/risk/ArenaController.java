package tora.train.risk;

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
		//TODO
		return 0;
	}
	
	
	public void addPlayer(Player p){
		players.add(p);
	}
	
	public boolean moveUnits(int nrOfUnits, Point init, Point dest, Player player){
		//TODO
		//TODO
		return false;
	}
	
	
	private void initArena(){
		//TODO
	}
	
	
	private boolean reinforce(int nrOfUnits, Point dest,Player player){
		//TODO
		return false;
	}

	private void resolveAttack(){
		
	}
	
}
