package com.cyberflux.management;

import com.cyberflux.resources.PC;
import com.cyberflux.resources.Headset;
import com.cyberflux.resources.Cadeira;
import com.cyberflux.utils.Logger;

// Gerencia a alocação e liberação de recursos
public class GerenciadorRecursos {
    private final PC pcs;
    private final Headset headsets;
    private final Cadeira cadeiras;
    private final Logger logger = Logger.getInstancia();

    public GerenciadorRecursos() {
        this.pcs = new PC();
        this.headsets = new Headset();
        this.cadeiras = new Cadeira();
    }

    public boolean alocarPC() throws InterruptedException {
        boolean sucesso = pcs.alocar();
        if (sucesso) {
            logger.log("PC alocado com sucesso.");
        } else {
            logger.log("Tentativa de alocar PC falhou (sem recursos disponíveis).");
        }
        return sucesso;
    }

    public void liberarPC() {
        pcs.liberar();
        logger.log("PC liberado.");
    }

    public boolean alocarHeadset() throws InterruptedException {
        boolean sucesso = headsets.alocar();
        if (sucesso) {
            logger.log("Headset alocado com sucesso.");
        } else {
            logger.log("Tentativa de alocar Headset falhou (sem recursos disponíveis).");
        }
        return sucesso;
    }

    public void liberarHeadset() {
        headsets.liberar();
        logger.log("Headset liberado.");
    }

    public boolean alocarCadeira() throws InterruptedException {
        boolean sucesso = cadeiras.alocar();
        if (sucesso) {
            logger.log("Cadeira alocada com sucesso.");
        } else {
            logger.log("Tentativa de alocar Cadeira falhou (sem recursos disponíveis).");
        }
        return sucesso;
    }

    public void liberarCadeira() {
        cadeiras.liberar();
        logger.log("Cadeira liberada.");
    }
}
