import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ActionTest {

    @Test
    public void testPerformDealsCorrectDamage() {
        // 준비: 공격자, 대상, 액션 생성
        Character attacker = new Character("Attacker", 30, 10, 2); // 체력, 힘, 방어
        Character defender = new Character("Defender", 30, 5, 3);
        Action action = new Action("Power Strike", 5);
        
        int diceRoll = 2; // 주사위 값 고정
        int expectedDamage = attacker.getStrength() + action.getDamageModifier() + diceRoll - defender.getDefense();

        if (expectedDamage < 0) expectedDamage = 0; // 음수 데미지 방지

        int defenderInitialHealth = defender.getHealth();

        // 실행
        String result = action.perform(attacker, defender, diceRoll);

        // 검증
        assertEquals(defenderInitialHealth - expectedDamage, defender.getHealth(),
            "Defender's health should decrease by expected damage");

        assertTrue(result.contains("Power Strike"), "Result message should mention the action name");
    }

    @Test
    public void testPerformOnAlreadyDefeatedTarget() {
        // 준비: 죽은 대상 만들기
        Character attacker = new Character("Attacker", 30, 10, 2);
        Character defender = new Character("Defender", 0, 5, 3); // 이미 체력 0

        Action action = new Action("Slash", 3);

        // 실행
        String result = action.perform(attacker, defender, 4);

        // 검증
        assertTrue(result.contains("is already defeated"), 
            "Should not allow targeting an already defeated character");
    }
}
