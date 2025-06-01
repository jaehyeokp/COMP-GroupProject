import java.util.ArrayList;
import java.util.Scanner;

public class Battle {

    private Team teamA;  // Team A instance (holds team name and members)
    private Team teamB;  // Team B instance
    private Dice dice;   // Dice object to generate random numbers for attacks
    private int roundNumber;  // Current round number (starts at 1)
    private Character lastActorTeamA;  // Last character who acted from team A
    private Character lastActorTeamB;  // Last character who acted from team B
    private Scanner sc;  // Scanner for reading user input from console

    private final Action BASIC_ATTACK = new Action("Basic Attack", 0);  // Represents the basic attack action
    private final int HEAL_AMOUNT = 10;  // Fixed healing amount when healer heals

    // Constructor to initialize battle with two teams
    public Battle(Team teamA, Team teamB) {
        this.teamA = teamA;
        this.teamB = teamB;
        this.dice = new Dice();
        this.roundNumber = 1;
        this.lastActorTeamA = null;
        this.lastActorTeamB = null;
        this.sc = new Scanner(System.in);
    }

    // Starts the main battle loop; keeps going until one team is defeated
    public void startBattle() {
        System.out.println("");
        System.out.println("Battle Start! " + teamA.getTeamName() + " vs " + teamB.getTeamName());
        displayTeamStatus(teamA);
        displayTeamStatus(teamB);

        while (!teamA.isDefeated() && !teamB.isDefeated()) {
            System.out.println("");
            System.out.println("------- Round " + roundNumber + " -------");

            if (roundNumber % 2 != 0) {  // Odd rounds: Team A's turn
                handleTurn(teamA, teamB, true);
                if (teamB.isDefeated()) break;
            } 
            else {  // Even rounds: Team B's turn
                handleTurn(teamB, teamA, false);
                if (teamA.isDefeated()) break;
            }
            roundNumber++;
        }
        determineWinner();
        sc.close();
    }
    // Handles one full turn for a team: selecting character and choosing action
    private void handleTurn(Team currentTeam, Team opponentTeam, boolean isTeamAActualTurn) {
    System.out.println("");
    System.out.println(">> " + currentTeam.getTeamName() + "'s Turn <<");

    // Decide which was the last actor from this team
        Character lastActor;
        if (isTeamAActualTurn) {
        lastActor = lastActorTeamA;
        } 
        else {
         lastActor = lastActorTeamB;
     }

     // Select a character to act this turn (respecting turn rules)
     Character actor = selectActor(currentTeam, lastActor, roundNumber <= 2);

     if (actor == null) {
        System.out.println(currentTeam.getTeamName() + " has no available characters this turn.");
        return;
        }

        // Update last actor tracker for this team
     if (isTeamAActualTurn) {
        lastActorTeamA = actor;
     } 
     else {
        lastActorTeamB = actor;
     }

        System.out.println("Selected: " + actor.getName() + " (HP: " + actor.getHealth() + ")");

        if (roundNumber <= 2) {  // First two rounds: healers cannot act, only attack allowed
        System.out.println("First turn: Only attack is allowed (healers wait).");
        performAttackAction(actor, opponentTeam);
     } 
     else {
        // Ask player to choose between attack or heal (if healer)
        System.out.println("Choose action for '" + actor.getName() + "':");
        System.out.println("1. Attack");
        if (actor.isHealer()) {
            System.out.println("2. Heal");
        }

        int actionChoice = -1;
        while (actionChoice == -1) {
            System.out.print("Enter choice: ");
            actionChoice = sc.nextInt();
            sc.nextLine();

            if (actionChoice == 1) {
                break;
            } 
            else if (actionChoice == 2 && actor.isHealer()) {
                break;
            }
            else {
                // Invalid input handling depending on character type
                if (actor.isHealer()) {
                    System.out.println("Invalid choice. Please enter 1 or 2.");
                } 
                else {
                    System.out.println("Invalid choice. Please enter 1.");
                }
                actionChoice = -1;
            }
        }

        // Execute the chosen action
        if (actionChoice == 1) {
            performAttackAction(actor, opponentTeam);
        }
        else {
            performHealAction(actor, currentTeam);
        }
    }

    // After action, display both team statuses
    displayTeamStatus(teamA);
    displayTeamStatus(teamB);
    }

