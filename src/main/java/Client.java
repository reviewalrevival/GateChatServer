import java.io.IOException;
import java.net.Socket;

/**
 * Created by Eugen on 18.04.2017.
 */
public class Client {
    public static void main(String[] args) {
        try {
            Socket client=new Socket("192.168.0.104",3000);
            while (true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
