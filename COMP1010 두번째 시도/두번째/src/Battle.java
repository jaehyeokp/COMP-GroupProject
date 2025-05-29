import java.util.ArrayList;
// import java.util.InputMismatchException; // 현재 코드에서 직접 사용되지 않음
import java.util.Scanner;

public class Battle {
  // === 필드 (Fields) ===
  private Team teamA;                     // A팀
  private Team teamB;                     // B팀
  private Dice dice;                      // 주사위 객체
  private int roundNumber;                // 현재 라운드 번호
  private Character lastActorTeamA;       // A팀의 직전 행동 캐릭터
  private Character lastActorTeamB;       // B팀의 직전 행동 캐릭터
  private Scanner scanner;                // 사용자 입력을 위한 Scanner 객체

  // 기본 공격 액션 (모든 캐릭터가 사용 가능)
  private final Action BASIC_ATTACK = new Action("Basic Attack", 0); // 이름, 데미지 보정치

  // 기본 치유량 (힐러가 사용)
  private final int HEAL_AMOUNT = 10;

  /**
   * Battle 생성자
   * @param teamA A팀 객체
   * @param teamB B팀 객체
   */
  public Battle(Team teamA, Team teamB) {
    this.teamA = teamA;
    this.teamB = teamB;
    this.dice = new Dice();
    this.roundNumber = 1;
    this.lastActorTeamA = null; // 초기에는 직전 행동 캐릭터 없음
    this.lastActorTeamB = null;
    this.scanner = new Scanner(System.in);
  }

  /**
   * 전투를 시작하고 한 팀이 패배할 때까지 진행합니다.
   */
  public void startBattle() {
    System.out.println("\n⚔️ Battle Start! " + teamA.getTeamName() + " vs " + teamB.getTeamName() + " ⚔️");
    displayTeamStatus(teamA); // 초기 팀 상태 표시
    displayTeamStatus(teamB);

    // 한 팀이라도 패배하지 않은 동안 전투 계속
    while (!teamA.isDefeated() && !teamB.isDefeated()) {
      System.out.println("\n--- Round " + roundNumber + " ---");

      // 홀수 라운드는 A팀, 짝수 라운드는 B팀의 턴
      if (roundNumber % 2 != 0) {
        handleTurn(teamA, teamB, true); // true는 A팀의 실제 턴임을 의미
        if (teamB.isDefeated()) break; // B팀이 패배하면 전투 종료
      } else {
        handleTurn(teamB, teamA, false); // false는 B팀의 실제 턴임을 의미
        if (teamA.isDefeated()) break; // A팀이 패배하면 전투 종료
      }
      roundNumber++; // 다음 라운드로
    }
    determineWinner(); // 승자 판별
    scanner.close();   // Scanner 자원 해제
  }

  /**
   * 현재 팀의 턴을 처리합니다.
   * @param currentTeam 현재 턴을 진행하는 팀
   * @param opponentTeam 상대 팀
   * @param isTeamAActualTurn 현재 턴이 실제 A팀의 턴인지 여부 (lastActor 저장용)
   */
  private void handleTurn(Team currentTeam, Team opponentTeam, boolean isTeamAActualTurn) {
    System.out.println("\n>> " + currentTeam.getTeamName() + "'s Turn <<");
    
    // 행동할 캐릭터 선택 (규칙 적용)
    Character actor = selectActor(currentTeam, 
                                  isTeamAActualTurn ? lastActorTeamA : lastActorTeamB, 
                                  roundNumber <= 2); // 라운드 1, 2는 첫 턴 규칙 적용
    
    if (actor == null) { // 행동 가능한 캐릭터가 없는 경우 (모두 쓰러졌거나 규칙 위반)
      System.out.println(currentTeam.getTeamName() + " has no available characters to act. Skipping turn.");
      return;
    }

    // 현재 행동한 캐릭터를 해당 팀의 '직전 행동 캐릭터'로 기록 (예외 케이스 4번 위함)
    if (isTeamAActualTurn) {
      lastActorTeamA = actor;
    } else {
      lastActorTeamB = actor;
    }

    System.out.println("\nSelected Character: " + actor.getName() + " (HP: " + actor.getHealth() + ")");

    // 라운드에 따른 행동 선택 로직
    // R1 (A팀 첫 턴), R2 (B팀 첫 턴)
    if (roundNumber <= 2) {
      System.out.println("This is your team's first turn. Only attack is available. (Healers cannot act)");
      performAttackAction(actor, opponentTeam); // 공격 행동 수행
    } 
    // R3 이후
    else {
      System.out.println("Choose action for '" + actor.getName() + "':");
      System.out.println("1. Attack");
      if (actor.isHealer()) { // 선택된 캐릭터가 힐러일 경우만 치유 선택지 표시
        System.out.println("2. Heal");
      }

      int actionChoice = -1;
      boolean validInput = false;
      while (!validInput) {
        try {
          System.out.print("Enter choice: ");
          actionChoice = Integer.parseInt(scanner.nextLine());
          
          if (actionChoice == 1) { // 공격 선택
            validInput = true;
          } else if (actionChoice == 2 && actor.isHealer()) { // 힐러가 치유 선택
            validInput = true;
          } else if (actionChoice == 2 && !actor.isHealer()) { // 힐러 아닌데 치유 선택
            System.out.println("'" + actor.getName() + "' is not a healer. Please choose Attack (1).");
          }
          else {
            System.out.println("Invalid input. Please enter 1" + (actor.isHealer() ? " or 2" : "") + ".");
          }
        } catch (NumberFormatException e) {
          System.out.println("Please enter a number.");
        }
      }

      if (actionChoice == 1) {
        performAttackAction(actor, opponentTeam); // 공격 행동 수행
      } else if (actionChoice == 2) { // 힐러가 치유를 선택한 경우 (위에서 isHealer 검증됨)
        performHealAction(actor, currentTeam);   // 치유 행동 수행
      }
    }
    displayTeamStatus(teamA); // 행동 후 팀 상태 표시
    displayTeamStatus(teamB);
  }

