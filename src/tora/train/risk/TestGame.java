package tora.train.risk;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

/**
 * Created by intern on 7/13/15.
 */
public class TestGame {
    private ArenaController arenaController;
    private List<Player> players;
    private static final int NR_OF_PLAYERS=2;

    @Before
    public void init(){
        arenaController=new ArenaController();
        players=new ArrayList<Player>();
        for (int i=0; i<NR_OF_PLAYERS; i++) {
            Player p=new Player("Player"+i);
            players.add(p);
            arenaController.addPlayer(p);
        }
    }

    @Test
    public void testDistributingReinforcements(){
        arenaController.distributePlayers(5, 1);

        for (int i=0; i<arenaController.getNumberOfPlayers(); i++){
            Player p=arenaController.getPlayerByIndex(i);
            if (!p.equals(Player.CPU_MAP_PLAYER)) {
                List<Territory> list = arenaController.getArena().getOwnedTerritories(p);
                assertThat(list.size(), equalTo(5));
            }
        }
    }

    /**
     * Helper methods (if something does not work)
     */
    private void printOwners(){
        for (int i=0; i<11; i++)
            for(int j=0; j<11; j++)
                System.out.println(arenaController.getArena().getTerritoryAtCoordinate(i,j).getOwner().getName());
    }

    private void printTerritoryListOwners(List<Territory> list){
        for (int i=0; i<list.size(); i++){
            if (!list.get(i).getOwner().equals(Player.CPU_MAP_PLAYER))
                System.out.println(list.get(i).getOwner());
        }
        System.out.println();
    }
}
