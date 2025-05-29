/**
 * Character 클래스는 게임 캐릭터를 나타내며, 체력, 힘, 방어력과 같은 기본 속성과
 * 파이터 또는 힐러로서의 역할을 가집니다. 물리 공격, 마법 공격, 치유와 같은 전투 행동을 지원합니다.
 */
public class Character {
    // === 필드 (Fields) ===
    private String name;          // 캐릭터 이름
    private int health;           // 현재 체력
    private int strength;         // 물리 공격력
    private int intelligence;     // 마법 공격력 (힐러는 생성 시 지능 0)
    private int defense;          // 방어력
    private boolean isHealer;     // 힐러 여부 플래그

    // === 생성자 (Constructors) ===

    /**
     * 파이터 타입 캐릭터 생성자.
     *
     * @param name          캐릭터 이름
     * @param health        시작 체력
     * @param strength      물리 공격력 스탯
     * @param intelligence  마법 공격력 스탯
     * @param defense       방어력 스탯
     */
    public Character(String name, int health, int strength, int intelligence, int defense) {
        this.name = name;
        this.health = health;
        this.strength = strength;
        this.intelligence = intelligence;
        this.defense = defense;
        this.isHealer = false; // 파이터는 힐러가 아님
    }

    /**
     * 힐러 타입 캐릭터 생성자.
     * 힐러는 기본적으로 공격 스탯(힘, 지능)이 0으로 설정됩니다.
     *
     * @param name     힐러 이름
     * @param health   시작 체력
     * @param defense  방어력 스탯
     */
    public Character(String name, int health, int defense) {
        this.name = name;
        this.health = health;
        this.strength = 0;       // 힐러는 힘 0
        this.intelligence = 0;   // 힐러는 지능 0
        this.defense = defense;
        this.isHealer = true;    // 힐러로 설정
    }

    // === 게터 (Getters) ===

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

    // === 행동 (Actions) ===

    /**
     * 대상 캐릭터에게 물리 공격을 수행합니다. (게임 로직에서는 주로 Action 클래스의 perform 사용)
     * 데미지는 공격자의 힘 - 대상의 방어력으로 계산됩니다.
     *
     * @param target 공격 대상 캐릭터
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
     * 대상 캐릭터에게 마법 공격을 수행합니다. (게임 로직에서는 주로 Action 클래스의 perform 사용)
     * 데미지는 지능 - 방어력으로 계산됩니다.
     *
     * @param target 공격 대상 캐릭터
     */
    public void abilityPower(Character target) {
        if (!this.isAlive()) {
            System.out.println(this.name + " cannot cast a spell because they are defeated.");
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