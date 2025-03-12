package src.main.models;
// Cliente que prioriza PC + Cadeira
class Freelancer extends Cliente {
    public Freelancer(String nome, GerenciadorRecursos gerenciador) {
        super(nome, gerenciador);
    }

    @Override
    protected boolean solicitarRecursos() {
        try {
            if (gerenciador.alocarPC() && gerenciador.alocarCadeira()) {
                gerenciador.alocarHeadset();  // Se tiver, pega, mas não é essencial
                return true;
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    protected void liberarRecursos() {
        gerenciador.liberarPC();
        gerenciador.liberarCadeira();
        gerenciador.liberarHeadset();
    }
}