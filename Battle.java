import java.util.ArrayList;
import java.util.Scanner;

public class Battle {
    private Team teamA;
    private Team teamB;
    private Dice dice;
    private int roundNumber;
    private Character lastActorTeamA;
    private Character lastActorTeamB;
    private Scanner scanner;

    private final Action BASIC_ATTACK = new Action("Basic Attack", 0);
    private final int HEAL_AMOUNT = 10;

    public Battle(Team teamA, Team teamB) {
        this.teamA = teamA;
        this.teamB = teamB;
        this.dice = new Dice();
        this.roundNumber = 1;
        this.lastActorTeamA = null;
        this.lastActorTeamB = null;
        this.scanner = new Scanner(System.in);
    }

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

                    if (actionChoice == 1) {
                        validInput = true;
                    } else if (actionChoice == 2 && actor.isHealer()) {
                        validInput = true;
                    } else if (actionChoice == 2 && !actor.isHealer()) {
                        System.out.println("'" + actor.getName() + "' is not a healer. Please choose Attack (1).");
                    } else {
                        System.out.println("Invalid input. Please enter 1" + (actor.isHealer() ? " or 2" : "") + ".");
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Please enter a number.");
                }
            }

            if (actionChoice == 1) {
                performAttackAction(actor, opponentTeam);
            } else if (actionChoice == 2) {
                performHealAction(actor, currentTeam);
            }
        }
        displayTeamStatus(teamA);
        displayTeamStatus(teamB);
    }

    private Character selectActor(Team team, Character lastActor, boolean isFirstTurnForTeam) {
        ArrayList<Character> aliveMembers = team.getAliveMembers();
        if (aliveMembers.isEmpty()) {
            System.out.println(team.getTeamName() + " has no characters available to act.");
            return null;
        }

        ArrayList<Character> selectableMembers = new ArrayList<>();
        System.out.println("Select character to act:");
        for (int i = 0; i < aliveMembers.size(); i++) {
            Character member = aliveMembers.get(i);
            String statusMessage = "";
            boolean canSelect = true;

            if (member == lastActor) {
                statusMessage = " (Acted last turn)";
                canSelect = false;
            } else if (isFirstTurnForTeam && member.isHealer()) {
                statusMessage = " (Healer cannot act on first turn)";
                canSelect = false;
            }

            System.out.println((i + 1) + ". " + member.getName() + " (HP: " + member.getHealth() + ", Role: " + (member.isHealer() ? "Healer" : "Fighter") + ")" + statusMessage);
            if (canSelect) {
                selectableMembers.add(member);
            }
        }

        if (selectableMembers.isEmpty()) {
            System.out.println("No characters available to select at this time (all excluded by rules).");
            return null;
        }

        Character selectedActor = null;
        while (selectedActor == null) {
            System.out.print("Enter number: ");
            try {
                int choiceInput = Integer.parseInt(scanner.nextLine());
                int choiceIndex = choiceInput - 1;

                boolean foundInSelectable = false;
                Character potentialActor = null;
                if (choiceIndex >= 0 && choiceIndex < aliveMembers.size()) {
                    potentialActor = aliveMembers.get(choiceIndex);
                    if (selectableMembers.contains(potentialActor)) {
                        selectedActor = potentialActor;
                        foundInSelectable = true;
                    }
                }

                if (!foundInSelectable) {
                    System.out.println("Invalid choice or character not allowed by rules. Please select again.");
                    System.out.println("--- Displaying selectable characters again ---");
                    for (int i = 0; i < aliveMembers.size(); i++) {
                        Character member = aliveMembers.get(i);
                        String statusMessage = "";
                        if (member == lastActor) {
                            statusMessage = " (Acted last turn)";
                        } else if (isFirstTurnForTeam && member.isHealer()) {
                            statusMessage = " (Healer cannot act on first turn)";
                        }

                        if (!(member == lastActor || (isFirstTurnForTeam && member.isHealer()))) {
                            System.out.println((i + 1) + ". " + member.getName() + " (HP: " + member.getHealth() + ", Role: " + (member.isHealer() ? "Healer" : "Fighter") + ")");
                        } else {
                            System.out.println((i + 1) + ". " + member.getName() + " (HP: " + member.getHealth() + ", Role: " + (member.isHealer() ? "Healer" : "Fighter") + ")" + statusMessage + " - Cannot select");
                        }
                    }
                }
            } catch (NumberFormatException e) {
                System.out.println("Please enter a number.");
            }
        }
        return selectedActor;
    }

    private void performAttackAction(Character actor, Team opponentTeam) {
        System.out.println("Select target to attack from " + opponentTeam.getTeamName() + ":");
        ArrayList<Character> aliveOpponents = opponentTeam.getAliveMembers();

        if (aliveOpponents.isEmpty()) {
            System.out.println(opponentTeam.getTeamName() + " has no available targets!");
            return;
        }

        for (int i = 0; i < aliveOpponents.size(); i++) {
            Character opponent = aliveOpponents.get(i);
            System.out.println((i + 1) + ". " + opponent.getName() + " (HP: " + opponent.getHealth() + ")");
        }

        Character target = null;
        while (target == null) {
            System.out.print("Enter target number: ");
            try {
                int choice = Integer.parseInt(scanner.nextLine()) - 1;
                if (choice >= 0 && choice < aliveOpponents.size()) {
                    Character potentialTarget = aliveOpponents.get(choice);
                    if (!potentialTarget.isAlive()) {
                        System.out.println(potentialTarget.getName() + " is already defeated. Select another target.");
                    } else {
                        target = potentialTarget;
                    }
                } else {
                    System.out.println("Number out of range. Please select again.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Please enter a number.");
            }
        }

        int diceRoll = dice.roll();
        String resultMessage = BASIC_ATTACK.perform(actor, target, diceRoll);
        System.out.println(resultMessage);
    }

    private void performHealAction(Character healer, Team ownTeam) {
        System.out.println(healer.getName() + " select target to heal from " + ownTeam.getTeamName() + " (can heal self):");
        ArrayList<Character> aliveAllies = ownTeam.getAliveMembers();

        if (aliveAllies.isEmpty()) {
            System.out.println(ownTeam.getTeamName() + " has no healable targets!");
            return;
        }

        ArrayList<Character> healableAllies = new ArrayList<>();
        for (int i = 0; i < aliveAllies.size(); i++) {
            Character ally = aliveAllies.get(i);
            if (ally.getHealth() < 30) {
                healableAllies.add(ally);
            }
        }

        if (healableAllies.isEmpty()) {
            System.out.println("All allies are at full health or cannot be healed.");
            return;
        }

        System.out.println("Healable targets:");
        for (int i = 0; i < healableAllies.size(); i++) {
            Character ally = healableAllies.get(i);
            System.out.println((i + 1) + ". " + ally.getName() + " (HP: " + ally.getHealth() + "/30)");
        }

        Character target = null;
        while (target == null) {
            System.out.print("Enter target number: ");
            try {
                int choice = Integer.parseInt(scanner.nextLine()) - 1;
                if (choice >= 0 && choice < healableAllies.size()) {
                    target = healableAllies.get(choice);
                } else {
                    System.out.println("Number out of range. Please select again.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Please enter a number.");
            }
        }
        healer.heal(target, HEAL_AMOUNT);
    }

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

    private void displayTeamStatus(Team team) {
        System.out.println("\n  [" + team.getTeamName() + " Team Status]");
        ArrayList<Character> members = team.getMember();
        for (int i = 0; i < members.size(); i++) {
            Character member = members.get(i);
            String lifeStatus;
            if (member.isAlive()) {
                lifeStatus = "Alive";
            } else {
                lifeStatus = "Defeated";
            }
            System.out.println("    - " + member.getName() + " (HP: " + member.getHealth() + "/30) [" + lifeStatus + "]");
        }
    }
}
