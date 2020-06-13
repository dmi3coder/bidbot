package de.optimax.bidbot.bidder;

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
}
