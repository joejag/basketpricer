package pricebasket.core;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Basket {

    public List<String> goods = new ArrayList<>();

    public List<String> getGoods() {
        return goods;
    }

    public int occurrencesOf(String good) {
        return Collections.frequency(goods, good);
    }

    public static Basket of(String... goods) {
        Basket basket = new Basket();
        Collections.addAll(basket.goods, goods);
        return basket;
    }
}
