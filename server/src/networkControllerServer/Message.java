package networkControllerServer;

public class Message {
    String  code;
    double amount = 0;
    int die = -1;

    public Message(String code) {
        this.code = code;
    }

    public Message(String code, double amount) {
        this.code = code;
        this.amount = amount;
    }

    public Message(String code, double amount, int die) {
        this.code = code;
        this.amount = amount;
        this.die = die;
    }

    public void setDie(int die) {
        this.die = die;
    }

    public void setMessage(String code) {
        this.code = code;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getMessage() {
        return code;
    }

    public double getAmount() {
        return amount;
    }

    public int getDie() {
        return die;
    }
}
