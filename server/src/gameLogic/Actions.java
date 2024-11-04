package gameLogic;

public class Actions {
    private double amount = 0.0;
    private boolean even = false;
    private boolean odd = false ;
    private boolean skip = true;

    public Actions(int amount, boolean even, boolean odd, boolean skip) {}

    public double getAmount() {return this.amount;}
    public boolean isEven() {return this.even;}
    public boolean isOdd() {return this.odd;}
    public boolean isSkip() {return this.skip;}

    public void setAmount(double amount) {this.amount = amount;}
    public void setEven(boolean even) {this.even = even;}
    public void setOdd(boolean odd) {this.odd = odd;}
    public void setSkip(boolean skip) {this.skip = skip;}

}
