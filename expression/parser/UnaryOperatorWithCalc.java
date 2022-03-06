package expression.parser;

import expression.generic.Calc;

public interface UnaryOperatorWithCalc<T> {
    GenericTripleExpression<T> apply(GenericTripleExpression<T> operand, Calc<T> calc);
}
