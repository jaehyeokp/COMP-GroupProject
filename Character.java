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

    
    public Character(String name, int health, int strength, int defense) {
        this.name = name;
        this.health = health;
        this.strength = strength;
        this.defense = defense;
        this.isHealer = false;  
    }   // Constructor for Fighter

    

    public Character(String name, int health, int defense) {
        this.name = name;
        this.health = health;
        this.strength = 0;       
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
     * 대상 캐릭터에게 마법 공격을 수행합니다. (게임 로직에서는 주로 Action 클래스의 perform 사용)
     * 데미지는 지능 - 방어력으로 계산됩니다.
     *
  

    /**
     * 캐릭터의 체력을 주어진 데미지만큼 감소시킵니다.
     * 체력은 0 미만으로 내려갈 수 없습니다.
     *
     * @param damage 받은 데미지 양
     */
    public void takeDamage(int damage) {
        this.health -= damage;
        if (this.health < 0) {
            this.health = 0; // 체력이 0 미만이 되지 않도록
        }
    }

    /**
     * 대상 캐릭터를 지정된 양만큼 치유합니다.
     * 힐러 캐릭터만 이 메소드를 사용할 수 있습니다.
     * 체력은 최대치(여기서는 30으로 가정)를 초과할 수 없습니다.
     *
     * @param target 치유 대상 캐릭터
     * @param amount 치유량
     */
    public void heal(Character target, int amount) {
        if (!this.isHealer) {
            System.out.println(this.name + " is not a healer and cannot heal.");
            return;
        }

        // 예외 케이스 2: 죽은 캐릭터는 살릴 수 없음
        if (!target.isAlive()) {
            System.out.println(target.getName() + " is already defeated and cannot be healed.");
            return;
        }

        // 예외 케이스 3: 체력이 가득 찬 캐릭터는 치유할 수 없음
        if (target.getHealth() == 30) { // 최대 체력을 30으로 가정
            System.out.println(target.getName() + " is already at full health.");
            return;
        }

        target.health += amount;
        if (target.health > 30) { // 최대 체력 30을 넘지 않도록
            target.health = 30;
        }

        System.out.println(this.name + " heals " + target.getName() + " for " + amount + " HP. Current HP: " + target.getHealth());
    }

    /**
     * 캐릭터가 살아있는지 확인합니다.
     *
     * @return 체력이 0보다 크면 true, 아니면 false
     */
    public boolean isAlive() {
        // 메시지 중복을 피하기 위해 System.out.println 제거
        return this.health > 0;
    }
}