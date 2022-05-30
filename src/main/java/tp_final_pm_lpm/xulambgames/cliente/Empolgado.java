package tp_final_pm_lpm.xulambgames.cliente;

public class Empolgado extends Cliente {
    private static final double PRECO_MENSALIDADE = 10;
    private static final int VALOR_DESCONTO = 10;

    public Empolgado(String nome, String nomeDeUsuario, String senha) {
        super(nome, nomeDeUsuario, senha);
    }

    @Override
    public double valorDesconto() {
        return VALOR_DESCONTO / 100d;
    }

    @Override
    public double precoMensalidade() {
        return PRECO_MENSALIDADE;
    }
}
