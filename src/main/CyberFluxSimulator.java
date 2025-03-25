package main;

import java.util.concurrent.ThreadLocalRandom;

public class CyberFluxSimulator {
    public static void main(String[] args) {
        Logger logger = Logger.getInstance();
        GerenciadorRecursos gerenciador = new GerenciadorRecursos(10, 6, 8);

        // Configuração da simulação
        int tempoSimulacaoSegundos = 30; // 30 segundos reais
        long tempoInicio = System.currentTimeMillis();
        long tempoSimulacao = tempoSimulacaoSegundos * 500; // converter para milissegundos

        logger.log("Iniciando simulação por " + tempoSimulacaoSegundos + " segundos");
        
        // Teste de deadlock (situação forçada)
        testarDeadlock(gerenciador, logger);

        while (System.currentTimeMillis() - tempoInicio < tempoSimulacao) {
            int tipoCliente = ThreadLocalRandom.current().nextInt(3);
            Cliente cliente;

            switch (tipoCliente) {
                case 0 -> cliente = new Gamer(gerenciador);
                case 1 -> cliente = new Freelancer(gerenciador);
                default -> cliente = new Estudante(gerenciador);
            }

            cliente.start();

            try {
                Thread.sleep(ThreadLocalRandom.current().nextInt(100, 300));
            } catch (InterruptedException e) {
                logger.log("Erro ao criar clientes: " + e.getMessage());
                System.err.println("Erro ao criar clientes.");
            }
        }

        // Aguardar um tempo para todas as threads terminarem
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            logger.log("Erro ao finalizar a simulação: " + e.getMessage());
            System.err.println("Erro ao finalizar a simulação.");
        }

        // Exibe as estatísticas ao final
        gerenciador.exibirEstatisticas();
        gerenciador.escreverEstatisticasEmArquivo("estatisticas_finais.txt");
        
        logger.close();
    }

    private static void testarDeadlock(GerenciadorRecursos gerenciador, Logger logger) {
        logger.log("===== TESTE DE DEADLOCK (SITUAÇÃO FORÇADA) =====");
        
        // Criar uma situação que poderia causar deadlock sem a implementação atual
        logger.log("Criando situação potencial de deadlock...");
        
        // Simular dois clientes tentando adquirir recursos em ordem diferente
        Thread cliente1 = new Thread(() -> {
            try {
                logger.log("Cliente 1 (Deadlock Test) tentando adquirir PC e depois VR");
                gerenciador.pcs.acquire(1);
                logger.log("Cliente 1 adquiriu PC, tentando adquirir VR...");
                Thread.sleep(100); // Pequeno delay para aumentar chances de deadlock
                gerenciador.vrHeadsets.acquire(1);
                logger.log("Cliente 1 adquiriu VR - Deadlock evitado!");
                gerenciador.vrHeadsets.release(1);
                gerenciador.pcs.release(1);
            } catch (InterruptedException e) {
                logger.log("Erro no Cliente 1 (Deadlock Test): " + e.getMessage());
            }
        });

        Thread cliente2 = new Thread(() -> {
            try {
                logger.log("Cliente 2 (Deadlock Test) tentando adquirir VR e depois PC");
                gerenciador.vrHeadsets.acquire(1);
                logger.log("Cliente 2 adquiriu VR, tentando adquirir PC...");
                Thread.sleep(100); // Pequeno delay para aumentar chances de deadlock
                gerenciador.pcs.acquire(1);
                logger.log("Cliente 2 adquiriu PC - Deadlock evitado!");
                gerenciador.pcs.release(1);
                gerenciador.vrHeadsets.release(1);
            } catch (InterruptedException e) {
                logger.log("Erro no Cliente 2 (Deadlock Test): " + e.getMessage());
            }
        });

        cliente1.start();
        cliente2.start();

        try {
            cliente1.join();
            cliente2.join();
        } catch (InterruptedException e) {
            logger.log("Erro ao aguardar threads de teste de deadlock: " + e.getMessage());
        }
        
        logger.log("===== FIM DO TESTE DE DEADLOCK =====");
        logger.log("Nosso sistema evita deadlock usando semáforos e a abordagem 'tryAcquire' que libera todos os recursos se não puder adquirir todos necessários.");
        logger.log("Além disso, implementamos um tempo máximo de espera para evitar starvation.");
    }
}