package pricebasket.core.ports;

import pricebasket.core.specialoffers.SpecialOffer;
import pricebasket.core.Basket;

import java.util.List;

public interface SpecialOffersProvider {

    List<SpecialOffer> applicableFor(Basket basket);
}
