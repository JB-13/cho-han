package networkControllerServer;

import gameLogic.Dealer;
import gameLogic.Player;


public class SendRequestToClient {

    private Dealer dealer;

    public SendRequestToClient(Dealer dealer) {
        this.dealer = dealer;
    }

    public void sendRoundOutcome(Player player, int rolledNumber) throws Exception {
      //  tcpSend.sendString("your bet is " + player.getBet());
        if (dealer.isOdd(rolledNumber)) {
            player.getTCPServer().getTcpSend().sendCode("IOD");
        } else if (dealer.isEven(rolledNumber)) {
            player.getTCPServer().getTcpSend().sendCode("IEV");
        }

        player.getTCPServer().getTcpSend().sendCode("INU");
        player.getTCPServer().getTcpSend().sendInt(rolledNumber);

        player.getTCPServer().getTcpSend().sendCode("BAL");
        player.getTCPServer().getTcpSend().sendDouble(player.getBalance());
    }
}


/*Server Actions
===========================
is odd: IOD
is even: IEV
is number: INU | 2-12(int)
new balance: BAL | amount (double)
===========================*/
