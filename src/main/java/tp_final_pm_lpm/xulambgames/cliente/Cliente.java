package tp_final_pm_lpm.xulambgames.cliente;

import tp_final_pm_lpm.xulambgames.Categoria;
import tp_final_pm_lpm.xulambgames.Compra;
import tp_final_pm_lpm.xulambgames.Jogo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public abstract class Cliente  implements Serializable {
    private final String nome;
    private final String nomeDeUsuario;
    private final String senha;
    private final List<Compra> compras;

    public Cliente(String nome, String nomeDeUsuario, String senha) {
        this.nome = nome;
        this.nomeDeUsuario = nomeDeUsuario;
        this.senha = senha;
        this.compras = new ArrayList<>();
    }

    public String getNome() {
        return this.nome;
    }

    public String getNomeDeUsuario() {
        return this.nomeDeUsuario;
    }

    public List<Compra> getCompras() {
        return this.compras;
    }

    public List<Compra> historicoDeCompras(Categoria categoria) {
        var compras = new ArrayList<Compra>();
        for(var compra : this.compras) {
            var jogos = compra.getJogos();
            for(var jogo : jogos) {
                if(jogo.getCategoria() == categoria)
                    compras.add(compra);
            }
        }
        return compras;
    }

    public List<Compra> historicoDeCompras(Date data) {
        return this.compras.stream().filter(x -> x.getDataCompra() == data).toList();
    }

    public abstract double valorDesconto();

    public abstract double precoMensalidade();
}
