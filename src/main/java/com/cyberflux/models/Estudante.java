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
            while (tempoEmEspera <= tempoMaximoDeEspera) {
                Cliente prioridade = gerenciador.proximoCliente();
                if (prioridade != null && prioridade != this) {
                    long newTime = (long) Math.random() * 500;
                    Thread.sleep(newTime); // Simula re-tentativa
                    tempoEmEspera += newTime;
                    return false;
                }

                return gerenciador.alocarPC(this); // Só precisa de um PC
            }

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
