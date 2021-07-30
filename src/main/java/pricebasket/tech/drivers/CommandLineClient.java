package pricebasket.tech.drivers;

import pricebasket.core.Checkout;
import pricebasket.core.Good;
import pricebasket.core.Money;
import pricebasket.core.Receipt;
import pricebasket.core.ports.SpecialOffersProvider;
import pricebasket.core.ports.StockProvider;
import pricebasket.core.specialoffers.Discount;
import pricebasket.core.specialoffers.SpecialOffer;
import pricebasket.tech.adapters.InMemorySpecialOffersProvider;
import pricebasket.tech.adapters.InMemoryStockProvider;

import java.util.logging.LogManager;

public class CommandLineClient {

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
                    basket -> basket.occurrencesOf("Soup") >= 2 &&
                            basket.occurrencesOf("Bread") >= 1,
                    basket -> {
                        int soupCount = basket.occurrencesOf("Soup");
                        int breadCount = basket.occurrencesOf("Bread");
                        int maxPossibleDiscountedBreads = soupCount / 2;
                        return new Money(0.40).multiply(Math.min(breadCount, maxPossibleDiscountedBreads));
                    }));

    public static void main(String[] args) {
        // Prevent javax.money printing to console - given we care about output
        LogManager.getLogManager().reset();

        new CommandLineClient().run(args);
    }

    private void run(String[] goods) {
        stock.convertToBasket(goods,
                basket -> {
                    Receipt receipt = new Checkout(stock, offers).price(basket);
                    print(receipt);
                },
                invalidGoods -> {
                    System.out.println("Sorry, I cannot process your order as these items are not known to me:");
                    for (String invalidGood : invalidGoods) {
                        System.out.println(" * " + invalidGood);
                    }
                });
    }

    private void print(Receipt receipt) {
        System.out.println("Subtotal: " + receipt.getSubTotal());

        if (receipt.getDiscounts().isEmpty()) System.out.println("(No offers available)");
        for (Discount discount : receipt.getDiscounts()) {
            System.out.println(discount.getName() + ": -" + discount.getAmount());
        }

        System.out.println("Total price: " + receipt.getTotal());
    }
}
