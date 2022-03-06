package expression.generic;

public class LongCalc implements Calc<Long> {

    @Override
    public Long add(Long left, Long right) {
        return left + right;
    }

    @Override
    public Long subtract(Long left, Long right) {
        return left - right;
    }

    @Override
    public Long divide(Long left, Long right) {
        return left / right;
    }

    @Override
    public Long multiply(Long left, Long right) {
        return left * right;
    }

    @Override
    public Long negate(Long operand) {
        return -operand;
    }

    @Override
    public Long valueOf(String value) {
        return Long.parseLong(value);
    }

    @Override
    public Long valueOf(int value) {
        return (long) value;
    }
}
