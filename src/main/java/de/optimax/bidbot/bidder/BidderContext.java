package de.optimax.bidbot.bidder;

import de.optimax.bidbot.history.AuctionHistory;

/**
 * Context used in {@link de.optimax.bidbot.strategy.BiddingStrategy} to get access into current state of bidder
 */
public interface BidderContext {

    /**
     * Get currently available quantity on auction,
     *
     * @return quantity left in auction including reserved for current bid
     */
    int getAuctionQuantity();

    /**
     * Get currently available cash for this bidder
     * This is amount of cash that can be used in next bid
     *
     * @return cash left in bidder including reserved for current bid
     */
    int getCash();

    /**
     * Get currently owned quantity for this bidder
     *
     * @return quantity owned by bidder
     */
    int getOwnQuantity();

    /**
     * Get current history of all transactions excluding currently active
     *
     * @return history of all bidding transactions
     */
    AuctionHistory getHistory();
}
