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

//criar uma solução para evitar deadlock e starvation - algo como limitar o tempo das threads em cada recurso
//criar um gerenciador de threads para isso (tipo dono da lan house kkk) -> alguma classe que tenha noção e analise a todo tempo os clientes e os recursos
//impedir que só um tipo de cliente acesse os recursos
//criar um tempo de simulaçao fixo (8h que nem diz no trabalho, pode ser 30s de simulação real)
//ideias para criar as estatisticas finais:
//                      \_ toda vez que uma thread aloca recurso pra um cliente, incrementa no numero de clientes totais
//                                                                           \_ criar um atributo contador privado de clientes em recurso (?)
//                      \_ sistema de espera: não tenho mta ideia do que fazer
//                      \_ taxa de utilização: criar contador conforme o tempo for passando em cada recurso
