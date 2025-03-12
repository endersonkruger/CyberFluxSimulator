package com.cyberflux.management;
import java.util.concurrent.Semaphore;
// Gerencia a alocação e liberação de recursos
public class GerenciadorRecursos {
    private final Semaphore pcs = new Semaphore(10, true);
    private final Semaphore headsets = new Semaphore(6, true);
    private final Semaphore cadeiras = new Semaphore(8, true);

    public boolean alocarPC() throws InterruptedException {
        return pcs.tryAcquire();
    }

    public void liberarPC() {
        pcs.release();
    }

    public boolean alocarHeadset() throws InterruptedException {
        return headsets.tryAcquire();
    }

    public void liberarHeadset() {
        headsets.release();
    }

    public boolean alocarCadeira() throws InterruptedException {
        return cadeiras.tryAcquire();
    }

    public void liberarCadeira() {
        cadeiras.release();
    }
}