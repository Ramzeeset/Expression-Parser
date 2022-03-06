package expression.generic;

public class ShortCalc implements Calc<Short> {
    @Override
    public Short add(Short left, Short right) {
        return (short) (left + right);
    }

    @Override
    public Short subtract(Short left, Short right) {
        return (short) (left - right);
    }

    @Override
    public Short divide(Short left, Short right) {
        return (short) (left / right);
    }

    @Override
    public Short multiply(Short left, Short right) {
        return (short) (left * right);
    }

    @Override
    public Short negate(Short operand) {
        return (short)(-operand);
    }

    @Override
    public Short valueOf(String value) {
        return Short.parseShort(value);
    }

    @Override
    public Short valueOf(int value) {
        return (short) value;
    }
}
