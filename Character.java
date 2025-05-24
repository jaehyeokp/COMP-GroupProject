
public class Character {
    /*
     *캐릭터의 스탯, 공격/방어 기능 담당


     *  + attack(target: Character): string - 일반공격
     *  + castSpell(targer:Character): String - 마법공격
     *  + takeDamage(amount :int) : void - 데미지 입히기
     *  + applyStatusEffects(): void : ---- 아직 보류 기능 뭔지 정확히X 
     *  +isAlive(): boolean : 살았는지 죽었는지 
     */
  
		private String name;    // variable for character name
		private int health; // variable for health points
		private int strength;   // variable for physical attack
		private int intelligence; //variable for magic attack
		private int defense;    // variable for defense points
	
    public Character(String name, int health, int strength, int intelligence, int defense) {
        this.name = name;
        this.health = health;   
        this.strength = strength;
        this.intelligence = intelligence;
        this.defense = defense;
    
    }
    // Getter methods for character attributes

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
    
    // 추가적인 메소드들 (공격, 마법 공격 등) 구현 필요


    public void attackDamage(Character target) {
        // Physical attack logic
        if (!this.isAlive()) {
            System.out.println(this.name + " cannot attack because they are dead.");
            return;
        }
        int damage = this.strength - target.getDefense();
        if (damage > 0) {
            target.takeDamage(damage);
            System.out.println(this.name + " attacks " + target.getName() + " for " + damage + " damage.");
        } 
        else {
            System.out.println(this.name + "'s attack was too weak to harm " + target.getName() + ".");
        }
    }

    public void abilityPower(Character target) {
        // Magic attack logic
        if (!this.isAlive()) {
            System.out.println(this.name + " cannot cast a spell because they are dead.");
            return;
        }
        int spellDamage = this.intelligence - target.getDefense();
        if (spellDamage > 0) {
            target.takeDamage(spellDamage);
            System.out.println(this.name + " casts a spell on " + target.getName() + " for " + spellDamage + " damage.");
        } 
        else {
            System.out.println(this.name + "'s spell was too weak to harm " + target.getName() + ".");
        }
    }

    public void takeDamage(int damage) {
        this.health -= damage;
        if (this.health < 0) {
            this.health = 0;
        }
    }

    public boolean isAlive() {
        if(this.health > 0) {
            return true; // Character is alive
        } 
        else {
            System.out.println(this.name + " has died.");
            return false; // Character is dead
        }
    }
}
