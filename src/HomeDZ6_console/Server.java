package HomeDZ6_console;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class Server {


    public static void main(String[] args) throws IOException {


        ServerSocket serverSocket = new ServerSocket(8000);
        System.out.println("Ожидание подключения...");
        Socket clientSocket = serverSocket.accept();
        System.out.println("Клиент подключился!");

        DataInputStream in = new DataInputStream(clientSocket.getInputStream());
        DataOutputStream out = new DataOutputStream(clientSocket.getOutputStream());


        Thread thread1 = new Thread(() -> {
            serverInput(in);
        });
        thread1.start();

        Thread thread2 = new Thread(() -> {
            serverOutput(out);
        });
        thread2.start();

    }

    private static void serverOutput(DataOutputStream out) {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            String messageFromServer = scanner.nextLine();
            try {
                out.writeUTF(messageFromServer);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private static void serverInput(DataInputStream in) {
        while (true) {
            String message = null;
            try {
                message = in.readUTF();
            } catch (IOException e) {
                e.printStackTrace();
            }
            System.out.println(message);


            if (message.equals("/exit")) {
                break;
            }
        }
    }

}
