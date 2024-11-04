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

    public void betEven(double amount) {
        this.bet = new Bet(amount,true, false,false);
    }

    public void betOdd(double amount) {
        this.bet = new Bet(amount,false, true,false);
    }

    public void skipRound() {
        this.bet = new Bet(0.0,false, false,true);
    }

    public void updateBalance(double amount) {
        this.balance += amount;
    }

}
