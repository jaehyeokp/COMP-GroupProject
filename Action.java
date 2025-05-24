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
    resultMessage = actor.getName() + " uses " + name + " on " + target.getName() + " for " + totalDamage + " damage.";

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
