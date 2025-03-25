package com.cyberflux.utils;

import java.util.concurrent.atomic.AtomicInteger;

public class RelogioGlobal extends Thread {
    private static RelogioGlobal instancia;
    private final AtomicInteger tempoSimulado = new AtomicInteger(0); // Tempo em minutos
    private final int tempoMaximo = 8 * 60; // 8 horas em minutos
    private volatile boolean rodando = true; // Volatile para garantir visibilidade entre threads

    private RelogioGlobal() {
    }

    public static RelogioGlobal getInstancia() {
        if (instancia == null) {
            instancia = new RelogioGlobal();
        }
        return instancia;
    }

    public int getTempoAtual() {
        return tempoSimulado.get();
    }

    public boolean aindaRodando() {
        return rodando;
    }

    @Override
    public void run() {
        while (tempoSimulado.get() < tempoMaximo) {
            try {
                Thread.sleep(1000); // 1 segundo real = 10 minutos simulados
                int novoTempo = tempoSimulado.addAndGet(10);
                // System.out.println("[Relogio] Tempo simulado: " + novoTempo + " minutos");
                System.out.println("\n[Relogio]\t[" + (novoTempo + 480) / 60 + "h" + (novoTempo % 60) + "]\n");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        rodando = false;
        System.out.println("[Relogio] Simulação encerrada!");
    }
}
