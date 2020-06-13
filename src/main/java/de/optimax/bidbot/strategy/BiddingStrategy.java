package de.optimax.bidbot.strategy;

import de.optimax.bidbot.bidder.BidderContext;

/**
 * Represents a strategy that will determine optimal bidding amount on each
 */
public interface BiddingStrategy {

    /**
     * Provides ability for BiddingStrategy to prepare for bidding session
     * Method should be called once {@link auction.Bidder#init(int, int)} was executed
     * Once executed, initial parameters such as {@link BidderContext#getCash()} should be set
     *
     * @param context BidderContext that contains initial information about auction.
     */
    void init(BidderContext context);

    /**
     * Computes or predicts next bid amount
     * This operation is blocking
     *
     * @return optimal cash amount to bid on next quantity
     */
    int nextBid();
}
