public class Dice {
  
  private final int DICE_SIDES = 6; // Number of sides on the dice (return 1-6)

  public Dice() {
  }

  /**
   * Rolls the dice and returns a value between 1 and DICE_SIDES (inclusive).
   * Since DICE_SIDES is 6, this returns a value between 1-6.
   */
  public int roll() {
    // Random Dice Range  1-6
    return (int)(Math.random() * DICE_SIDES+1);  
  }
}