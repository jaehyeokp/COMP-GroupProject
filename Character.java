
public class Character {
    /*
     *캐릭터의 스탯, 공격/방어 기능 담당


     *  + attack(target: Character): string - 일반공격
     *  + healTeammate(target: Character): string - 팀원 힐힐
     *  + takeDamage(amount :int) : void - 데미지 입히기 
     *  + isAlive(): boolean : 살았는지 죽었는지 
     */
  
		private String name;    // variable for character name
        private String teamName; 
		private int health; // variable for health points
		private int strength;   // variable for physical attack
		private int heal; //variable for healing teammate
		private int defense;    // variable for defense points

    // Constructor to initialize character attributes
    public Character(String name, String teamName, int health, int strength, int heal, int defense) {
        this.name = name;
        this.teamName = teamName;
        this.health = health;   
        this.strength = strength;
        this.heal = heal;
        this.defense = defense;
    }
    
    // Getter methods for character attributes

    public String getName() {
        return name;
    }

    public String getTeamName() {
        return teamName;
    }

    public int getHealth() {
        return health;
    }

    public int getStrength() {
        return strength;
    }

    public int getHeal() {
        return heal;
    }

    public int getDefense() {
        return defense;
    }
    
    public boolean isAlive() {
    if (this.health > 0) {
        return true;
    } 
    else {
        System.out.println(this.name + " has died.");
        return false;
    }
    }

    // 추가적인 메소드들 (공격, 힐 등) 구현 필요


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

    public void healingTeammate(Character target) {
        // Healing logic
        if (!this.isAlive()) {
            System.out.println(this.name + " cannot heal because they are dead.");
            return;
        }
        if (target == null || !target.isAlive()) {
            System.out.println("Cannot heal a dead or non-existent character.");
            return;
        }
        if (!this.teamName.equals(target.getTeamName())) {
            System.out.println(this.name + " cannot heal " + target.getName() + " because they are not on the same team.");
            return; // 팀이 다르면 힐을 하지 못함
        }
        if(target.getHealth() >= 30) {
            System.out.println(target.getName() + " is already at full health.");
            return; // target이 이미 30 이상의 체력을 가지고 있다면 힐을 하지 못함
        }
        int healAmount = this.heal + target.getHealth();
        target.health = healAmount; // Heal the target character
        System.out.println(this.name + " heals " + target.getName() + " for " + this.heal + " health points.");
        
    }

    public void takeDamage(int damage) {
        this.health -= damage;
        if (this.health < 0) {
            this.health = 0;
        }
    }
}
