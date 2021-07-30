package pricebasket.core.ports;

import pricebasket.core.Basket;
import pricebasket.core.Money;

import java.util.List;
import java.util.function.Consumer;

public interface StockProvider {

    Money priceOf(String good);

    boolean isValidBasket(String[] goods);

    void convertToBasket(String[] goods, Consumer<Basket> valid, Consumer<List<String>> invalid);
}
