package tp_final_pm_lpm.xulambgames;

import tp_final_pm_lpm.xulambgames.clientes.Cliente;
import tp_final_pm_lpm.xulambgames.jogos.Jogo;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class Compra implements Serializable {
    private final Date dataDaCompra;
    private final Cliente cliente;
    private final List<Jogo> jogos;

    public Compra(Cliente cliente, List<Jogo> jogos) {
        this.cliente = cliente;
        this.jogos = jogos;
        this.dataDaCompra = new Date();
    }

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
