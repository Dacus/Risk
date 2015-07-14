package tora.train.risk;

import java.util.List;

/**
 * Created by intern on 7/14/15.
 */
public class RiskSimulation {

    private static int NR_PLAYERS=2;

    public static void main(String[] args) {

        ArenaController arenaController=new ArenaController();
        System.out.println(arenaController.getArena().toString());

        for (int i=1;i<=NR_PLAYERS;i++) {
            arenaController.addPlayer(new Player("Player" + i));
        }
        arenaController.distributePlayers(5,1);
        System.out.println(arenaController.getArena().toString());

        //intarire => fiecare teritoriu=4 unitati
        for (int i=1;i<=NR_PLAYERS;i++){
            List<Territory> teritories= arenaController.getArena().getOwnedTerritories(arenaController.getPlayerByIndex(i));
            for (Territory t:teritories){
                arenaController.reinforce(3,t,arenaController.getPlayerByIndex(i));
            }
        }
        System.out.println(arenaController.getArena().toString());

        
    }
}
