package de.optimax.bidbot.strategy;

import de.optimax.bidbot.bidder.BidderContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;

class SimpleBiddingStrategyTest {

    private SimpleBiddingStrategy simpleBiddingStrategy;
    private BidderContext mockedContext;

    @BeforeEach
    void setUp() {
        simpleBiddingStrategy = new SimpleBiddingStrategy();
        mockedContext = Mockito.mock(BidderContext.class);

    }

    @Test
    void nextBid_haveZeroInitialAmount_returnZero() {
        Mockito.when(mockedContext.getCash()).thenReturn(0);
        Mockito.when(mockedContext.getAuctionQuantity()).thenReturn(0);
        simpleBiddingStrategy.init(mockedContext);
        assertEquals(0, simpleBiddingStrategy.nextBid());
    }
    @Test
    void nextBid_haveUnevenAmount_roundToHighest() {
        Mockito.when(mockedContext.getCash()).thenReturn(995);
        Mockito.when(mockedContext.getAuctionQuantity()).thenReturn(20);
        simpleBiddingStrategy.init(mockedContext);
        assertEquals(100, simpleBiddingStrategy.nextBid());
    }
}
