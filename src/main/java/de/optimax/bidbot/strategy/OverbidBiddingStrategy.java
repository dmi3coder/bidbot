package de.optimax.bidbot.strategy;

import de.optimax.bidbot.bidder.BidderContext;
import de.optimax.bidbot.history.AuctionTransaction;

/**
 * Simple bidding strategy that bids {@link #bidCoefficient} more than previous opponent's bid
 */
public class OverbidBiddingStrategy implements BiddingStrategy {

    private static final double DEFAULT_BID_COEFFICIENT = 1.2;

    private BidderContext context;
    private final double bidCoefficient;

    /**
     * Create bidding strategy with default bid coefficient
     *
     * @see #DEFAULT_BID_COEFFICIENT
     */
    public OverbidBiddingStrategy() {
        this(DEFAULT_BID_COEFFICIENT);
    }

    /**
     * Create bidding strategy with custom bid coefficient
     *
     * @param bidCoefficient custom bidding coefficient
     */
    public OverbidBiddingStrategy(double bidCoefficient) {
        this.bidCoefficient = bidCoefficient;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void init(BidderContext context) {
        this.context = context;
    }

    /**
     * Returns {@link #bidCoefficient} times more than what opponent bidded in last transaction
     *
     * @return {@link #bidCoefficient} of last opponent's transaction, or 0 if last transaction is null
     */
    @Override
    public int nextBid() {
        final AuctionTransaction lastTransaction = context.getHistory().getLastTransaction();
        if (lastTransaction == null) {
            return 0;
        }
        return (int) Math.ceil(lastTransaction.getOpponentCashAmount() * bidCoefficient);
    }
}
