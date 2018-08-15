package learn. mina.one;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

public class Test {
    public static void main(String[] args) {
        try {
            Socket s = new Socket("localhost", 9123);
        } catch (UnknownHostException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
