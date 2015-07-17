package tora.train.risk.Test;

import org.junit.Before;
import tora.train.risk.ArenaController;
import tora.train.risk.Player;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by intern on 7/17/15.
 */
public class TestArenaAttack {
    private static final int NR_OF_PLAYERS = 2;
    private ArenaController arenaController;
    private List<Player> players;

    @Before
    public void init() {
        arenaController = new ArenaController();
        players = new ArrayList<>();
        for (int i = 0; i < NR_OF_PLAYERS; i++) {
            Player p = new Player("Player" + i);
            players.add(p);
            arenaController.addPlayer(p);
        }
    }
}
