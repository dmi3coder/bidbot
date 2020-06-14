package de.optimax.bidbot.history;

import java.util.Date;

/**
 * Auction transaction created after both participants submitted bids
 * Created transaction cannot be changed once created
 */
public class AuctionTransaction {
    private final int bidderCashAmount;
    private final int opponentCashAmount;
    private final int bidderQuReceived;
    private final int opponentQuReceived;
    private final Date createdAt;

    /**
     * Creates an auction transaction object.
     *
     * @param bidderCashAmount   Amount of cash submitted by bidder
     * @param opponentCashAmount Amount of cash submitted by opponent
     * @param bidderQuReceived   Amount of quantity units received by bidder
     * @param opponentQuReceived Amount of quantity units received by opponent
     * @param createdAt          Time when this transaction was created
     */
    public AuctionTransaction(int bidderCashAmount, int opponentCashAmount, int bidderQuReceived, int opponentQuReceived, Date createdAt) {
        this.bidderCashAmount = bidderCashAmount;
        this.opponentCashAmount = opponentCashAmount;
        this.bidderQuReceived = bidderQuReceived;
        this.opponentQuReceived = opponentQuReceived;
        this.createdAt = createdAt;
    }

    /**
     * Creates an auction transaction object with current time for when transaction was created
     *
     * @param bidderCashAmount   Amount of cash submitted by bidder
     * @param opponentCashAmount Amount of cash submitted by opponent
     * @param bidderQuReceived   Amount of quantity units received by bidder
     * @param opponentQuReceived Amount of quantity units received by opponent
     */
    public AuctionTransaction(int bidderCashAmount, int opponentCashAmount, int bidderQuReceived, int opponentQuReceived) {
        this(bidderCashAmount, opponentCashAmount, bidderQuReceived, opponentQuReceived, new Date());
    }

    /**
     * @return Amount of cash submitted by bidder
     */
    public int getBidderCashAmount() {
        return bidderCashAmount;
    }

    /**
     * @return Amount of cash submitted by opponent
     */
    public int getOpponentCashAmount() {
        return opponentCashAmount;
    }

    /**
     * @return Amount of quantity units received by bidder
     */
    public int getBidderQuReceived() {
        return bidderQuReceived;
    }

    /**
     * @return Amount of quantity units received by opponent
     */
    public int getOpponentQuReceived() {
        return opponentQuReceived;
    }

    /**
     * @return Time when this transaction was created
     */
    public Date getCreatedAt() {
        return createdAt;
    }
}
