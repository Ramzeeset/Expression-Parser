package expression.generic;

import java.math.BigInteger;

public class BigIntegerCalc implements Calc<BigInteger> {

    @Override
    public BigInteger add(BigInteger left, BigInteger right) {
        return left.add(right);
    }

    @Override
    public BigInteger subtract(BigInteger left, BigInteger right) {
        return left.subtract(right);
    }

    @Override
    public BigInteger divide(BigInteger left, BigInteger right) {
        return left.divide(right);
    }

    @Override
    public BigInteger multiply(BigInteger left, BigInteger right) {
        return left.multiply(right);
    }

    @Override
    public BigInteger negate(BigInteger operand) {
        return operand.negate();
    }

    @Override
    public BigInteger valueOf(String value) {
        return new BigInteger(value);
    }

    @Override
    public BigInteger valueOf(int value) {
        return BigInteger.valueOf(value);
    }
}
