package pricebasket.core;

public class Good {

    private final String name;
    private final Money price;

    public Good(String name, Money price) {
        this.name = name;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public Money getPrice() {
        return price;
    }
}
