package de.optimax.bidbot.bdd.stepdefs;

import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import de.optimax.bidbot.bdd.BidderTestContext;

import static org.junit.Assert.assertEquals;

public class PlaceBidFeatureTest {

    private BidderTestContext context;
    private int bidResult;

    public PlaceBidFeatureTest(BidderTestContext context) {
        this.context = context;
    }

    @When("Auction asks to bid")
    public void auctionAsksToBid() {
        bidResult = context.getBidder().placeBid();
    }

    @Then("Auction received {int} cash")
    public void auctionReceivedBid_amountCash(int bidAmount) {
        assertEquals("Returned bid result should equal to bid amount", bidAmount, bidResult);
    }
}
