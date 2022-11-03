import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Server {
    private ServerSocket serverSocket;
    private List<ServerLine> clients = new ArrayList<>();

    public Server(int port)  {
        try {
            this.serverSocket = new ServerSocket(port);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void start(){
        System.out.println("[SERVER] start");
        listen();
    }

    public void listen()  {
        System.out.println("[SERVER] listening...");
        Socket clientSocket;
        while(true) {
            try {
                clientSocket = serverSocket.accept();
                System.out.println("[SERVER] accepted");
                ServerLine line = new ServerLine(clientSocket , this);
                clients.add(line);
                line.start();

            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

    public void notifyRegisterClient(ServerLine sender){
       // System.out.println("STEP1 " +sender);
        String command = "NF";
        String message = command + sender.getClientName() +" "+ sender.getAccessKey();
        sendExceptSender(message , sender);
        for (ServerLine client : clients) {
            if (client != sender) sender.sendToClient(command + client.getClientName() + " " + client.getAccessKey());
        }
    }

    public void sendPrivateMessage(ServerLine sender , String receiverKey , String message){
        ServerLine receriver = findClient(receiverKey);
        System.out.println(receriver);
        String command = "IM";
        receriver.sendToClient(command + message);
    }

    public void sendToAll(String message){
        for (ServerLine client : clients) {
            client.sendToClient(message);
        }
    }

    public ServerLine findClient(String receiverKey){
        ServerLine lost = null;
        for (ServerLine client : clients) {
            if (client.getAccessKey().equals(receiverKey))
                lost = client;
        }
        return lost;
    }

    public void removeClient(ServerLine exiter){

    }

    public void sendExceptSender(String message , ServerLine sender){
      //  System.out.println("STEP2 " + message + sender);
        for (ServerLine client : clients) {
         //   System.out.println("     STEP2.1 " + client);
            if (client != sender) client.sendToClient(message);
        }
    }








}
