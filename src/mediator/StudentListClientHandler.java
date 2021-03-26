package mediator;

import com.google.gson.Gson;
import model.Model;
import network.NetworkPackage;
import network.NetworkType;
import network.StringPackage;
import network.StudentPackage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class StudentListClientHandler implements Runnable {
    private Socket socket;
    private BufferedReader in;
    private PrintWriter out;
    private boolean running;
    private Gson gson;
    private Model model;
    private NetworkType type;

    public StudentListClientHandler(Socket socket, Model model, ThreadGroup group) throws IOException {
        this.socket = socket;
        this.model = model;

        //Create input stream attached to the socket
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

        //Create output stream attached to the socket
        out = new PrintWriter(socket.getOutputStream(), true);
    }

    public void stop(){
        running = false;
    }

    @Override
    public void run() {
        Gson gson = new Gson();
        try {
            //Read a request from client
            String request = in.readLine();
            System.out.println("Client> " + request);

            //Convert from JSon
            NetworkPackage networkPackage = gson.fromJson(request, NetworkPackage.class);
            System.out.println("Student> " + networkPackage);

            //Get types
            switch(type){
                case NAME:
                    StringPackage namePackage = gson.fromJson(request, StringPackage.class);
                    model.getStudentByName();
                    break;
                case NUMBER:
                    StringPackage numberPackage = gson.fromJson(request, StringPackage.class);
                    model.getStudentByStudyNumber();
                    break;
                case STUDENT:
                    StudentPackage studentPackage = gson.fromJson(request, StudentPackage.class);
                    break;
                case ERROR:
                    break;
                default:
                    break;
            }

            //Create reply
            String reply = out.toString();
            System.out.println("Server> " + reply);

            //Convert reply to Json
            String replyJson = gson.toJson(reply);

            //Send reply to client
            System.out.println("Server> " + replyJson);
            out.println(replyJson);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
