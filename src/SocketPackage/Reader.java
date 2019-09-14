package SocketPackage;

import java.io.BufferedReader;
import java.net.Socket;
import java.util.ArrayList;

public interface Reader {
    ArrayList<String> read(Socket socket);
    ArrayList<String> read(Socket socket, BufferedReader reader);
}
