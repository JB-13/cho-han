package gameLogic;


import networkControllerServer.TCPServer;

import java.io.Serializable;

public class Player implements Serializable {
    private static final long serialVersionUID = 6076348035636872825L;
    private String name;
    private String password;
    private Double balance;
    private Bet bet;
    private TCPServer server;

    public Player(String name, String password, Double balance) {
        this.name = name;
        this.password = password;
        this.balance = balance;
        this.bet = new Bet();
    }

    public Player(String name, String password) {
        this.name = name;
        this.password = password;
        this.setBalance(1000.0);
        this.bet = new Bet();
    }

    public TCPServer getServer() {
        return server;
    }
    public void setTCPServer(TCPServer server){
        this.server = server;
    }

    public String getName() {
        return this.name;
    }

    public String getPassword() {
        return this.password;
    }

    public Double getBalance() {
        return this.balance;
    }

    public Bet getBet() {
        return this.bet;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }

    public void setBet(Bet bet) {
        this.bet = bet;
    }

    public void betEven(double amount) {
            this.bet.setAmount(amount);
            this.bet.setEven(true);
            this.bet.setOdd(false);
            this.bet.setSkip(false);
            this.bet.setNumber(-1);
    }

    public void betOdd(double amount) {
            this.bet.setAmount(amount);
            this.bet.setEven(false);
            this.bet.setOdd(true);
            this.bet.setSkip(false);
    }

    public void betNumber(int number, double amount) {
            this.bet.setAmount(amount);
            this.bet.setEven(false);
            this.bet.setOdd(false);
            this.bet.setSkip(false);
            this.bet.setNumber(number);
    }

    public void skipRound() {
            this.bet.setAmount(0.0);
            this.bet.setEven(false);
            this.bet.setOdd(false);
            this.bet.setSkip(true);
            this.bet.setNumber(-1);
    }

    public void updateBalance(double amount) {
        this.balance += amount;
    }
}
