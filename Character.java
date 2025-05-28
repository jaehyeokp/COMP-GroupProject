/**
 * The Character class represents a game character with basic attributes
 * such as health, strength, defense, and role (either a fighter or healer).
 * It supports combat actions like physical and magical attacks, as well as healing.
 */
public class Character {
    // === Fields ===
    private String name;          // Character's name
    private int health;           // Current health points
    private int strength;         // Physical attack power
    private int intelligence;     // Magic attack power
    private int defense;          // Defense stat to reduce incoming damage
    private boolean isHealer;     // Flag to determine if character is a healer

    // === Constructors ===

    /**
     * Constructor for fighter-type characters.
     *
     * @param name          the character's name
     * @param health        starting health points
     * @param strength      strength stat used for physical attacks
     * @param intelligence  intelligence stat used for magic attacks
     * @param defense       defense stat used to reduce damage
     */
    public Character(String name, int health, int strength, int intelligence, int defense) {
        this.name = name;
        this.health = health;
        this.strength = strength;
        this.intelligence = intelligence;
        this.defense = defense;
        this.isHealer = false;
    }

    /**
     * Constructor for healer-type characters.
     * Healers have no attack stats (strength or intelligence).
     *
     * @param name     the healer's name
     * @param health   starting health points
     * @param defense  defense stat
     */
    public Character(String name, int health, int defense) {
        this.name = name;
        this.health = health;
        this.strength = 0;
        this.intelligence = 0;
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

    public int getIntelligence() {
        return intelligence;
    }

    public int getDefense() {
        return defense;
    }

    public boolean isHealer() {
        return isHealer;
    }

    // === Actions ===

    /**
     * Performs a physical attack on a target character.
     * Damage is calculated as attacker's strength minus target's defense.
     *
     * @param target the target character to attack
     */
    public void attackDamage(Character target) {
        if (!this.isAlive()) {
            System.out.println(this.name + " cannot attack because they are dead.");
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
     * Performs a magic attack on a target character.
     * Damage is calculated as intelligence minus defense.
     *
     * @param target the target character to attack
     */
    public void abilityPower(Character target) {
        if (!this.isAlive()) {
            System.out.println(this.name + " cannot cast a spell because they are dead.");
            return;
        }

        int spellDamage = this.intelligence - target.getDefense();
        if (spellDamage > 0) {
            target.takeDamage(spellDamage);
            System.out.println(this.name + " casts a spell on " + target.getName() + " for " + spellDamage + " damage.");
        } else {
            System.out.println(this.name + "'s spell was too weak to harm " + target.getName() + ".");
        }
    }

    /**
     * Reduces the character's health by the given damage amount.
     * Health cannot go below zero.
     *
     * @param damage the amount of damage taken
     */
    public void takeDamage(int damage) {
        this.health -= damage;
        if (this.health < 0) {
            this.health = 0;
        }
    }

    /**
     * Heals a target character for a specified amount.
     * Only characters marked as healers can use this method.
     *
     * @param target the character to be healed
     * @param amount the amount of HP to heal
     */
    public void heal(Character target, int amount) {
        if (!this.isHealer) {
            System.out.println(this.name + " is not a healer and cannot heal.");
            return;
        }

        if (!target.isAlive()) {
            System.out.println(target.name + " is dead and cannot be healed.");
            return;
        }

        if (target.health == 30) {
            System.out.println(target.name + " is already at full health.");
            return;
        }

        target.health += amount;
        if (target.health > 30) target.health = 30;

        System.out.println(this.name + " heals " + target.name + " for " + amount + " HP.");
    }

    /**
     * Checks whether the character is alive.
     *
     * @return true if health is greater than 0, false otherwise
     */
    public boolean isAlive() {
        if (this.health > 0) {
            return true;
        } else {
            System.out.println(this.name + " has died.");
            return false;
        }
    }
}
