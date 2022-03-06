package expression.parser;

import expression.generic.Calc;

public interface BinaryOperatorWithCalc<T> {
    GenericTripleExpression<T> apply(GenericTripleExpression<T> arg1, GenericTripleExpression<T> arg2, Calc<T> calc);
}
