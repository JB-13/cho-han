package networkControllerServer.marshalling;

import java.io.OutputStream;
import java.nio.ByteBuffer;

public class TCPSend {

    OutputStream out = null;
    int bufferLength = 256;
    byte [] buffer;
    ByteBuffer bbuf;
    int writeBytes;
    public TCPSend (OutputStream out) {
        this.out = out;
        buffer = new byte [bufferLength];
        bbuf = ByteBuffer.wrap(buffer);
    } // TCP_Send
    public void sendInt (int n) throws Exception {
        bbuf.clear(); //leere den buffer
        bbuf.putInt(n);
        bbuf.flip(); //read only mode
        while (bbuf.hasRemaining()) {
            out.write(bbuf.get()); // Byte für Byte schreiben, bis der Buffer leer ist
        }
        out.flush();


    } // sendInt

    public void sendCode (String code) throws Exception{
        bbuf.clear();
        String codeUppercase = code.toUpperCase();
        char [] charCode = codeUppercase.toCharArray();
        for (int i = 0; i < charCode.length; i++) {
            bbuf.putChar(charCode[i]);
            System.out.println(charCode[i]);
        }
        while (bbuf.hasRemaining()) {
            out.write(bbuf.get());
        }
        out.flush();



    }

    public void sendCode (String code, double amount) throws Exception{
        String codeUppercase = code.toUpperCase();
    }


    public void sendCode(String code, double amount, int die) throws Exception{
        String codeUppercase = code.toUpperCase();
    }

}
