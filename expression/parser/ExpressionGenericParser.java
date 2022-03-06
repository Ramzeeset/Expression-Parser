package expression.parser;

import expression.generic.Calc;
import expression.operations.*;

import java.util.*;

public class ExpressionGenericParser<T> extends AbstractGenericExpressionParser<T> {
    public ExpressionGenericParser(Calc<T> calc) {
        calculator = calc;
        binaryOperations = new ArrayList<>(Arrays.asList("+", "-", "*", "/"));
        unaryOperations = new ArrayList<>(Collections.singletonList("-"));
        bracketsType = new ArrayList<>(Arrays.asList('(', ')'));
        variableType = new ArrayList<>(Arrays.asList('x', 'y', 'z'));
        rankMapBinaryOperations = new ArrayList<>(Arrays.asList(Map.of(),
                Map.of("/", Divide<T>::new, "*", Multiply<T>::new),
                Map.of("-", Subtract<T>::new, "+", Add<T>::new)));
        rankMapUnaryOperations = new ArrayList<>(Arrays.asList(Map.of("-", Negate<T>::new),
                Map.of(),
                Map.of()));
        maxRank = 2;
        trieForParse = new Trie();
        for (String var: new ArrayList<String>() { { addAll(binaryOperations); addAll(unaryOperations); } }) {
            trieForParse.insert(var);
        }
    }
}
