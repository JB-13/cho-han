package properties;

public class Actions {
    private Bet bet;
    public Actions() {

    }
    public void updateBetAmount(double amount) {
        bet.setAmount(amount);
    }
    public void updateBetOdd(){
        bet.setOdd(true);
        bet.setSkip(false);
    }
    public void updateBetEven(){
        bet.setEven(true);
        bet.setSkip(false);
    }

}
