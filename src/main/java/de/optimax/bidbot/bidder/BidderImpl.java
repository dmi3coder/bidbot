package de.optimax.bidbot.bidder;

import auction.Bidder;

/**
 * Implementation of {@link Bidder} interface
 * Used for communication with Auction, tracking of {@link #auctionQuantity} and {@link #cash} changes
 *
 * @author Dmytro Chaban
 */
public class BidderImpl implements Bidder, BidderContext {

    private int auctionQuantity;
    private int cash;

    /**
     * {@inheritDoc}
     */
    @Override
    public void init(int quantity, int cash) {
        if (quantity < 0 || cash < 0) {
            return;
        }
        this.auctionQuantity = quantity;
        this.cash = cash;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int placeBid() {
        throw new UnsupportedOperationException("Method not implemented yet");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void bids(int own, int other) {
        throw new UnsupportedOperationException("Method not implemented yet");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getAuctionQuantity() {
        return auctionQuantity;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getCash() {
        return cash;
    }
}
