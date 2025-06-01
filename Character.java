/**
 * The Character class represents a game character.
 * It can be either a Fighter or a Healer, with stats like health, strength, and defense.
 * Fighters can attack, and Healers can restore health to allies.
 */
public class Character {

    // === Fields ===

    private String name;          // Character's name
    private int health;           // Current health points
    private int strength;         // Offensive power (0 for Healer)
    private int defense;          // Defensive resistance
    private boolean isHealer;     // True if character is a healer

    // === Constructors ===

    /**
     * Constructor for Fighter characters.
     *
     * @param name     character name
     * @param health   initial health
     * @param strength attack strength
     * @param defense  defense points
     */
    public Character(String name, int health, int strength, int defense) {
        this.name = name;
        this.health = health;
        this.strength = strength;
        this.defense = defense;
        this.isHealer = false;
    }

    /**
     * Constructor for Healer characters (strength = 0 by default).
     *
     * @param name     character name
     * @param health   initial health
     * @param defense  defense points
     */
    public Character(String name, int health, int defense) {
        this.name = name;
        this.health = health;
        this.strength = 0; // Healers have no attack strength
        this.defense = defense;
        this.isHealer = true;
    }

    // === Getters ===

    public String getName() {
        return name;
    }

    public int getHealth() {
        return health;
    }

    public int getStrength() {
        return strength;
    }

    public int getDefense() {
        return defense;
    }

    public boolean isHealer() {
        return isHealer;
    }

    /**
     * Returns true if the character is alive (health > 0).
     */
    public boolean isAlive() {
        return this.health > 0;
    }

    // === Combat Actions ===

    /**
     * Attacks a target character.
     * Damage = this character's strength - target's defense.
     *
     * @param target the target character
     */
    public void attackDamage(Character target) {
        if (!this.isAlive()) {
            System.out.println(this.name + " cannot attack because they are defeated.");
            return;
        }

        int damage = this.strength - target.getDefense();

        if (damage > 0) {
            target.takeDamage(damage);
            System.out.println(this.name + " attacks " + target.getName() + " for " + damage + " damage.");
        } else {
            System.out.println(this.name + "'s attack was too weak to harm " + target.getName() + ".");
        }
    }

    /**
     * Reduces this character's health by the given damage amount.
     * Health is never allowed to go below 0.
     *
     * @param damage amount of damage taken
     */
    public void takeDamage(int damage) {
        this.health -= damage;
        if (this.health < 0) {
            this.health = 0;
        }
    }

    /**
     * Heals a target character (only usable by Healers).
     * Healing cannot exceed 30 HP.
     *
     * @param target the character to be healed
     * @param amount amount of health to restore
     */
    public void heal(Character target, int amount) {
        if (!this.isHealer) {
            System.out.println(this.name + " is not a healer and cannot heal.");
            return;
        }

        if (!target.isAlive()) {
            System.out.println(target.getName() + " is already defeated and cannot be healed.");
            return;
        }

        if (target.getHealth() == 30) {
            System.out.println(target.getName() + " is already at full health.");
            return;
        }

        target.health += amount;
        if (target.health > 30) {
            target.health = 30;
        }

        System.out.println(this.name + " heals " + target.getName() + " for " + amount + " HP. Current HP: " + target.getHealth());
    }
}
