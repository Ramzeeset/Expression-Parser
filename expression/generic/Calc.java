package expression.generic;

public interface Calc <T> {
    T add(T left, T right);
    T subtract(T left, T right);
    T divide(T left, T right);
    T multiply(T left, T right);
    T negate(T operand);
    T valueOf(String value);
    T valueOf(int value);
}
