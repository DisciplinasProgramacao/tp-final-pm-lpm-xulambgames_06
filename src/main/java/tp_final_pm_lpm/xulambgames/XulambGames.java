package tp_final_pm_lpm.xulambgames;

import tp_final_pm_lpm.xulambgames.clientes.Cliente;
import tp_final_pm_lpm.xulambgames.jogos.*;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class XulambGames {
    private final List<Jogo> jogos;
    private final List<Compra> vendas;
    private final List<Cliente> clientes;
    private final Scanner scanner;

    public XulambGames() {
        this.jogos = new ArrayList<>();
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

    private void cadastrarCliente() {
        System.out.println("Cadastrar novo cliente");

        System.out.print("Nome: ");
        var nome = this.scanner.nextLine();

        System.out.print("Nome de usuario: ");
        var usuario = this.scanner.nextLine();

        System.out.print("Senha: ");
        var senha = this.scanner.nextLine();

        var cliente = new Cliente(nome, usuario, senha);
        this.clientes.add(cliente);

        System.out.println("Cliente cadastrado com sucesso");
    }

    private void cadastrarJogo() {
        System.out.println("Cadastrar novo jogo");

        var menuString = """
                0 - Lançamento
                1 - Premium
                2 - Regular
                3 - Promoção
                """;

        Jogo jogo = null;
        while(jogo == null) {
            System.out.println("Categorias: ");
            System.out.println(menuString);
            System.out.print("Categoria: ");
            int option = this.scanner.nextInt();

            switch (option) {
                case 0 -> jogo = new Lancamento();
                case 1 -> jogo = new Premium();
                case 2 -> jogo = new Regular();
                case 3 -> jogo = new Promocao();
            }
        }

        this.scanner.nextLine();
        System.out.print("Nome: ");
        var nome = this.scanner.nextLine();
        jogo.setNome(nome);

        this.jogos.add(jogo);

        System.out.println("Jogo cadastrado com sucesso");
    }

    private void cadastrarCompra() {
        System.out.println("Cadastrar nova compra");

        Cliente cliente = null;
        while(cliente == null) {
            System.out.print("Nome do cliente: ");
            var nome = this.scanner.nextLine();

            cliente = this.clientes.stream().filter(x -> x.getNome().equals(nome)).findAny().orElse(null);
            if(cliente == null) {
                System.out.println("Cliente não encontrado!");
            }
        }

        var jogos = new ArrayList<Jogo>();
        var menuString = """
                0 - Sair
                1 - Adicionar jogo
                """;
        int option = -1;
        while(option != 0) {
            System.out.print(menuString);
            System.out.print("Opção: ");
            option = this.scanner.nextInt();
            this.scanner.nextLine();

            if(option == 1) {
                Jogo jogo = null;
                while(jogo == null) {
                    System.out.print("Nome do jogo: ");
                    var nome = this.scanner.nextLine();

                    jogo = this.jogos.stream().filter(x -> x.getNome().equals(nome)).findAny().orElse(null);
                    if(jogo == null) {
                        System.out.println("Jogo não encontrado!");
                    }
                }

                if(!jogos.contains(jogo)) {
                    jogos.add(jogo);
                } else {
                    System.out.println("Jogo ja adicionado");
                }
            }
        }

        var compra = new Compra(cliente, jogos);
        this.vendas.add(compra);

        System.out.println("Compra cadastrada com sucesso");
    }

    public void salvar() {
        try {
            var fileOutputStream = new FileOutputStream("data.bin");
            var objectOutputStream = new ObjectOutputStream(fileOutputStream);

            for(var jogo : this.jogos) {
                objectOutputStream.writeObject(jogo);
            }

            for(var venda : this.vendas) {
                objectOutputStream.writeObject(venda);
            }

            for(var cliente : this.clientes) {
                objectOutputStream.writeObject(cliente);
            }

            objectOutputStream.close();
            fileOutputStream.close();
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
                    this.jogos.add(jogo);
                } else if (object instanceof Compra venda) {
                    this.vendas.add(venda);
                }
            }

            objectInputStream.close();
            fileInputStream.close();
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

            this.scanner.nextLine();
            switch (option) {
                case 0 -> salvar();
                case 1 -> cadastrarCliente();
                case 2 -> System.out.println("Historico cliente");
                case 3 -> cadastrarJogo();
                case 4 -> cadastrarCompra();
            }
        }
    }
}
