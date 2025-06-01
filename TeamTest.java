import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.ArrayList;

/*
 * Unit tests for the Team class.
 * These tests verify member addition, defeat status, and alive member retrieval.
 */
public class TeamTest {

    // Tests that a character can be added to the team successfully.
    @Test
    public void testAddMember() {
        // Arrange
        Team team = new Team("Avengers");
        Character hulk = new Character("Hulk", 30, 10, 3);

        // Act
        team.addMember(hulk);
        ArrayList<Character> members = team.getMember();

        // Assert
        assertEquals(1, members.size(), "Team should have one member after adding");
        assertEquals(hulk, members.get(0), "The added member should be Hulk");
    }

    // Tests that isDefeated() returns false if at least one character is alive.
    @Test
    public void testIsDefeatedFalseWhenOneAlive() {
        // Arrange
        Team team = new Team("Avengers");
        Character hulk = new Character("Hulk", 30, 10, 3);
        Character ironMan = new Character("Iron Man", 0, 10, 3); // defeated

        // Act
        team.addMember(hulk);
        team.addMember(ironMan);

        // Assert
        assertFalse(team.isDefeated(), "Team should not be defeated if at least one member is alive");
    }

    
    //Tests that isDefeated() returns true if all characters are dead.
    @Test
    public void testIsDefeatedTrueWhenAllDead() {
        // Arrange
        Team team = new Team("Avengers");
        Character hulk = new Character("Hulk", 0, 10, 3);
        Character ironMan = new Character("Iron Man", 0, 10, 3);

        // Act
        team.addMember(hulk);
        team.addMember(ironMan);

        // Assert
        assertTrue(team.isDefeated(), "Team should be defeated if all members are dead");
    }

    
    // Tests that getAliveMembers() returns only characters with health > 0.
    @Test
    public void testGetAliveMembers() {
        // Arrange
        Team team = new Team("Avengers");
        Character hulk = new Character("Hulk", 30, 10, 3);
        Character ironMan = new Character("Iron Man", 0, 10, 3); // defeated

        // Act
        team.addMember(hulk);
        team.addMember(ironMan);
        ArrayList<Character> alive = team.getAliveMembers();

        // Assert
        assertEquals(1, alive.size(), "Only one member should be alive");
        assertEquals(hulk, alive.get(0), "Hulk should be the alive member");
    }
}
