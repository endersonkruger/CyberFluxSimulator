package com.cyberflux.resources;
// Classe representando o Headset VR
import java.util.concurrent.Semaphore;

public class Headset implements Recurso {
    private static final Semaphore semaforo = new Semaphore(6, true); // 6 Headsets disponíveis

    @Override
    public boolean alocar() throws InterruptedException {
        return semaforo.tryAcquire(); // Tenta pegar um Headset
    }

    @Override
    public void liberar() {
        semaforo.release(); // Libera um Headset
    }
}
