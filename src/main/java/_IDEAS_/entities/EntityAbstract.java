package _IDEAS_.entities;

/**
 * Created by alexr on 17.02.2017.
 */
public class EntityAbstract {
    private final TypeEn type;

    public EntityAbstract(TypeEn type) {
        this.type = type;
    }

    public TypeEn type() {
        return type;
    }
}
