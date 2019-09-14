package SocketPackage;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class ServerImpl implements Server {
    ServerSocket socket;
    @Override
    public boolean initServer() {
        try {
            ServerSocket socket = new ServerSocket(5005);
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
    @Override
    public boolean openServer() {
        while(true) {
            Socket c_socket = null;
            try {
                c_socket = socket.accept();
            } catch (IOException e) {
                e.printStackTrace();
            }
            /*TODO Writer,Reader Class Import*/
            Writer writer = new WriterImpl();
            writer.write(c_socket);
            Reader reader = new ReaderImpl();
            ArrayList<String> strings = reader.read(c_socket);
            for(String value : strings) {
                System.out.println(value);
            }
        }
    }
}
