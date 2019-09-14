package SocketPackage;

import java.io.BufferedWriter;
import java.net.Socket;

public interface Writer {
    void write(Socket socket);
    void write(Socket socket, BufferedWriter writer);
    void write(Socket socket, String value);
    void write(Socket socket, String...value);
}
