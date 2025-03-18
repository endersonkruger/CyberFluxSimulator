package com.cyberflux;
import java.util.concurrent.ThreadLocalRandom;
import com.cyberflux.management.*;
import com.cyberflux.models.*;

// Classe principal que inicia a simulação
public class CyberFluxSimulator {
    public static void main(String[] args) {
        GerenciadorRecursos gerenciador = new GerenciadorRecursos();
        
        for (int i = 0; i < 20; i++) {
            new Gamer("Gamer " + i, gerenciador).start();
            try {
                Thread.sleep(ThreadLocalRandom.current().nextInt(500, 1500));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
