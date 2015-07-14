package tora.train.risk;

import org.junit.Before;
import org.junit.BeforeClass;
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
    private static final List<Player> players=new ArrayList<Player>();
    private static final int NR_OF_PLAYERS=2;

    @Before
    public void init(){
        arenaController=new ArenaController();
        for (int i=0; i<NR_OF_PLAYERS; i++)
            arenaController.addPlayer(players.get(i));
    }

    @BeforeClass
    public static void setup(){
        for (int i=0; i<NR_OF_PLAYERS; i++)
            players.add(new Player("Player" + i));
    }

    @Test
    public void testDistributingReinforcements(){
        arenaController.distributePlayers(5, 1);

        for (int i=0; i<arenaController.getNumberOfPlayers(); i++){
            Player p=arenaController.getPlayerByIndex(i);
            if (!p.equals(Player.CPU_MAP_PLAYER)) {
                List<Territory> list = arenaController.getArena().getOwnedTerritories(p);
                System.out.println(arenaController.getArena().printArena());
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
