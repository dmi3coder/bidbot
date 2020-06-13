package de.optimax.bidbot.bdd.stepdefs;


import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import de.optimax.bidbot.bdd.CucumberContext;
import de.optimax.bidbot.bidder.BidderImpl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;


public class InitializeFeatureTest {

    private CucumberContext context;

    public InitializeFeatureTest(CucumberContext context) {
        this.context = context;
    }

    @Given("dummy Bidder created")
    public void dummyBidderCreated() {
        context.setBidder(new BidderImpl());
    }

    @When("call init with {int} quantity and {int} cash")
    public void callInitWithQuQuantityAndCashCash(int qu, int cash) {
        assertNotNull("Bidder shouldn't be null", context.getBidder());
        context.getBidder().init(qu, cash);
    }

    @Then("dummy Bidder should have {int} quantity and {int} cash")
    public void dummyBidderShouldHaveQuQuantityAndCashCash(long qu, long cash) {
        assertEquals("Qu should be equal to initialized value", qu, context.getBidder().getAuctionQuantity());
        assertEquals("Cash should be equal to initialized value", cash, context.getBidder().getCash());
    }
}
