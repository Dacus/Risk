package tora.train.risk.Test;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import tora.train.risk.*;

import java.util.List;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;


/**
 * Created by Isa on 7/20/15.
 */
public class TestPlayerBonus {
    private static final int BONUS_FOR_GROUP = 1;
    private static final int GROUP_SIZE = 3;
    private static final int DEFAULT_BONUS = 5;
    private static Arena defaultArena;
    private static BonusStrategy defaultStrat;
    private static Player randomPlayer;

    /**
     * Initialize the arena and strategy we are testing
     */
    @BeforeClass
    public static void init() {
        defaultArena = new Arena();
        defaultStrat = new RiskDefaultBonus(defaultArena);
    }

    private static Integer formula(int territories, int continentBonus) {
        int bonus = DEFAULT_BONUS + BONUS_FOR_GROUP * (territories) / GROUP_SIZE + continentBonus;

        return bonus;
    }

    private static int setRandomPlayerContinentOwner(Continent continent) {
        int territories = 0;
        continent.removePlayer(Player.CPU_MAP_PLAYER);
        continent.addPlayer(randomPlayer);
        for (int i = 0; i < defaultArena.getXSize(); i++) {
            for (int j = 0; j < defaultArena.getYSize(); j++) {
                if (defaultArena.getTerritoryAtCoordinate(i, j).getContinent().equals(continent)) {
                    defaultArena.getTerritoryAtCoordinate(i, j).setOwner(randomPlayer);
                    territories++;
                }
            }
        }
        return territories;
    }

    @Before
    public void initiateRandomPlayer() {
        randomPlayer = new Player("Osama bin Laden");
    }

    @After
    public void setDefaultPlayer() {
        List<Territory> territories = defaultArena.getOwnedTerritories(randomPlayer);
        for (Continent continent : defaultArena.getContinents()) {
            continent.removePlayer(randomPlayer);
            continent.addPlayer(Player.CPU_MAP_PLAYER);
        }

        for (Territory territory : territories) {
            territory.setOwner(Player.CPU_MAP_PLAYER);
        }
    }

    @Test
    public void giveBonus_GivenNoTerritories_ExpectedZero() {
        int bonus = defaultStrat.computePlayerBonus(randomPlayer);

        assertEquals(bonus, 0);
    }

    @Test
    public void giveBonus_GivenOneTerritoryNoContinent_ExpectedDefault() {
        defaultArena.getTerritoryAtCoordinate(0, 0).setOwner(randomPlayer);
        int bonus = defaultStrat.computePlayerBonus(randomPlayer);

        assertEquals(DEFAULT_BONUS, bonus);
    }

    @Test
    public void giveBonusGivenGroupSizeTerritoriesNoContinent_ExpectedGroupBonus() {
        for (int i = 0; i < GROUP_SIZE; i++) {
            defaultArena.getTerritoryAtCoordinate(0, i).setOwner(randomPlayer);
        }

        int bonus = defaultStrat.computePlayerBonus(randomPlayer);

        assertEquals(DEFAULT_BONUS + BONUS_FOR_GROUP, bonus);
    }

    @Test
    public void giveBonus_GivenOneContinent_ExpectedContinentBonus() {
        Continent firstContinent = defaultArena.getContinents().get(0);
        int territories = setRandomPlayerContinentOwner(firstContinent);

        Integer bonus = defaultStrat.computePlayerBonus(randomPlayer);
        int continentBonus = firstContinent.getType().getBonus();
        Integer actual = formula(territories, continentBonus);

        assertThat(bonus, equalTo(actual));
    }

    @Test
    public void giveBonus_GivenTwoContinents_ExpectedContinentBonuses() {
        Continent firstContinent = defaultArena.getContinents().get(0);
        Continent secondContinent = defaultArena.getContinents().get(1);

        int territories = setRandomPlayerContinentOwner(firstContinent);
        territories += setRandomPlayerContinentOwner(secondContinent);

        Integer bonus = defaultStrat.computePlayerBonus(randomPlayer);
        int continentBonus = firstContinent.getType().getBonus();
        continentBonus += secondContinent.getType().getBonus();
        Integer actual = formula(territories, continentBonus);

        assertThat(bonus, equalTo(actual));
    }

    @Test
    public void giveBonus_GivenNullPlayer_ExpectedZero() {
        randomPlayer = null;

        int bonus = defaultStrat.computePlayerBonus(randomPlayer);

        assertThat(bonus, equalTo(0));
    }

    @Test
    public void giveBonus_GivenCpuPlayer_ExpectedZero() {
        randomPlayer = Player.CPU_MAP_PLAYER;

        int bonus = defaultStrat.computePlayerBonus(randomPlayer);

        assertThat(bonus, equalTo(0));
    }

}
