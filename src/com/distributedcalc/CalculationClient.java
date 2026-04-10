package com.distributedcalc;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class CalculationClient {
    private static final String DEFAULT_HOST = "127.0.0.1";
    private static final int DEFAULT_PORT = 5000;

    public static void main(String[] args) {
        String host = args.length > 0 ? args[0] : DEFAULT_HOST;
        int port = args.length > 1 ? parsePort(args[1]) : DEFAULT_PORT;

        try (
                Socket socket = new Socket(host, port);
                BufferedReader serverInput = new BufferedReader(
                        new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8));
                PrintWriter serverOutput = new PrintWriter(
                        new OutputStreamWriter(socket.getOutputStream(), StandardCharsets.UTF_8), true);
                BufferedReader keyboard = new BufferedReader(
                        new InputStreamReader(System.in, StandardCharsets.UTF_8))
        ) {
            System.out.println(serverInput.readLine());
            System.out.println(serverInput.readLine());

            while (true) {
                System.out.print("Digite a operacao: ");
                String operation = keyboard.readLine();

                if (operation == null) {
                    break;
                }

                serverOutput.println(operation);

                String response = serverInput.readLine();
                if (response == null) {
                    System.out.println("Servidor encerrou a conexao.");
                    break;
                }

                System.out.println(response);

                if ("sair".equalsIgnoreCase(operation.trim())) {
                    break;
                }
            }
        } catch (IOException e) {
            System.err.println("Erro no cliente: " + e.getMessage());
        }
    }

    private static int parsePort(String portText) {
        try {
            return Integer.parseInt(portText);
        } catch (NumberFormatException e) {
            System.err.println("Porta invalida. Usando porta padrao " + DEFAULT_PORT);
            return DEFAULT_PORT;
        }
    }
}
