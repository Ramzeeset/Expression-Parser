package expression.operations;

import expression.parser.GenericTripleExpression;
import expression.generic.Calc;

public abstract class AbstractBinaryOperation<T> implements GenericTripleExpression<T> {
    protected GenericTripleExpression<T> left = null;
    protected GenericTripleExpression<T> right = null;
    protected Calc<T> calculator;
}
