package de.optimax.bidbot.bidder;

import auction.Bidder;

public class BidderImpl implements Bidder {

    private int quantity;
    private int cash;

    @Override
    public void init(int quantity, int cash) {
        this.quantity = quantity;
        this.cash = cash;
    }

    @Override
    public int placeBid() {
        return 0;
    }

    @Override
    public void bids(int own, int other) {

    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getCash() {
        return cash;
    }

    public void setCash(int cash) {
        this.cash = cash;
    }
}
