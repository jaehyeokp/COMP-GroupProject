import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/*
 * Unit tests for the Action class.
 * Tests focus on damage calculation and defeated target conditions.
 */
public class ActionTest {

    // Tests that perform() correctly deals damage and updates target health.
    @Test
    public void testPerformDealsCorrectDamage() {
        // Arrange: Create attacker, defender, and action to be performed
        Character attacker = new Character("Attacker", 30, 10, 2); // Name, HP, Str, Int, Def
        Character defender = new Character("Defender", 30, 5, 3); // Name, HP, Str, Int, Def
        Action action = new Action("Power Strike", 5);
        int diceRoll = 2;

        // Calculate what the expected damage should be based on the formula
        int expectedDamage = attacker.getStrength() + action.getDamageModifier() + diceRoll - defender.getDefense();
        if (expectedDamage < 0) expectedDamage = 0;

        int defenderInitialHealth = defender.getHealth();

        // Act : Perform the action
        String result = action.perform(attacker, defender, diceRoll);

        // Assert: Check if the defender's health was reduced as expected and if the result message is appropriate.
        assertEquals(defenderInitialHealth - expectedDamage, defender.getHealth(),
            "Defender's health should decrease by expected damage");

        assertTrue(result.contains("Power Strike"), // Check specific damage in message
            "Result message should mention the action name");
    }

    
    // Tests that perform() does not allow targeting an already defeated character.
    @Test
    public void testPerformOnAlreadyDefeatedTarget() {
        // Arrange: Defender starts with 0 HP (defeated)
        Character attacker = new Character("Attacker", 30, 10, 2);
        Character defender = new Character("Defender", 0, 5, 3);
        Action action = new Action("Slash", 3);

        // Act
        String result = action.perform(attacker, defender, 4);

        // Assert
        assertTrue(result.contains("is already defeated"),
            "Should not allow targeting an already defeated character");
    }
}