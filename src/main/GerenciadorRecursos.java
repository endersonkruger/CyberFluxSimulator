package main;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.concurrent.Semaphore;

public class GerenciadorRecursos {
    protected final Semaphore pcs;
    protected final Semaphore vrHeadsets;
    protected final Semaphore chairs;

    private final Semaphore semaforoEstatisticas = new Semaphore(1);

    // Estatísticas
    private int totalClientesAtendidos = 0;
    private int clientesNaoAtendidos = 0;
    private long totalTempoEspera = 0;
    private Logger logger = Logger.getInstance();

    // Contadores de IDs para tipos de clientes
    private int contadorGamer = 0;
    private int contadorFreelancer = 0;
    private int contadorEstudante = 0;

    public GerenciadorRecursos(int numPCs, int numVR, int numCadeiras) {
        this.pcs = new Semaphore(numPCs);
        this.vrHeadsets = new Semaphore(numVR);
        this.chairs = new Semaphore(numCadeiras);
    }

    // Estatísticas de clientes atendidos
    public void clienteAtendido(long tempoEspera) {
        try {
            semaforoEstatisticas.acquire();
            totalClientesAtendidos++;
            totalTempoEspera += tempoEspera;
        } catch (InterruptedException e) {
            System.err.println("Erro ao atualizar clientes atendidos.");
        } finally {
            semaforoEstatisticas.release();
        }
    }

    // Estatísticas de clientes não atendidos
    public void clienteNaoAtendido() {
        try {
            semaforoEstatisticas.acquire();
            clientesNaoAtendidos++;
        } catch (InterruptedException e) {
            System.err.println("Erro ao atualizar clientes não atendidos.");
        } finally {
            semaforoEstatisticas.release();
        }
    }

    // Métodos de criação de IDs
    public int getNovoIdGamer() {
        try {
            semaforoEstatisticas.acquire();
            return ++contadorGamer;
        } catch (InterruptedException e) {
            System.err.println("Erro ao gerar novo ID para Gamer.");
            return -1;
        } finally {
            semaforoEstatisticas.release();
        }
    }

    public int getNovoIdFreelancer() {
        try {
            semaforoEstatisticas.acquire();
            return ++contadorFreelancer;
        } catch (InterruptedException e) {
            System.err.println("Erro ao gerar novo ID para Freelancer.");
            return -1;
        } finally {
            semaforoEstatisticas.release();
        }
    }

    public int getNovoIdEstudante() {
        try {
            semaforoEstatisticas.acquire();
            return ++contadorEstudante;
        } catch (InterruptedException e) {
            System.err.println("Erro ao gerar novo ID para Estudante.");
            return -1;
        } finally {
            semaforoEstatisticas.release();
        }
    }

    // Aquisição de recursos
    public boolean adquirirRecursos(Cliente cliente, int pcsNecessarios, int vrNecessarios, int cadeirasNecessarias) {
        long tempoInicio = System.currentTimeMillis();
        
        try {
            logger.log(cliente.getClass().getSimpleName() + " " + ((Cliente)cliente).id + 
                      " tentando adquirir: " + pcsNecessarios + " PC(s), " + 
                      vrNecessarios + " VR(s), " + cadeirasNecessarias + " cadeira(s)");
            
            boolean conseguiuRecursos = pcs.tryAcquire(pcsNecessarios) &&
                    vrHeadsets.tryAcquire(vrNecessarios) &&
                    chairs.tryAcquire(cadeirasNecessarias);
    
            if (conseguiuRecursos) {
                long tempoEspera = System.currentTimeMillis() - tempoInicio;
                clienteAtendido(tempoEspera);
                logger.log(cliente.getClass().getSimpleName() + " " + ((Cliente)cliente).id + 
                          " adquiriu recursos com sucesso. Tempo de espera: " + tempoEspera + "ms");
                return true;
            } else {
                logger.log(cliente.getClass().getSimpleName() + " " + ((Cliente)cliente).id + 
                          " não conseguiu adquirir todos os recursos necessários");
                
                if (pcsNecessarios > 0) pcs.release(pcsNecessarios);
                if (vrNecessarios > 0) vrHeadsets.release(vrNecessarios);
                if (cadeirasNecessarias > 0) chairs.release(cadeirasNecessarias);
    
                clienteNaoAtendido();
                return false;
            }
        } catch (Exception e) {
            logger.log("Erro ao adquirir recursos para " + cliente.getClass().getSimpleName() + 
                      " " + ((Cliente)cliente).id + ": " + e.getMessage());
            System.err.println("Erro ao adquirir recursos: " + e.getMessage());
            return false;
        }
    }
    
