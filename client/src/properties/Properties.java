package properties;

public class Properties {
    private String name;
    private String password;
    private Double balance;

    public Properties(String name, String password, String balance) {}
    public Properties(String name, String password) {setBalance(1000.0);}

    public String getName() {return this.name;}
    public String getPassword() { return this.password;}
    public Double getBalance() { return this.balance;}

    public void setName(String name) { this.name = name;}
    public void setPassword(String password) { this.password = password;}
    public void setBalance(Double balance) { this.balance = balance;}
}
