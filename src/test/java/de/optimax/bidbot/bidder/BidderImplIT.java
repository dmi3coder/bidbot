package de.optimax.bidbot.bidder;

import de.optimax.bidbot.history.AuctionTransaction;
import de.optimax.bidbot.strategy.regression.LinearRegressionBiddingStrategy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Random;

import static org.junit.Assert.assertTrue;

public class BidderImplIT {
    public static final int QU_AMOUNT = 2000;

    private BidderImpl bidder;

    @BeforeEach
    void setUp() {
        bidder = new BidderImpl();

    }

    /**
     * Adapting Linear Regression strategy is better than chaotic random bids from opponent
     */
    @Test
    void bidder_normalFlow() {
        int bidderQuMax = 0, opponentQuMax = 0;
        for (int i = 0; i < 10; i++) {
            bidder = new BidderImpl(new LinearRegressionBiddingStrategy());
            bidder.init(QU_AMOUNT, QU_AMOUNT * 300);
            final Random random = new Random();
            for (int j = 0; j < QU_AMOUNT / 2; j++) {
                final int bidderBid = bidder.placeBid();
                final int opponentBid = random.nextInt(1000); // Imitate bidder
                bidder.bids(bidderBid, opponentBid);
                if (j % 100 == 0) {
                    System.out.println("Auction # " + j + " Own bid: " + bidderBid + " Opponent bid: " + opponentBid);
                }
            }
            final List<AuctionTransaction> auctionTransactions = bidder.getHistory().getTransactions();
            final int bidderTotalQuReceived = auctionTransactions.stream().mapToInt(AuctionTransaction::getBidderQuReceived).sum();
            final int opponentTotalQuReceived = auctionTransactions.stream().mapToInt(AuctionTransaction::getOpponentQuReceived).sum();
            if (bidderQuMax < bidderTotalQuReceived) {
                bidderQuMax = bidderTotalQuReceived;
            }
            if (opponentQuMax < opponentTotalQuReceived) {
                opponentQuMax = opponentTotalQuReceived;
            }
        }
        System.out.println("Auction results own max won QU amount: " + bidderQuMax + " vs opponent: " + opponentQuMax);
        assertTrue(bidderQuMax > opponentQuMax);
    }
}
