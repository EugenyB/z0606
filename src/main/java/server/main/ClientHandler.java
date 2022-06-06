package server.main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientHandler implements Runnable {
    private final Socket socket;

    public ClientHandler(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             PrintWriter writer = new PrintWriter(socket.getOutputStream(), true)
        ) {
            writer.println("Hello from server");
            String s;
            do {
                 s = reader.readLine();
                writer.println(reverse(s));
            } while (!s.isBlank());
        } catch (IOException ex) {
            throw new RuntimeException();
        } finally {
            try {
                socket.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private String reverse(String s) {
        StringBuilder builder = new StringBuilder(s);
        return builder.reverse().toString();
    }
}
