package pricebasket.core;

import pricebasket.core.specialoffers.Discount;

import java.util.List;

public class Receipt {

    private final Money subTotal;
    private final List<Discount> discounts;

    public Receipt(Money subTotal, List<Discount> discounts) {
        this.subTotal = subTotal;
        this.discounts = discounts;
    }

    public Money getSubTotal() {
        return subTotal;
    }

    public Money getTotal() {
        Money discountTotal = discounts.stream()
                .map(Discount::getAmount)
                .reduce(new Money(0), Money::add);

        return subTotal.minus(discountTotal);
    }

    public List<Discount> getDiscounts() {
        return discounts;
    }
}
