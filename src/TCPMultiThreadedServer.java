import mediator.StudentListClientHandler;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class TCPMultiThreadedServer {
    public static void main(String[] args) throws IOException {
        final int PORT = 9876;
        System.out.println("Starting Server...");

        //Create welcoming socket at port 9876
        ServerSocket welcomeSocket = new ServerSocket(PORT);
        while(true){
            System.out.println("Waiting for a client...");

            //Wait, on welcoming socket for contact by client
            Socket socket = welcomeSocket.accept();

            //Start a thread with the client communication
            StudentListClientHandler c = new StudentListClientHandler();
            Thread t = new Thread(c);
            t.start();
        }
    }
}