  // Lets the player pick which character from the team will act
    private Character selectActor(Team team, Character lastActor, boolean isFirstTurnForTeam) {
     ArrayList<Character> aliveMembers = team.getAliveMembers();  // Get all alive members
     if (aliveMembers.isEmpty()) {
        return null;  // No one left to act
     }

     ArrayList<Character> selectable = new ArrayList<>();  // Stores characters eligible to act
     System.out.println("Pick a character to act:");

      int index = 1;
      for (int i = 0; i < aliveMembers.size(); i++) {
        Character member = aliveMembers.get(i);
        boolean canSelect = true;

        // Check if the member acted last turn → skip them
        if (member == lastActor) {
            System.out.println(index + ". " + member.getName() + " (acted last turn, skip)");
            canSelect = false;
        } 
        // Check if it's the first turn and the member is a healer → healers skip first turn
        else if (isFirstTurnForTeam && member.isHealer()) {
            System.out.println(index + ". " + member.getName() + " (healer, skips first turn)");
            canSelect = false;
        } 
        // Default: display as selectable
        else {
            System.out.println(index + ". " + member.getName() + " (HP: " + member.getHealth() + ")");
        }

        if (canSelect) selectable.add(member);  // Add to selectable list only if eligible
        index++;
     }

      if (selectable.isEmpty()) {
            return null;  // No selectable members left (edge case)
      }

       Character selected = null;
       while (selected == null) {
          System.out.print("Enter number: ");
           int choice = sc.nextInt() - 1;  // Convert to zero-based index
            sc.nextLine();
            if (choice >= 0 && choice < aliveMembers.size()) {
               Character picked = aliveMembers.get(choice);
               // Check if picked character is in selectable list
               for (int i = 0; i < selectable.size(); i++) {
                  if (selectable.get(i) == picked) {
                     selected = picked;
                     break;
                 }
              }
             if (selected == null) {
                  System.out.println("That character can't act now.");  // Selected but not allowed this turn
              }
            }
            else {
                System.out.println("Out of range.");  // Entered number is invalid
            }
      }
      return selected;
    }
    // Performs attack logic: selecting target and applying damage
    // Delegated attack action logic to its own method:
    //  Keeps handleTurn() cleaner and focused only on high-level flow
    //  Allows easy future modification or extension (ex: adding special attacks)
    //  Avoids duplicating attack selection logic across multiple places
    private void performAttackAction(Character actor, Team opponentTeam) {
    ArrayList<Character> targets = opponentTeam.getAliveMembers();
    if (targets.isEmpty()) return;  // No valid targets

    System.out.println("Pick target to attack:");
    for (int i = 0; i < targets.size(); i++) {
        Character c = targets.get(i);
        System.out.println((i + 1) + ". " + c.getName() + " (HP: " + c.getHealth() + ")");
    }

    Character target = null;
    while (target == null) {
        int choice = sc.nextInt() - 1;  // Convert to zero-based index
        sc.nextLine();
        if (choice >= 0 && choice < targets.size()) {
            Character picked = targets.get(choice);
            if (picked.isAlive()) {
                target = picked;  // Valid, alive target selected
            } 
            else {
                System.out.println("Already down.");  // Chose a defeated target
            }
        }
        else {
            System.out.println("Invalid number.");  // Out-of-range selection
        }
    }

    int roll = dice.roll();  // Random bonus from dice
    String result = BASIC_ATTACK.perform(actor, target, roll);  // Perform attack and get result message
    System.out.println(result);
    }

    // Performs healing logic: selecting teammate and applying healing
    // Delegated healing action logic to its own method:
    //  Separates healer-specific logic from general turn handling
    //  Improves readability and maintainability of the Battle class
    //  Allows future extension (e.g., adding status effect removal, multi-target heals)
    private void performHealAction(Character healer, Team ownTeam) {
     ArrayList<Character> targets = ownTeam.getAliveMembers();
     ArrayList<Character> healables = new ArrayList<>();  // Store teammates that are not at full health

     for (int i = 0; i < targets.size(); i++) {
         Character c = targets.get(i);
         if (c.getHealth() < 30) {  // Only consider those needing healing
                healables.add(c);
          }
    }

    if (healables.isEmpty()) {
        System.out.println("Nobody needs healing.");  // Skip if no one can be healed
        return;
    }

      System.out.println("Pick teammate to heal:");
    for (int i = 0; i < healables.size(); i++) {
        Character ally = healables.get(i);
        System.out.println((i + 1) + ". " + ally.getName() + " (HP: " + ally.getHealth() + "/30)");
    }

    Character target = null;
    while (target == null) {
        int choice = sc.nextInt() - 1;  // Convert to zero-based index
        sc.nextLine();
        if (choice >= 0 && choice < healables.size()) {
            target = healables.get(choice);  // Select valid ally to heal
        } 
        else {
            System.out.println("Try a valid number.");  // Invalid input
        }
    }
    healer.heal(target, HEAL_AMOUNT);  // Apply healing
    }
    // Checks which team has won the game and prints the result
    private void determineWinner() {
        System.out.println("");
        System.out.println("------- Battle Over --------");
        
        // Check if Team A is fully defeated → Team B wins
        if (teamA.isDefeated()) {
            System.out.println(teamB.getTeamName() + " wins!");
        } 
        // Check if Team B is fully defeated → Team A wins
        else if (teamB.isDefeated()) {
            System.out.println(teamA.getTeamName() + " wins!");
        } 
        // If neither team is fully defeated (edge case), it's a draw
        else {
            System.out.println("It's a draw!");
        }
    }

    // Displays each team member's name, HP, and status (alive or down)
    private void displayTeamStatus(Team team) {
        System.out.println("");
        System.out.println("[" + team.getTeamName() + " Team Status]");
        
        ArrayList<Character> members = team.getMember();
        for (int i = 0; i < members.size(); i++) {
            Character member = members.get(i);
            String status;
            
            // Determine alive/down status for each member
            if (member.isAlive()) {
                status = "Alive";
            } 
            else {
                status = "Down";
            }
            
            // Print detailed status line for each member
            System.out.println("- " + member.getName() + " (HP: " + member.getHealth() + "/30) [" + status + "]");
        }
    }
}