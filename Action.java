public class Action {
  // 변수 (Fields)
  String name; // 행동의 이름 (예: "Slash", "Power Strike")
  int damageModifier; // 행동의 기본 데미지 보정치

  /**
   * Action 생성자
   * @param name 행동의 이름
   * @param damageModifier 행동의 데미지 보정치
   */
  public Action(String name, int damageModifier) {
    this.name =name;
    this.damageModifier = damageModifier;
  }
    
  /**
   * 캐릭터가 대상에게 행동을 수행합니다.
   * 데미지는 공격자의 힘 + 행동의 데미지 보정치 + 주사위 값 - 대상의 방어력으로 계산됩니다.
   * @param actor 행동을 수행하는 캐릭터
   * @param target 행동의 대상이 되는 캐릭터
   * @param diceRoll 주사위를 굴려 나온 값 (0~5)
   * @return 행동의 결과를 설명하는 문자열
   */
  public String perform(Character actor, Character target, int diceRoll) {
    // 대상이 살아있는지 먼저 확인
    if (!target.isAlive()) {
      return target.getName() + " is already defeated and cannot be targeted."; // 예외 케이스 5번 관련 메시지
    }

    // 총 데미지 계산: 힘 + 추가 데미지 + 주사위 값 - 방어력
    int totalDamage = actor.getStrength() + damageModifier + diceRoll - target.getDefense();
    
    // 데미지가 0보다 작으면 0으로 처리 (음수 데미지 방지)
    if (totalDamage < 0) {
      totalDamage = 0; 
    }

    target.takeDamage(totalDamage); // 대상에게 데미지를 입힘
    
    String resultMessage;
    // 결과 메시지 생성 (주사위 값 포함)
    resultMessage = actor.getName() + " uses " + name + " on " + target.getName() 
                  + " (rolled: " + diceRoll + ")! Dealt " + totalDamage + " damage!";

    // 행동 수행 후 대상이 쓰러졌는지 확인
    if (!target.isAlive()) {
      resultMessage = target.getName() + " was defeated by " + actor.getName() + "'s " + name + " attack!";
    }
    return resultMessage;
  }

  public String getName() {
    return name;
  }

  public int getDamageModifier() {
    return damageModifier;
  }
}