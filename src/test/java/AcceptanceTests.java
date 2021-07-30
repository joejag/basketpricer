import org.junit.Test;
import pricebasket.core.*;
import pricebasket.core.ports.SpecialOffersProvider;
import pricebasket.core.ports.StockProvider;
import pricebasket.core.specialoffers.SpecialOffer;
import pricebasket.tech.adapters.InMemorySpecialOffersProvider;
import pricebasket.tech.adapters.InMemoryStockProvider;

import static org.junit.Assert.assertEquals;

public class AcceptanceTests {

    StockProvider stock = InMemoryStockProvider.of(
            new Good("Soup", new Money(0.65)),
            new Good("Bread", new Money(0.80)),
            new Good("Milk", new Money(1.30)),
            new Good("Apples", new Money(1.00)));

    SpecialOffersProvider offers = InMemorySpecialOffersProvider.of(
            new SpecialOffer("Apples 10% off",
                    basket -> basket.occurrencesOf("Apples") >= 1,
                    basket -> new Money(0.10).multiply(basket.occurrencesOf("Apples"))),
            new SpecialOffer("Buy two soups get one half price bread",
                    basket -> basket.occurrencesOf("Soup") >= 2,
                    basket -> {
                        int soupCount = basket.occurrencesOf("Soup");
                        int breadCount = basket.occurrencesOf("Bread");
                        int maxPossibleDiscountedBreads = soupCount / 2;
                        return new Money(0.40).multiply(Math.min(breadCount, maxPossibleDiscountedBreads));
                    }));

    @Test
    public void noSpecialOffersApplied() {
        Basket basket = Basket.of("Milk");

        Receipt receipt = new Checkout(stock, offers).price(basket);

        assertEquals(new Money(1.30), receipt.getSubTotal());
        assertEquals(new Money(1.30), receipt.getTotal());
    }

    @Test
    public void withSpecialOfferOnApples() {
        Basket basket = Basket.of("Apples", "Milk", "Bread");

        Receipt receipt = new Checkout(stock, offers).price(basket);

        assertEquals(new Money(3.10), receipt.getSubTotal());
        assertEquals(new Money(3.00), receipt.getTotal());
    }

    @Test
    public void withSpecialOfferOnSoupAndBread() {
        Basket basket = Basket.of("Soup", "Soup", "Soup", "Soup", "Bread");

        Receipt receipt = new Checkout(stock, offers).price(basket);

        assertEquals(new Money(3.40), receipt.getSubTotal());
        assertEquals(new Money(3.00), receipt.getTotal());
    }
}
