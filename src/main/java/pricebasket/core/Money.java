package pricebasket.core;

import javax.money.Monetary;
import javax.money.MonetaryAmount;
import java.text.NumberFormat;
import java.util.Locale;

public class Money {

    private final MonetaryAmount amount;

    public Money(double pounds) {
        this(Monetary.getDefaultAmountFactory()
                .setNumber(pounds)
                .setCurrency("GBP")
                .create());
    }

    private Money(MonetaryAmount amount) {
        this.amount = amount;
    }

    public Money add(Money money) {
        return new Money(this.amount.add(money.amount));
    }

    public Money minus(Money money) {
        return new Money(this.amount.subtract(money.amount));
    }

    public Money multiply(int num) {
        return new Money(this.amount.multiply(num));
    }

    @Override
    public String toString() {
        NumberFormat numberFormat = NumberFormat.getCurrencyInstance(Locale.UK);
        return numberFormat.format(amount.getNumber());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Money money = (Money) o;

        return amount != null ? amount.equals(money.amount) : money.amount == null;
    }

    @Override
    public int hashCode() {
        return amount != null ? amount.hashCode() : 0;
    }
}
