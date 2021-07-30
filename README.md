# Basket Pricer by Joe Wright

Hello! This is a solution for the [Price a Basket](docs/assignment.md) Kata.

# Design Notes

The solution is laid out according to [Hexagonal Architecture](http://alistair.cockburn.us/Hexagonal+architecture) principles.
This involves separating out business logic from technical concerns.
This means that you can write fast acceptance tests on the whole application and simply change out the Stock and SpecialOffers parts of the codebase to use a database or external service.

The application kicks off from `pricebasket.tech.drivers.CommandLineClient`.
I'd recommend starting there to get a feel for how the application works.

I've tried to reuse the domain language from the assignment instructions at all times.
If you look in the `pricebasket.core` package you'll see the main business objects that are used.

The code was test driven from an acceptance test called - unsurprisingly - `AcceptanceTests`.
If there was a real customer to talk to I might externalise that test into Cucumber, but I haven't for this example since there's no customer to talk to.
The acceptance tests come first, but if lower level logic was required then unit tests were added to cover the scenarios.

This was a fun kata to code.
The special offers part was hard to think about.
I'm quite happy with the modelling of the `SpecialOffers` class, which has qualification and calculation split out.
One thing I struggled with was whether to make the SpecialOFfers into concrete classes, which would make them easier to test.
But I feel like in the real world the discounts would be updated frequently by a marketing team, so you wouldn't want to wait on a development team to create new ones.

## Usage

This application requires JDK8 and Gradle 3.x.

### OSX / Linux

To build it run:

```sh
./ci
```

To run it:

```sh
./PriceBasket Soup Soup Bread
```

### Windows

To build it run:

```
gradle clean shadow
```

To run it:

```
java -cp build\libs\basket-pricer-1.0-all.jar pricebasket.tech.drivers.CommandLineClient Soup Soup Bread
```
