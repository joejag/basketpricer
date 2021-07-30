package pricebasket.core.specialoffers;

import pricebasket.core.Basket;
import pricebasket.core.Money;

@FunctionalInterface
public interface SpecialOfferCalculator {

    Money calculateDiscountOn(Basket basket);
}
