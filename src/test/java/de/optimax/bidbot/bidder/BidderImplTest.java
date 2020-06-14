package de.optimax.bidbot.bidder;

import de.optimax.bidbot.history.AuctionHistoryImpl;
import de.optimax.bidbot.strategy.SimpleBiddingStrategy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.stream.IntStream;

import static org.junit.Assert.assertEquals;

class BidderImplTest {
    private BidderImpl bidder;
    private AuctionHistoryImpl history;
    private SimpleBiddingStrategy strategy;

    @BeforeEach
    void setUp() {
        strategy = new SimpleBiddingStrategy();
        history = new AuctionHistoryImpl();
        bidder = new BidderImpl(strategy, history);
        bidder.init(10, 1000);
    }

    @Test
    void placeBid_whenEndOfAuction_shouldHandleEndCorrectly() {
        IntStream.range(0, 5).forEach(it -> bidder.bids(200, 0));
        final int bidAmount = bidder.placeBid();
        assertEquals(0, bidAmount);
    }

    @Test
    void placeBid_whenLeftSmallAmount_shouldNotBidMoreThanAvailable() {
        IntStream.range(0, 5).forEach(it -> bidder.bids(190, 0));
        final int bidAmount = bidder.placeBid();
        assertEquals(50, bidAmount);
    }
}
