public class Client {
  public static void main(String[] args) {
    Team teamA = new Team("Team A");
    teamA.addMember(new Character("Aang", "Team A", 30, 8, 6, 5));
    teamA.addMember(new Character("Katara", "Team A", 30, 6, 7, 4));
    teamA.addMember(new Character("Toph", "Team A", 30, 9, 3, 6));
    teamA.addMember(new Character("Sokka", "Team A", 30, 7, 5, 5));
    teamA.addMember(new Character("Haru", "Team A", 30, 6, 4, 6));

    Team teamB = new Team("Team B");
  teamB.addMember(new Character("Zuko", "Team B", 30, 8, 5, 5));
  teamB.addMember(new Character("Azula", "Team B", 30, 7, 6, 4));
  teamB.addMember(new Character("Mai", "Team B", 30, 6, 4, 6));
  teamB.addMember(new Character("Ty Lee", "Team B", 30, 7, 5, 5));
  teamB.addMember(new Character("Iroh", "Team B", 30, 6, 7, 4));

  }
}
