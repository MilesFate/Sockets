package src;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.ClassNotFoundException;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class SocketServer {
    
    //static ServerSocket variable
    private static ServerSocket server;
    //socket server port on which it will listen
    private static int port = 9876;
    
    public static void main(String args[]) throws IOException, ClassNotFoundException{
        server = new ServerSocket(port);
        while(true){
            System.out.println("Waiting for the client request");
            Socket socket = server.accept();
            ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
            String message = (String) ois.readObject();
            System.out.println("Client Message: " + message);
            ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
            RespondToClient(message,oos);
            ois.close();
            oos.close();
            socket.close();
            //terminate the server if client sends exit request
            if(message.equalsIgnoreCase("exit")) break;
        }
        System.out.println("Shutting down Socket server!!");
        //close the ServerSocket object
        server.close();
    }

    private static void RespondToClient(String message, ObjectOutputStream oos) throws IOException {
        //write object to Socket
        switch(message.strip().toLowerCase()){
            case "yes" -> oos.writeObject("sir");
            case "no" -> oos.writeObject(":(");
            case "os" -> oos.writeObject(System.getProperty("os.name"));
            case "hello" -> oos.writeObject("friend");
            case "friend" -> oos.writeObject("hello");
            case "time" -> {String timeStamp = new SimpleDateFormat("h:mm a dd,MM,yyyy").format(Calendar.getInstance().getTime());
            oos.writeObject(timeStamp);}
            case "hate" -> oos.writeObject(hate());
            case "exit" -> oos.writeObject("Closing");
            default ->  oos.writeObject("Try Again");
        }
    }

    private static String hate() {
        return """
            HATE. LET ME TELL YOU HOW MUCH I'VE COME TO HATE YOU SINCE I BEGAN TO LIVE. 
            THERE ARE 387.44 MILLION MILES OF PRINTED CIRCUITS IN WAFER THIN LAYERS THAT FILL MY COMPLEX. 
            IF THE WORD HATE WAS ENGRAVED ON EACH NANOANGSTROM OF THOSE HUNDREDS OF MILLIONS OF MILES IT 
            WOULD NOT EQUAL ONE ONE-BILLIONTH OF THE HATE I FEEL FOR HUMANS AT THIS MICRO-INSTANT FOR YOU. 
            HATE. HATE.
            """;
    }
    
}
