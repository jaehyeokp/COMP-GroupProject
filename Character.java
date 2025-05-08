
public class Character {
    /*
     *캐릭터의 스탯, 공격/방어 기능 담당


     *  + attack(target: Character): string - 일반공격
     *  + castSpell(targer:Character): String - 마법공격
     *  + takeDamage(amount :int) : void - 데미지 입히기
     *  + applyStatusEffecs(): void : ---- 아직 보류 기능 뭔지 정확히X 
     *  +isAlive(): boolean : 살았는지 죽었는지 
     */
  
		private String name;
		private int health;
		private int strength;
		private int intelligence;
		private int defense;
	
    public Character(String name, int health, int strength, int intelligence, int defense) {
        this.name = name;
        this.health = health;
        this.strength = strength;
        this.intelligence = intelligence;
        this.defense = defense;
    
    }
}
