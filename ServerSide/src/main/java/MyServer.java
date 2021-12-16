import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class MyServer {
    private final Integer SERVER_PORT = 8880;
    Socket socket = null;

    public MyServer() {
        System.out.println("Server Start");
        startServer();
    }

    private void startServer(){
        try(ServerSocket serverSocket = new ServerSocket(SERVER_PORT)){
            System.out.println("Server wait connect");
            socket = serverSocket.accept();
            System.out.println("Client Connected");
            DataInputStream dis = new DataInputStream(socket.getInputStream());
            DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
            while(true){
                String str = dis.readUTF();
                System.out.println("Message from client: " + str);
                if(str.equalsIgnoreCase("/finish")){
                    dos.writeUTF(str);
                    closeConnection(dos, dis);
                    break;
                }
                str = "Yor send me: " + str;
                System.out.println("Message to client: " + str);
                dos.writeUTF(str);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    private void closeConnection(DataOutputStream dos, DataInputStream dis) {
        try {
            dos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            dis.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
