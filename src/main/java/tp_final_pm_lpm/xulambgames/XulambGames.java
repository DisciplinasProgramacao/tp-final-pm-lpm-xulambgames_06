package tp_final_pm_lpm.xulambgames;

import tp_final_pm_lpm.xulambgames.cliente.Cadastrado;
import tp_final_pm_lpm.xulambgames.cliente.Cliente;
import tp_final_pm_lpm.xulambgames.cliente.Empolgado;
import tp_final_pm_lpm.xulambgames.cliente.Fanatico;
import tp_final_pm_lpm.xulambgames.exceptions.JaCadastradoException;
import tp_final_pm_lpm.xulambgames.exceptions.NaoExisteException;
import tp_final_pm_lpm.xulambgames.menu.Menu;
import tp_final_pm_lpm.xulambgames.menu.MenuItem;

import java.io.*;
import java.util.*;

public class XulambGames {
    private final List<Cliente> clientes;
    private final List<Jogo> jogos;
    private final List<Compra> compras;

    public XulambGames() {
        this.clientes = new ArrayList<>();
        this.jogos = new ArrayList<>();
        this.compras = new ArrayList<>();
    }

    private double valorMensalVendido(int mes) {
        if(mes <= 0)
            mes = 0;
        else if(mes >= 12)
            mes = 11;
        else
            mes -= 1;

        int finalMes = mes;
        var compras = this.compras.stream().filter(x -> x.getDataCompra().getMonth() == finalMes);
        return compras.mapToDouble(Compra::valorCompra).sum();
    }

    private double valorMedioCompras() {
        return this.compras.stream().mapToDouble(Compra::valorCompra).sum() / this.compras.size();
    }

    private Optional<Jogo> jogoMaisVendido() {
        return this.jogos.stream().max(Comparator.comparing(Jogo::getVendas));
    }

    private Optional<Jogo> jogoMenosVendido() {
        return this.jogos.stream().min(Comparator.comparing(Jogo::getVendas));
    }

    private boolean clienteJaCadastrado(Cliente cliente) {
        return this.clientes.stream().anyMatch(x -> x.getNomeDeUsuario().equals(cliente.getNomeDeUsuario()));
    }

    private Optional<Cliente> procurarCliente(String nome) {
        return this.clientes.stream().filter(x -> x.getNome().equals(nome)).findFirst();
    }

    private boolean jogoJaCadastrado(Jogo jogo) {
        return this.jogos.stream().anyMatch(x -> x.getNome().equals(jogo.getNome()));
    }

    private Optional<Jogo> procurarJogo(String nome) {
        return this.jogos.stream().filter(x -> x.getNome().equals(nome)).findFirst();
    }

