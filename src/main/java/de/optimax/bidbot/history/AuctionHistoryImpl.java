package de.optimax.bidbot.history;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Implementation of Auction history
 * Uses {@link ArrayList} to keep memory footprint lower
 */
public class AuctionHistoryImpl implements AuctionHistory {
    private final List<AuctionTransaction> transactions;

    /**
     * Creates empty history ready for adding new transactions
     */
    public AuctionHistoryImpl() {
        transactions = new ArrayList<>();
    }

    /**
     * Creates history with existing transactions, can be used to restore current state of bot
     *
     * @param transactions list with previous transactions
     */
    public AuctionHistoryImpl(List<AuctionTransaction> transactions) {
        this.transactions = new ArrayList<>(transactions);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void addTransaction(AuctionTransaction transaction) {
        transactions.add(transaction);
    }

    /**
     * Returns unmodifiable list of all transactions
     * {@inheritDoc}
     */
    @Override
    public List<AuctionTransaction> getTransactions() {
        return Collections.unmodifiableList(transactions);
    }

}
