package gameLogic;

import java.util.Random;

public class Dealer {
    private Random random = new Random();

    public int rollDice() {
        int die1 = random.nextInt(6) + 1;
        int die2 = random.nextInt(6) + 1;
        System.out.println("Geworfene  Würfel sind " + die1 + " + " + die2 + " = " +  (die1 + die2) );

        return die1 + die2;
    }

    public boolean isEven(int number) {
        return (number % 2) == 0;
    }

    public boolean isOdd(int number) {
        return (number % 2) == 1;
    }

}
