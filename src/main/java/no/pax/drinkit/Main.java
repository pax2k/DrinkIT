package no.pax.drinkit;

import no.pax.drinkit.server.DrinkServer;

import java.util.Scanner;

public class Main {
    DrinkServer server;

    public Main() {
        try {
            startEmbeddedServer();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String... arg) throws Exception {
        new Main();
    }

    private void startEmbeddedServer() throws Exception {
        Runnable serverRunnable = new Runnable() {

            public void run() {
                try {
                    server = new DrinkServer();
                } catch (Exception e) {
                    e.printStackTrace();
                }

                Scanner scanner = new Scanner(System.in);
                final String next = scanner.next();

                if (next.equals("x")) {
                    try {
                        server.stop();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        };

        Thread serverThread = new Thread(serverRunnable);
        serverThread.start();
    }
}
