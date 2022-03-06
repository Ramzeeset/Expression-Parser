package expression.operations;

import expression.parser.GenericTripleExpression;
import expression.generic.Calc;

public class Negate<T> extends AbstractUnaryOperation<T> {
    public Negate(GenericTripleExpression<T> operand, Calc<T> calc) {
        this.operand = operand;
        this.calculator = calc;
    }

    @Override
    public T evaluate(int x, int y, int z) {
        return calculator.negate(operand.evaluate(x, y, z));
    }
}
