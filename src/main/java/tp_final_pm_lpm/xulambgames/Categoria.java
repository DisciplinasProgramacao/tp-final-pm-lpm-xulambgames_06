package tp_final_pm_lpm.xulambgames;

import java.util.HashMap;
import java.util.Map;

public enum Categoria {
    Lancamento(1),
    Premium(2),
    Regular(3),
    Promocao(4);

    private final int value;
    private static final Map<Integer, Categoria> map = new HashMap<>();

    Categoria(int value) {
        this.value = value;
    }

    static {
        for (Categoria categoria : Categoria.values()) {
            map.put(categoria.value, categoria);
        }
    }

    public static Categoria valueOf(int value) {
        return map.get(value);
    }
}
