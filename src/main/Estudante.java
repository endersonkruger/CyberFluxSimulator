package main;

public class Estudante extends Cliente {
    public Estudante(GerenciadorRecursos gerenciador) {
        super(gerenciador.getNovoIdEstudante(), gerenciador);
    }

    @Override
    public void usarRecursos() {
        try {
            logger.log("Estudante " + id + " tentando adquirir recursos...");
            System.out.println("Estudante " + id + " tentando adquirir recursos...");

            if (gerenciador.adquirirRecursos(this, 1, 0, 0)) {
                logger.log("Estudante " + id + " está usando PC.");
                System.out.println("Estudante " + id + " está usando PC.");
                Thread.sleep(usoMaximo);
                gerenciador.liberarRecursos(1, 0, 0);
                logger.log("Estudante " + id + " liberou PC.");
                System.out.println("Estudante " + id + " liberou PC.");
            } else {
                logger.log("Estudante " + id + " não conseguiu recursos.");
                System.out.println("Estudante " + id + " não conseguiu recursos.");
            }
        } catch (InterruptedException e) {
            logger.log("Erro no Estudante " + id + ": " + e.getMessage());
            System.err.println("Erro no Estudante " + id + ": " + e.getMessage());
        }
    }
}