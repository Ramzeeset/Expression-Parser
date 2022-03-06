package expression.parser;

public class StringSource implements CharSource {
    String data;
    int pos;

    public StringSource(String expression) {
        data = expression;
        pos = 0;
    }

    @Override
    public boolean hasNext() {
        return pos < data.length();
    }

    @Override
    public char next() {
        return data.charAt(pos++);
    }

    public void back() { pos--; }
}
