import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class CharacterTest {

    @Test
    public void testAttackDamage() {
        Character attacker = new Character("Attacker", 30, 10, 2);
        Character defender = new Character("Defender", 30, 5, 3);

        attacker.attackDamage(defender);

        assertTrue(defender.getHealth() < 30, "Defender should take damage");
    }

    @Test
    public void testHeal() {
        Character healer = new Character("Healer", 30, 2);
        Character target = new Character("Target", 20, 5, 2);

        healer.heal(target, 5);

        assertEquals(25, target.getHealth(), "Target should heal to 25 HP");
    }
}
