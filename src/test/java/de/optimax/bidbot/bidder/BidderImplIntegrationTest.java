package de.optimax.bidbot.bidder;

import de.optimax.bidbot.history.AuctionTransaction;
import de.optimax.bidbot.strategy.regression.LinearRegressionBiddingStrategy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Random;

import static org.junit.Assert.assertTrue;

public class BidderImplIntegrationTest {
    public static final int QU_AMOUNT = 2000;

    private BidderImpl bidder;

    @BeforeEach
    void setUp() {
        bidder = new BidderImpl();

    }

    /**
     * Adapting Linear Regression strategy is better than chaotic random bids from opponent
     */
    @Test
    void bidder_linearRegressionVersusRandom() {
        int bidderQuMax = 0, opponentQuMax = 0;
        for (int i = 0; i < 10; i++) {
            bidder = new BidderImpl(new LinearRegressionBiddingStrategy());
            bidder.init(QU_AMOUNT, QU_AMOUNT * 300);
            final Random random = new Random();
            for (int j = 0; j < QU_AMOUNT / 2; j++) {
                final int bidderBid = bidder.placeBid();
                final int opponentBid = random.nextInt(1000); // Imitate bidder
                bidder.bids(bidderBid, opponentBid);
                if (j % 100 == 0) {
                    System.out.println("Auction # " + j + " Own bid: " + bidderBid + " Opponent bid: " + opponentBid);
                }
            }
            final List<AuctionTransaction> auctionTransactions = bidder.getHistory().getTransactions();
            final int bidderTotalQuReceived = auctionTransactions.stream().mapToInt(AuctionTransaction::getBidderQuReceived).sum();
            final int opponentTotalQuReceived = auctionTransactions.stream().mapToInt(AuctionTransaction::getOpponentQuReceived).sum();
            if (bidderQuMax < bidderTotalQuReceived) {
                bidderQuMax = bidderTotalQuReceived;
            }
            if (opponentQuMax < opponentTotalQuReceived) {
                opponentQuMax = opponentTotalQuReceived;
            }
        }
        System.out.println("Auction results own max won QU amount: " + bidderQuMax + " vs opponent: " + opponentQuMax);
        assertTrue(bidderQuMax > opponentQuMax);
    }

    /**
     * Two bidders with {@link LinearRegressionBiddingStrategy}. First bidder has larger coefficient, so he wins
     */
    @Test
    void bidder_regressionVersusRegression() {
        int bidderQuMax = 0, opponentQuMax = 0;
        BidderImpl opponent;
        for (int i = 0; i < 10; i++) {
            bidder = new BidderImpl(new LinearRegressionBiddingStrategy(1.3));
            opponent = new BidderImpl(new LinearRegressionBiddingStrategy());
            processAuction(opponent);
            final List<AuctionTransaction> auctionTransactions = bidder.getHistory().getTransactions();
            final int bidderTotalQuReceived = auctionTransactions.stream().mapToInt(AuctionTransaction::getBidderQuReceived).sum();
            final int opponentTotalQuReceived = auctionTransactions.stream().mapToInt(AuctionTransaction::getOpponentQuReceived).sum();
            if (bidderQuMax < bidderTotalQuReceived) {
                bidderQuMax = bidderTotalQuReceived;
            }
            if (opponentQuMax < opponentTotalQuReceived) {
                opponentQuMax = opponentTotalQuReceived;
            }
        }
        System.out.println("Auction results own max won QU amount: " + bidderQuMax + " vs opponent: " + opponentQuMax);
        assertTrue(bidderQuMax > opponentQuMax);
    }

    /**
     * Bidder with {@link LinearRegressionBiddingStrategy} versus Opponent with {@link de.optimax.bidbot.strategy.SimpleBiddingStrategy}.
     * Smart bidder can win constant with 1% difference
     */
    @Test
    void bidder_regressionVersusConstant() {
        int bidderQuMax = 0, opponentQuMax = 0;
        BidderImpl opponent;
        for (int i = 0; i < 10; i++) {
            bidder = BidderImpl.newBuilder().withStrategy(new LinearRegressionBiddingStrategy(1.01)).build();
            opponent = new BidderImpl();
            processAuction(opponent);
            final List<AuctionTransaction> auctionTransactions = bidder.getHistory().getTransactions();
            final int bidderTotalQuReceived = auctionTransactions.stream().mapToInt(AuctionTransaction::getBidderQuReceived).sum();
            final int opponentTotalQuReceived = auctionTransactions.stream().mapToInt(AuctionTransaction::getOpponentQuReceived).sum();
            if (bidderQuMax < bidderTotalQuReceived) {
                bidderQuMax = bidderTotalQuReceived;
            }
            if (opponentQuMax < opponentTotalQuReceived) {
                opponentQuMax = opponentTotalQuReceived;
            }
        }
        System.out.println("Auction results own max won QU amount: " + bidderQuMax + " vs opponent: " + opponentQuMax);
        assertTrue(bidderQuMax > opponentQuMax);
    }


    private void processAuction(BidderImpl opponent) {
        bidder.init(QU_AMOUNT, QU_AMOUNT * 300);
        opponent.init(QU_AMOUNT, QU_AMOUNT * 300);
        for (int j = 0; j < QU_AMOUNT / 2; j++) {
            final int bidderBid = bidder.placeBid();
            final int opponentBid = opponent.placeBid();
            bidder.bids(bidderBid, opponentBid);
            opponent.bids(opponentBid, bidderBid);
            if (j % 100 == 0) {
                System.out.println("Auction # " + j + " Own bid: " + bidderBid + " Opponent bid: " + opponentBid);
                System.out.println("\t\t\tOwn money: " + bidder.getCash() + " Opponent money: " + opponent.getCash());
            }
        }
    }

}
