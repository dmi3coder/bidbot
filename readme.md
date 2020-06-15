# Optimax Energy Bidbot
### Bidding bot with pluggable strategies
Application that can participate in auction and compete with opponent.

## Getting started

### Installation

1. Clone this repository
    ```commandline
    git clone https://github.com/dmi3coder/optimax-bidbot.git && cd optimax-bidbot
    ```
2. Install required dependencies(optional)
    ```commandline
   ./mvnw install
    ```
3. Run tests and integration tests
    ```commandline
    ./mvnw test integration-test
    ```
4. Package jar for external usage
    ```commandline
    ./mvnw package
    ```
    You then will have jar file create at `./target`.
    
### Entrypoint

#### 1. Behavior-driven development
It's highly recommended to start with `./src/test/resources/features/` to understand how code behaves. 
This project uses BDD because rules clearly specified. Project uses Cucumber(see `literature.md`) to bind gherkin to java tests

#### 2. BidderImpl
Main class that's responsible for execution and redirection of logic called `BidderImpl`. 

You can create new instance by using default constructor(or any other present)
```java
new BidderImpl(); // Create new Bidder with default strategy and empty history
new BidderImpl(new LinearRegressionBiddingStrategy()) // Create Bidder with custom strategy
``` 
You can also use provided Builder(will be useful in future when there'll be more possible parameters)
```java
BidderImpl.newBuilder().withStrategy(new LinearRegressionBiddingStrategy()).build()
```

### Available Strategies
This project has few different strategies available located under `./src/main/java/de/optimax/bidbot/strategy`
See their usage in `BidderImplIntegrationTest`
#### SimpleBiddingStrategy
This strategy bids constant computed value, which is `maxAmountOfCash / (maxAmountOfQu / 2)`
#### OverbidBiddingStrategy
Bids x times more than previous opponent's bid 

e.g if in previous auction opponent bidded `10`, with default coefficient of `1.2`, strategy will bid `12`
#### LinearRegressionBiddingStrategy
This strategy uses Linear regression to predict the next possible bid.
As an input, it uses bidder's cash amount from all transactions
As an output, it uses opponent's cash amount from all transactions multiplied by coefficient

At prediction time, it looks at average opponent's bid amount as input.

It's really simple implementation of single-feature linear regression and needs to be improved
(At the very least add more input features such as how much money left etc).
 It's probably better to execute external service or [deeplearning4j](https://deeplearning4j.org/)  

### What's not included?
1. Usage of Bidder in Spring.
   
    At start Spring Boot was used to cover possible integrations(e.g websockets, db access etc). 
    Later it was clear that this project doesn't require any of Spring components.
2. Notification of user on a successful bid(deducted from #1)
3. CI or CD.

    Project uses Github Actions to verify if all the tests are successful.
4. Dockerfile or docker-compose/k8s deployment since nothing to run
5. OpenAPI since no API

## Rules

A product x QU (quantity units) will be auctioned under 2 parties. The parties have each y MU (monetary units) for auction. They offer then simultaneously an arbitrary number of its MU on the first 2 QU of the product. After that, the bids will be visible to both. The 2 QU of the product is awarded to who has offered the most MU; if both bid the same, then both get 1 QU. Both bidders must pay their amount - including the defeated. A bid of 0 MU is allowed. Bidding on each 2 QU is repeated until the supply of x QU is fully auctioned. Each bidder aims to get a larger amount than its competitor.

In an auction wins the program that is able to get more QU than the other. With a tie, the program that retains more MU wins.
