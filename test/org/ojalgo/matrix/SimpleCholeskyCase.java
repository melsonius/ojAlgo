/*
 * Copyright 1997-2014 Optimatika (www.optimatika.se)
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package org.ojalgo.matrix;

import java.math.BigDecimal;

import org.ojalgo.TestUtils;
import org.ojalgo.matrix.decomposition.Cholesky;
import org.ojalgo.matrix.decomposition.CholeskyDecomposition;
import org.ojalgo.type.context.NumberContext;

/**
 * This problem is taken from example 2.21 of the Scientific Computing, An Introductory Survey.
 *
 * @author apete
 */
public class SimpleCholeskyCase extends BasicMatrixTest {

    public static BigMatrix getFactorL() {
        return BigMatrix.FACTORY.rows(new double[][] { { 1.7321, 0.0, 0.0 }, { -0.5774, 1.6330, 0.0 }, { -0.5774, -0.8165, 1.4142 } }).enforce(DEFINITION);
    }

    public static BigMatrix getFactorR() {
        return BigMatrix.FACTORY.rows(new double[][] { { 1.7321, -0.5774, -0.5774 }, { 0.0, 1.6330, -0.8165 }, { 0.0, 0.0, 1.4142 } }).enforce(DEFINITION);
    }

    /**
     * This matrix is taken from example 2.21 of the Scientific Computing, An Introductory Survey
     *
     * @return The data00 value
     */
    public static BigMatrix getOriginal() {
        return BigMatrix.FACTORY.rows(new double[][] { { 3.0, -1.0, -1.0 }, { -1.0, 3.0, -1.0 }, { -1.0, -1.0, 3.0 } }).enforce(DEFINITION);
    }

    public SimpleCholeskyCase() {
        super();
    }

    public SimpleCholeskyCase(final String arg0) {
        super(arg0);
    }

    /**
     * @see org.ojalgo.matrix.BasicMatrixTest#testData()
     */
    @Override
    public void testData() {

        final BasicMatrix tmpA = SimpleCholeskyCase.getOriginal();
        final BasicMatrix tmpL = SimpleCholeskyCase.getFactorL();
        final BasicMatrix tmpR = SimpleCholeskyCase.getFactorR();

        myExpMtrx = tmpL;
        myActMtrx = tmpR.transpose();

        TestUtils.assertEquals(myExpMtrx, myActMtrx, EVALUATION);

        myExpMtrx = tmpA;
        myActMtrx = tmpL.multiply(tmpR);

        TestUtils.assertEquals(myExpMtrx, myActMtrx, EVALUATION);
    }

    /**
     * @see org.ojalgo.matrix.BasicMatrixTest#testProblem()
     */
    @Override
    public void testProblem() {

        final BasicMatrix tmpMtrx = SimpleCholeskyCase.getOriginal();
        final Cholesky<BigDecimal> tmpDecomp = CholeskyDecomposition.makeBig();
        tmpDecomp.compute(tmpMtrx.toBigStore());

        TestUtils.assertEquals(tmpMtrx.toBigStore(), tmpDecomp, EVALUATION);
    }

    //    public void testSolve() {
    //
    //        BasicMatrix tmpMtrx = SimpleCholeskyCase.getOriginal();
    //        Cholesky<BigDecimal> tmpDecomp = ArbitraryCholesky.makeBig();
    //        tmpDecomp.compute(tmpMtrx.toBigStore());
    //
    //        BasicMatrix tmpL = SimpleCholeskyCase.getFactorL();
    //        BasicMatrix tmpR = SimpleCholeskyCase.getFactorR();
    //
    //        PhysicalStore<BigDecimal> tmpXY = dbI.toBigStore().copy();
    //        tmpXY.substituteForwards(tmpL.toBigStore(), false);
    //        tmpXY.substituteBackwards(tmpR.toBigStore(), false);
    //
    //        MatrixStore<BigDecimal> tmpExpMtrx = dbI.toBigStore();
    //        MatrixStore<BigDecimal> tmpActMtrx = tmpMtrx.toBigStore().multiplyRight(tmpXY);
    //
    //        JUnitUtils.assertEquals(tmpExpMtrx, tmpActMtrx, EVAL_CNTXT);
    //    }

    @Override
    protected void setUp() throws Exception {

        DEFINITION = new NumberContext(7, 4);
        EVALUATION = new NumberContext(4, 3);

        myBigAA = SimpleCholeskyCase.getFactorL();
        myBigAX = SimpleCholeskyCase.getFactorR();
        myBigAB = SimpleCholeskyCase.getOriginal();

        myBigI = BasicMatrixTest.getIdentity(myBigAA.countRows(), myBigAA.countColumns(), DEFINITION);
        myBigSafe = BasicMatrixTest.getSafe(myBigAA.countRows(), myBigAA.countColumns(), DEFINITION);

        super.setUp();
    }

}
