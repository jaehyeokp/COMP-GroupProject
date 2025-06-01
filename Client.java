/**
 * The Client class initializes characters, assigns them to teams,
 * and starts the RPG turn-based battle.
 */
public class Client {
    public static void main(String[] args) {

        // === Create Team A ===
        Team teamA = new Team("Marvel Comics");
        teamA.addMember(new Character("Hulk", 30, 16, 2));         // Fighter
        teamA.addMember(new Character("Iron Man", 30, 18, 3));     // Fighter
        teamA.addMember(new Character("Doctor Strange", 30, 4));   // Healer

        // === Create Team B ===
        Team teamB = new Team("DC Comics");
        teamB.addMember(new Character("Batman", 30, 20, 5));       // Fighter
        teamB.addMember(new Character("Superman", 30, 14, 10));    // Fighter
        teamB.addMember(new Character("Joker", 30, 3));            // Healer

        // === Start the Game ===
        System.out.println(" ### Masters of MQ: Turn-Based RPG Combat ### ");
        System.out.println("==============================================");

        Battle battle = new Battle(teamA, teamB);
        battle.startBattle(); // Start the turn-based battle

        System.out.println(" ### Game Over. ### ");
    }
}
