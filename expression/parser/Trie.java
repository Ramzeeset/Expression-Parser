package expression.parser;

public class Trie {
    static final int alphabetSize = 128;

    static class Node {
        Node[] children = new Node[alphabetSize];
        boolean endOfWord;

        public Node () {
            endOfWord = false;
            for (int i = 0; i < alphabetSize; i++) {
                children[i] = null;
            }
        }
    }

    class BufferForSearch {
        public Node pos;

        public BufferForSearch() {
            this.pos = root;
        }
    }

    public BufferForSearch buffer;
    public Node root;

    public Trie() {
        this.root = new Node();
        this.buffer = new BufferForSearch();
    }

    public void insert(String key) {
        int index;
        Node buffer = root;
        for (int i = 0; i < key.length(); i++) {
            index = key.charAt(i);
            if (buffer.children[index] == null) {
                buffer.children[index] = new Node();
            }
            buffer = buffer.children[index];
        }
        buffer.endOfWord = true;
    }

    public boolean search(char c) {
        if (buffer.pos.children[c] == null) {
            return false;
        } else {
            buffer.pos = buffer.pos.children[c];
            return true;
        }
    }
}
