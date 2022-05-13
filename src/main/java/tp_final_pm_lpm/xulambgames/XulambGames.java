package tp_final_pm_lpm.xulambgames;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class XulambGames {
    private final List<Jogo> jogosVendidosTotal;
    private final List<Compra> vendas;
    private final List<Cliente> clientes;

    public XulambGames() {
        this.jogosVendidosTotal = new ArrayList<>();
        this.vendas = new ArrayList<>();
        this.clientes = new ArrayList<>();
    }

    public double valorMensalVendido(int mes, int ano) {
        return 0;
    }

    public Jogo calcularJogoMaisVendido() {
        return new Jogo();
    }

    public Jogo calcularJogoMenosVendido() {
        return new Jogo();
    }

    public String mostrarJogosMaisEMenosVendidos() {
        return "";
    }

    public void cadastrarVenda(Compra compra) {

    }

    public void salvar() {
        try {
            var fileOutputStream = new FileOutputStream("data.bin");
            var objectOutputStream = new ObjectOutputStream(fileOutputStream);

            for(var jogo : this.jogosVendidosTotal) {
                objectOutputStream.writeObject(jogo);
            }

            for(var venda : this.vendas) {
                objectOutputStream.writeObject(venda);
            }

            for(var cliente : this.clientes) {
                objectOutputStream.writeObject(cliente);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void ler() {
        try {
            var fileInputStream = new FileInputStream("data.bin");
            var objectInputStream = new ObjectInputStream(fileInputStream);

            Object object;
            while ((object = objectInputStream.readObject()) != null) {
                if (object instanceof Cliente cliente) {
                    this.clientes.add(cliente);
                } else if (object instanceof Jogo jogo) {
                    this.jogosVendidosTotal.add(jogo);
                } else if (object instanceof Compra venda) {
                    this.vendas.add(venda);
                }
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
