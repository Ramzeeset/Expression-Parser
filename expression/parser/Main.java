package expression.parser;

import expression.generic.Calc;
import expression.generic.IntegerCalc;

public class Main {
    public static void main(String[] args) {
        Calc<Integer> calc = new IntegerCalc();
        ExpressionGenericParser<Integer>parser = new ExpressionGenericParser<>(calc);
        GenericTripleExpression<Integer> a = parser.parse("10 * x + 10 *y + z +3");
        System.out.println(a.evaluate(0, 0, 1));

    }
}
