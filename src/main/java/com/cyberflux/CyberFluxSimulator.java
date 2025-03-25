package com.cyberflux;

import java.util.concurrent.ThreadLocalRandom;

import com.cyberflux.management.GerenciadorRecursos;
import com.cyberflux.models.Cliente;
import com.cyberflux.models.Estudante;
import com.cyberflux.models.Freelancer;
import com.cyberflux.models.Gamer;
import com.cyberflux.utils.RelogioGlobal;

// Classe principal que inicia a simulação
public class CyberFluxSimulator {
    public static void main(String[] args) {
        // Instancia o Gerenciador de Recursos
        GerenciadorRecursos gerenciador = new GerenciadorRecursos();

        RelogioGlobal clock = RelogioGlobal.getInstancia();

        // Limpa estatísticas anteriores
        gerenciador.getEstatisticas().limparEstatisticasAnteriores();

        // Inicia o relógio
        clock.start();

        // Lista de clientes que será criada
        Cliente[] clientes = new Cliente[100];

        int clienteIndex = 0;
        while (clock.aindaRodando() && clienteIndex < clientes.length) {
            // Criar cliente de acordo com o tipo
            if (clienteIndex % 3 == 0) {
                clientes[clienteIndex] = new Gamer("Gamer " + clienteIndex, gerenciador);
            } else if (clienteIndex % 3 == 1) {
                clientes[clienteIndex] = new Freelancer("Freelancer " + clienteIndex, gerenciador);
            } else {
                clientes[clienteIndex] = new Estudante("Estudante " + clienteIndex, gerenciador);
            }

            // Iniciar a thread do cliente
            clientes[clienteIndex].start();

            // Incrementar o índice
            clienteIndex++;

            try {
                // Aguarda um tempo aleatório entre a criação de cada cliente
                Thread.sleep(ThreadLocalRandom.current().nextInt(500, 1500));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        // Aguarde o relógio terminar antes de gerar o relatório
        try {
            clock.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Gera o relatório final
        gerenciador.getEstatisticas().gerarRelatorio();
    }
}

// impedir que só um tipo de cliente acesse os recursos
// criar um tempo de simulaçao fixo (8h que nem diz no trabalho, pode ser 30s de
// simulação real)
// ideias para criar as estatisticas finais:
// \_ toda vez que uma thread aloca recurso pra um cliente, incrementa no numero
// de clientes totais
// \_ criar um atributo contador privado de clientes em recurso (?)
// \_ sistema de espera: não tenho mta ideia do que fazer
// \_ taxa de utilização: criar contador conforme o tempo for passando em cada
// recurso

// NOVAS MODIFICAÇÕES!!

// O que mudou?

// Vou explicar mais ou menos como foi a implementação do algoritmo que
// previne deadlock e starvation.

// Rollback com backoff exponencial → Se não conseguir todos os recursos,
// libera os adquiridos e espera um tempo aleatório antes de tentar novamente.

// Prioridade adaptativa → Se um cliente falhar muitas vezes, ele recebe
// prioridade maior.

// Na classe cliente
// > implementei o método getNome

// Nas classes filhas de cliente
// > agora temos uma fila de prioridades que é
// gerenciada pelo gerenciador de recursos.
// > também tornei necessário a aquisição de todos os
// recursos, pois pensei no caso do gamer por exemplo:
// hm, ele tem headset, pc mas não tem cadeira? dai
// nao teria como seguir adiante. Dai apliquei essa logica
// aos demais.
// > Então de modo geral, se o cliente consegue os prioritários
// mas não consegue os demais, ele libera os recursos que possui
// e aguarda mais um tempo para tentar novamente. (essa é a teoria)

// No gerenciador de recursos
// > temos um hashmap com <cliente><prioridade>, baseado no número de falhas:
// quanto mais vezes falhou, maior será sua prioridade para alocar recursos
// futuramente.
// > também temos um mutex para impedir que outras threads alterem o valor
// da fila de prioridades fora de ordem
// > e uma fila de clientes em si (baseado na logica de prioridade), quem
// estiver
// por ultimo, será o mais prioritário
// > ainda no gerenciador, remove o ultimo elemento da fila de prioridade com o
// método proximoCliente() e depois adiciona novamente, se for necessario, com o
// método incrementarFalha()

// QUANDO FOR ADICIONADO UMA LÓGICA DE TEMPO MÁXIMO NO CÓDIGO, TUDO ISSO PRECISA
// SER VERIFICADO NOVAMENTE! PORQUE, POR EXEMPLO, QUANDO OS CLIENTES NÃO
// CONSEGUIREM
// ALOCAR ALGUM RECURSO, AS MENSAGENS DISTO PRECISAM SER ENVIADAS NO TERMINAL E
// ISSO
// PRECISA IR PROS CÁLCULOS DAS ESTATISTICAS FINAIS TAMBÉM