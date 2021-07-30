package pricebasket.core;

import pricebasket.core.ports.SpecialOffersProvider;
import pricebasket.core.ports.StockProvider;
import pricebasket.core.specialoffers.Discount;
import pricebasket.core.specialoffers.SpecialOffer;

import java.util.List;
import java.util.stream.Collectors;

public class Checkout {

    private final StockProvider stockProvider;
    private final SpecialOffersProvider specialOffersProvider;

    public Checkout(StockProvider stockProvider, SpecialOffersProvider specialOffersProvider) {
        this.stockProvider = stockProvider;
        this.specialOffersProvider = specialOffersProvider;
    }

    public Receipt price(Basket basket) {
        Money subTotal = calculateSubTotal(basket);
        List<Discount> discounts = calculateDiscounts(basket);
        return new Receipt(subTotal, discounts);
    }

    private Money calculateSubTotal(Basket basket) {
        return basket.getGoods().stream()
                .map(stockProvider::priceOf)
                .reduce(new Money(0), Money::add);
    }

    private List<Discount> calculateDiscounts(Basket basket) {
        List<SpecialOffer> specialOffers = specialOffersProvider.applicableFor(basket);
        return specialOffers.stream()
                .map(specialOffer -> new Discount(specialOffer.getName(), specialOffer.calculate(basket)))
                .collect(Collectors.toList());
    }

}
