package pricebasket.tech.adapters;

import pricebasket.core.specialoffers.SpecialOffer;
import pricebasket.core.Basket;
import pricebasket.core.ports.SpecialOffersProvider;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class InMemorySpecialOffersProvider implements SpecialOffersProvider {

    private List<SpecialOffer> specialOffers = new ArrayList<>();

    @Override
    public List<SpecialOffer> applicableFor(Basket basket) {
        return specialOffers.stream()
                .filter(specialOffer -> specialOffer.applicable(basket))
                .collect(Collectors.toList());
    }

    public static SpecialOffersProvider of(SpecialOffer... specialOffers) {
        InMemorySpecialOffersProvider discountsProvider = new InMemorySpecialOffersProvider();
        Collections.addAll(discountsProvider.specialOffers, specialOffers);
        return discountsProvider;
    }
}
