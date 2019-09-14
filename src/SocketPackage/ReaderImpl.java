package SocketPackage;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.ArrayList;

public class ReaderImpl implements Reader {

    @Override
    public ArrayList<String> read(Socket socket) {
        ArrayList<String> line = null;

        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            if(reader.ready())
            {
                line = new ArrayList<String>();
                while(reader.ready()) {
                    line.add(reader.readLine());
                }
            }
            return line;
        } catch (IOException e) {
            e.printStackTrace();
        }
       return line;
    }

    @Override
    public ArrayList<String> read(Socket socket, BufferedReader reader) {
        ArrayList<String> line = null;
        try {
            if(reader.ready())
            {
                line = new ArrayList<String>();
                while(reader.ready()) {
                    line.add(reader.readLine());
                }
            }
            return line;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return line;
    }
}
