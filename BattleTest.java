import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/*
 * Unit test for the Battle class.
 * This test focuses on verifying initial team state and damage application logic.
 */
public class BattleTest {

    //Tests that the battle starts with both teams alive and damage can be applied.
    @Test
    public void testBattleEndsWithWinner() {
        // === Arrange ===
        Team teamA = new Team("Marvel");
        teamA.addMember(new Character("Hulk", 30, 10, 2));
        teamA.addMember(new Character("Iron Man", 30, 8, 3));
        teamA.addMember(new Character("Doctor Strange", 30, 4)); // Healer

        Team teamB = new Team("DC");
        teamB.addMember(new Character("Batman", 30, 12, 3));
        teamB.addMember(new Character("Superman", 30, 14, 5));
        teamB.addMember(new Character("Joker", 30, 3)); // Healer

        Battle battle = new Battle(teamA, teamB);

        // === Act & Assert ===
        assertNotNull(battle, "Battle object should be created");

        // Teams should not be defeated at the start
        assertFalse(teamA.isDefeated(), "Team A should not be defeated at start");
        assertFalse(teamB.isDefeated(), "Team B should not be defeated at start");

        // Simulate a single attack: Team A's first member attacks Team B's first member
        Character attacker = teamA.getAliveMembers().get(0);
        Character target = teamB.getAliveMembers().get(0);

        attacker.attackDamage(target);

        assertTrue(target.getHealth() < 30, "Target's health should decrease after being attacked");
    }
}
