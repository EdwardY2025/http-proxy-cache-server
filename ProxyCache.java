//114385084
//Edward Yeboah
// ISE 316 Extra Credit Assignment

import java.net.*;
import java.io.*;

public class ProxyCache {
    private static int port;
    private static ServerSocket serverSocket;

    private static final int BUF_SIZE = 8192;
    private static final int MAX_OBJECT_SIZE = 100000;

    public static void initialize(int portNumber) {
        port = portNumber;
        try {
            serverSocket = new ServerSocket(port);
        } catch (IOException e) {
            System.out.println("Error creating socket: " + e);
            System.exit(-1);
        }
    }

    public static void handleClientRequest(Socket clientSocket) {
        try {
            BufferedReader fromClient = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            HttpRequest request = new HttpRequest(fromClient);
            Socket serverSocket = new Socket(request.getHost(), request.getPort());
            DataOutputStream toServer = new DataOutputStream(serverSocket.getOutputStream());
            toServer.writeBytes(request.generateRequestString());

            DataInputStream fromServer = new DataInputStream(serverSocket.getInputStream());
            HttpResponse response = new HttpResponse(fromServer);
            DataOutputStream toClient = new DataOutputStream(clientSocket.getOutputStream());
            toClient.writeBytes(response.generateResponseString());

            clientSocket.close();
            serverSocket.close();
        } catch (IOException e) {
            System.out.println("Error handling client request: " + e);
        }
    }

    public static void main(String args[]) {
        int myPort = 0;
        try {
            myPort = Integer.parseInt(args[0]);
        } catch (ArrayIndexOutOfBoundsException | NumberFormatException e) {
            System.out.println("Invalid port number. Please provide a valid integer port number.");
            System.exit(-1);
        }

        initialize(myPort);

        while (true) {
            try {
                Socket clientSocket = serverSocket.accept();
                handleClientRequest(clientSocket);
            } catch (IOException e) {
                System.out.println("Error accepting client connection: " + e);
            }
        }
    }

	public static int getBufSize() {
		return BUF_SIZE;
	}

	public static int getMaxObjectSize() {
		return MAX_OBJECT_SIZE;
	}
}


