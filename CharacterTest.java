import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the Character class.
 * Tests focus on combat (attack) and healing behavior.
 */
public class CharacterTest {

    /**
     * Tests that attacking another character reduces their health.
     */
    @Test
    public void testAttackDamage() {
        // Arrange
        Character attacker = new Character("Attacker", 30, 10, 2);
        Character defender = new Character("Defender", 30, 5, 3);
        int initialHealth = defender.getHealth();

        // Act
        attacker.attackDamage(defender);

        // Assert
        assertTrue(defender.getHealth() < initialHealth,
            "Defender's health should decrease after being attacked");
    }

    /**
     * Tests that healing increases the target's health correctly.
     */
    @Test
    public void testHeal() {
        // Arrange
        Character healer = new Character("Healer", 30, 2); // Healer constructor
        Character target = new Character("Target", 20, 5, 2); // Fighter with HP < 30

        // Act
        healer.heal(target, 5);

        // Assert
        assertEquals(25, target.getHealth(),
            "Target should have 25 HP after healing by 5");
    }
}
