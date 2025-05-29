public class Action {
  // Fields

  String name; 
  int damageModifier;  // Base damage modifier for the action

  
  //Constructor for Action.
  public Action(String name, int damageModifier) {
    this.name =name;
    this.damageModifier = damageModifier;
  }
    
  /*
   * Performs the action on the target character.
   * Damage is calculated as: actor's strength + action's damage modifier + dice roll - target's defense.
   */
  public String perform(Character actor, Character target, int diceRoll) {
    
    if (!target.isAlive()) {
      return target.getName() + " is already defeated and cannot be targeted."; 
    }   // check if actor is alive

    
    int totalDamage = actor.getStrength() + damageModifier + diceRoll - target.getDefense();
    
    
    if (totalDamage < 0) {
      totalDamage = 0; 
    }  // prevent negative damage

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