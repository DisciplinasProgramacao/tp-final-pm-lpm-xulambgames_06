package tp_final_pm_lpm.xulambgames.cliente;

import tp_final_pm_lpm.xulambgames.INotificavel;
import tp_final_pm_lpm.xulambgames.cliente.Cliente;

public class Cadastrado extends Cliente implements INotificavel {
    public Cadastrado(String nome, String nomeDeUsuario, String senha) {
        super(nome, nomeDeUsuario, senha);
    }

    @Override
    public void mandarEmail() {
        System.out.println("Email enviado!");
    }

    @Override
    public double valorDesconto() {
        return 0;
    }

    @Override
    public double precoMensalidade() {
        return 0;
    }
}
