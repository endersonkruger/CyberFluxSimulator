package main;

public class Gamer extends Cliente {
    public Gamer(GerenciadorRecursos gerenciador) {
        super(gerenciador.getNovoIdGamer(), gerenciador);
    }

    @Override
    public void usarRecursos() {
        try {
            logger.log("Gamer " + id + " tentando adquirir recursos...");
            System.out.println("Gamer " + id + " tentando adquirir recursos...");

            if (gerenciador.adquirirRecursos(this, 1, 1, 0)) {
                logger.log("Gamer " + id + " está usando PC e Headset VR.");
                System.out.println("Gamer " + id + " está usando PC e Headset VR.");
                Thread.sleep(usoMaximo);
                gerenciador.liberarRecursos(1, 1, 0);
                logger.log("Gamer " + id + " liberou PC e Headset VR.");
                System.out.println("Gamer " + id + " liberou PC e Headset VR.");
            } else {
                logger.log("Gamer " + id + " não conseguiu recursos.");
                System.out.println("Gamer " + id + " não conseguiu recursos.");
            }
        } catch (InterruptedException e) {
            logger.log("Erro no Gamer " + id + ": " + e.getMessage());
            System.err.println("Erro no Gamer " + id + ": " + e.getMessage());
        }
    }
}