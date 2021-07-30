package pricebasket.core.specialoffers;

import pricebasket.core.Money;

public class Discount {

    private final String name;
    private final Money amount;

    public Discount(String name, Money amount) {
        this.name = name;
        this.amount = amount;
    }

    public String getName() {
        return name;
    }

    public Money getAmount() {
        return amount;
    }
}
