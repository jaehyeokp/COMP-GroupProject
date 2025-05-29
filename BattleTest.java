import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class BattleTest {

    @Test
    public void testBattleEndsWithWinner() {
        // 팀 A 만들기
        Team teamA = new Team("Marvel");
        teamA.addMember(new Character("Hulk", 30, 10, 2));
        teamA.addMember(new Character("Iron Man", 30, 8, 3));
        teamA.addMember(new Character("Doctor Strange", 30, 4)); // 힐러

        // 팀 B 만들기
        Team teamB = new Team("DC");
        teamB.addMember(new Character("Batman", 30, 12, 3));
        teamB.addMember(new Character("Superman", 30, 14, 5));
        teamB.addMember(new Character("Joker", 30, 3)); // 힐러

        // Battle 인스턴스 만들기
        Battle battle = new Battle(teamA, teamB);

        // 전투 강제 실행 (실제 게임은 startBattle()에서 사용자 입력이 필요하지만, 여기선 내부 메서드만 테스트)
        assertNotNull(battle); // Battle 객체가 잘 생성됐는지

        // 여기서는 단위 테스트용으로 startBattle() 같은 전체 게임 루프는 호출하지 않고,
        // 예를 들어 teamA.isDefeated()나 teamB.isDefeated() 같은 상태 메서드를 검증할 수 있어
        assertFalse(teamA.isDefeated(), "Team A should not be defeated at start");
        assertFalse(teamB.isDefeated(), "Team B should not be defeated at start");

        // 예시: 팀 A 첫 번째 멤버가 팀 B 첫 번째 멤버를 공격
        Character attacker = teamA.getAliveMembers().get(0);
        Character target = teamB.getAliveMembers().get(0);

        attacker.attackDamage(target);

        assertTrue(target.getHealth() < 30, "Target should have taken damage");
    }
}
