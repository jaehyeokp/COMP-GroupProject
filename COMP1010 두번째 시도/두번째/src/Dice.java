public class Dice {
  // final 변수명은 ALL_UPPERS 관례, 주사위 면의 수 (0~5 결과 위해 6 사용)
  private final int DICE_SIDES = 6; 

  public Dice() {
  }

  /**
   * 주사위를 굴려 0에서 (DICE_SIDES - 1) 사이의 값을 반환합니다.
   * 현재 DICE_SIDES가 6이므로 0~5 사이의 값을 반환합니다.
   * @return 주사위 눈의 값
   */
  public int roll() {
    // Random Dice Range 0-5 
    return (int)(Math.random() * DICE_SIDES);  
  }
}