import java.util.ArrayList; // ArrayList를 사용하기 위해 import

public class Team {
  private String teamName; // 팀의 이름
  private ArrayList<Character> members; // 팀에 속한 캐릭터들의 리스트

  /**
   * Team 생성자
   * @param teamName 팀의 이름
   */
  public Team(String teamName) {
    this.teamName = teamName;
    this.members = new ArrayList<>(); // 새 ArrayList로 멤버 리스트 초기화
  }

  /**
   * 팀에 캐릭터를 추가합니다.
   * @param character 추가할 캐릭터 객체
   */
  public void addMember(Character character) {
    this.members.add(character);
  }

  /**
   * 팀의 이름을 반환합니다.
   * @return 팀 이름
   */
  public String getTeamName() {
      return this.teamName;
  }
  
  /**
   * 팀의 모든 멤버 리스트를 반환합니다.
   * @return 캐릭터 객체로 이루어진 ArrayList
   */
  public ArrayList<Character> getMember() {
    return this.members;
  }

  /**
   * 팀이 패배했는지 (모든 멤버가 쓰러졌는지) 확인합니다.
   * @return 모든 멤버가 쓰러졌으면 true, 아니면 false
   */
  public boolean isDefeated() {
    for (Character member : members) {
      if (member.isAlive()) { // 한 명이라도 살아있으면 패배 아님
        return false;
      }
    }
    return true; // 모든 멤버가 isAlive() == false 이면 패배
  }

  /**
   * 팀에서 살아있는 멤버들만으로 이루어진 리스트를 반환합니다.
   * @return 살아있는 캐릭터 객체들로 이루어진 ArrayList
   */
  public ArrayList<Character> getAliveMembers() {
    // 변수명 관례에 따라 alivemember -> aliveMembers 로 수정 가능하나,
    // 기존 파일 내용을 존중하여 유지합니다. 기능상 문제는 없습니다.
    ArrayList<Character> aliveMembers = new ArrayList<>(); 
    for (Character member : members) {
      if (member.isAlive()) {
        aliveMembers.add(member);
      }
    }
    return aliveMembers;
  } 
}