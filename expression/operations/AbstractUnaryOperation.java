package expression.operations;

import expression.parser.GenericTripleExpression;
import expression.generic.Calc;

public abstract class AbstractUnaryOperation<T> implements GenericTripleExpression<T> {
    protected GenericTripleExpression<T> operand = null;
    protected Calc<T> calculator;
}
