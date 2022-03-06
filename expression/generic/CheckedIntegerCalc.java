package expression.generic;

import expression.exception.DivisionByZero;
import expression.exception.OverflowException;

public class CheckedIntegerCalc implements Calc<Integer> {

    @Override
    public Integer add(Integer left, Integer right) {
        Integer result = left + right;
        if (((left ^ result) & (right ^ result)) < 0) {
            throw new OverflowException("integer overflow");
        } else {
            return result;
        }
    }

    @Override
    public Integer subtract(Integer left, Integer right) {
        Integer result = left - right;
        if (((left ^ right) & (left ^ result)) < 0) {
            throw new OverflowException("integer overflow");
        } else {
            return result;
        }
    }

    @Override
    public Integer divide(Integer left, Integer right) {
        if (right == 0) {
            throw new DivisionByZero("division by zero");
        } else if ((right == Integer.MIN_VALUE && left == -1) ||
                (right == -1 && left == Integer.MIN_VALUE)) {
            throw new OverflowException("integer overflow");
        } else {
            return left / right;
        }
    }

    @Override
    public Integer multiply(Integer left, Integer right) {
        int result = left * right;
        int absLeft, absRight;
        if (left > 0) {
            absLeft = left;
        } else {
            absLeft = -left;
        }
        if (right > 0) {
            absRight = right;
        } else {
            absRight = -right;
        }
        if ((absLeft | absRight) >> 15 != 0) {
            if (((right != 0) && (result / right != left))
                    || (left == Integer.MIN_VALUE && right == -1)) {
                throw new OverflowException("overflow");
            }
        }
        return result;
    }

    @Override
    public Integer negate(Integer operand) {
        if (operand == Integer.MIN_VALUE) {
            throw new OverflowException("integer overflow");
        }
        else {
            return -operand;
        }
    }

    @Override
    public Integer valueOf(String value) {
        return Integer.parseInt(value);
    }

    @Override
    public Integer valueOf(int value) {
        return value;
    }
}
