package networkControllerClient;

import static networkControllerClient.TCPClient.tcpRec;

public class HandleRequestFromServer {

    public static void handleRequest() throws Exception {
        String code = tcpRec.receiveCode();
        switch (code){
            case "IOD":
                System.out.println("odd die count"); break;
            case "IEV":
                System.out.println("even die count"); break;
            case "INU":
                int number = tcpRec.receiveInt();
                System.out.println("die count is " + number); break;
            case "BAL":
                double balance = tcpRec.receiveDouble();
                System.out.println("your balance is " + balance); break;

        }
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
