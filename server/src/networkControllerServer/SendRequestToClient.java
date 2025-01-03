package networkControllerServer;

import gameLogic.Dealer;
import gameLogic.Player;

import static database.UserDatabase.saveUser;
import static database.UserDatabase.updateBalance;


public class SendRequestToClient {

    private Dealer dealer;

    public SendRequestToClient(Dealer dealer) {
        this.dealer = dealer;
    }

    public void sendRoundOutcome(Player player, int rolledNumber) throws Exception {
        if (dealer.isOdd(rolledNumber)) {
            player.getServer().getTcpSend().sendCode("IOD");
        } else if (dealer.isEven(rolledNumber)) {
            player.getServer().getTcpSend().sendCode("IEV");
        }

        player.getServer().getTcpSend().sendCode("INU");
        player.getServer().getTcpSend().sendInt(rolledNumber);

        player.getServer().getTcpSend().sendCode("BAL");
        player.getServer().getTcpSend().sendDouble(player.getBalance());
        updateBalance(player.getName(), player.getBalance());
    }
}


/*Server Actions
===========================
is odd: IOD
is even: IEV
is number: INU | 2-12(int)
new balance: BAL | amount (double)
===========================*/

