package tora.train.risk;

import org.junit.BeforeClass;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

/**
 * Created by intern on 7/13/15.
 */
public class TestGame {
    private ArenaController arenaController;

    @BeforeClass
    public void init(){
        arenaController=new ArenaController();

        //add players
    }

    @Test
    public void testDistributingReinforcements(){
        arenaController.distributePlayers(5, 1);

        for (int i=0; i<arenaController.getNumberOfPlayers(); i++){
            Player p=arenaController.getPlayerByIndex(i);
            assertThat(arenaController.getArena().getOwnedTerritories(p).size(), equalTo(1));
        }
    }




}
