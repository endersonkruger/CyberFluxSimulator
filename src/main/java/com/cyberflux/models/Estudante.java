package com.cyberflux.models;
import com.cyberflux.management.GerenciadorRecursos;

// Cliente que precisa apenas de um PC
public class Estudante extends Cliente {
    public Estudante(String nome, GerenciadorRecursos gerenciador) {
        super(nome, gerenciador);
    }

    @Override
    protected boolean solicitarRecursos() {
        try {
            Cliente prioridade = gerenciador.proximoCliente();
            if (prioridade != null && prioridade != this) {
                Thread.sleep((long) (Math.random() * 500)); // Simula re-tentativa
                return false;
            }

            return gerenciador.alocarPC(this);  // Só precisa de um PC
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    protected void liberarRecursos() {
        gerenciador.liberarPC(this);
    }
}
