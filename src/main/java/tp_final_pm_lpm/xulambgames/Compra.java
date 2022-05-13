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

    public Cliente getCliente() {
        return this.cliente;
    }

    public double precoVenda() {
        return 0;
    }

    public void cadastrarJogosVendidos(Jogo jogo) {

    }

    public String relatorioCompletoDeVenda() {
        var stringBuilder = new StringBuilder();

        stringBuilder.append("Cliente: ");
        stringBuilder.append(this.cliente.getNome());
        stringBuilder.append("\n");

        stringBuilder.append("Data da compra: ");
        stringBuilder.append(this.dataDaCompra);
        stringBuilder.append("\n");

        stringBuilder.append("Jogos:\n");
        for(var jogo : this.jogos) {
            stringBuilder.append("\t");
            stringBuilder.append(jogo.getNome());
            stringBuilder.append("\n");
        }

        return stringBuilder.toString();
    }

    public void clienteDaVenda(Cliente cliente) {

    }
}
