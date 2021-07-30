package pricebasket.core.specialoffers;

import pricebasket.core.Basket;
import pricebasket.core.Money;

public class SpecialOffer {

    private final String name;
    private final SpecialOfferQualification qualifier;
    private final SpecialOfferCalculator calculator;

    public SpecialOffer(String name, SpecialOfferQualification qualifier, SpecialOfferCalculator calculator) {
        this.name = name;
        this.qualifier = qualifier;
        this.calculator = calculator;
    }

    public String getName() {
        return name;
    }

    public boolean applicable(Basket basket) {
        return qualifier.qualifies(basket);
    }

    public Money calculate(Basket basket) {
        return calculator.calculateDiscountOn(basket);
    }
}
