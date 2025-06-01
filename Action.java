/**
 * The Action class models an action that a character can perform in battle.
 * It contains the action name and a damage modifier that contributes to total damage.
 */
public class Action {

    // === Fields ===

    private String name;                // Name of the action (e.g., "Slash", "Fireball")
    private int damageModifier;        // Additional damage applied by this action

    /**
     * Constructs an Action with the specified name and damage modifier.
     *
     * @param name           the name of the action
     * @param damageModifier the base extra damage this action contributes
     */
    public Action(String name, int damageModifier) {
        this.name = name;
        this.damageModifier = damageModifier;
    }

    /**
     * Performs the action from the actor to the target.
     * Damage = actor's strength + action's damage modifier + dice roll - target's defense
     *
     * @param actor     the character performing the action
     * @param target    the character being targeted
     * @param diceRoll  random value to add variability to the damage
     * @return          a descriptive string of the action result
     */
    public String perform(Character actor, Character target, int diceRoll) {

        // Target already defeated; action invalid
        if (!target.isAlive()) {
            return target.getName() + " is already defeated and cannot be targeted.";
        }

        // Calculate total damage dealt
        int totalDamage = actor.getStrength() + damageModifier + diceRoll - target.getDefense();

        // Ensure damage is not negative
        if (totalDamage < 0) {
            totalDamage = 0;
        }

        // Apply the damage to the target
        target.takeDamage(totalDamage);

        // Compose result message
        String resultMessage = actor.getName() + " uses " + name + " on " + target.getName()
                             + " (rolled: " + diceRoll + ")! Dealt " + totalDamage + " damage!";

        // If target is defeated after the attack, change message
        if (!target.isAlive()) {
            resultMessage = target.getName() + " was defeated by " + actor.getName() + "'s " + name + " attack!";
        }

        return resultMessage;
    }

    // === Getters ===

    /**
     * Returns the name of the action.
     *
     * @return the action name
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the damage modifier value.
     *
     * @return the damage modifier
     */
    public int getDamageModifier() {
        return damageModifier;
    }
}
