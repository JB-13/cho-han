package networkControllerClient;

import static networkControllerClient.TCPClient.tcpRec;

public class HandleRequestFromServer {
    private static double balance = 1000.0;
    public static void handleRequest() throws Exception {
        String code = tcpRec.receiveCode();
        switch (code){
            case "IOD":
                System.out.println("=========================");
                System.out.println("odd die count"); break;
            case "IEV":
                System.out.println("=========================");
                System.out.println("even die count"); break;
            case "INU":
                int number = tcpRec.receiveInt();
                System.out.println("die count is " + number); break;
            case "BAL":
                balance = tcpRec.receiveDouble();
                System.out.println("your balance is " + balance);
                System.out.println("=========================");
                System.out.println("choose your option by inputing a number");
                System.out.println("1) bet odd");
                System.out.println("2) bet even");
                System.out.println("3) bet die count");
                System.out.println("4) skip round");
                System.out.println("5) exit lobby");
                break;
        }
    }

    public static double getBalance(){
        return balance;

    }

    public static void handleRoundOutcome()  throws Exception{
        System.out.println("=========================");
        handleRequest();
        handleRequest();
        handleRequest();
        System.out.println("==========================");
        System.out.println();
    }
}
/*
Server Actions
===========================
is odd: IOD
is even: IEV
is number: INU | 2-12(int)
new balance: BAL | amount (double)
===========================*/
