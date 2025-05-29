public class Action {
  
  String name; 
  int damageModifier; 

  
  public Action(String name, int damageModifier) {
    this.name =name;
    this.damageModifier = damageModifier;
  }
    
 
  public String perform(Character actor, Character target, int diceRoll) {
    // check if actor is alive
    if (!target.isAlive()) {
      return target.getName() + " is already defeated and cannot be targeted."; 
    }

    
    int totalDamage = actor.getStrength() + damageModifier + diceRoll - target.getDefense();
    
    // prevent negative damage
    if (totalDamage < 0) {
      totalDamage = 0; 
    }

    target.takeDamage(totalDamage); 
    
    String resultMessage;
    
    resultMessage = actor.getName() + " uses " + name + " on " + target.getName() 
                  + " (rolled: " + diceRoll + ")! Dealt " + totalDamage + " damage!";

    // check if target is defeated by the action
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