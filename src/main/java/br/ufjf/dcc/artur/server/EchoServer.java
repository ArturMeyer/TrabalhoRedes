/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.ufjf.dcc.artur.server;

/**
 *
 * @author Artur Welerson Sott Meyer
 */
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.ServerSocket;
import java.util.ArrayList;

public class EchoServer {

    private int PORT = 4444;
    private ServerSocket serverSocket;

    private static ArrayList<ClientHandler> clients = new ArrayList<>();

    public void start() throws IOException {
        serverSocket = new ServerSocket(PORT);
        System.err.println("Servidor iniciado na porta " + PORT);
    }

    public static void main(String[] args) {
        try {
            EchoServer server = new EchoServer();
            server.start();

            while (true) {
                Socket clientSocket = server.serverSocket.accept();
                System.err.println("Coneccao com cliente " + clientSocket.getInetAddress().getHostAddress() + " aceita");
                ClientHandler client = new ClientHandler(clientSocket);
                client.start();
            }

        } catch (IOException ex) {
            System.err.println("Erro - conexao encerrada com");
        }

    }
}

class ClientHandler extends Thread {

    private Socket socket;
    private BufferedReader in;
    private PrintWriter out;

    public ClientHandler(Socket socket) throws IOException {
        this.socket = socket;
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        out = new PrintWriter(socket.getOutputStream(), true);
    }

    public void run() {

        try {
            String mensage;
            while ((mensage = in.readLine()) != null) {
                if (mensage.contains("echo")) {
                    out.println("Escreva sua mensagem:");
                    String mensageEcho = in.readLine();
                    out.println(mensageEcho);
                } else if (mensage.equals("quit")) {
                    break;
                } else {
                    out.println("Comando invalido");
                }

            }
        } catch (IOException ex) {
            System.err.println("Erro - conexao encerrada");
        } finally {
            try {
                System.err.println("Fechando a conexao com " + socket.getInetAddress().getHostAddress());
                out.close();
                in.close();
                socket.close();
            } catch (IOException ex) {
                System.err.println("Conexao encerrada");
            }

        }
    }

}
