public class Dice {
  
  private final int DICE_SIDES = 6; // Number of sides on the dice (using 6 for 0-5 results)

  public Dice() {
  }

  /*
   * Rolls the dice and returns a value between 0 and (DICE_SIDES - 1).
   * Since DICE_SIDES is 6, this returns a value between 0-5.
   */
  public int roll() {
    // Random Dice Range 0-5 
    return (int)(Math.random() * DICE_SIDES+1);  
  }
}