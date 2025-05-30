
public class Client {
  public static void main(String[] args) {
    // Create Team A and add members
    Team teamA = new Team("Marvel Comics"); // Set team name
    teamA.addMember(new Character("Hulk", 30, 16,  2)); // fighter: name, health, strength, defense
    teamA.addMember(new Character("Iron Man", 30, 18,  3));   // fighter
    teamA.addMember(new Character("Doctor Strange", 30, 4)); // healer : name, health, defense
      teamA.addMember(new Character("Doctor Strange", 30, 4)); // healer

   

    //// Create Team B and add members
    Team teamB = new Team("DC Comics"); 
    teamB.addMember(new Character("Batman", 30, 20,  5));    // fighter
    teamB.addMember(new Character("Superman", 30, 14,  10));   // fighter
    teamB.addMember(new Character("Joker", 30, 3));      // healer

    System.out.println(" ### Masters of MQ: Turn-Based RPG Combat ### ");
    System.out.println("==============================================");

    // Start the battle
    Battle battle = new Battle(teamA, teamB);
    battle.startBattle(); // Call method to begin the battle

    System.out.println(" ### Game Over. ### ");
  }
}