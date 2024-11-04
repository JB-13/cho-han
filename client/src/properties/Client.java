package properties;

public class Client {
    private String name;
    private String password;
    private Double balance;
    private Bet bet;

    public Client(String name, String password, String balance, Bet bet) {}
    public Client(String name, String password, Bet bet) {setBalance(1000.0);}

    public String getName() {return this.name;}
    public String getPassword() { return this.password;}
    public Double getBalance() { return this.balance;}
    public Bet getBet() { return this.bet;}

    public void setName(String name) { this.name = name;}
    public void setPassword(String password) { this.password = password;}
    public void setBalance(Double balance) { this.balance = balance;}
    public void setBet(Bet bet) { this.bet = bet;}

    public void amountE(double amount) {
        this.bet.setAmount(amount);
        this.bet.setEven(true);
        this.bet.setOdd(false);
        this.bet.setSkip(false);
    }

    public void amountO(double amount) {
        this.bet.setAmount(amount);
        this.bet.setOdd(true);
        this.bet.setEven(false);
        this.bet.setSkip(false);
    }

}
