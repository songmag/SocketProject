import Controller.ClientService;
import Controller.Controller;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.LinkedList;

public class ServerMain {
	private ServerSocket sock;
	private int port;
	private LinkedList<ClientClass> clients;
	public ServerMain(int port)
	{
		this.port = port;
		clients = new LinkedList<ClientClass>();
	}
	public void runServer() {
		try {
			sock = new ServerSocket(port);
			while(true) {
				Socket socket = sock.accept();
				Thread t=new Thread(new ClientClass(socket,new ClientService()));
				t.start();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	class ClientClass implements Runnable{
		Socket socket;
		PrintWriter out;
		BufferedReader in;
		boolean status;
		Controller controller;
		public ClientClass(Socket socket,Controller controller){
			this.socket = socket;
			status = true;
			this.controller = controller;
			try {
				out = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));
				in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			} catch (IOException e) {
				System.out.println("Socket Input Output Error");
				e.printStackTrace();
			}
		}
		public boolean writeClient(String object) {
			if(out == null) return false;
			out.println(object);
			return true;
		}
		@Override
		public void run() {
			clients.add(this);
			while(status)
			{
				try {
					String value = in.readLine();
					//Client Logic		
					value=controller.runSystem(value);
					System.out.println(value);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		public void setController(Controller controller) {
			this.controller = controller;
		}
	}
	public static void main(String[] args) {
		//ServerMain server = new ServerMain(5555);
		//server.runServer();
	}
}
