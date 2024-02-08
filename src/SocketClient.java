package src;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Objects;
import java.util.Scanner;

public class SocketClient {

    public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException{
        //get the localhost IP address, if server is running on some other IP, you need to use that
        Scanner in = new Scanner(System.in);
        InetAddress host = InetAddress.getLocalHost();
        Socket socket;
        ObjectOutputStream oos;
        ObjectInputStream ois;
        String input;
        int port = 9876;

        boolean exit = false;

        while (!exit) {
            //establish socket connection to server
            socket = new Socket(host.getHostName(), port);
            //write to socket using ObjectOutputStream
            oos = new ObjectOutputStream(socket.getOutputStream());
            System.out.print(">");
            input = in.next();
            oos.writeObject(input);
            //read the server response message
            ois = new ObjectInputStream(socket.getInputStream());
            String message = (String) ois.readObject();
            System.out.println("Server: " + message);
            ois.close();
            oos.close();
            socket.close();
            Thread.sleep(100);
            if(Objects.equals(input, "exit")){
                exit = true;
            }
        }
        //close resources
        in.close();
    }
}