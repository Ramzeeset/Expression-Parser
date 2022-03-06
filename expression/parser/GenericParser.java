package expression.parser;

public interface GenericParser<T> {
    GenericTripleExpression<T> parse(String expression) ;
}
