package com.cyberflux.models;
import com.cyberflux.management.GerenciadorRecursos;

// Cliente que precisa apenas de um PC
class Estudante extends Cliente {
    public Estudante(String nome, GerenciadorRecursos gerenciador) {
        super(nome, gerenciador);
    }

    @Override
    protected boolean solicitarRecursos() {
        try {
            return gerenciador.alocarPC();  // Só precisa de um PC
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    protected void liberarRecursos() {
        gerenciador.liberarPC();
    }
}