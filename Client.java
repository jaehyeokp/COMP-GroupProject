public class Client {
  public static void main(String[] args) {
    Team teamA = new Team("Team A");
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

  }
}
