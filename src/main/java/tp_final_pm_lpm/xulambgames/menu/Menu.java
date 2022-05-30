package tp_final_pm_lpm.xulambgames.menu;

import java.util.HashMap;
import java.util.Scanner;

public class Menu {
    private int proximoNumeroItem;
    private final Scanner scanner;
    private final HashMap<Integer, MenuItem> items;

    public Menu() {
        this.proximoNumeroItem = 0;
        this.items = new HashMap<>();
        this.scanner = new Scanner(System.in);
    }

    public void adicionaItem(MenuItem item) {
        this.items.put(proximoNumeroItem++, item);
    }

    public void iniciar() {
        var option = 0;
        do {
            this.items.forEach((x, y) -> System.out.format("%d - %s\n", x, y.getTitulo()));

            System.out.print("Opção: ");
            option = this.scanner.nextInt();
            this.scanner.nextLine();

            if(this.items.containsKey(option)) {
                var item = this.items.get(option);
                var callable = item.getCallable();
                if(callable != null)
                    callable.call(this.scanner);
            }
        } while(option != 0);
    }
}
