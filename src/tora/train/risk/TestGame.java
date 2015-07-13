package tora.train.risk;

import org.junit.BeforeClass;
import org.junit.Test;

/**
 * Created by intern on 7/13/15.
 */
public class TestGame {
    private ArenaController arenaController;
    private Player p1;
    private Player p2;

    @BeforeClass
    public void init(){
        arenaController=new ArenaController();

        p1=new Player("Player1");
        p2=new Player("Player2");

        arenaController.addPlayer(p1);
        arenaController.addPlayer(p2);
    }

    @Test
    public void testDistributingReinforcements(){
        arenaController.distributePlayers(5, 1);

        for (int i=0; i<arenaController.getNumberOfPlayers(); i++){
            assertThat()
        }
    }




}
