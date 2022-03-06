package expression.generic;

import expression.parser.GenericTripleExpression;
import expression.parser.AbstractGenericExpressionParser;
import expression.parser.ExpressionGenericParser;

public class GenericTabulator implements Tabulator {
    public static void main(String[] args) {
        Object[][][] tab = new Object[0][][];
        String mode = args[0].substring(1);
        StringBuilder buffer = new StringBuilder();
        for (int i = 1; i < args.length; i++) {
            buffer.append(args[i]);
        }
        String expression = buffer.toString();
        GenericTabulator a = new GenericTabulator();
        try {
            tab = a.tabulate(mode, expression, -2, 2, -2, 2, -2, 2);
        } catch (Exception ignored) {
        }
        for (int i = 0; i <= 4; i++) {
            for (int j = 0; j <= 4; j++) {
                for (int k = 0; k <= 4; k++) {
                    System.out.println(tab[i][j][k]);
                }
            }
        }
    }

    @Override
    public Object[][][] tabulate(String mode, String expression, int x1, int x2, int y1, int y2, int z1, int z2) throws Exception {
        Calc<?> calc;
        switch (mode) {
            case "i" -> calc = new CheckedIntegerCalc();
            case "u" -> calc = new IntegerCalc();
            case "s" -> calc = new ShortCalc();
            case "l" -> calc = new LongCalc();
            case "d" -> calc = new DoubleCalc();
            case "bi" -> calc = new BigIntegerCalc();
            default -> throw new IllegalStateException("Unknown mode");
        }
        AbstractGenericExpressionParser<?> parser = new ExpressionGenericParser<>(calc);
        GenericTripleExpression<?> tabulatorExpression = parser.parse(expression);
        Object[][][] result = new Object[(int) (x2 - x1 + 1)][(int) (y2 - y1 + 1)][(int) (z2 - z1 + 1)];
        for (int i = 0; x1 + i <= x2; i++) {
            for (int j = 0; y1 + j <= y2; j++) {
                for (int k = 0; z1 + k <= z2; k++) {
                    try {
                        result[i][j][k] = tabulatorExpression.evaluate(x1 + i, y1 + j, z1 + k);
                    } catch (Exception e) {
                        result[i][j][k] = null;
                    }
                }
            }
        }
        return result;
    }
}
