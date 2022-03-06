package expression.generic;

public class IntegerCalc implements Calc<Integer> {
    @Override
    public Integer add(Integer left, Integer right) {
        return left + right;
    }

    @Override
    public Integer subtract(Integer left, Integer right) {
        return left - right;
    }

    @Override
    public Integer divide(Integer left, Integer right) {
        return left / right;
    }

    @Override
    public Integer multiply(Integer left, Integer right) {
        return left * right;
    }

    @Override
    public Integer negate(Integer operand) {
        return -operand;
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
