package mediator;

import com.google.gson.Gson;
import model.Model;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class StudentListClientConnector implements Runnable {

    final int PORT = 9876;
    private boolean running;
    private Model model;
    private ServerSocket welcomeSocket;

    public StudentListClientConnector(Model model){
        this.model = model;
    }

    public void stop(){
        running = false;
    }

    @Override
    public void run(){
        try {
        System.out.println("Starting Server...");
        welcomeSocket = new ServerSocket(PORT);
        while(true) {
          System.out.println("Waiting for a client...");

          //Wait, on welcoming socket for contact by client
          Socket socket = welcomeSocket.accept();
          Gson gson = new Gson();

          //Create input stream attached to the socket
          ObjectInputStream in = new ObjectInputStream(socket.getInputStream());

          //Create output stream attached to the socket
          PrintWriter out = new PrintWriter((socket.getOutputStream()));

          //Read an object from client
          Object data = in.readObject();
          System.out.println("Client> " + data);

          //Send data to client
          out.println(data);

         /* Student student = gson.fromJson(data, Student.class);

          StudentListClientHandler studentList = new StudentListClientHandler(socket);
          Thread clientThread = new Thread(new StudentListClientHandler(studentList));
          clientThread.start();
          */
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
