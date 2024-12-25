package networkControllerServer;

import gameLogic.Dealer;
import gameLogic.Player;

import static networkControllerServer.TCPServer.tcpSend;

public class SendRequestToClient {

    private Dealer dealer;

    public SendRequestToClient(Dealer dealer) {
        this.dealer = dealer;
    }

    public void sendRoundOutcome(Player player, int rolledNumber) throws Exception {
        tcpSend.sendString("your bet is " + player.getBet());
        if (dealer.isOdd(rolledNumber)) {
            tcpSend.sendCode("IOD");
        } else if (dealer.isEven(rolledNumber)) {
            tcpSend.sendCode("IEV");
        }

        tcpSend.sendCode("INU");
        tcpSend.sendInt(rolledNumber);

        tcpSend.sendCode("BAL");
        tcpSend.sendDouble(player.getBalance());
    }
}


/*Server Actions
===========================
is odd: IOD
is even: IEV
is number: INU | 2-12(int)
new balance: BAL | amount (double)
===========================*/