    // Modificar o método liberarRecursos para incluir logs
    public void liberarRecursos(int pcsUsados, int vrUsados, int cadeirasUsadas) {
        logger.log("Liberando recursos: " + pcsUsados + " PC(s), " + 
                  vrUsados + " VR(s), " + cadeirasUsadas + " cadeira(s)");
        
        pcs.release(pcsUsados);
        vrHeadsets.release(vrUsados);
        chairs.release(cadeirasUsadas);
        
        logger.log("Recursos liberados com sucesso. Disponíveis agora: " + 
                  pcs.availablePermits() + " PC(s), " + 
                  vrHeadsets.availablePermits() + " VR(s), " + 
                  chairs.availablePermits() + " cadeira(s)");
    }

    // Exibição de estatísticas
    public void exibirEstatisticas() {
        try {
            semaforoEstatisticas.acquire();
            System.out.println("===== Estatísticas Finais =====");
            System.out.println("Total de clientes atendidos: " + totalClientesAtendidos);
            System.out.println("Clientes não atendidos: " + clientesNaoAtendidos);

            if (totalClientesAtendidos > 0) {
                System.out.println("Tempo médio de espera por recurso: " +
                        (totalTempoEspera / totalClientesAtendidos) + " ms");
            } else {
                System.out.println("Tempo médio de espera por recurso: N/A");
            }

            System.out.println("Total de IDs gerados:");
            System.out.println("- Gamer IDs gerados: " + contadorGamer);
            System.out.println("- Freelancer IDs gerados: " + contadorFreelancer);
            System.out.println("- Estudante IDs gerados: " + contadorEstudante);
            System.out.println("=================================");
        } catch (InterruptedException e) {
            System.err.println("Erro ao exibir estatísticas.");
        } finally {
            semaforoEstatisticas.release();
        }
    }

    // Escrever estatísticas em arquivo
    public void escreverEstatisticasEmArquivo(String nomeArquivo) {
        try {
            semaforoEstatisticas.acquire(); // Garante acesso exclusivo às estatísticas
            BufferedWriter writer = new BufferedWriter(new FileWriter(nomeArquivo));

            writer.write("===== Estatísticas Finais =====\n");
            writer.write("Total de clientes atendidos: " + totalClientesAtendidos + "\n");
            writer.write("Clientes não atendidos: " + clientesNaoAtendidos + "\n");

            if (totalClientesAtendidos > 0) {
                double tempoMedioEspera = (double) totalTempoEspera / totalClientesAtendidos; // Calcula o tempo médio
                writer.write(String.format("Tempo médio de espera por recurso: %.5f ms\n", tempoMedioEspera));
            } else {
                writer.write("Tempo médio de espera por recurso: N/A\n");
            }

            writer.write("Total de IDs gerados:\n");
            writer.write("- Gamer IDs gerados: " + contadorGamer + "\n");
            writer.write("- Freelancer IDs gerados: " + contadorFreelancer + "\n");
            writer.write("- Estudante IDs gerados: " + contadorEstudante + "\n");
            writer.write("=================================\n");

            writer.close(); // Fecha o arquivo
        } catch (IOException | InterruptedException e) {
            System.err.println("Erro ao escrever estatísticas em arquivo: " + e.getMessage());
        } finally {
            semaforoEstatisticas.release();
        }
    }
}