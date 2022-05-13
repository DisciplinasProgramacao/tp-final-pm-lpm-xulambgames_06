package tp_final_pm_lpm.xulambgames;

import java.util.Scanner;

public class Main {
    private static final int EXIT_OPTION = 0;

    public static void main(String[] args) {
        var menuString = """
                0 - Sair
                1 - Cadastrar cliente
                2 - Historico cliente
                3 - Cadastrar jogo
                4 - Cadastrar compra
                """;

        var scanner = new Scanner(System.in);
        int option = -1;
        while(option != EXIT_OPTION) {
            System.out.println(menuString);
            System.out.print("Opção: ");
            option = scanner.nextInt();

            switch (option) {
                case 0 -> System.out.println("Sair");
                case 1 -> System.out.println("Cadastrar cliente");
                case 2 -> System.out.println("Historico cliente");
                case 3 -> System.out.println("Cadastrar jogo");
                case 4 -> System.out.println("Cadastrar compra");
            }
        }
    }
}
