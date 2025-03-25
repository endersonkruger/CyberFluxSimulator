package main;

public abstract class Cliente extends Thread {
    protected GerenciadorRecursos gerenciador;
    protected int id;
    protected long startTime;
    protected long usoMaximo;
    protected Logger logger;

    public Cliente(int id, GerenciadorRecursos gerenciador) {
        this.id = id;
        this.gerenciador = gerenciador;
        this.startTime = System.currentTimeMillis();
        this.usoMaximo = 3500;
        this.logger = Logger.getInstance();
    }

    public abstract void usarRecursos();

    @Override
    public void run() {
        usarRecursos();
    }
}