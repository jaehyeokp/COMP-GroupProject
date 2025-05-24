import java.util.*;

public class Team {
  /*
   * -members : ArrayList<Character>
   * 
   * + addMember(character: Character) : void
   * + isDefeated(): boolean
   * + getAliveMembers(): ArrayList<Character>
   */

  private String teamName;
    
  private ArrayList<Character> members;

  public Team(String teamName) {
    this.teamName = teamName;
    this.members = new ArrayList<>();
  }

  public void addMember(Character character) {
    this.members.add(character);
}

  public ArrayList<Character> getMenber() {
    return this.members;
  }

  public boolean isDefeated() {
    for (Character member : members) {
      if (member.isAlive()) {
        return false;
      }
    }
    return true;
  }

  public ArrayList<Character> getAliveMembers() {
    ArrayList<Character> alivemember = new ArrayList<>();
    for (Character member : members) {
      if (member.isAlive()) {
        alivemember.add(member);
      }
    }
    return alivemember;
  } 
}

