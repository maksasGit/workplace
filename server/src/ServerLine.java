import java.io.*;
import java.net.Socket;

public class ServerLine extends Thread{

    private Socket socket;
    private PrintWriter writer;
    private Server server;
    private String clientName = null;
    private String accessKey;


    public ServerLine(Socket socket, Server server) {
        this.socket = socket;
        this.server = server;
        accessKey = randomKeyGenerate();
    }

    private String randomKeyGenerate(){
        String key = "";
        for (int i = 0; i < 8; i++) {
            char a = (char) (Math.random() * 26 + 'a');
            key = key + a;
        }
        System.out.println(this + " :: " + key);
        return key;
    }


    public void run(){
        try {
            InputStream input = socket.getInputStream();
            OutputStream output = socket.getOutputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(input));
            writer = new PrintWriter(output, true);
            String message;
            while ((message = reader.readLine()) != null){
                String command = message.substring(0,2);
                String content = message.substring(2);
                switch(command) {
                    case "LO" -> registClient(content);
                    case "ME" -> privateMessage(content);
                }
                System.out.println(message);
            }
            System.out.println("[SERVER] disconnected");
        }
        catch (IOException e) {
            System.out.println("[SERVER] disconnected");
            server.removeClient(this);
        }
    }

    public void sendToClient(String text) {
        //System.out.println("STEP3 " + clientName + " " + text);
        writer.println(text);
    }

    public String getAccessKey() {
        return accessKey;
    }

    public String getClientName() {
        return clientName;
    }

    public void privateMessage(String content){
        String receiverKey = content.substring(0,8);
        String message = content.substring(8);
        server.sendPrivateMessage(this, receiverKey , message);
    }

    public void registClient(String clientName) {
        this.clientName = clientName;
        server.notifyRegisterClient(this);
    }




}
