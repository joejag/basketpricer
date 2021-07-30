package pricebasket.core;

import org.junit.Assert;
import org.junit.Test;

public class MoneyTests {

    @Test
    public void understandsEquality() {
        Assert.assertEquals(new Money(1.00), new Money(1.00));
        Assert.assertNotEquals(new Money(1.23), new Money(4.56));
    }

    @Test
    public void addMoney() {
        Money product = new Money(0.50).add(new Money(0.50));
        Assert.assertEquals(new Money(1.00), product);
    }

    @Test
    public void subtractMoney() {
        Money product = new Money(1.50).minus(new Money(0.50));
        Assert.assertEquals(new Money(1.00), product);
    }

    @Test
    public void multiplyMoney() {
        Money product = new Money(10.00).multiply(3);
        Assert.assertEquals(new Money(30.00), product);
    }
}
