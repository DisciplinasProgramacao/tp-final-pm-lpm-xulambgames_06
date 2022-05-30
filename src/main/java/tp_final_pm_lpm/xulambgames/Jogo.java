package tp_final_pm_lpm.xulambgames;

import java.io.Serializable;
import java.util.Random;

public class Jogo implements Serializable {
    private final String nome;
    private final Categoria categoria;
    private int vendas;
    private final double precoOriginal;
    private final Random random;

    public Jogo(String nome, Categoria categoria, double preco) {
        this.nome = nome;
        this.categoria = categoria;
        this.vendas = 0;
        this.precoOriginal = preco;
        this.random = new Random();
    }

    public String getNome() {
        return this.nome;
    }

    public int getVendas() {
        return this.vendas;
    }

    public Categoria getCategoria() {
        return this.categoria;
    }

    private double calculaPrecoFinalEntre(int porcentagemMinima, int porcentagemMaxima, double preco) {
        var adicional = this.random.nextInt() * (porcentagemMaxima - porcentagemMinima - 1) + porcentagemMinima;
        return (adicional / 100d) * preco;
    }

    public double precoDeVenda() {
        double preco = this.precoOriginal;
        double desconto = 0;
        switch(this.categoria) {
            case Premium -> desconto = 0;
            case Regular -> desconto = calculaPrecoFinalEntre(70, 100, preco);
            case Promocao -> desconto = calculaPrecoFinalEntre(30, 50, preco);
            case Lancamento -> desconto = 0.1 * preco;
        }
        return preco - desconto;
    }

    public void comprar() {
        this.vendas++;
    }

    public String relatorio() {
        return String.format("Nome: %s\nCategoria: %s\nValor: %f", this.nome, this.categoria, this.precoDeVenda());
    }
}