  /**
   * 행동할 캐릭터를 선택합니다. 규칙을 적용합니다:
   * 1. 첫 턴에는 힐러 선택 불가.
   * 2. 바로 이전 턴에 행동했던 캐릭터는 선택 불가.
   * @param team 캐릭터를 선택할 팀
   * @param lastActor 해당 팀의 직전에 행동했던 캐릭터
   * @param isFirstTurnForTeam 해당 팀의 첫 번째 턴인지 여부 (라운드 1 또는 2)
   * @return 선택된 캐릭터, 없으면 null
   */
  private Character selectActor(Team team, Character lastActor, boolean isFirstTurnForTeam) {
    ArrayList<Character> aliveMembers = team.getAliveMembers(); // 살아있는 멤버만 가져옴
    if (aliveMembers.isEmpty()) {
      System.out.println(team.getTeamName() + " has no characters available to act.");
      return null;
    }

    ArrayList<Character> selectableMembers = new ArrayList<>();
    System.out.println("Select character to act:");
    for (int i = 0; i < aliveMembers.size(); i++) {
      Character member = aliveMembers.get(i);
      String statusMessage = ""; // Renamed from 'status' to avoid conflict if 'status' is a field
      boolean canSelect = true;

      // 예외 케이스 4: 바로 이전 라운드에 행동한 캐릭터 선택 불가
      if (member == lastActor) {
        statusMessage = " (Acted last turn)";
        canSelect = false;
      }
      // 예외 케이스 1: 팀의 첫 턴(R1, R2)에는 힐러 선택 불가
      else if (isFirstTurnForTeam && member.isHealer()) {
        statusMessage = " (Healer cannot act on first turn)";
        canSelect = false;
      }
      
      System.out.println((i + 1) + ". " + member.getName() + " (HP: " + member.getHealth() + ", Role: " + (member.isHealer() ? "Healer" : "Fighter") + ")" + statusMessage);
      if (canSelect) {
        selectableMembers.add(member); // 선택 가능한 멤버만 리스트에 추가
      }
    }
    
    if (selectableMembers.isEmpty()){
        System.out.println("No characters available to select at this time (all excluded by rules).");
        return null;
    }

    Character selectedActor = null;
    while (selectedActor == null) {
      System.out.print("Enter number: ");
      try {
        int choiceInput = Integer.parseInt(scanner.nextLine());
        int choiceIndex = choiceInput -1; // 사용자 입력은 1부터 시작, 인덱스는 0부터

        boolean foundInSelectable = false;
        Character potentialActor = null;
        if(choiceIndex >= 0 && choiceIndex < aliveMembers.size()){ 
            potentialActor = aliveMembers.get(choiceIndex);
            if(selectableMembers.contains(potentialActor)){ 
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
                 if (member == lastActor) statusMessage = " (Acted last turn)";
                 else if (isFirstTurnForTeam && member.isHealer()) statusMessage = " (Healer cannot act on first turn)";
                 
                 if (!(member == lastActor || (isFirstTurnForTeam && member.isHealer()))){
                     System.out.println((i + 1) + ". " + member.getName() + " (HP: " + member.getHealth() + ", Role: " + (member.isHealer() ? "Healer" : "Fighter") + ")");
                 } else {
                     System.out.println((i + 1) + ". " + member.getName() + " (HP: " + member.getHealth() + ", Role: " + (member.isHealer() ? "Healer" : "Fighter") + ")" + statusMessage + " - Cannot select");
                 }
             }
        }

      } catch (NumberFormatException e) {
        System.out.println("Please enter a number.");
      } catch (IndexOutOfBoundsException e) { // Should be less likely with current logic but good to keep
        System.out.println("Number out of range. Please select again.");
      }
    }
    return selectedActor;
  }

