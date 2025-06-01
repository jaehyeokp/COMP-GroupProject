// The Action class contains the action name and a damage modifier that contributes to total damage.
public class Action {

    // === Fields ===

    private String name;                // Name of the action 
    private int damageModifier;        // Additional damage applied for the action

    //Constructor for Action.
    public Action(String name, int damageModifier) {
        this.name = name;
        this.damageModifier = damageModifier;
    }

    /*
     * Performs the action on the target character
     * Damage = actor's strength + action's damage modifier + dice roll - target's defense
     */
    public String perform(Character actor, Character target, int diceRoll) {

        // check if actor is alive to be targeted
        if (!target.isAlive()) {
            return target.getName() + " is already defeated and cannot be targeted.";
        }

        // Calculate total damage
        int totalDamage = actor.getStrength() + damageModifier + diceRoll - target.getDefense();

        // prevent negative damage
        if (totalDamage < 0) {
            totalDamage = 0;
        }

        // Apply the damage to the target
        target.takeDamage(totalDamage);

        //result message
        String resultMessage = actor.getName() + " uses " + name + " on " + target.getName()
                             + " (rolled: " + diceRoll + ")! Dealt " + totalDamage + " damage!";

        // If target is defeated after the attack, change message
        if (!target.isAlive()) {
            resultMessage = target.getName() + " was defeated by " + actor.getName() + "'s " + name + " attack!";
        }

        return resultMessage;
    }

    // === Getters ===

    // Returns the name of the action
    public String getName() {
        return name;
    }

    // Returns the damage modifier of the action
    public int getDamageModifier() {
        return damageModifier;
    }
}
