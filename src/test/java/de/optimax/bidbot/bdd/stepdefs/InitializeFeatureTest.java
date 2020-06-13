package de.optimax.bidbot.bdd.stepdefs;


import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import de.optimax.bidbot.bidder.BidderImpl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;


public class InitializeFeatureTest {

    private BidderImpl bidder;

    @Given("dummy Bidder created")
    public void dummyBidderCreated() {
        bidder = new BidderImpl();
    }

    @When("call init with {int} quantity and {int} cash")
    public void callInitWithQuQuantityAndCashCash(int qu, int cash) {
        assertNotNull("Bidder shouldn't be null", bidder);
        bidder.init(qu, cash);
    }

    @Then("dummy Bidder should have {int} quantity and {int} cash")
    public void dummyBidderShouldHaveQuQuantityAndCashCash(long qu, long cash) {
        assertEquals("Qu should be equal to initialized value", qu, bidder.getQuantity());
        assertEquals("Cash should be equal to initialized value", cash, bidder.getCash());
    }
}
