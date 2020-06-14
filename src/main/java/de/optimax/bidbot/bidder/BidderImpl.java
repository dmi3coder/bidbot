package de.optimax.bidbot.bidder;

import auction.Bidder;
import de.optimax.bidbot.history.AuctionHistory;
import de.optimax.bidbot.history.AuctionHistoryImpl;
import de.optimax.bidbot.history.AuctionTransaction;
import de.optimax.bidbot.strategy.BiddingStrategy;
import de.optimax.bidbot.strategy.SimpleBiddingStrategy;

/**
 * Implementation of {@link Bidder} interface
 * Used for communication with Auction, tracking of {@link #auctionQuantity} and {@link #cash} changes
 */
public class BidderImpl implements Bidder, BidderContext {

    private final BiddingStrategy strategy;

    private int auctionQuantity;
    private int cash;
    private int ownQuantity;
    private final AuctionHistory history;


    /**
     * Initialize BidderImpl with default bidding strategy
     */
    public BidderImpl() {
        this.strategy = new SimpleBiddingStrategy();
        this.history = new AuctionHistoryImpl();
    }

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
        strategy.init(this);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int placeBid() {
        return strategy.nextBid();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void bids(int own, int other) {
        int bidderQuWon = 0, opponentQuWon = 0;
        if (own > other) {
            bidderQuWon = 2;
        } else if (own < other) {
            opponentQuWon = 2;
        } else {
            bidderQuWon = opponentQuWon = 1;
        }
        this.ownQuantity += bidderQuWon;
        this.cash -= own;
        history.addTransaction(new AuctionTransaction(own, other, bidderQuWon, opponentQuWon));
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

    /**
     * {@inheritDoc}
     */
    @Override
    public int getOwnQuantity() {
        return ownQuantity;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public AuctionHistory getHistory() {
        return history;
    }
}
