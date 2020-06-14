package de.optimax.bidbot.strategy.regression;

import org.ejml.dense.row.CommonOps_DDRM;
import org.ejml.simple.SimpleMatrix;
import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;


class LinearRegressionUtilsTest {

    public static SimpleMatrix SINGLE_VALUE_MATRIX = new SimpleMatrix(new double[][]{new double[]{10.0}});

    @Test
    public void verify_1minusM_equalsTo_negativeMPlus1() {
        final SimpleMatrix plus = SINGLE_VALUE_MATRIX.negative().plus(1);
        assertEquals(-9.0, plus.get(0, 0), 0.0);
    }

    @Test
    public void gradientDescent_feedMatrix_receiveCorrectResult() {
        for (int i = 0; i < 10; i++) {
            SimpleMatrix x = new SimpleMatrix(new double[][]{
                    new double[]{
                            0.1
                    },
                    new double[]{
                            0.2
                    },
                    new double[]{
                            0.3
                    },
                    new double[]{
                            0.4
                    },
                    new double[]{
                            0.5
                    },
            });
            final SimpleMatrix y = new SimpleMatrix(new double[][]{
                    new double[]{
                            0.1 + 0.1
                    },
                    new double[]{
                            0.2 + 0.1
                    },
                    new double[]{
                            0.3 + 0.1
                    },
                    new double[]{
                            0.4 + 0.1
                    },
                    new double[]{
                            0.5 + 0.1
                    },
            });
            Random rand = new Random();
            SimpleMatrix theta = SimpleMatrix.random_DDRM(1, 1, 0, 1, rand); //3x4?

            for (int j = 0; j < 1000; j++) {
                theta = LinearRegressionUtils.gradientDescent(x, theta, y, 0.01);
            }

            // Prediction input
            x = new SimpleMatrix(new double[][]{
                    new double[]{
                            0.3
                    }
            });
            SimpleMatrix result = x.mult(theta);
            //Prediction output
            double max = CommonOps_DDRM.elementMax(result.getDDRM());
            assertTrue(max > 0.3);
        }
    }
}
