public class Action {
  //vairalbe
  /*
   * name- String
   * damageModifier -int
   * 
   * + perform(actor:Character, target:Character )
   */
  String name;
  int damageModifier;

  public Action(String name, int damageModifier) {
    this.name =name;
    this.damageModifier = damageModifier;
  }
    
  public String perform(Character actor, Character target) {
    
    if (!target.isAlive()) {
      return target.getName() + " cannot be targeted because they are dead.";
    }
    int totalDamage = actor.getStrength() + damageModifier - target.getDefense();
    if (totalDamage < 0) {
            totalDamage = 0; 
        }

    target.takeDamage(totalDamage);
    
    String resultMessage;
        if (totalDamage == 0) {
            resultMessage = actor.getName() + "이(가) " + this.name + "(으)로 " + target.getName() + "을(를) 공격했지만 아무런 피해도 주지 못했습니다.";
        } else {
            resultMessage = actor.getName() + "이(가) " + this.name + "(으)로 " + target.getName() + "에게 " + totalDamage + "의 피해를 입혔습니다!";
        }

    if (!target.isAlive()) {
      resultMessage = target.getName() + " has been defeated by " + actor.getName() + " using " + name + ".";
      return resultMessage;
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
