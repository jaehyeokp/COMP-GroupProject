# COMP1010-GroupK-

//Structure(지워도됨 나중에)
majorAssignmentRpgGame.zip
└── majorAssignmentTopic1
    ├── Client.java
    ├── Battle.java
    ├── Team.java
    ├── Character.java
    ├── Dice.java
    ├── Action.java
    ├── StatusEffect.java (optional)
    ├── README.txt
    ├── UML Diagram.png
    └── lib (optional, e.g. JUnit)


//*Problem Statement
// *UML Diagram
// *Design  Process(클래스 설계 + 주요 구성요소)
// *Task Allocation
// *How to Run
// *Method Analysis
(두개 세개 가능한 메소드를 비교해서 ,왜 더 나은 선택인지 설명 -> ex)메모리 효율 etc.


--------------------------------------------------------------------------------------------------------------------
// 최종검토 사항
// 1-파일이름 , 팀원기여도 분배 ,how to run 미기재시 ,감점


//Component B(10%)
 1- 주석처리 : 메소드마다 - 이 메소드의 기능,이유,왜 등 핵심내용위주로만만
(예시) 

boolean allUnique(int[] data) {
  /*
  the logic of my design and implementation is to
  compare each item in the array with every OTHER
  item (not itself), and returning false as soon
  as two items at different indices are the same
  in their value
  */

  if(data == null) {    //to avoid NullPointerException
    return false;
    }
}


// 2- 들여쓰기 
좋은예시

Example of well-indented code:

int mystery(int[] a) {
  int count = 0;
//one blank line to separate logical sections, if needed

  for(int i=0; i < a.length; i++) {
    for(int k=i+1; k < a.length; k++) {
      if(a[i] == a[k]) {
          count++;
        }
    }
}
//again, at most one blank line

return false;
}



// 3. 변수이름  -> camelCased , final 사용시 모두 다 CAPITAL  ** 의미있는 변수이름으로만 사용할것**

//4. Delegation  (기능 분리)
같은 로직 반복 → 별도 메소드로 만들기



COMP1010 Major Assignment –K  README
=========================================================

1. What problem your application solves

This program simulates a turn-based RPG-style battle game between two teams of characters. Each character can either attack or heal, depending on their role (Fighter or Healer). The program is designed to demonstrate object-oriented programming principles, such as class design, encapsulation, and modular interaction, while solving the problem of managing complex game logic in a structured and maintainable way.
===============================================================================================================================
2. Program structure:

- Character.java: Represents a player character with fields like health, strength, and defense. It distinguishes between Fighters and Healers and handles actions like attacking and healing.
- Team.java: Manages a group of characters, allowing addition of members, checking defeat status, and filtering alive members.
- Action.java: Encapsulates an action (e.g., "Basic Attack") with a damage modifier and provides the logic to execute the action.
- Dice.java: Simulates a random dice roll to add unpredictability to attacks.
- Battle.java: Manages the flow of battle rounds, alternating turns between teams, choosing actions, and printing combat results.
- Client.java: The main program that initializes teams, adds members, and starts the battle simulation.

===============================================================================================================================

3. How to run the program:
- Open the folder named `majorAssignmentTopic1` in Visual Studio Code
- Ensure all `.java` files are compiled
- Run the program by executing `Client.java` as the entry point
- Follow on-screen prompts to simulate the turn-based battle

===============================================================================================================================

4. Task allocation:


(1) Hanseong Park (47975679)  Contribution (33%)
  Co-developed the Battle class with team, implemented Action, Dice, and Client classes. Designed UML, Wrote the README file.


(2)June Chang (48209163)  Contribution (33%)

Implemented the Character class and JUnit tests. Helped refine Action, Team, Battle classes ,and overall code structure and style.


(3) Jaehyeok Park(48640972) Contribution (33%)

Contributed to Character, Action, Battle, and Team class development. Focused on improving code readability with consistent commenting and style.

===============================================================================================================================



5. UML Diagram:



===============================================================================================================================

6. Method analysis:

Method A: Action.perform()
- Efficiency: Combines logic for calculating and applying damage in a single method.
- Pros: Clean encapsulation of combat logic; easy to extend with new action types.
- Cons: All logic is in one place; adding new mechanics (e.g. status effects) could clutter the method.
- Alternative: Use a Strategy design pattern to decouple behavior from the Action class.

Method B: Team.containsCharacterRecursive()
- Efficiency: Recursive search avoids external libraries but becomes inefficient for large teams.
- Pros: Demonstrates understanding of recursion, a key learning goal.
- Cons: Less readable and potentially stack-heavy for large input sizes.
- Alternative: Use a HashSet<String> for constant-time name lookups.

====================================================================================================
7.Game Rules

#The game is a turn-based RPG-style battle between Team A and Team B

#Each team has 3 characters:

2 Attackers with strength and defense stats
1 Healer with intelligence stat (used for healing allies)

Turns alternate between Team A → Team B, one character at a time

During each turn:

-If the character is an attacker, they attack a random enemy character
-If the character is a healer, they heal a random ally who is still alive

Damage is calculated using:

-attacker's strength - target's defense + dice roll (0–5)

##A minimum of 0 damage is guaranteed

Healing restores a fixed amount (10 HP), but cannot exceed max HP (30)

A character with 0 HP cannot act

The battle continues until one entire team is defeated (all HPs are 0)