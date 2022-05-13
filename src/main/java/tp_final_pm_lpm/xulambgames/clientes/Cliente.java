package tp_final_pm_lpm.xulambgames.clientes;

import java.io.Serializable;

public class Cliente implements Serializable {
    protected String nome;
    protected String nomeDeUsuario;
    protected String senha;

    public Cliente() {
        this.nome = "";
        this.nomeDeUsuario = "";
        this.senha = "";
    }

    public Cliente(String nome, String usuario, String senha) {
        this.nome = nome;
        this.nomeDeUsuario = usuario;
        this.senha = senha;
    }

    public String getNome() {
        return this.nome;
    }

    public double precoMensalidade() {
        return 0;
    }

    public String historicoDeCompras() {
        return "";
    }
}
