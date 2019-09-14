import SocketPackage.Server;
import SocketPackage.ServerImpl;

public class Main {
    public static void main(String[] args) {
        Server server = new ServerImpl();
        if(server.initServer())
        {
            System.out.println("SocketError");
        }
        else{
            server.openServer();
        }
    }
}
