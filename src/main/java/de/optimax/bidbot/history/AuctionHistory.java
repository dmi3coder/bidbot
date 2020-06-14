package de.optimax.bidbot.history;

import java.util.List;

/**
 * Represents history of auction, containing all the recorded transactions.
 * Can be used to recalculate amount of cash spent, or amount of QU received
 */
public interface AuctionHistory {
    /**
     * Add new transaction to the auction history
     *
     * @param transaction Transaction that captures expenses and gains of both bidder and opponent
     */
    void addTransaction(AuctionTransaction transaction);

    /**
     * Returns last transaction from history
     *
     * @return Earliest transaction by history(Last by time)
     */
    default AuctionTransaction getLastTransaction() {
        return getTransactions().stream().reduce((first, second) -> second).orElse(null);
    }

    /**
     * Get whole history of transactions, can be used for calculations or analysis
     *
     * @return All transaction history
     */
    List<AuctionTransaction> getTransactions();
}
