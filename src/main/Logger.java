package main;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.Semaphore;

public class Logger {
    private static Logger instance;
    private BufferedWriter writer;
    private final Semaphore semaphore = new Semaphore(1);
    private final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");

    private Logger() {
        try {
            writer = new BufferedWriter(new FileWriter("log.txt", true));
            log("========== INÍCIO DA SIMULAÇÃO ==========");
        } catch (IOException e) {
            System.err.println("Erro ao inicializar o logger: " + e.getMessage());
        }
    }

    public static Logger getInstance() {
        if (instance == null) {
            instance = new Logger();
        }
        return instance;
    }

    public void log(String message) {
        try {
            semaphore.acquire();
            String timestamp = dateFormat.format(new Date());
            writer.write("[" + timestamp + "] " + message + "\n");
            writer.flush();
        } catch (InterruptedException | IOException e) {
            System.err.println("Erro ao registrar log: " + e.getMessage());
        } finally {
            semaphore.release();
        }
    }

    public void close() {
        try {
            log("========== FIM DA SIMULAÇÃO ==========");
            writer.close();
        } catch (IOException e) {
            System.err.println("Erro ao fechar o logger: " + e.getMessage());
        }
    }
}