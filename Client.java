public class Client {
  public static void main(String[] args) {
    Team teamA = new Team("Team A");
    teamA.addMember(new Character("Attacker Aang", 30, 8, 6, 5));
    teamA.addMember(new Character("Sword Man Katara", 30, 6, 7, 4));
    teamA.addMember(new Character("Archer Toph", 30, 9, 3, 6));
    teamA.addMember(new Character("Soldier Sokka", 30, 7, 5, 5));
    teamA.addMember(new Character("Healer Haru", 30, 6, 4, 6));

    Team teamB = new Team("Team B");
  teamB.addMember(new Character("Attacker Zuko", 30, 8, 5, 5));
  teamB.addMember(new Character("Sword Man Azula", 30, 7, 6, 4));
  teamB.addMember(new Character("Archer Mai", 30, 6, 4, 6));
  teamB.addMember(new Character("Soldier Lee", 30, 7, 5, 5));
  teamB.addMember(new Character("Healer Iroh", 30, 6, 7, 4));

  }
}
