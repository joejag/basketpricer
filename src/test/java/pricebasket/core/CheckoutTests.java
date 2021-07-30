package pricebasket.core;

import org.junit.Test;
import pricebasket.core.ports.SpecialOffersProvider;
import pricebasket.core.ports.StockProvider;
import pricebasket.core.specialoffers.SpecialOffer;
import pricebasket.tech.adapters.InMemorySpecialOffersProvider;
import pricebasket.tech.adapters.InMemoryStockProvider;

import static org.junit.Assert.assertEquals;

public class CheckoutTests {

    StockProvider stock = InMemoryStockProvider.of(
            new Good("Milk", new Money(1.00)),
            new Good("Honey", new Money(0.25)));

    @Test
    public void basketWithMultipleItems() {
        Basket basket = Basket.of("Milk", "Milk", "Honey");

        Receipt receipt = new Checkout(stock, new InMemorySpecialOffersProvider()).price(basket);

        assertEquals(new Money(2.25), receipt.getTotal());
    }

    @Test
    public void basketWithAlwaysAvailableDiscount() {
        SpecialOffersProvider offers = InMemorySpecialOffersProvider.of(
                new SpecialOffer("50p off all orders",
                        basket -> true,
                        basket -> new Money(0.50)));
        Basket basket = Basket.of("Milk", "Milk");

        Receipt receipt = new Checkout(stock, offers).price(basket);

        assertEquals(new Money(1.50), receipt.getTotal());
        assertEquals(1, receipt.getDiscounts().size());
        assertEquals("50p off all orders", receipt.getDiscounts().get(0).getName());
        assertEquals(new Money(0.50), receipt.getDiscounts().get(0).getAmount());

    }
}
