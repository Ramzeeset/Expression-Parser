package expression.arguments;

import expression.parser.GenericTripleExpression;

public class Const<T> implements GenericTripleExpression<T> {
    T const_meaning;

    public Const(T meaning) {
        const_meaning = meaning;
    }

    public T evaluate(int x, int y, int z) {
        return const_meaning;
    }
}
