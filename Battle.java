import java.util.*;

public class Battle {
    private Team teamA;
    private Team teamB;
    private Dice dice;
    private Scanner input;
    private Character lastActor; // 이전 턴에 사용된 캐릭터
    private int round;

    public Battle(Team teamA, Team teamB) {
        this.teamA = teamA;
        this.teamB = teamB;
        this.dice = new Dice();
        this.input = new Scanner(System.in);
        this.lastActor = null;
        this.round = 1;
    }

    public void startBattle() {
        System.out.println(" Battle Begins Between " + teamA.getMember() + " and " + teamB.getMember() );

        boolean isTeamATurn = true;

        while (!teamA.isDefeated() && !teamB.isDefeated()) {
            System.out.println("=== ROUND " + round + " ===");

            Team currentTeam = isTeamATurn ? teamA : teamB;
            Team opponentTeam = isTeamATurn ? teamB : teamA;

            playTurn(currentTeam, opponentTeam);

            isTeamATurn = !isTeamATurn;
            round++;
        }

        if (teamA.isDefeated()) {
            System.out.println(teamB.getMember() + " wins!");
        } else {
            System.out.println(teamA.getMember() + " wins!");
        }
    }

    private void playTurn(Team currentTeam, Team opponentTeam) {
        System.out.println(currentTeam + "'s Turn");

        Character actor = selectActor(currentTeam);
        lastActor = actor;

        if (actor.getHeal() > 0 && round > 1) {
            Character target = selectTarget(currentTeam, true);
            actor.healingTeammate(target);
        } else {
            Character target = selectTarget(opponentTeam, false);
            int diceValue = dice.roll();
            System.out.println(" Dice rolled: " + diceValue);
            Action attack = new Action("Basic Attack", diceValue);
            String result = attack.perform(actor, target);
            System.out.println(result);
        }
    }

    private Character selectActor(Team team) {
        while (true) {
            System.out.println("Choose your actor:");
            ArrayList<Character> alive = team.getAliveMembers();
            for (int i = 0; i < alive.size(); i++) {
                System.out.println(i + ": " + alive.get(i).getName());
            }
            System.out.print("Enter number: ");
            int choice = input.nextInt();

            if (choice >= 0 && choice < alive.size()) {
                Character selected = alive.get(choice);
                if (selected.equals(lastActor)) {
                    System.out.println("Cannot reuse the same actor from last turn.");
                    continue;
                }
                if (round == 1 && selected.getHeal() > 0) {
                    System.out.println("Healers cannot act in round 1.");
                    continue;
                }
                return selected;
            } else {
                System.out.println("Invalid choice. Try again.");
            }
        }
    }

    private Character selectTarget(Team team, boolean healing) {
        while (true) {
            System.out.println("Choose a target from " + team.getMember() + ":");
            ArrayList<Character> alive = team.getAliveMembers();
            for (int i = 0; i < alive.size(); i++) {
                System.out.println(i + ": " + alive.get(i).getName() + " (HP: " + alive.get(i).getHealth() + ")");
            }
            System.out.print("Enter number: ");
            int choice = input.nextInt();

            if (choice >= 0 && choice < alive.size()) {
                Character selected = alive.get(choice);
                if (healing && selected.getHealth() == 30) {
                    System.out.println("That teammate is already at full health.");
                    continue;
                }
                return selected;
            } else {
                System.out.println("Invalid choice. Try again.");
            }
        }
    }
}
