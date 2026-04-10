package com.distributedcalc;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class ClientHandler implements Runnable {
    private final Socket socket;

    public ClientHandler(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try (
                Socket clientSocket = socket;
                BufferedReader input = new BufferedReader(
                        new InputStreamReader(clientSocket.getInputStream(), StandardCharsets.UTF_8));
                PrintWriter output = new PrintWriter(
                        new OutputStreamWriter(clientSocket.getOutputStream(), StandardCharsets.UTF_8), true)
        ) {
            output.println("Conexao estabelecida. Envie operacoes como: 2 + 3");
            output.println("Digite sair para encerrar.");

            String request;
            while ((request = input.readLine()) != null) {
                String normalizedRequest = request.trim();

                if (normalizedRequest.isEmpty()) {
                    output.println("ERRO: informe uma operacao.");
                    continue;
                }

                if (normalizedRequest.equalsIgnoreCase("sair")) {
                    output.println("Conexao encerrada.");
                    break;
                }

                try {
                    String result = ExpressionEvaluator.evaluate(normalizedRequest);
                    output.println("RESULTADO: " + result);
                } catch (IllegalArgumentException e) {
                    output.println("ERRO: " + e.getMessage());
                }
            }
        } catch (IOException e) {
            System.err.println("Falha ao atender cliente " + socket.getRemoteSocketAddress() + ": " + e.getMessage());
        } finally {
            System.out.println("Cliente desconectado: " + socket.getRemoteSocketAddress());
        }
    }
}
