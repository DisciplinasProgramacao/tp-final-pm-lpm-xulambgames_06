package tp_final_pm_lpm.xulambgames.jogos;

import java.io.Serializable;

public class Jogo implements Serializable {
    private String nome;

    public String getNome() {
        return this.nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public double calcularPrecoVenda() {
        return 0;
    }
}
