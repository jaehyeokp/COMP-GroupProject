import java.util.ArrayList;
import java.util.Scanner;

/**
 * The Battle class controls the turn-based combat between two teams.
 * It manages turn logic, character actions, and displays results.
 */
public class Battle {

    // === Fields ===
    private Team teamA;
    private Team teamB;
    private Dice dice;
    private int roundNumber;
    private Character lastActorTeamA;
    private Character lastActorTeamB;
    private Scanner scanner;

    private final Action BASIC_ATTACK = new Action("Basic Attack", 0); // Standard attack
    private final int HEAL_AMOUNT = 10; // Fixed heal amount per heal action

    /**
     * Constructs a Battle instance with two teams.
     */
    public Battle(Team teamA, Team teamB) {
        this.teamA = teamA;
        this.teamB = teamB;
        this.dice = new Dice();
        this.roundNumber = 1;
        this.lastActorTeamA = null;
        this.lastActorTeamB = null;
        this.scanner = new Scanner(System.in);
    }

    /**
     * Starts the battle loop until one team is defeated.
     */
    public void startBattle() {
        System.out.println("\n Battle Start! " + teamA.getTeamName() + " vs " + teamB.getTeamName() + " ");
        displayTeamStatus(teamA);
        displayTeamStatus(teamB);

        while (!teamA.isDefeated() && !teamB.isDefeated()) {
            System.out.println("\n--- Round " + roundNumber + " ---");

            if (roundNumber % 2 != 0) {
                handleTurn(teamA, teamB, true);
                if (teamB.isDefeated()) break;
            } else {
                handleTurn(teamB, teamA, false);
                if (teamA.isDefeated()) break;
            }
            roundNumber++;
        }
        determineWinner();
        scanner.close();
    }

