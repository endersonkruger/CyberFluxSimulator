package com.cyberflux.management;

import java.util.Queue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.Semaphore;

import com.cyberflux.models.Cliente;
import com.cyberflux.resources.Cadeira;
import com.cyberflux.resources.Headset;
import com.cyberflux.resources.PC;
import com.cyberflux.utils.Logger;

// Gerencia a alocação e liberação de recursos
public class GerenciadorRecursos {
    private final PC pcs;
    private final Headset headsets;
    private final Cadeira cadeiras;
    private final Logger logger = Logger.getInstancia();
    private final Estatisticas estatisticas = new Estatisticas();
    private final ConcurrentHashMap<Cliente, Integer> falhasClientes;
    private final Semaphore mutexFila; // Controle da fila de prioridade
    private final Queue<Cliente> filaPrioridade;

    public GerenciadorRecursos() {
        this.pcs = new PC();
        this.headsets = new Headset();
        this.cadeiras = new Cadeira();
        this.falhasClientes = new ConcurrentHashMap<>();
        this.filaPrioridade = new ConcurrentLinkedQueue<>();
        this.mutexFila = new Semaphore(1); // Mutex para fila
    }

    public Estatisticas getEstatisticas() {
        return estatisticas;
    }

    private void incrementarFalha(Cliente cliente) throws InterruptedException {
        mutexFila.acquire(); // Bloqueia acesso à fila
        falhasClientes.put(cliente, falhasClientes.getOrDefault(cliente, 0) + 1);
        filaPrioridade.add(cliente); // Adiciona à fila de prioridade
        mutexFila.release(); // Libera acesso à fila
    }

    public boolean alocarPC(Cliente cliente) throws InterruptedException {
        boolean sucesso = pcs.alocar();
        if (sucesso) {
            estatisticas.incrementarPcUsados();
            falhasClientes.put(cliente, 0);
            logger.log(cliente.getNome() + " alocou um PC.");
        } else {
            incrementarFalha(cliente);
            logger.log(cliente.getNome() + " tentou alocar PC, mas falhou.");
        }
        return sucesso;
    }

    public boolean alocarHeadset(Cliente cliente) throws InterruptedException {
        boolean sucesso = headsets.alocar();
        if (sucesso) {
            estatisticas.incrementarHeadsetUsados();
            falhasClientes.put(cliente, 0);
            logger.log(cliente.getNome() + " alocou um Headset.");
        } else {
            incrementarFalha(cliente);
            logger.log(cliente.getNome() + " tentou alocar Headset, mas falhou.");
        }
        return sucesso;
    }

    public boolean alocarCadeira(Cliente cliente) throws InterruptedException {
        boolean sucesso = cadeiras.alocar();
        if (sucesso) {
            estatisticas.incrementarCadeiraUsados();
            falhasClientes.put(cliente, 0);
            logger.log(cliente.getNome() + " alocou uma Cadeira.");
        } else {
            incrementarFalha(cliente);
            logger.log(cliente.getNome() + " tentou alocar Cadeira, mas falhou.");
        }
        return sucesso;
    }

    public Cliente proximoCliente() throws InterruptedException {
        mutexFila.acquire();
        Cliente cliente = filaPrioridade.poll();
        mutexFila.release();
        return cliente;
    }

    public void liberarPC(Cliente cliente) {
        pcs.liberar();
        logger.log("PC de " + cliente.getNome() + " liberado.");
    }

    public void liberarCadeira(Cliente cliente) {
        cadeiras.liberar();
        logger.log("Cadeira de " + cliente.getNome() + " liberada.");
    }

    public void liberarHeadset(Cliente cliente) {
        headsets.liberar();
        logger.log("Headset de " + cliente.getNome() + " liberado.");
    }
}

//