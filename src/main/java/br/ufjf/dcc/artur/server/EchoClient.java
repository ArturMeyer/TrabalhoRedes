/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.ufjf.dcc.artur.server;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

/**
 *
 * @author Artur Welerson Sott Meyer
 */
public class EchoClient {

    private int PORT = 4444;
    private String SCREEN_NAME;
    private String HOST;
    private PrintWriter out;
    private BufferedReader in;
    private Scanner stdin;
    private Socket socket;

    public void iniciarConexao(String args[]) throws IOException {
        SCREEN_NAME = args[0];
        HOST = args[1];

        socket = new Socket(HOST, PORT);

        out = new PrintWriter(socket.getOutputStream(), true);
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        stdin = new Scanner(System.in);

        System.err.println("Conectado no " + HOST + " na porta " + PORT);
    }

    public static void main(String[] args) {
        try {
            EchoClient client = new EchoClient();
            client.iniciarConexao(args);
            String mensagem = "";
            while (!mensagem.equals("quit")) {

                System.out.print("[" + client.SCREEN_NAME + "]:");
                mensagem = client.stdin.nextLine();
                client.out.println("[SERVIDOR]:" + mensagem);
                System.out.println(client.in.readLine());
                
            }
            
            System.err.println("Fechando a conexao com " + client.HOST);
            client.out.close();
            client.in.close();
            client.socket.close();
            
            
        } catch (IOException ex) {
            System.err.println("Erro - conexao encerrada");
        }
    }
}
