package pricebasket.core.specialoffers;

import pricebasket.core.Basket;

@FunctionalInterface
public interface SpecialOfferQualification {

    boolean qualifies(Basket basket);
}
