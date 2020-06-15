package de.optimax.bidbot.bdd;

import de.optimax.bidbot.bidder.BidderImpl;
import org.springframework.stereotype.Component;

@Component
public class BidderTestContext {

    private BidderImpl bidder;

    public BidderImpl getBidder() {
        return bidder;
    }

    public void setBidder(BidderImpl bidder) {
        this.bidder = bidder;
    }
}
