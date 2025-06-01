import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/*
 * Unit tests for the Character class.
 * Tests focus on combat (attack) and healing behavior.
 */
public class CharacterTest {

    //Tests that attacking another character reduces their health.
    @Test
    public void testAttackDamage() {
        // Setting up our attacker and defender characters
        Character attacker = new Character("Attacker", 30, 10, 2); // Assuming this is a Fighter: Name, HP, Str, Def
        Character defender = new Character("Defender", 30, 5, 3);
        int initialHealth = defender.getHealth();

        // Attacker hits the defender
        attacker.attackDamage(defender); // Uses the Character's own attackDamage method

        // Defender's health should be lower now.
        assertTrue(defender.getHealth() < initialHealth,
            "Defender's health should decrease after being attacked");
    }

    //Tests that healing increases the target's health correctly.
    @Test
    public void testHeal() {
        // Create a healer and a target that needs some healing
        Character healer = new Character("Healer", 30, 2); // Healer constructor
        Character target = new Character("Target", 20, 5, 2); // Fighter with HP < 30

        // Healer performs the heal action on the target.
        healer.heal(target, 5);

        // Target's health should have increased by the heal amount.
        assertEquals(25, target.getHealth(),
            "Target should have 25 HP after healing by 5");
    }
}
