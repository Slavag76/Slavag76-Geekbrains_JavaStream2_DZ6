package HomeDZ6_console;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) throws IOException {
        Socket socket = new Socket("localhost", 8000);

        DataInputStream in = new DataInputStream(socket.getInputStream());
        DataOutputStream out = new DataOutputStream(socket.getOutputStream());


        Thread thread1 = new Thread(() -> {
            clientInput(in);
        });
        thread1.start();


        Thread thread2 = new Thread(() -> {
            clientOutput(out);
        });
        thread2.start();

    }

    private static void clientOutput(DataOutputStream out) {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            String messageFromClient = scanner.nextLine();
            try {
                out.writeUTF(messageFromClient);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private static void clientInput(DataInputStream in) {
        while (true) {
            String message = "";
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
