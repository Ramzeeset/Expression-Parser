package expression.generic;

public class DoubleCalc implements Calc<Double> {

    @Override
    public Double add(Double left, Double right) {
        return left + right;
    }

    @Override
    public Double subtract(Double left, Double right) {
        return left - right;
    }

    @Override
    public Double divide(Double left, Double right) {
        return left / right;
    }

    @Override
    public Double multiply(Double left, Double right) {
        return left * right;
    }

    @Override
    public Double negate(Double operand) {
        return -operand;
    }

    @Override
    public Double valueOf(String value) {
        return Double.parseDouble(value);
    }

    @Override
    public Double valueOf(int value) {
        return (double) value;
    }
}
