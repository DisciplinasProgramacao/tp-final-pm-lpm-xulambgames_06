package tp_final_pm_lpm.xulambgames;

import tp_final_pm_lpm.xulambgames.cliente.Cliente;
import tp_final_pm_lpm.xulambgames.exceptions.JaCadastradoException;
import tp_final_pm_lpm.xulambgames.exceptions.NaoExisteException;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public class Compra implements Serializable {
    private final List<Jogo> jogos;
    private final Cliente cliente;
    private Date dataCompra;

    public Compra(Cliente cliente) {
        this.cliente = cliente;
        this.jogos = new ArrayList<>();
    }

    public List<Jogo> getJogos() {
        return this.jogos;
    }

    public Date getDataCompra() {
        return this.dataCompra;
    }

    public String relatorioCompleto() {
        var stringBuilder = new StringBuilder();

        stringBuilder.append("Cliente: ");
        stringBuilder.append(this.cliente.getNome());
        stringBuilder.append("\n");

        stringBuilder.append("Jogos:\n");
        this.jogos.forEach(x -> stringBuilder.append("\t- ").append(x.getNome()).append("\n"));

        stringBuilder.append("Descontos:\n");

        stringBuilder.append("\t- Cliente: ");
        stringBuilder.append(this.cliente.valorDesconto() * 100);
        stringBuilder.append("\n");

        stringBuilder.append("\t- Compra: ");
        stringBuilder.append(this.valorDesconto() * 100);
        stringBuilder.append("\n");

        stringBuilder.append("Valor pago: ");
        stringBuilder.append(this.valorCompra());

        return stringBuilder.toString();
    }

    public double valorCompra() {
        var preco = 0d;
        for(var jogo : this.jogos) {
            preco += jogo.precoDeVenda();
        }

        var descontoCompra = this.valorDesconto() * preco;
        preco -= descontoCompra;

        var descontoCliente = this.cliente.valorDesconto() * preco;
        preco -= descontoCliente;

        return preco;
    }

    private double valorDesconto() {
        int quantLancamentos = 0;
        int quantPremium = 0;
        int quantRegulares = 0;
        int quantJogos = 0;
        for(var jogo : this.jogos) {
            switch (jogo.getCategoria()) {
                case Premium -> quantPremium++;
                case Regular -> quantRegulares++;
                case Lancamento -> quantLancamentos++;
                default -> quantJogos++;
            }
        }

        int desconto = 0;
        if(quantLancamentos >= 2 || (quantPremium == 2 && quantJogos == 1) || quantPremium == 3 || (quantRegulares == 3 && quantJogos == 1) || quantRegulares == 5) {
            desconto = 20;
        } else if (quantPremium == 2 || quantRegulares == 4) {
            desconto = 10;
        }
        return desconto / 100d;
    }

    private Optional<Jogo> procurarJogo(String nome) {
        return this.jogos.stream().filter(x -> x.getNome().equals(nome)).findFirst();
    }

    private boolean jogoExiste(String nome) {
        return this.procurarJogo(nome).isPresent();
    }

    public void adicionarJogo(Jogo jogo) throws JaCadastradoException {
        if(this.jogoExiste(jogo.getNome()))
            throw new JaCadastradoException("Jogo já está na lista!");

        this.jogos.add(jogo);
    }

    public void removerJogo(String nome) throws NaoExisteException {
        var jogo = this.procurarJogo(nome);
        if(jogo.isEmpty())
            throw new NaoExisteException("Jogo não encontrado!");

        this.jogos.remove(jogo.get());
    }

    public void finalizar() {
        this.dataCompra = new Date();
        this.cliente.getCompras().add(this);

        for(var jogo : this.jogos)
            jogo.comprar();
    }
}
