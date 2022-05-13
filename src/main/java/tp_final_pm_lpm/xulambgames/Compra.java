package tp_final_pm_lpm.xulambgames;

import tp_final_pm_lpm.xulambgames.clientes.Cliente;
import tp_final_pm_lpm.xulambgames.jogos.Jogo;

import java.io.Serializable;

public class Compra implements Serializable {
    private String jogosVendidos;
    private String dataDeVenda;
    private Cliente cliente;

    public double precoVenda() {
        return 0;
    }

    public void cadastrarJogosVendidos(Jogo jogo) {

    }

    public String relatorioCompletoDeVenda() {
        return "";
    }

    public void clienteDaVenda(Cliente cliente) {

    }
}
