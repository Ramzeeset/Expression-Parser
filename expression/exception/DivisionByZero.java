package expression.exception;

public class DivisionByZero extends RuntimeException {
    public DivisionByZero(String divisionByZero) {
        super(divisionByZero);
    }
}
