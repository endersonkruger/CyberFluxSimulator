package com.cyberflux.utils;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

// Classe para registrar eventos e logs
public class Logger {
    private static Logger instancia;
    private static final String LOG_FILE = "log.txt";

    private Logger() {}  // Construtor privado (Singleton)

    public static Logger getInstancia() {
        if (instancia == null) {
            synchronized (Logger.class) {
                if (instancia == null) {
                    instancia = new Logger();
                }
            }
        }
        return instancia;
    }

    public void log(String mensagem) {
        String timestamp = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format(new Date());
        String logMessage = "[" + timestamp + "] " + mensagem;

        // Exibir no console
        System.out.println(logMessage);

        // Salvar no arquivo
        try (PrintWriter out = new PrintWriter(new FileWriter(LOG_FILE, true))) {
            out.println(logMessage);
        } catch (IOException e) {
            System.err.println("Erro ao escrever no log: " + e.getMessage());
        }
    }
}
