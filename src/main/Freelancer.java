package main;

public class Freelancer extends Cliente {
    public Freelancer(GerenciadorRecursos gerenciador) {
        super(gerenciador.getNovoIdFreelancer(), gerenciador);
    }

    @Override
    public void usarRecursos() {
        try {
            logger.log("Freelancer " + id + " tentando adquirir recursos...");
            System.out.println("Freelancer " + id + " tentando adquirir recursos...");

            if (gerenciador.adquirirRecursos(this, 1, 0, 1)) {
                logger.log("Freelancer " + id + " está usando PC e Cadeira.");
                System.out.println("Freelancer " + id + " está usando PC e Cadeira.");
                Thread.sleep(usoMaximo);
                gerenciador.liberarRecursos(1, 0, 1);
                logger.log("Freelancer " + id + " liberou PC e Cadeira.");
                System.out.println("Freelancer " + id + " liberou PC e Cadeira.");
            } else {
                logger.log("Freelancer " + id + " não conseguiu recursos.");
                System.out.println("Freelancer " + id + " não conseguiu recursos.");
            }
        } catch (InterruptedException e) {
            logger.log("Erro no Freelancer " + id + ": " + e.getMessage());
            System.err.println("Erro no Freelancer " + id + ": " + e.getMessage());
        }
    }
}