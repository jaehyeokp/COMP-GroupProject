import java.util.ArrayList;

public class Team {

    private String teamName;                     // Name of the team
    private ArrayList<Character> members;        // List of all characters in the team

    // Constructor
    public Team(String teamName) {
        this.teamName = teamName;
        this.members = new ArrayList<>();  // Initialize member list as a new ArrayList
    }


    // Adds a character to the team if a character with the same name doesn't exist
    public void addMember(Character c) {
        if (containsCharacterRecursive(c.getName(), 0)) {
            System.out.println("Character with name '" + c.getName() + "' already exists!");
        } else {
            members.add(c);
        }
    }

    // Returns the name of the team.
    public String getTeamName() {
        return this.teamName;
    }

    // Returns the list of all members in the team.
    public ArrayList<Character> getMember() {
        return this.members;
    }

    /*
   * Checks if the team is defeated
   * true if all members are defeated, false otherwise.
   */
    public boolean isDefeated() {
        for (Character member : members) {
            if (member.isAlive()) {  // If at least one member is alive, team is not defeated
                return false;
            }
        }
        return true;  // All members must be not alive to be defeated
    }

    // Returns an ArrayList of only the alive Character objects in the team.
    public ArrayList<Character> getAliveMembers() {
        ArrayList<Character> aliveMembers = new ArrayList<>();
        for (Character member : members) {
            if (member.isAlive()) {
                aliveMembers.add(member);
            }
        }
        return aliveMembers;
    }

    // Recursively finds if a character with the given name exists in the Team
    public boolean containsCharacterRecursive(String name, int index) {
        if (index >= members.size()) {
            return false;
        }

        if (members.get(index).getName().equals(name)) {
            return true;
        }

        return containsCharacterRecursive(name, index + 1);
    }
}
