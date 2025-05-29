import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.ArrayList;

public class TeamTest {

    @Test
    public void testAddMember() {
        Team team = new Team("Avengers");
        Character hulk = new Character("Hulk", 30, 10, 3);

        team.addMember(hulk);

        ArrayList<Character> members = team.getMember();

        assertEquals(1, members.size(), "Team should have one member after adding");
        assertEquals(hulk, members.get(0), "The added member should be Hulk");
    }

    @Test
    public void testIsDefeatedFalseWhenOneAlive() {
        Team team = new Team("Avengers");
        Character hulk = new Character("Hulk", 30, 10, 3);
        Character ironMan = new Character("Iron Man", 0, 10, 3); // 죽은 상태

        team.addMember(hulk);
        team.addMember(ironMan);

        assertFalse(team.isDefeated(), "Team should not be defeated if at least one member is alive");
    }

    @Test
    public void testIsDefeatedTrueWhenAllDead() {
        Team team = new Team("Avengers");
        Character hulk = new Character("Hulk", 0, 10, 3);
        Character ironMan = new Character("Iron Man", 0, 10, 3);

        team.addMember(hulk);
        team.addMember(ironMan);

        assertTrue(team.isDefeated(), "Team should be defeated if all members are dead");
    }

    @Test
    public void testGetAliveMembers() {
        Team team = new Team("Avengers");
        Character hulk = new Character("Hulk", 30, 10, 3);
        Character ironMan = new Character("Iron Man", 0, 10, 3);

        team.addMember(hulk);
        team.addMember(ironMan);

        ArrayList<Character> alive = team.getAliveMembers();

        assertEquals(1, alive.size(), "Only one member should be alive");
        assertEquals(hulk, alive.get(0), "Hulk should be the alive member");
    }
}
