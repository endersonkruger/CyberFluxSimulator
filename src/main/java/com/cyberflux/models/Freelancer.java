package com.cyberflux.models;

import com.cyberflux.management.GerenciadorRecursos;

// Cliente que prioriza PC + Cadeira
public class Freelancer extends Cliente {
    public Freelancer(String nome, GerenciadorRecursos gerenciador) {
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

                if (gerenciador.alocarPC(this) && gerenciador.alocarCadeira(this)) {
                    if (!gerenciador.alocarHeadset(this)) {
                        liberarRecursos();
                        return false;
                    }
                    return true;
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    protected void liberarRecursos() {
        gerenciador.liberarPC(this);
        gerenciador.liberarCadeira(this);
        gerenciador.liberarHeadset(this);
    }
}
