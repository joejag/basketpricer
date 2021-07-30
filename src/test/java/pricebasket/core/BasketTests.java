package pricebasket.core;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class BasketTests {

    @Test
    public void countOccurancesOfGoods() {
        Basket basket = Basket.of("Milk", "Milk", "Honey");

        assertEquals(1, basket.occurrencesOf("Honey"));
        assertEquals(2, basket.occurrencesOf("Milk"));
    }
}
