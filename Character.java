/*
 * The Character class represents a game character, with basic attributes and their role (Fighter or Healer).
 * Supports combat actions like physical attacks and healing.
 */
public class Character {
    
    

    private String name;          
    private int health;          
    private int strength;        
    private int defense;          
    private boolean isHealer;   

    
    // -- Constructors --

    public Character(String name, int health, int strength, int defense) {
        this.name = name;
        this.health = health;
        this.strength = strength;
        this.defense = defense;
        this.isHealer = false;   // Indicates this is a Fighter character
    }   // Constructor for Fighter

    

    public Character(String name, int health, int defense) { 
        this.name = name;
        this.health = health;
        this.strength = 0;      // Healers have 0 strength except dice rolls
        this.defense = defense;
        this.isHealer = true;    
    }   // Constructor for Healer


    // -- Getters --

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

    // -- Actions --

    // Attack to the target character

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


    // Reduces the character's health by the given amount of damage

    public void takeDamage(int damage) {
        this.health -= damage;
        if (this.health < 0) {
            this.health = 0; // Health cannot go below 0
        }
    }

    /*
     * Only Healer characters can use this method
     * Health cannot exceed 30
     */
    public void heal(Character target, int amount) {
        if (!this.isHealer) {
            System.out.println(this.name + " is not a healer and cannot heal.");
            return;
        }

        // Cannot heal defeated characters
        if (!target.isAlive()) {
            System.out.println(target.getName() + " is already defeated and cannot be healed.");
            return;
        }

        // Cannot heal characters already at full health
        if (target.getHealth() == 30) { 
            System.out.println(target.getName() + " is already at full health.");
            return;
        }

        target.health += amount;
        if (target.health > 30) {  // Don't overheal past max health
            target.health = 30;
        }

        System.out.println(this.name + " heals " + target.getName() + " for " + amount + " HP. Current HP: " + target.getHealth());
    }

    
    public boolean isAlive() {  // Checks if the character is alive
        return this.health > 0;
    }
}