package expression.exception;

public class OverflowException extends RuntimeException {
    public OverflowException(String integer_overflow) {
        super(integer_overflow);
    }
}
