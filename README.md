# COMP1010-GroupK-

COMP1010 Major Assignment –K  README
=========================================================

1. What problem does your application solve

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
