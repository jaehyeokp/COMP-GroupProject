public class Dice {
  // + roll (max: int) int
   private final int DiceNum=6;

    public Dice() {
    }

    public int roll() {
        return (int)(Math.random() * DiceNum) + 1;
    }
    

    // 이 부분은 확인용 나중에 지워야함! 
    public static void main(String[] args) {
        Dice d1= new Dice();
        System.out.println("Test: "+ d1.roll());
    }
}
