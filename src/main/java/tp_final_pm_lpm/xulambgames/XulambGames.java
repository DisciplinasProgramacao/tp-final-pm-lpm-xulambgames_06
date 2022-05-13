package tp_final_pm_lpm.xulambgames;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class XulambGames {
    private final List<Jogo> jogosVendidosTotal;
    private final List<Compra> vendas;
    private final List<Cliente> clientes;
    private final Scanner scanner;


    public XulambGames() {
        this.jogosVendidosTotal = new ArrayList<>();
        this.vendas = new ArrayList<>();
        this.clientes = new ArrayList<>();
        this.scanner = new Scanner(System.in);
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

    public void cadastrarCliente() {
        System.out.println("Cadastrar novo cliente");

        System.out.print("Nome: ");
        var nome = this.scanner.next();

        System.out.print("Nome de usuario: ");
        var usuario = this.scanner.next();

        System.out.print("Senha: ");
        var senha = this.scanner.next();

        var cliente = new Cliente(nome, usuario, senha);
        this.clientes.add(cliente);

        System.out.println("Cadastro concluido com sucesso");
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

    public void inicializar() {
        ler();

        var menuString = """
                0 - Sair
                1 - Cadastrar cliente
                2 - Historico cliente
                3 - Cadastrar jogo
                4 - Cadastrar compra
                """;
        int option = -1;
        while(option != 0) {
            System.out.println(menuString);
            System.out.print("Opção: ");
            option = scanner.nextInt();

            switch (option) {
                case 0 -> salvar();
                case 1 -> cadastrarCliente();
                case 2 -> System.out.println("Historico cliente");
                case 3 -> System.out.println("Cadastrar jogo");
                case 4 -> System.out.println("Cadastrar compra");
            }
        }
    }
}
