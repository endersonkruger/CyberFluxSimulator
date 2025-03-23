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
            Cliente prioridade = gerenciador.proximoCliente();
            if (prioridade != null && prioridade != this) {
                Thread.sleep((long) (Math.random() * 500)); // Simula re-tentativa
                return false;
            }
    
            if (gerenciador.alocarPC(this) && gerenciador.alocarHeadset(this)) {
                if (!gerenciador.alocarCadeira(this)) {
                    liberarRecursos();
                    return false;
                }
                return true;
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
