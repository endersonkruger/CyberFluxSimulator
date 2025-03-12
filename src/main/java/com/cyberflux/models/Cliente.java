package com.cyberflux.models;
import java.util.concurrent.ThreadLocalRandom;
import com.cyberflux.management.GerenciadorRecursos;
// Classe abstrata para os clientes
abstract class Cliente extends Thread {
    protected String nome;
    protected GerenciadorRecursos gerenciador;

    public Cliente(String nome, GerenciadorRecursos gerenciador) {
        this.nome = nome;
        this.gerenciador = gerenciador;
    }

    @Override
    public void run() {
        try {
            if (solicitarRecursos()) {
                System.out.println(nome + " está utilizando os recursos.");
                Thread.sleep(ThreadLocalRandom.current().nextInt(2000, 5000));
                liberarRecursos();
                System.out.println(nome + " liberou os recursos.");
            } else {
                System.out.println(nome + " não conseguiu os recursos necessários e foi embora.");
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    protected abstract boolean solicitarRecursos();
    protected abstract void liberarRecursos();
}
