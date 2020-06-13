package de.optimax.bidbot.strategy;

import de.optimax.bidbot.bidder.BidderContext;

/**
 * Simple bidding strategy that bids {@link #initialMoneyAmount} / ({@link #initialQuantity} / 2) of initial amount.
 */
public class SimpleBiddingStrategy implements BiddingStrategy {
    private int initialMoneyAmount;
    private int initialQuantity;

    /**
     * {@inheritDoc}
     */
    @Override
    public void init(BidderContext context) {
        this.initialMoneyAmount = context.getCash();
        this.initialQuantity = context.getAuctionQuantity();
    }


    /**
     * Returns constant percent of initial cash amount in relation to half the quantity
     *
     * @return {@link #initialMoneyAmount} / ({@link #initialQuantity} / 2), or 0 if either of input is 0
     */
    @Override
    public int nextBid() {
        if (initialMoneyAmount == 0 || initialQuantity == 0) {
            return 0;
        }
        return initialMoneyAmount / (initialQuantity / 2);
    }
}
