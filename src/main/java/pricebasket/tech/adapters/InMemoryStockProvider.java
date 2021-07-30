package pricebasket.tech.adapters;

import pricebasket.core.Basket;
import pricebasket.core.Good;
import pricebasket.core.Money;
import pricebasket.core.ports.StockProvider;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.stream.Collectors;

public class InMemoryStockProvider implements StockProvider {

    private final Map<String, Good> knownGoods = new HashMap<>();

    public Money priceOf(String good) {
        return knownGoods.get(good).getPrice();
    }

    public boolean isValidBasket(String[] goods) {
        return Arrays.stream(goods)
                .allMatch(knownGoods::containsKey);
    }

    public void convertToBasket(String[] goods, Consumer<Basket> valid, Consumer<List<String>> copingStrategy) {
        List<String> unknownGoods = Arrays.stream(goods)
                .filter(good -> !knownGoods.containsKey(good))
                .collect(Collectors.toList());

        if (unknownGoods.isEmpty()) {
            valid.accept(Basket.of(goods));
        } else {
            copingStrategy.accept(unknownGoods);
        }
    }

    public static StockProvider of(Good... goods) {
        InMemoryStockProvider stock = new InMemoryStockProvider();
        for (Good good : goods) {
            stock.knownGoods.put(good.getName(), good);
        }
        return stock;
    }
}
