public class Client {
  public static void main(String[] args) {
    Team teamA = new Team("Team A");


    teamA.addMember(new Character("Attacker Aang", "Team A", 30, 8, 6, 5));
    teamA.addMember(new Character("Sword Man Katara", "Team A", 30, 6, 7, 4));
    teamA.addMember(new Character("Archer Toph", "Team A", 30, 9, 3, 6));
    teamA.addMember(new Character("Soldier Sokka", "Team A", 30, 7, 5, 5));
    teamA.addMember(new Character("Healer Haru", "Team A", 30, 6, 4, 6));

    Team teamB = new Team("Team B");
  teamB.addMember(new Character("Attacker Zuko", "Team B", 30, 8, 5, 5));
  teamB.addMember(new Character("Sword Man Azula", "Team B", 30, 7, 6, 4));
  teamB.addMember(new Character("Archer Mai", "Team B", 0, 6, 4, 6));
  teamB.addMember(new Character("Soldier Lee", "Team B", 30, 7, 5, 5));
  teamB.addMember(new Character("Healer Iroh", "Team B", 30, 6, 7, 4));

  }
}
