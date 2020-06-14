package de.optimax.bidbot.bidder;

import de.optimax.bidbot.history.AuctionTransaction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Random;

import static org.junit.Assert.assertTrue;

public class BidderImplIT {
    public static final int QU_AMOUNT = 100000;

    private BidderImpl bidder;

    @BeforeEach
    void setUp() {
        bidder = new BidderImpl();

    }

    /**
     * Stable calculated strategy better than random bids from 0 to 1000
     * Won't help versus learning bot
     */
    @Test
    void bidder_normalFlow() {
        int bidderQuMax = 0, opponentQuMax = 0;
        for (int i = 0; i < 100; i++) {
            bidder = new BidderImpl();
            bidder.init(QU_AMOUNT, QU_AMOUNT * 300);
            final Random random = new Random();
            for (int j = 0; j < QU_AMOUNT / 2; j++) {
                final int bidderBid = bidder.placeBid();
                final int opponentBid = random.nextInt(1000);
                bidder.bids(bidderBid, opponentBid);
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
        assertTrue(bidderQuMax > opponentQuMax);
        System.out.println("TOTAL MAX: " + bidderQuMax + " vs opponent: " + opponentQuMax);
    }
}
