package gameLogic;

import java.util.Random;

public class Dice {
    private int [] number = {1,2,3,4,5,6};

    public int throwDice(){
        Random random = new Random();
        int index = random.nextInt(number.length);
        return number[index];

    }
}
