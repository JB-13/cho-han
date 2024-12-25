package networkControllerClient.marshalling;

import java.io.InputStream;
import java.nio.ByteBuffer;

public class TCPReceive {

    InputStream in = null;
    byte [] buffer;
    ByteBuffer bbuf;
    int bufferLength = 256;

    public TCPReceive (InputStream in) {
        this.in = in;
       // buffer = new byte [bufferLength];
       // bbuf = ByteBuffer.wrap(buffer);
    } // TCP_Receive



/*    private void fillBuffer(int size) throws Exception {
        bbuf.clear();
        int bytesRead = 0;
        while (bytesRead < size) {
            int result = in.read(buffer, bytesRead, size - bytesRead);
            if (result == -1) {
                throw new Exception("End of stream reached");
            }
            bytesRead += result;
        }
        bbuf.flip();
    }*/



    public int receiveInt ( ) throws Exception {
        byte[] buffer = new byte[4]; // 4 Bytes für einen Int-Wert
        int bytesRead = 0;

        // Den Buffer vollständig auslesen (sicherstellen, dass 4 Bytes im Puffer sind)
        while (bytesRead < 4) {
            int result = in.read(buffer, bytesRead, 4 - bytesRead); // Read remaining bytes
            if (result == -1) {
                throw new Exception("End of stream reached while reading int data");
            }
            bytesRead += result;
        }

        // Byte-Array in einen Double-Wert umwandeln
        ByteBuffer byteBuffer = ByteBuffer.wrap(buffer);
        return byteBuffer.getInt();
    } // receiveInt


    public double receiveDouble ( ) throws Exception{
        byte[] buffer = new byte[8]; // 8 Bytes für einen Double-Wert
        int bytesRead = 0;

        // Den Buffer vollständig auslesen (sicherstellen, dass 8 Bytes im Puffer sind)
        while (bytesRead < 8) {
            int result = in.read(buffer, bytesRead, 8 - bytesRead); // Read remaining bytes
            if (result == -1) {
                throw new Exception("End of stream reached while reading double data");
            }
            bytesRead += result;
        }

        // Byte-Array in einen Double-Wert umwandeln
        ByteBuffer byteBuffer = ByteBuffer.wrap(buffer);
        return byteBuffer.getDouble();
    } // receiveDouble


    public String receiveString ( ) throws Exception{
        // Erst die Länge des Strings empfangen
        byte[] lengthBuffer = new byte[Integer.BYTES];
        int bytesRead = 0;
        while (bytesRead < Integer.BYTES) {
            int result = in.read(lengthBuffer, bytesRead, Integer.BYTES - bytesRead);
            if (result == -1) {
                throw new Exception("End of stream reached while reading string length");
            }
            bytesRead += result;
        }
        int strLength = ByteBuffer.wrap(lengthBuffer).getInt();

        // Danach die tatsächlichen String-Daten empfangen
        byte[] strBytes = new byte[strLength];
        bytesRead = 0;
        while (bytesRead < strLength) {
            int result = in.read(strBytes, bytesRead, strLength - bytesRead);
            if (result == -1) {
                throw new Exception("End of stream reached while reading string data");
            }
            bytesRead += result;
        }
        return new String(strBytes, "UTF-8");
    } // receiveString


    public String receiveCode() throws Exception{
        // Erst die Länge des Strings empfangen
        byte[] codeBuffer = new byte[3];
        int bytesRead = 0;
        while (bytesRead < 3) {
            int result = in.read(codeBuffer, bytesRead, 3 - bytesRead);
            if (result == -1) {
                throw new Exception("End of stream reached while reading code");
            }
            bytesRead += result;
        }

        return new String(codeBuffer, "UTF-8").trim();
    }




}//TCPReceive
