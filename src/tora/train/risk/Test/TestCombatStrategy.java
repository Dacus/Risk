package tora.train.risk.Test;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import tora.train.risk.*;

import java.util.List;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;

/**
 * Created by intern on 7/17/15.
 */
public class TestCombatStrategy {
    private static final int NR_OF_PLAYERS = 2;
    private static CombatStrategy combatMock;
    private static Territory territory1;
    private static Territory territory2;
    private static Player player1;
    private static Player player2;
    private static Continent ANW;
    private List<Player> players;

    @BeforeClass
    public static void init() {
        combatMock = new CombatStrategyMock();
        ANW = new Continent(ContinentType.A);
        player1 = new Player("Player1");
        player2 = new Player("Player2");
    }

    @Before
    public void beforeTest() {
        territory1 = new Territory(ANW);
        territory1.setOwner(player1);
        territory2 = new Territory(ANW);
        territory2.setOwner(player2);
    }

    @Test
    public void solveAttack_GivenHigherAttackKills_ExpectedConquer() {
        territory1.setUnitNr(6);
        territory2.setUnitNr(5);
        CombatStrategyMock.setAttackingKills(5);
        CombatStrategyMock.setDefenceKills(1);

        boolean fightResult = combatMock.solveAttack(5, territory1, territory2);
        int unitsOn2 = territory2.getUnitNr();
        int unitsOn1 = territory1.getUnitNr();
        Player ownerOf2 = territory2.getOwner();

        assertEquals(fightResult, true);
        assertEquals(unitsOn1, 1);
        assertEquals(unitsOn2, 4);
        assertTrue(ownerOf2.equals(player1));
    }

    @Test
    public void solveAttack_GivenLowerAttackKills_ExpectedUnitNumbersChange() {
        territory1.setUnitNr(6);
        territory2.setUnitNr(7);
        CombatStrategyMock.setDefenceKills(7);
        CombatStrategyMock.setAttackingKills(2);

        boolean fightResult = combatMock.solveAttack(4, territory1, territory2);
        int unitsOn2 = territory2.getUnitNr();
        int unitsOn1 = territory1.getUnitNr();
        Player ownerOf2 = territory2.getOwner();

        assertEquals(fightResult, false);
        assertEquals(unitsOn1, 2);
        assertEquals(unitsOn2, 5);
        assertTrue(ownerOf2.equals(player2));
    }

    @Test
    public void solveAttack_GivenEqualAttackKills_ExpectedBecomeNeutral() {
        territory1.setUnitNr(5);
        territory2.setUnitNr(4);
        CombatStrategyMock.setDefenceKills(4);
        CombatStrategyMock.setAttackingKills(4);

        boolean fightResult = combatMock.solveAttack(4, territory1, territory2);
        int unitsOn2 = territory2.getUnitNr();
        int unitsOn1 = territory1.getUnitNr();
        Player ownerOf2 = territory2.getOwner();

        assertEquals(fightResult, true);
        assertEquals(unitsOn1, 1);
        assertEquals(unitsOn2, 0);
        assertTrue(ownerOf2.equals(Player.CPU_MAP_PLAYER));
    }
}