    /**
     * Handles one team's turn in the battle.
     */
    private void handleTurn(Team currentTeam, Team opponentTeam, boolean isTeamAActualTurn) {
        System.out.println("\n>> " + currentTeam.getTeamName() + "'s Turn <<");

        Character actor = selectActor(currentTeam,
            isTeamAActualTurn ? lastActorTeamA : lastActorTeamB,
            roundNumber <= 2);

        if (actor == null) {
            System.out.println(currentTeam.getTeamName() + " has no available characters to act. Skipping turn.");
            return;
        }

        if (isTeamAActualTurn) {
            lastActorTeamA = actor;
        } else {
            lastActorTeamB = actor;
        }

        System.out.println("\nSelected Character: " + actor.getName() + " (HP: " + actor.getHealth() + ")");

        if (roundNumber <= 2) {
            System.out.println("This is your team's first turn. Only attack is available. (Healers cannot act)");
            performAttackAction(actor, opponentTeam);
        } else {
            System.out.println("Choose action for '" + actor.getName() + "':");
            System.out.println("1. Attack");
            if (actor.isHealer()) {
                System.out.println("2. Heal");
            }

            int actionChoice = -1;
            boolean validInput = false;
            while (!validInput) {
                try {
                    System.out.print("Enter choice: ");
                    actionChoice = Integer.parseInt(scanner.nextLine());

                    if (actionChoice == 1 || (actionChoice == 2 && actor.isHealer())) {
                        validInput = true;
                    } else {
                        System.out.println("Invalid input. Please enter 1" + (actor.isHealer() ? " or 2" : "") + ".");
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Please enter a number.");
                }
            }

            if (actionChoice == 1) {
                performAttackAction(actor, opponentTeam);
            } else {
                performHealAction(actor, currentTeam);
            }
        }
        displayTeamStatus(teamA);
        displayTeamStatus(teamB);
    }

    /**
     * Lets the team select a character to act.
     */
    private Character selectActor(Team team, Character lastActor, boolean isFirstTurnForTeam) {
        ArrayList<Character> aliveMembers = team.getAliveMembers();
        if (aliveMembers.isEmpty()) return null;

        ArrayList<Character> selectableMembers = new ArrayList<>();
        System.out.println("Select character to act:");

        for (Character member : aliveMembers) {
            String statusMessage = "";
            boolean canSelect = true;

            if (member == lastActor) {
                statusMessage = " (Acted last turn)";
                canSelect = false;
            } else if (isFirstTurnForTeam && member.isHealer()) {
                statusMessage = " (Healer cannot act on first turn)";
                canSelect = false;
            }

            System.out.println("- " + member.getName() + " (HP: " + member.getHealth() + ", Role: " +
                (member.isHealer() ? "Healer" : "Fighter") + ")" + statusMessage);

            if (canSelect) selectableMembers.add(member);
        }

        if (selectableMembers.isEmpty()) return null;

        Character selectedActor = null;
        while (selectedActor == null) {
            System.out.print("Enter number: ");
            try {
                int choiceInput = Integer.parseInt(scanner.nextLine()) - 1;
                if (choiceInput >= 0 && choiceInput < aliveMembers.size()) {
                    Character potential = aliveMembers.get(choiceInput);
                    if (selectableMembers.contains(potential)) {
                        selectedActor = potential;
                    } else {
                        System.out.println("Selected character is not allowed this turn.");
                    }
                } else {
                    System.out.println("Invalid choice. Try again.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number.");
            }
        }
        return selectedActor;
    }

    /**
     * Performs an attack action by actor to opponent.
     */
    private void performAttackAction(Character actor, Team opponentTeam) {
        ArrayList<Character> targets = opponentTeam.getAliveMembers();
        if (targets.isEmpty()) return;

        System.out.println("Select target to attack:");
        for (int i = 0; i < targets.size(); i++) {
            Character c = targets.get(i);
            System.out.println((i + 1) + ". " + c.getName() + " (HP: " + c.getHealth() + ")");
        }

        Character target = null;
        while (target == null) {
            try {
                int choice = Integer.parseInt(scanner.nextLine()) - 1;
                if (choice >= 0 && choice < targets.size()) {
                    Character potential = targets.get(choice);
                    if (potential.isAlive()) {
                        target = potential;
                    } else {
                        System.out.println("Target is already defeated.");
                    }
                } else {
                    System.out.println("Choice out of range.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Please enter a number.");
            }
        }

        int roll = dice.roll();
        String result = BASIC_ATTACK.perform(actor, target, roll);
        System.out.println(result);
    }

    /**
     * Performs a heal action by the healer to one of its teammates.
     */
    private void performHealAction(Character healer, Team ownTeam) {
        ArrayList<Character> targets = ownTeam.getAliveMembers();
        ArrayList<Character> healables = new ArrayList<>();

        for (Character c : targets) {
            if (c.getHealth() < 30) {
                healables.add(c);
            }
        }

        if (healables.isEmpty()) {
            System.out.println("No allies need healing.");
            return;
        }

        System.out.println("Select ally to heal:");
        for (int i = 0; i < healables.size(); i++) {
            Character ally = healables.get(i);
            System.out.println((i + 1) + ". " + ally.getName() + " (HP: " + ally.getHealth() + "/30)");
        }

        Character target = null;
        while (target == null) {
            try {
                int choice = Integer.parseInt(scanner.nextLine()) - 1;
                if (choice >= 0 && choice < healables.size()) {
                    target = healables.get(choice);
                } else {
                    System.out.println("Invalid input. Try again.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Please enter a number.");
            }
        }
        healer.heal(target, HEAL_AMOUNT);
    }

    /**
     * Declares the winner based on team defeat.
     */
    private void determineWinner() {
        System.out.println("\n--- Battle Over ---");
        if (teamA.isDefeated()) {
            System.out.println(" " + teamB.getTeamName() + " wins! ");
        } else if (teamB.isDefeated()) {
            System.out.println(" " + teamA.getTeamName() + " wins! ");
        } else {
            System.out.println("The battle is a draw!");
        }
    }

    /**
     * Displays the health and status of each member of the team.
     */
    private void displayTeamStatus(Team team) {
        System.out.println("\n  [" + team.getTeamName() + " Team Status]");
        ArrayList<Character> members = team.getMember();
        for (Character member : members) {
            String status = member.isAlive() ? "Alive" : "Defeated";
            System.out.println("    - " + member.getName() + " (HP: " + member.getHealth() + "/30) [" + status + "]");
        }
    }
}