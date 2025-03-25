package com.cyberflux.models;

import com.cyberflux.management.GerenciadorRecursos;

// Cliente que prioriza PC + Headset
public class Gamer extends Cliente {
    public Gamer(String nome, GerenciadorRecursos gerenciador) {
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

                if (gerenciador.alocarPC(this) && gerenciador.alocarHeadset(this)) {
                    if (!gerenciador.alocarCadeira(this)) {
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
        gerenciador.liberarHeadset(this);
        gerenciador.liberarCadeira(this);
    }
}
