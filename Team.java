import java.util.ArrayList;

/**
 * The Team class manages a list of characters belonging to the same group.
 * It handles adding members, checking if the team is defeated,
 * retrieving alive members, and preventing duplicate names using recursion.
 */
public class Team {

    // === Fields ===

    private String teamName;                     // Name of the team
    private ArrayList<Character> members;        // List of all characters in the team

    // === Constructor ===

    /**
     * Constructs a Team with the specified team name.
     *
     * @param teamName the name of the team
     */
    public Team(String teamName) {
        this.teamName = teamName;
        this.members = new ArrayList<>();
    }

    // === Team Management ===

    /**
     * Adds a character to the team if a character with the same name doesn't already exist.
     *
     * @param c the character to add
     */
    public void addMember(Character c) {
        if (containsCharacterRecursive(c.getName(), 0)) {
            System.out.println("Character with name '" + c.getName() + "' already exists!");
        } else {
            members.add(c);
        }
    }

    /**
     * Returns the name of the team.
     *
     * @return the team name
     */
    public String getTeamName() {
        return this.teamName;
    }

    /**
     * Returns the list of all characters in the team.
     *
     * @return list of team members
     */
    public ArrayList<Character> getMember() {
        return this.members;
    }

    /**
     * Checks if the team is completely defeated (all members are dead).
     *
     * @return true if all characters are dead, false otherwise
     */
    public boolean isDefeated() {
        for (Character member : members) {
            if (member.isAlive()) {
                return false;
            }
        }
        return true;
    }

    /**
     * Returns a list of all characters in the team who are still alive.
     *
     * @return list of alive characters
     */
    public ArrayList<Character> getAliveMembers() {
        ArrayList<Character> aliveMembers = new ArrayList<>();
        for (Character member : members) {
            if (member.isAlive()) {
                aliveMembers.add(member);
            }
        }
        return aliveMembers;
    }

    /**
     * Recursively checks if a character with the given name exists in the team.
     *
     * @param name  the name to check
     * @param index current index during recursion
     * @return true if name exists, false otherwise
     */
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
