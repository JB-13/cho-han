package gameLogic;

import javax.xml.namespace.QName;

public class Player {
    private double balance;
    private String name;
    private String password;
    private Actions action;

    public Player(double balance, String name, String password, Actions action) {
        this.balance = balance;
        this.name = name;
        this.password = password;
       // this.action = action;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Actions getAction() {
        return action;
    }

    public void setAction(Actions action) {
        this.action = action;
    }
}
