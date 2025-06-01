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
        Character lastActor;

        if (isTeamAActualTurn) {
            lastActor = lastActorTeamA;
        }
         else {
            lastActor = lastActorTeamB;
        }

        Character actor = selectActor(currentTeam, lastActor, roundNumber <= 2);

        if (actor == null) {
            System.out.println(currentTeam.getTeamName() + " has no available characters this turn.");
            return;
        }

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
                    if (actor.isHealer()) {
                        System.out.println("Invalid choice. Please enter 1 or 2.");
                    } 
                    else {
                        System.out.println("Invalid choice. Please enter 1.");
                    }
                    actionChoice = -1;
                }
            }

            if (actionChoice == 1) {
                performAttackAction(actor, opponentTeam);
            }
             else {
                performHealAction(actor, currentTeam);
            }
        }
        displayTeamStatus(teamA);
        displayTeamStatus(teamB);
    }

    // Lets the player pick which character from the team will act
    private Character selectActor(Team team, Character lastActor, boolean isFirstTurnForTeam) {
        ArrayList<Character> aliveMembers = team.getAliveMembers();
        if (aliveMembers.isEmpty()) {
            return null;
        }
        ArrayList<Character> selectable = new ArrayList<>();
        System.out.println("Pick a character to act:");

        int index = 1;
        for (int i = 0; i < aliveMembers.size(); i++) {
            Character member = aliveMembers.get(i);
            boolean canSelect = true;

            if (member == lastActor) {
                System.out.println(index + ". " + member.getName() + " (acted last turn, skip)");
                canSelect = false;
            } 
            else if (isFirstTurnForTeam && member.isHealer()) {
                System.out.println(index + ". " + member.getName() + " (healer, skips first turn)");
                canSelect = false;
            } 
            else {
                System.out.println(index + ". " + member.getName() + " (HP: " + member.getHealth() + ")");
            }

            if (canSelect) selectable.add(member);
            index++;
        }

        if (selectable.isEmpty()) {
            return null;
        }
        Character selected = null;
        while (selected == null) {
            System.out.print("Enter number: ");
            int choice = sc.nextInt() - 1;
            sc.nextLine();
            if (choice >= 0 && choice < aliveMembers.size()) {
                Character picked = aliveMembers.get(choice);
                for (int i = 0; i < selectable.size(); i++) {
                    if (selectable.get(i) == picked) {
                        selected = picked;
                        break;
                    }
                }
                if (selected == null) {
                    System.out.println("That character can't act now.");
                }
            }
            else {
                System.out.println("Out of range.");
            }
        }
        return selected;
    }

    // Performs attack logic: selecting target and applying damage
    private void performAttackAction(Character actor, Team opponentTeam) {
        ArrayList<Character> targets = opponentTeam.getAliveMembers();
        if (targets.isEmpty()) return;

        System.out.println("Pick target to attack:");
        for (int i = 0; i < targets.size(); i++) {
            Character c = targets.get(i);
            System.out.println((i + 1) + ". " + c.getName() + " (HP: " + c.getHealth() + ")");
        }

        Character target = null;
        while (target == null) {
            int choice = sc.nextInt() - 1;
            sc.nextLine();
            if (choice >= 0 && choice < targets.size()) {
                Character picked = targets.get(choice);
                if (picked.isAlive()) {
                    target = picked;
                } 
                else {
                    System.out.println("Already down.");
                }
            }
            else {
                System.out.println("Invalid number.");
            }
        }

        int roll = dice.roll();
        String result = BASIC_ATTACK.perform(actor, target, roll);
        System.out.println(result);
    }

    // Performs healing logic: selecting teammate and applying healing
    private void performHealAction(Character healer, Team ownTeam) {
        ArrayList<Character> targets = ownTeam.getAliveMembers();
        ArrayList<Character> healables = new ArrayList<>();

        for (int i = 0; i < targets.size(); i++) {
            Character c = targets.get(i);
            if (c.getHealth() < 30) {
                healables.add(c);
            }
        }

        if (healables.isEmpty()) {
            System.out.println("Nobody needs healing.");
            return;
        }

        System.out.println("Pick teammate to heal:");
        for (int i = 0; i < healables.size(); i++) {
            Character ally = healables.get(i);
            System.out.println((i + 1) + ". " + ally.getName() + " (HP: " + ally.getHealth() + "/30)");
        }

        Character target = null;
        while (target == null) {
            int choice = sc.nextInt() - 1;
            sc.nextLine();
            if (choice >= 0 && choice < healables.size()) {
                target = healables.get(choice);
            } 
            else {
                System.out.println("Try a valid number.");
            }
        }
        healer.heal(target, HEAL_AMOUNT);
    }

    // Checks which team has won the game and prints the result
    private void determineWinner() {
        System.out.println("");
        System.out.println("------- Battle Over --------");
        if (teamA.isDefeated()) {
            System.out.println(teamB.getTeamName() + " wins!");
        } 
        else if (teamB.isDefeated()) {
            System.out.println(teamA.getTeamName() + " wins!");
        } 
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
            if (member.isAlive()) {
                status = "Alive";
            } 
            else {
                status = "Down";
            }
            System.out.println("- " + member.getName() + " (HP: " + member.getHealth() + "/30) [" + status + "]");
        }
    }
}
