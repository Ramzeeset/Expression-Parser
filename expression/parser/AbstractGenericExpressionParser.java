package expression.parser;

import expression.arguments.Const;
import expression.arguments.Variable;
import expression.exception.ParseException;
import expression.generic.Calc;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public abstract class AbstractGenericExpressionParser<T> implements GenericParser<T> {
    protected List<Map<String, BinaryOperatorWithCalc<T>>> rankMapBinaryOperations;
    protected List<Map<String, UnaryOperatorWithCalc<T>>> rankMapUnaryOperations;
    protected int maxRank;
    protected Trie trieForParse;
    protected Calc<T> calculator;
    protected ArrayList<String> binaryOperations;
    protected ArrayList<String> unaryOperations;
    protected ArrayList<Character> bracketsType;
    protected ArrayList<Character> variableType;
    protected StringSource source;

    private List<Token> tokenGeneration() {
        List<Token> tokens = new ArrayList<>();
        int[] bracketsBalance = new int[bracketsType.size() / 2];
        while (source.hasNext()) {
            char ch = source.next();
            boolean flagOfUsing = false;
            for (int i = 0; i < bracketsType.size(); i++) {
                if (bracketsType.get(i) == ch) {
                    if (i % 2 == 0) {
                        tokens.add(new Token(TokenType.LEFT_BRACKET, String.valueOf(ch)));
                        bracketsBalance[i / 2]++;
                    } else {
                        if (tokens.size() > 1 && (tokens.get(tokens.size() - 1).tokenType == TokenType.UNARY_OPERATION ||
                                tokens.get(tokens.size() - 1).tokenType == TokenType.BINARY_OPERATION)) {
                            throw new ParseException("unexpected left bracket at " + tokens.size());
                        }
                        if (tokens.size() > 1 && (tokens.get(tokens.size() - 1).tokenType == TokenType.LEFT_BRACKET)) {
                            throw new ParseException("empty expression at " + tokens.size());
                        }
                        tokens.add(new Token(TokenType.RIGHT_BRACKET, String.valueOf(ch)));
                        bracketsBalance[i / 2]--;
                    }
                    flagOfUsing = true;
                }
            }
            if (ch >= '0' && ch <= '9') {
                StringBuilder number = new StringBuilder();
                flagOfUsing = true;
                while (source.hasNext() && ch >= '0' && ch <= '9') {
                    number.append(ch);
                    ch = source.next();
                }
                if (ch >= '0' && ch <= '9') {
                    number.append(ch);
                } else {
                    source.back();
                    ch = '0';
                }
                if (tokens.size() >= 1 && (tokens.get(tokens.size() - 1).tokenType == TokenType.VARIABLE ||
                        tokens.get(tokens.size() - 1).tokenType == TokenType.NUMBER ||
                        tokens.get(tokens.size() - 1).tokenType == TokenType.RIGHT_BRACKET)) {
                    throw new ParseException("unexpected number at " + tokens.size());
                }
                tokens.add(new Token(TokenType.NUMBER, number.toString()));
            }
            for (Character character : variableType) {
                if (character == ch) {
                    if (tokens.size() >= 1 && (tokens.get(tokens.size() - 1).tokenType == TokenType.VARIABLE ||
                            tokens.get(tokens.size() - 1).tokenType == TokenType.NUMBER ||
                            tokens.get(tokens.size() - 1).tokenType == TokenType.RIGHT_BRACKET)) {
                        throw new ParseException("unexpected variable at " + tokens.size());
                    }
                    flagOfUsing = true;
                    tokens.add(new Token(TokenType.VARIABLE, String.valueOf(ch)));
                }
            }
            StringBuilder operation = new StringBuilder();
            while (trieForParse.search(ch)) {
                operation.append(ch);
                ch = source.next();
                if (trieForParse.buffer.pos.endOfWord) {
                    break;
                }
            }
            trieForParse.buffer.pos = trieForParse.root;
            if (!operation.isEmpty()) {
                flagOfUsing = true;
                boolean flag = false;
                for (String var : unaryOperations) {
                    if (var.equals(operation.toString()) && tokens.size() == 0 || (var.equals(operation.toString()) && (tokens.get(tokens.size() - 1).tokenType == TokenType.LEFT_BRACKET ||
                            tokens.get(tokens.size() - 1).tokenType == TokenType.BINARY_OPERATION ||
                            tokens.get(tokens.size() - 1).tokenType == TokenType.UNARY_OPERATION))) {
                        tokens.add(new Token(TokenType.UNARY_OPERATION, operation.toString()));
                        flag = true;
                    }
                }
                for (String var : binaryOperations) {
                    if (var.equals(operation.toString()) && tokens.size() >= 1 && (tokens.get(tokens.size() - 1).tokenType == TokenType.RIGHT_BRACKET ||
                            tokens.get(tokens.size() - 1).tokenType == TokenType.VARIABLE ||
                            tokens.get(tokens.size() - 1).tokenType == TokenType.NUMBER)) {
                        tokens.add(new Token(TokenType.BINARY_OPERATION, operation.toString()));
                        flag = true;
                    }
                }
                if (!flag) {
                    throw new ParseException("unexpected binary operation at " + tokens.size());
                }
            }
            if (!flagOfUsing && ch != ' ' && ch != '\t' && ch != '\n') {
                throw new ParseException("unexpected symbol at " + tokens.size());
            }

        }
        for (int countOfUnclosedBracket : bracketsBalance) {
            if (countOfUnclosedBracket != 0) {
                throw new ParseException("unequal number of open and close brackets");
            }
        }
        if (tokens.get(tokens.size() - 1).tokenType == TokenType.BINARY_OPERATION ||
                tokens.get(tokens.size() - 1).tokenType == TokenType.UNARY_OPERATION) {
            throw new ParseException("operation without operand");
        }
        tokens.add(new Token(TokenType.EOF, "end"));
        return tokens;
    }

    @Override
    public GenericTripleExpression<T> parse(String expression) {
        source = new StringSource(expression);
        List<Token> array;
        array = tokenGeneration();
        TokenBuffer tokenBuffer = new TokenBuffer(array);
        return parseExpression(tokenBuffer, maxRank);
    }

    private GenericTripleExpression<T> parseExpression(TokenBuffer buffer, int rank) {
        if (rank >= 0) {
            GenericTripleExpression<T> result = parseExpression(buffer, rank - 1);
            while (true) {
                Token newToken = buffer.next();
                boolean flag = false;
                if (newToken.tokenType == TokenType.BINARY_OPERATION) {
                    GenericTripleExpression<T> temp = parseBinaryOperation(result, buffer, newToken, rank);
                    if (temp != result) {
                        flag = true;
                        result = temp;
                    }
                } else if (newToken.tokenType == TokenType.UNARY_OPERATION) {
                    result = parseUnaryOperation(buffer, newToken, rank);
                    if (result != null) {
                        flag = true;
                    }
                } if (!flag) {
                    buffer.back();
                    return result;
                }
            }
        } else {
            return factor(buffer);
        }
    }

    private GenericTripleExpression<T> parseBinaryOperation(GenericTripleExpression<T> result, TokenBuffer buffer, Token newToken, int rank) {
        for (String var : rankMapBinaryOperations.get(rank).keySet()) {
            if (newToken.value.equals(var)) {
                GenericTripleExpression<T> right = parseExpression(buffer, rank - 1);
                return rankMapBinaryOperations.get(rank).get(var).apply(result, right, calculator);
            }
        }
        return result;
    }

    private GenericTripleExpression<T> parseUnaryOperation(TokenBuffer buffer, Token newToken, int rank) {
        GenericTripleExpression<T> result;
        for (String var : rankMapUnaryOperations.get(rank).keySet()) {
            if (newToken.value.equals(var)) {
                if (newToken.value.equals("-")) {
                    Token nextToken = buffer.next();
                    if (nextToken.tokenType == TokenType.NUMBER) {
                        nextToken.value = "-" + nextToken.value;
                        return new Const<>(calculator.valueOf(nextToken.value));
                    } else {
                        buffer.back();
                    }
                }
                result = parseExpression(buffer, rank);
                return rankMapUnaryOperations.get(rank).get(var).apply(result, calculator);
            }
        }
        return null;
    }

    private GenericTripleExpression<T> factor(TokenBuffer buffer) {
        Token newToken = buffer.next();
        if (newToken.tokenType == TokenType.VARIABLE) {
            return new Variable<>(newToken.value, calculator);
        } else if (newToken.tokenType == TokenType.NUMBER) {
            return new Const<>(calculator.valueOf(newToken.value));
        } else if (newToken.tokenType == TokenType.LEFT_BRACKET) {
            GenericTripleExpression<T> exp = parseExpression(buffer, maxRank);
            buffer.next();
            return exp;
        } else {
            buffer.back();
            return null;
        }
    }

    protected enum TokenType {
        UNARY_OPERATION, BINARY_OPERATION,
        LEFT_BRACKET, RIGHT_BRACKET,
        NUMBER, VARIABLE,
        EOF
    }

    private static class TokenBuffer {
        private int pos;
        private final List<Token> tokens;

        public TokenBuffer(List<Token> buffer) {
            this.tokens = buffer;
            pos = 0;
        }

        public Token next() {
            return tokens.get(pos++);
        }

        public void back() {
            pos--;
        }
    }

    private static class Token {
        TokenType tokenType;
        String value;

        public Token(TokenType tokenType, String value) {
            this.tokenType = tokenType;
            this.value = value;
        }
    }

}
