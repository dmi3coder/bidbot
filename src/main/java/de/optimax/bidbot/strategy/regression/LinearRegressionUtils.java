package de.optimax.bidbot.strategy.regression;

import org.ejml.simple.SimpleMatrix;

/**
 * Linear regression utils
 */
public class LinearRegressionUtils {

    private LinearRegressionUtils() {
    }

    /**
     * Create gradient for current dataset
     *
     * @param x     input features
     * @param theta scales for features
     * @param y     labels
     * @return difference that should be applied in order to match labels
     */
    public static SimpleMatrix gradient(SimpleMatrix x, SimpleMatrix theta, SimpleMatrix y) {
        final int m = y.numCols();
        final SimpleMatrix h_x = x.mult(theta);
        final SimpleMatrix loss = h_x.minus(y);
        final SimpleMatrix gradient = x.transpose().mult(loss).divide(m);
        return gradient;

    }

    /**
     * Make single loop of gradient descent
     *
     * @param x     input features
     * @param theta scales for features
     * @param y     labels
     * @param alpha scale at which gradient descent should occur
     * @return updated theta
     */
    public static SimpleMatrix gradientDescent(SimpleMatrix x, SimpleMatrix theta, SimpleMatrix y, double alpha) {
        return theta.minus(gradient(x, theta, y).scale(alpha));
    }

}
