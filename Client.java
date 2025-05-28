public class Client {
  public static void main(String[] args) {
    Team teamA = new Team("Team A");
<<<<<<< Updated upstream
    teamA.addMember(new Character("Aang", 30, 8, 6, 5));
    teamA.addMember(new Character("Katara", 30, 6, 7, 4));
    teamA.addMember(new Character("Toph", 30, 9, 3, 6));
    teamA.addMember(new Character("Sokka", 30, 7, 5, 5));
    teamA.addMember(new Character("Haru", 30, 6, 4, 6));

    Team teamB = new Team("Team B");
  teamB.addMember(new Character("Zuko", 30, 8, 5, 5));
  teamB.addMember(new Character("Azula", 30, 7, 6, 4));
  teamB.addMember(new Character("Mai", 30, 6, 4, 6));
  teamB.addMember(new Character("Ty Lee", 30, 7, 5, 5));
  teamB.addMember(new Character("Iroh", 30, 6, 7, 4));
=======


    teamA.addMember(new Character("1 Attacker Aang", "Team A", 30, 20, 0, 5));
    teamA.addMember(new Character("1 Sword Man Katara", "Team A", 30, 16, 0, 4));
    teamA.addMember(new Character("1 Archer Toph", "Team A", 30, 19, 0, 6));
    teamA.addMember(new Character("1 Soldier Sokka", "Team A", 30, 17, 0, 5));
    teamA.addMember(new Character("1 Healer Haru", "Team A", 30, 0, 4, 6));

    Team teamB = new Team("Team B");
  teamB.addMember(new Character("2 Attacker Zuko", "Team B", 30, 18, 0, 5));
  teamB.addMember(new Character("2 Sword Man Azula", "Team B", 30, 17, 0, 4));
  teamB.addMember(new Character("2 Archer Mai", "Team B", 30, 16, 0, 6));
  teamB.addMember(new Character("2 Soldier Lee", "Team B", 30, 17, 0, 5));
  teamB.addMember(new Character("2 Healer Iroh", "Team B", 30, 0, 7, 4));
>>>>>>> Stashed changes



  Battle battle = new Battle(teamA, teamB);  // 전투 인스턴스 생성
  battle.startBattle();                      // 전투 시작
  }
}
