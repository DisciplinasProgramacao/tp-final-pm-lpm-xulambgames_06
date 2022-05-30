package tp_final_pm_lpm.xulambgames.menu;

import java.util.Scanner;

public class MenuItem {
    public interface Callable {
        void call(Scanner scanner);
    }

    private String titulo;
    private Callable callable;

    public MenuItem(String titulo, Callable callable) {
        this.titulo = titulo;
        this.callable = callable;
    }

    public Callable getCallable() {
        return this.callable;
    }

    public String getTitulo() {
        return this.titulo;
    }
}