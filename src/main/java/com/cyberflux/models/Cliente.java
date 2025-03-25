package com.cyberflux.models;

import java.util.concurrent.ThreadLocalRandom;

import com.cyberflux.management.GerenciadorRecursos;

// Classe abstrata para os clientes
public abstract class Cliente extends Thread {
    protected String nome;
    protected GerenciadorRecursos gerenciador;
    final int tempoMaximoDeEspera = 12000;

    protected long tempoEmEspera;
    // protected volatile boolean ativo = true; // Flag para encerrar a thread com
    // segurança

    public Cliente(String nome, GerenciadorRecursos gerenciador) {
        this.nome = nome;
        this.gerenciador = gerenciador;
    }

    public String getNome() {
        return this.nome;
    }

    // public void pararCliente() {
    // ativo = false; // Indica que a thread deve parar na próxima verificação
    // }

    @Override
    public void run() {
        try {
            if (solicitarRecursos()) {
                System.out.println("[Cliente]\t" + nome + " está utilizando os recursos.");
                Thread.sleep(ThreadLocalRandom.current().nextInt(5000, 10000));
                liberarRecursos();
                System.out.println("[Cliente]\t" + nome + " liberou os recursos.");
            } else if (tempoEmEspera >= tempoMaximoDeEspera) {
                System.out.println("[Cliente]\t" + nome + " não conseguiu os recursos necessários e foi embora.");
                // pararCliente();
            }

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    protected abstract boolean solicitarRecursos();

    protected abstract void liberarRecursos();
}
