// import java.util.Scanner; // Battle 클래스에서 Scanner를 사용하므로, 여기서는 직접 필요 없으나 일관성을 위해 유지 가능

public class Client {
  public static void main(String[] args) {
    // 팀 A 생성 및 멤버 추가
    Team teamA = new Team("Marvel Comics"); // 팀 이름 설정
    teamA.addMember(new Character("Hulk", 30, 16,  2)); // 파이터: 이름, 체력, 힘, 지능, 방어력
    teamA.addMember(new Character("Iron Man", 30, 18,  3));   // 파이터
    // 힐러 캐릭터 추가 (이름, 체력, 방어력)
    teamA.addMember(new Character("Doctor Strange", 30, 4)); // 힐러
      teamA.addMember(new Character("Doctor Strange", 30, 4)); // 힐러

   

    // 팀 B 생성 및 멤버 추가
    Team teamB = new Team("DC Comics"); // 팀 이름 설정
    teamB.addMember(new Character("Batman", 30, 20,  5));    // 파이터
    teamB.addMember(new Character("Superman", 30, 14,  10));   // 파이터
    // 힐러 캐릭터 추가
    teamB.addMember(new Character("Joker", 30, 3));      // 힐러 (원작과 다를 수 있지만 게임 밸런스를 위함)

    System.out.println(" ### Masters of MQ: Turn-Based RPG Combat ### ");
    System.out.println("==============================================");

    // 전투 시작
    Battle battle = new Battle(teamA, teamB);
    battle.startBattle(); // 전투 시작 메소드 호출

    // System.out.println("==============================================");
    System.out.println(" ### Game Over. ### ");
  }
}