  /**
   * 공격 행동을 처리합니다. 대상을 선택하고 Action의 perform 메소드를 호출합니다.
   * @param actor 공격을 수행할 캐릭터
   * @param opponentTeam 상대 팀 (공격 대상이 있는 팀)
   */
  private void performAttackAction(Character actor, Team opponentTeam) {
    System.out.println("Select target to attack from " + opponentTeam.getTeamName() + ":");
    ArrayList<Character> aliveOpponents = opponentTeam.getAliveMembers(); // 살아있는 상대만 가져옴

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
        int choice = Integer.parseInt(scanner.nextLine()) - 1; // 사용자 입력은 1부터, 인덱스는 0부터
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

    int diceRoll = dice.roll(); // 주사위 굴리기 (0-5)
    String resultMessage = BASIC_ATTACK.perform(actor, target, diceRoll); 
    System.out.println(resultMessage); // 결과 메시지 출력
  }

  /**
   * 치유 행동을 처리합니다. 같은 팀 내에서 치유 대상을 선택하고 Character의 heal 메소드를 호출합니다.
   * @param healer 치유를 수행할 힐러 캐릭터
   * @param ownTeam 힐러가 속한 팀 (치유 대상이 있는 팀)
   */
  private void performHealAction(Character healer, Team ownTeam) {
    System.out.println(healer.getName()+" select target to heal from " + ownTeam.getTeamName() + " (can heal self):");
    ArrayList<Character> aliveAllies = ownTeam.getAliveMembers(); 

    if (aliveAllies.isEmpty()) { 
      System.out.println(ownTeam.getTeamName() + " has no healable targets!");
      return;
    }
    
    ArrayList<Character> healableAllies = new ArrayList<>();
    for(Character ally : aliveAllies){
        if(ally.getHealth() < 30){ // 최대 체력 30 가정
            healableAllies.add(ally);
        }
    }

    if(healableAllies.isEmpty()){
        System.out.println("All allies are at full health or cannot be healed.");
        return;
    }

    System.out.println("Healable targets:");
    for (int i = 0; i < healableAllies.size(); i++) {
      Character ally = healableAllies.get(i);
      System.out.println((i + 1) + ". " + ally.getName() + " (HP: " + ally.getHealth() + "/" + 30 + ")");
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
    healer.heal(target, HEAL_AMOUNT); // Character 클래스의 heal 메소드 호출
  }

  /**
   * 전투 종료 후 승자를 판별하고 메시지를 출력합니다.
   */
  private void determineWinner() {
    System.out.println("\n--- Battle Over ---");
    if (teamA.isDefeated()) { // A팀이 패배한 경우
      System.out.println("🎉 " + teamB.getTeamName() + " wins! 🎉");
    } else if (teamB.isDefeated()) { // B팀이 패배한 경우
      System.out.println("🎉 " + teamA.getTeamName() + " wins! 🎉");
    } else {
      System.out.println("The battle is a draw!"); 
    }
  }

  /**
   * 해당 팀의 현재 상태(멤버들의 체력 등)를 출력합니다.
   * @param team 상태를 표시할 팀
   */
  private void displayTeamStatus(Team team) {
    System.out.println("\n  [" + team.getTeamName() + " Team Status]");
    for (Character member : team.getMember()) { 
      String lifeStatus = member.isAlive() ? "Alive" : "Defeated"; // Renamed 'status' to avoid conflict
      System.out.println("    - " + member.getName() + " (HP: " + member.getHealth() + "/30) [" + lifeStatus + "]");
    }
  }
}