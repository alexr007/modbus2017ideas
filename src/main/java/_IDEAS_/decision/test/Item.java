package _IDEAS_.decision.test;

import java.math.BigDecimal;

/**
 * Created by mac on 26.07.2017.
 */
public class Item {
    private final String name;
    private final int qty;
    private final BigDecimal price;

    public Item(String name, int qty, BigDecimal price) {
        this.name = name;
        this.qty = qty;
        this.price = price;
    }

    public String name() {
        return name;
    }

    public int qty() {
        return qty;
    }

    public BigDecimal price() {
        return price;
    }

    @Override
    public String toString() {
        return String.format("Name: %s, Qty: %d, Price:%s",
            name, qty, price);
    }
}

