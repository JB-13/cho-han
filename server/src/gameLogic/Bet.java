package gameLogic;

public class Bet {
    private double amount = 0.0;
    private boolean even = false;
    private boolean odd = false ;
    private boolean skip = true;
    private int number = -1;

    public double getAmount() {return this.amount;}
    public boolean isEven() {return this.even;}
    public boolean isOdd() {return this.odd;}
    public boolean isSkip() {return this.skip;}
    public int getNumber() {return this.number;}

    public void setAmount(double amount) {this.amount = amount;}
    public void setEven(boolean even) {this.even = even;}
    public void setOdd(boolean odd) {this.odd = odd;}
    public void setSkip(boolean skip) {this.skip = skip;}
    public void setNumber(int number) {this.number = number;}

}
