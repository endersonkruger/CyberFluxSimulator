package com.cyberflux.resources;
// Classe representando a Cadeira ergonômica
import java.util.concurrent.Semaphore;

public class Cadeira implements Recurso {
    private static final Semaphore semaforo = new Semaphore(8, true); // 8 Cadeiras disponíveis

    @Override
    public boolean alocar() throws InterruptedException {
        return semaforo.tryAcquire(); // Tenta pegar uma Cadeira
    }

    @Override
    public void liberar() {
        semaforo.release(); // Libera uma Cadeira
    }
}