    public void lerDados() {
        try {
            var fileInputStream = new FileInputStream("data.bin");
            var objectInputStream = new ObjectInputStream(fileInputStream);

            Object object;
            while ((object = objectInputStream.readObject()) != null) {
                if (object instanceof Cliente cliente) {
                    this.clientes.add(cliente);
                } else if (object instanceof Jogo jogo) {
                    this.jogos.add(jogo);
                } else if (object instanceof Compra compra) {
                    this.compras.add(compra);
                }
            }

            objectInputStream.close();
            fileInputStream.close();
        } catch (FileNotFoundException e) {
            System.out.println("Apresentamos o XulambGames!");
        } catch (EOFException e) {
            System.out.println("Todos os dados do sistema foram carregados!");
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void controlaSalvarDados(Scanner scanner) {
        try {
            var fileOutputStream = new FileOutputStream("data.bin");
            var objectOutputStream = new ObjectOutputStream(fileOutputStream);

            for(var jogo : this.jogos) {
                objectOutputStream.writeObject(jogo);
            }

            for(var compra : this.compras) {
                objectOutputStream.writeObject(compra);
            }

            for(var cliente : this.clientes) {
                objectOutputStream.writeObject(cliente);
            }

            objectOutputStream.close();
            fileOutputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("Todos os dados do sistema foram salvos!");
    }

    public void controlaCadastroClientes(Scanner scanner) {
        System.out.print("Nome: ");
        var nome = scanner.nextLine();

        System.out.print("Nome de usuario: ");
        var nomeDeUsuario = scanner.nextLine();

        System.out.print("Senha: ");
        var senha = scanner.nextLine();

        System.out.println("Tipos de cliente:");
        System.out.println("1 - Cadastrado");
        System.out.println("2 - Empolgado");
        System.out.println("3 - Fanatico");
        System.out.print("Opção: ");
        var tipoCliente = scanner.nextInt();

        Cliente cliente = null;
        switch(tipoCliente) {
            case 1 -> cliente = new Empolgado(nome, nomeDeUsuario, senha);
            case 2 -> cliente = new Cadastrado(nome, nomeDeUsuario, senha);
            case 3 -> cliente = new Fanatico(nome, nomeDeUsuario, senha);
        }

        if(clienteJaCadastrado(cliente)) {
            System.out.println("Cliente já cadastrado!");
            return;
        }

        this.clientes.add(cliente);
        System.out.println("Cliente cadastrado com sucesso!");
    }

    public void controlaCadastroJogos(Scanner scanner) {
        System.out.print("Nome: ");
        var nome = scanner.nextLine();

        System.out.println("Categorias:");
        System.out.println("1 - Lançamento");
        System.out.println("2 - Premium");
        System.out.println("3 - Regular");
        System.out.println("4 - Promoção");
        System.out.print("Categoria: ");
        var categoria = scanner.nextInt();

        System.out.print("Preço: ");
        var preco = scanner.nextDouble();

        var jogo = new Jogo(nome, Categoria.valueOf(categoria), preco);
        if(this.jogoJaCadastrado(jogo)) {
            System.out.println("Jogo já cadastrado!");
            return;
        }

        this.jogos.add(jogo);
        System.out.println("Jogo cadastrado com sucesso!");
    }

    public void controlaCadastroCompras(Scanner scanner) {
        System.out.print("Cliente: ");
        var nomeCliente = scanner.nextLine();
        var cliente = this.procurarCliente(nomeCliente);
        if(cliente.isEmpty()) {
            System.out.print("Cliente não encontrado!");
            return;
        }

        var compra = new Compra(cliente.get());
        var menu = new Menu();
        menu.adicionaItem(new MenuItem("Finalizar", null));

        menu.adicionaItem(new MenuItem("Adicionar jogo", scanner1 -> {
            System.out.print("Nome: ");
            var nome = scanner.nextLine();
            var jogo = this.procurarJogo(nome);
            if (jogo.isEmpty()) {
                System.out.print("Jogo não encontrado!");
                return;
            }

            try {
                compra.adicionarJogo(jogo.get());
            } catch (JaCadastradoException e) {
                System.out.println(e.getMessage());
            }
        }));

        menu.adicionaItem(new MenuItem("Remover Jogo", scanner1 -> {
            System.out.print("Nome: ");
            var nome = scanner.nextLine();
            try {
                compra.removerJogo(nome);
            } catch (NaoExisteException e) {
                System.out.println(e.getMessage());
            }
        }));

        menu.adicionaItem(new MenuItem("Listar Jogos", scanner1 -> {
            for (var jogo : compra.getJogos()) {
                System.out.println(jogo.relatorio());
            }
        }));

        menu.iniciar();

        compra.finalizar();
        this.compras.add(compra);

        System.out.println(compra.relatorioCompleto());
        System.out.println("Compra cadastrada com sucesso!");
    }

    public void controlaJogoMaisVendido(Scanner scanner) {
        var jogo = this.jogoMaisVendido();
        if(jogo.isPresent()) {
            System.out.println("Jogo mais vendido:");
            System.out.println(jogo.get().relatorio());
        } else {
            System.out.println("Ainda não foi vendido nenhum jogo!");
        }
    }

    public void controlaJogoMenosVendido(Scanner scanner) {
        var jogo = this.jogoMenosVendido();
        if(jogo.isPresent()) {
            System.out.println("Jogo menos vendido:");
            System.out.println(jogo.get().relatorio());
        } else {
            System.out.println("Ainda não foi vendido nenhum jogo!");
        }
    }

    public void controlaValorMensalVendido(Scanner scanner) {
        System.out.print("Mes: ");
        var mes = scanner.nextInt();
        System.out.printf("Valor mensal vendido: %f\n", this.valorMensalVendido(mes));
    }

    public void controlaValorMedioCompras(Scanner scanner) {
        System.out.printf("Valor medio de compras: %f\n", this.valorMedioCompras());
    }
    public void controlaHistoricoDeCompras(Scanner scanner) {
        System.out.print("Nome: ");
        var nome = scanner.nextLine();
        var cliente = this.procurarCliente(nome);
        if(cliente.isEmpty()) {
            System.out.println("Cliente nao foi encontrado!");
            return;
        }

        var menu = new Menu();
        menu.adicionaItem(new MenuItem("Sair", null));

        menu.adicionaItem(new MenuItem("Historico de compras (Categoria)", scanner1 -> {
            System.out.println("Categorias:");
            System.out.println("1 - Lançamento");
            System.out.println("2 - Premium");
            System.out.println("3 - Regular");
            System.out.println("4 - Promoção");
            System.out.print("Categoria: ");
            var categoria = scanner.nextInt();
            var compras = cliente.get().historicoDeCompras(Categoria.valueOf(categoria));

            for(var compra : compras) {
                System.out.println(compra.relatorioCompleto());
            }
        }));

        menu.adicionaItem(new MenuItem("Historico de compras (Data)", scanner1 -> {
            System.out.print("Dia: ");
            var dia = scanner.nextInt();
            scanner.nextLine();

            System.out.print("Mes: ");
            var mes = scanner.nextInt();
            scanner.nextLine();

            if(mes <= 0)
                mes = 0;
            else if(mes >= 12)
                mes = 11;
            else
                mes -= 1;

            System.out.print("Ano: ");
            var ano = scanner.nextInt();

            var compras = cliente.get().historicoDeCompras(new Date(ano, mes, dia));
            for(var compra : compras) {
                System.out.println(compra.relatorioCompleto());
            }
        }));

        menu.iniciar();

        cliente.get().getCompras().forEach(x -> System.out.println(x.relatorioCompleto()));
    }

    public static void main(String[] args) {
        var xulambGames = new XulambGames();
        xulambGames.lerDados();

        var menu = new Menu();
        menu.adicionaItem(new MenuItem("Sair", xulambGames::controlaSalvarDados));
        menu.adicionaItem(new MenuItem("Cadastrar cliente", xulambGames::controlaCadastroClientes));
        menu.adicionaItem(new MenuItem("Cadastrar jogo", xulambGames::controlaCadastroJogos));
        menu.adicionaItem(new MenuItem("Cadastrar compra", xulambGames::controlaCadastroCompras));
        menu.adicionaItem(new MenuItem("Jogo mais vendido", xulambGames::controlaJogoMaisVendido));
        menu.adicionaItem(new MenuItem("Jogo menos vendido", xulambGames::controlaJogoMenosVendido));
        menu.adicionaItem(new MenuItem("Valor mensal vendido", xulambGames::controlaValorMensalVendido));
        menu.adicionaItem(new MenuItem("Valor medio compras", xulambGames::controlaValorMedioCompras));
        menu.adicionaItem(new MenuItem("Historico de compras", xulambGames::controlaHistoricoDeCompras));
        menu.iniciar();
    }
}
