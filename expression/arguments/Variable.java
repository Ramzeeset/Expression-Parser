package expression.arguments;

import expression.generic.Calc;
import expression.parser.GenericTripleExpression;

public class Variable<T> implements GenericTripleExpression<T> {
    String name;
    Calc<T> calculator;
    public Variable(String var, Calc<T> calc) {
        name = var;
        calculator = calc;
    }

    public T evaluate(int x, int y, int z) {
        if ("x".equals(name)) {
            return calculator.valueOf(x);
        } else if ("y".equals(name)) {
            return calculator.valueOf(y);
        } else {
            return calculator.valueOf(z);
        }
    }
}

