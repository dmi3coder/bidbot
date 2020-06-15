package de.optimax.bidbot.bdd.stepdefs;

import cucumber.api.java.en.And;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import de.optimax.bidbot.bdd.BidderTestContext;
import de.optimax.bidbot.history.AuctionTransaction;

import java.util.stream.IntStream;

import static org.junit.Assert.assertEquals;

public class HandleResultsFeature {

    private final BidderTestContext context;

    public HandleResultsFeature(BidderTestContext context) {
        this.context = context;
    }

    @When("Bidder received {int} from Bidder and {int} from Opponent")
    public void bidderReceivedFromBidderAndFromOpponent(int ownBidAmount, int opponentBidAmount) {
        context.getBidder().bids(ownBidAmount, opponentBidAmount);
    }

    @Then("Bidder should store {int} from Bidder and {int} from Opponent into results history")
    public void bidderShouldStoreFromBidderAndFromOpponentIntoResultsHistory(int ownBidAmount, int opponentBidAmount) {
        final AuctionTransaction lastTransaction = context.getBidder().getHistory().getLastTransaction();

        assertEquals("Own bid amount should equal initial input in latest transaction",
                ownBidAmount, lastTransaction.getBidderCashAmount());
        assertEquals("Opponent bid amount should equal initial input in latest transaction",
                opponentBidAmount, lastTransaction.getOpponentCashAmount());
    }

    @And("Bidder should withdraw {int} from available {int} cash")
    public void bidderShouldWithdrawFromAvailableCash(int withdrawAmount, int initialCashAmount) {
        assertEquals("Cash amount should decrease after completed transaction",
                initialCashAmount - withdrawAmount, context.getBidder().getCash()
        );
    }

    @And("Bidder should add {int} QU to Bidder and {int} QU to Opponent")
    public void bidderShouldAddBidder_qu_amountToBidderAndOpponent_qu_amountToOpponent(int bidderQuAmount, int opponentQuAmount) {
        assertEquals(
                "ownQuantity should be updated once bidding completed",
                bidderQuAmount, context.getBidder().getOwnQuantity()
        );
        final AuctionTransaction lastTransaction = context.getBidder().getHistory().getLastTransaction();
        assertEquals(
                "Bidder should record won quantity in last transaction",
                bidderQuAmount, lastTransaction.getBidderQuReceived()
        );
        assertEquals(
                "Bidder should record quantity received by opponent in last transaction",
                opponentQuAmount, lastTransaction.getOpponentQuReceived()
        );
    }

    @When("Bidder received {int} from Bidder and {int} from Opponent {int} times")
    public void bidderReceivedOwnBidAmountFromBidderAndOpponentBidAmountFromOpponentTimes(int ownBidAmount, int opponentBidAmount, int times) {
        IntStream.range(0, times).forEach(it -> context.getBidder().bids(ownBidAmount, opponentBidAmount));
    }

    @Then("Bidder should have {int} cash and {int} quantity")
    public void bidderShouldHaveBidderFinalCashAmountCashAndBidderFinalQuAmountQuantity(int bidderFinalCashAmount, int bidderFinalQuAmount) {
        assertEquals(
                "Amount of cash should be updated after transactions",
                bidderFinalCashAmount,
                context.getBidder().getCash()
        );
        assertEquals(
                "Amount of QU should be updated after transactions",
                bidderFinalQuAmount,
                context.getBidder().getOwnQuantity()

        );
    }
}
