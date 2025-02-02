package gameLogic;

import java.util.Random;

public class Dealer {
    private Random random = new Random();

    //Methode der 2 Würfel
    public int rollDice() {
        int die1 = random.nextInt(6) + 1;
        int die2 = random.nextInt(6) + 1;
        System.out.println("Rolled dice are " + die1 + " + " + die2 + " = " +  (die1 + die2) );

        return die1 + die2;
    }

    public boolean isEven(int number) {
        return (number % 2) == 0;
    }

    public boolean isOdd(int number) {
        return (number % 2) == 1;
    }

}
