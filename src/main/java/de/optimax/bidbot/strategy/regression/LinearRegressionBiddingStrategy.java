package de.optimax.bidbot.strategy.regression;

import de.optimax.bidbot.bidder.BidderContext;
import de.optimax.bidbot.history.AuctionHistory;
import de.optimax.bidbot.history.AuctionTransaction;
import de.optimax.bidbot.strategy.BiddingStrategy;
import de.optimax.bidbot.strategy.SimpleBiddingStrategy;
import org.ejml.simple.SimpleMatrix;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

/**
 * Bidding strategy that bids {@link #bidCoefficient} times more than predicted average of opponent
 * Gradient descent was used to train linear regression. Training batched by 10 bids intervals.
 * Training is continuous with mini-batches that provides better performance and can be executed while bidding.
 * First 10 bids will be zero, because regression doesn't have enough data to train correctly
 */
public class LinearRegressionBiddingStrategy implements BiddingStrategy {

    private static final int EPOCHS_COUNT = 1000;
    private static final double ALPHA = 0.0001;
    private static final double DEFAULT_BID_COEFFICIENT = 1.2;

    private BidderContext context;
    private SimpleMatrix theta;
    private int lastPosition = 0;
    private final double bidCoefficient;
    private final BiddingStrategy fallbackStrategy;

    /**
     * Init LinearRegressionBiddingStrategy with default bid coefficient {@link #DEFAULT_BID_COEFFICIENT}
     */
    public LinearRegressionBiddingStrategy() {
        this(DEFAULT_BID_COEFFICIENT);
    }

    /**
     * Inits LinearRegressionBiddingStrategy with custom bidCoefficient
     *
     * @param bidCoefficient coefficient which should be applied to
     */
    public LinearRegressionBiddingStrategy(double bidCoefficient) {
        this(bidCoefficient, new SimpleBiddingStrategy());
    }

    /**
     * Inits LinearRegressionBiddingStrategy with custom bidCoefficient and fallbackStrategy
     *
     * @param bidCoefficient coefficient which should be applied to
     * @param fallbackStrategy strategy that is used when LinearRegression doesn't have enough data
     */
    public LinearRegressionBiddingStrategy(double bidCoefficient, BiddingStrategy fallbackStrategy) {
        this.bidCoefficient = bidCoefficient;
        this.fallbackStrategy = fallbackStrategy;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void init(BidderContext context) {
        fallbackStrategy.init(context);
        this.context = context;
    }

    /**
     * Predict next bid with linear regression
     *
     * @return predicted value or 0 if linearRegression hasn't been initialized
     */
    @Override
    public int nextBid() {
        final List<AuctionTransaction> transactions = context.getHistory().getTransactions();
        // We want to learn from opponent, use fallback strategy
        if (transactions.size() < 10) {
            return fallbackStrategy.nextBid();
        }
        if (transactions.size() % 10 == 0) {
            this.trainModel();
        }
        final double averageOpponentBid = context.getHistory().getTransactions().stream().mapToInt(AuctionTransaction::getOpponentCashAmount)
                .average().orElse(0);
        return (int) Math.ceil(theta.get(0, 0) * averageOpponentBid);
    }

    /**
     * Train model with new data.
     * Input(features): bidder's cash amount from all transactions
     * Output(label): opponent's cash amount from all transactions multiplied by {@link #bidCoefficient}
     * Training occurs in small batches starting from {@link #lastPosition} and ending with size of {@link AuctionHistory#getTransactions()}
     */
    private void trainModel() {

        final List<AuctionTransaction> transactions = context.getHistory().getTransactions();
        final double maxValue = transactions.stream().mapToInt(AuctionTransaction::getOpponentCashAmount).max().orElse(0);

        // Each item of list is a array of features, in our case it's 1 feature
        final List<double[]> inputData = transactions.stream()
                .skip(lastPosition)
                .mapToInt(AuctionTransaction::getBidderCashAmount)
                .mapToDouble(it -> it / maxValue)
                .mapToObj(it -> new double[]{it}).collect(Collectors.toList());
        // Each item of output list is an array with 1 length, representing label, or output
        final List<double[]> outputData = transactions.stream()
                .mapToDouble(transaction -> transaction.getOpponentCashAmount() * bidCoefficient)
                .skip(lastPosition)
                .map(it -> it / maxValue)
                .mapToObj(it -> new double[]{it}).collect(Collectors.toList());

        if (theta == null) {
            theta = SimpleMatrix.random_DDRM(1, 1, 0, 1, new Random());
        }
        for (int j = 0; j < EPOCHS_COUNT; j++) {
            SimpleMatrix x = new SimpleMatrix(inputData.toArray(new double[0][0]));
            SimpleMatrix y = new SimpleMatrix(outputData.toArray(new double[0][0]));
            theta = LinearRegressionUtils.gradientDescent(x, theta, y, ALPHA);
        }
        lastPosition = inputData.size();
    }
}
