package com.distributedcalc;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.atomic.AtomicInteger;

public class CalculationServer {
    private static final int DEFAULT_PORT = 5000;
    private static final AtomicInteger THREAD_COUNTER = new AtomicInteger(1);

    public static void main(String[] args) {
        int port = parsePort(args);

        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Servidor iniciado na porta " + port);

            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("Cliente conectado: " + clientSocket.getRemoteSocketAddress());
                Thread clientThread = new Thread(new ClientHandler(clientSocket));
                clientThread.setName("client-handler-" + THREAD_COUNTER.getAndIncrement());
                clientThread.start();
            }
        } catch (IOException e) {
            System.err.println("Erro no servidor: " + e.getMessage());
        }
    }

    private static int parsePort(String[] args) {
        if (args.length == 0) {
            return DEFAULT_PORT;
        }

        try {
            return Integer.parseInt(args[0]);
        } catch (NumberFormatException e) {
            System.err.println("Porta invalida. Usando porta padrao " + DEFAULT_PORT);
            return DEFAULT_PORT;
        }
    }
}
