package networkControllerClient;

public class HandleRequestFromServer {

    public void handleRequest(){
        //String code = receiveCode();
        String code = ""; //Platzhalter
        switch (code){
            case "IOD":
                System.out.println("odd die count"); break;
            case "IEV":
                System.out.println("even die count"); break;
            case "INU":
                //int number = receiveInt();
                System.out.println("die count is " /*+ number*/); break;
            case "BAL":
                //double balance = receiveDouble();
                System.out.println("your balance is " /*+ balance*/); break;




        }
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
