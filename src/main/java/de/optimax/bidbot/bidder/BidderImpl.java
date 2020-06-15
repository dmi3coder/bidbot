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
        this(new SimpleBiddingStrategy(), new AuctionHistoryImpl());
    }

    public BidderImpl(BiddingStrategy strategy) {
        this(strategy, new AuctionHistoryImpl());
    }

    public BidderImpl(BiddingStrategy strategy, AuctionHistory history) {
        this.strategy = strategy;
        this.history = history;
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
        if (cash == 0) {
            return 0;
        }
        final int nextBid = strategy.nextBid();
        return Math.min(nextBid, cash);
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
        this.auctionQuantity -= 2;
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

    /**
     * Returns new {@link Builder} for BidderImpl
     * @return new builder for BidderIml
     */
    public static Builder newBuilder() {
        return new Builder();
    }

    /**
     * Builder for {@link BidderImpl} that allows to selectively set parameters, leaving other by default
     */
    public static class Builder {

        private Builder() {
        }

        private BiddingStrategy strategy = new SimpleBiddingStrategy();
        private AuctionHistory history = new AuctionHistoryImpl();

        public Builder withStrategy(BiddingStrategy strategy) {
            this.strategy = strategy;
            return this;
        }

        public Builder withHistory(AuctionHistory history) {
            this.history = history;
            return this;
        }

        public BidderImpl build() {
            return new BidderImpl(strategy, history);
        }


    }
}
