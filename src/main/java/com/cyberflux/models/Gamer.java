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
            if (gerenciador.alocarPC() && gerenciador.alocarHeadset()) {
                gerenciador.alocarCadeira();
                return true;
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    protected void liberarRecursos() {
        gerenciador.liberarPC();
        gerenciador.liberarHeadset();
        gerenciador.liberarCadeira();
    }
}
