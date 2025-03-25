package com.cyberflux.management;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.atomic.AtomicInteger;

// Coleta dados sobre uso dos recursos
public class Estatisticas {
    private final AtomicInteger clientesAtendidos = new AtomicInteger(0);
    private final AtomicInteger clientesRejeitados = new AtomicInteger(0);
    private final AtomicInteger totalPcUsados = new AtomicInteger(0);
    private final AtomicInteger totalHeadsetUsados = new AtomicInteger(0);
    private final AtomicInteger totalCadeiraUsados = new AtomicInteger(0);
    private long tempoTotalEspera = 0;
    private static final String STATS_FILE = "statistics.txt";

    public void incrementarClientesAtendidos() {
        clientesAtendidos.incrementAndGet();
    }

    public void incrementarClientesRejeitados() {
        clientesRejeitados.incrementAndGet();
    }

    public void incrementarPcUsados() {
        totalPcUsados.incrementAndGet();
    }

    public void incrementarHeadsetUsados() {
        totalHeadsetUsados.incrementAndGet();
    }

    public void incrementarCadeiraUsados() {
        totalCadeiraUsados.incrementAndGet();
    }

    public void adicionarTempoEspera(long tempo) {
        tempoTotalEspera += tempo;
    }

    public void gerarRelatorio() {
        String timestamp = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format(new Date());
        double tempoMedioEspera = clientesAtendidos.get() > 0 ? (double) tempoTotalEspera / clientesAtendidos.get() : 0;

        // Cálculo das novas métricas
        int totalRecursosDisponiveis = 10 + 8 + 6; // PCs + Cadeiras + Headsets
        int totalRecursosUtilizados = totalPcUsados.get() + totalCadeiraUsados.get() + totalHeadsetUsados.get();
        double taxaUtilizacao = (double) totalRecursosUtilizados / (totalRecursosDisponiveis * 8 * 60) * 100;

        // Formatar mensagem com destaques e separadores
        String relatorio = String.format(
                "=========================================\n" +
                        "       ESTATÍSTICAS FINAIS - %s          \n" +
                        "=========================================\n" +
                        "Principais Métricas\n" +
                        "-> Total de clientes atendidos: %d\n" +
                        "-> Total de clientes rejeitados: %d\n" +
                        "-> Tempo médio de espera: %.2f ms\n\n" +
                        "Taxas de Utilização dos Recursos\n" +
                        "-> PCs: %d usos\n" +
                        "-> Headsets: %d usos\n" +
                        "-> Cadeiras: %d usos\n" +
                        "-> Taxa geral de utilização: %.2f%%\n\n" +
                        "Recursos Disponíveis no Dia\n" +
                        "-> Total de recursos utilizados: %d\n" +
                        "-> Total de recursos disponíveis: %d\n" +
                        "=========================================\n",
                timestamp,
                clientesAtendidos.get(),
                clientesRejeitados.get(),
                tempoMedioEspera,
                totalPcUsados.get(),
                totalHeadsetUsados.get(),
                totalCadeiraUsados.get(),
                taxaUtilizacao,
                totalRecursosUtilizados,
                totalRecursosDisponiveis);

        // Escrever no console
        System.out.println(relatorio);

        // Escrever no arquivo
        try (PrintWriter out = new PrintWriter(new FileWriter(STATS_FILE, true))) {
            out.println(relatorio);
        } catch (IOException e) {
            System.err.println("[Estatísticas]/tErro ao escrever no arquivo: " + e.getMessage());
        }
    }

    // Método para limpar o arquivo de estatísticas no início da simulação
    public void limparEstatisticasAnteriores() {
        try (PrintWriter out = new PrintWriter(new FileWriter(STATS_FILE))) {
            out.print(""); // Limpa o arquivo
        } catch (IOException e) {
            System.err.println("[Estatísticas] Erro ao limpar o arquivo: " + e.getMessage());
        }
    }
}
