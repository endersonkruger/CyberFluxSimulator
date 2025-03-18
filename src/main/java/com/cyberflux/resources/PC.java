package com.cyberflux.resources;
// Classe representando o PC
import java.util.concurrent.Semaphore;

public class PC implements Recurso {
    private static final Semaphore semaforo = new Semaphore(10, true); // 10 PCs disponíveis

    @Override
    public boolean alocar() throws InterruptedException {
        return semaforo.tryAcquire(); // Tenta pegar um PC
    }

    @Override
    public void liberar() {
        semaforo.release(); // Libera um PC
    }
}
