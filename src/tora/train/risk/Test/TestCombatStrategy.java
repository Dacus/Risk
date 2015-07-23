package tora.train.risk.Test;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import tora.train.risk.*;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.core.Is.is;

/**
 * Created by intern on 7/17/15.
 */
public class TestCombatStrategy {
    private static CombatStrategy combatMock;
    private static Territory territory1;
    private static Territory territory2;
    private static Player player1;
    private static Player player2;
    private static Continent ANW;

    @BeforeClass
    public static void beforeClass() {
        combatMock = new CombatStrategyMock();
        ANW = new Continent(ContinentType.A);
        player1 = new Player("Player1");
        player2 = new Player("Player2");
    }

    @Before
    public void setUp() {
        territory1 = new Territory(ANW);
        territory1.setOwner(player1);
        territory2 = new Territory(ANW);
        territory2.setOwner(player2);
    }

    @After
    public void tearDown() {
        territory2 = null;
        territory1 = null;
    }

    @Test
    public void solveAttack_GivenHigherAttackKills_ExpectedConquer() {
        territory1.setUnitNr(6);
        territory1.setMovableUnits(6);
        territory2.setUnitNr(5);
        territory2.setMovableUnits(5);
        CombatStrategyMock.setAttackingKills(5);
        CombatStrategyMock.setDefenceKills(1);

        boolean fightResult = combatMock.solveAttack(5, territory1, territory2);
        int unitsOn2 = territory2.getUnitNr();
        int unitsOn1 = territory1.getUnitNr();
        int movableOn1 = territory1.getMovableUnits();
        int movableOn2 = territory2.getMovableUnits();
        Player ownerOf2 = territory2.getOwner();

        assertThat(fightResult, is(true));
        assertThat(unitsOn1, is(1));
        assertThat(unitsOn2, is(4));
        assertThat(movableOn1, is(1));
        assertThat(movableOn2, is(0));
        assertThat(ownerOf2, equalTo(player1));
    }

    @Test
    public void solveAttack_GivenLowerAttackKills_ExpectedUnitNumbersChange() {
        territory1.setUnitNr(6);
        territory2.setUnitNr(7);
        territory1.setMovableUnits(6);
        territory2.setMovableUnits(7);
        CombatStrategyMock.setDefenceKills(7);
        CombatStrategyMock.setAttackingKills(2);

        boolean fightResult = combatMock.solveAttack(4, territory1, territory2);
        int unitsOn2 = territory2.getUnitNr();
        int unitsOn1 = territory1.getUnitNr();
        int movableOn1 = territory1.getMovableUnits();
        int movableOn2 = territory2.getMovableUnits();
        Player ownerOf2 = territory2.getOwner();

        assertThat(fightResult, is(false));
        assertThat(unitsOn1, is(2));
        assertThat(unitsOn2, is(5));
        assertThat(movableOn1, is(2));
        assertThat(movableOn2, is(5));
        assertThat(ownerOf2, equalTo(player2));
    }

    @Test
    public void solveAttack_GivenEqualAttackKills_ExpectedBecomeNeutral() {
        territory1.setUnitNr(5);
        territory2.setUnitNr(4);
        territory1.setMovableUnits(5);
        territory2.setMovableUnits(4);
        CombatStrategyMock.setDefenceKills(4);
        CombatStrategyMock.setAttackingKills(4);

        boolean fightResult = combatMock.solveAttack(4, territory1, territory2);
        int unitsOn2 = territory2.getUnitNr();
        int unitsOn1 = territory1.getUnitNr();
        int movableOn1 = territory1.getMovableUnits();
        int movableOn2 = territory2.getMovableUnits();
        Player ownerOf2 = territory2.getOwner();

        assertThat(fightResult, is(true));
        assertThat(unitsOn1, is(1));
        assertThat(unitsOn2, is(0));
        assertThat(movableOn2, is(0));
        assertThat(movableOn1, is(1));
        assertThat(ownerOf2, equalTo(Player.CPU_MAP_PLAYER));
    }
}
