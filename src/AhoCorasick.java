import javafx.util.Pair;
import java.util.*;

public class AhoCorasick {

    class Node
    {
        Node() {

        }

        public Map<Character, Integer> links = new HashMap<>();
        public String replacement = null;
        public String word = null;

        public int suffix_link = -1;
        public int parent = -1;

        public boolean root = false;
        public char letter;
    };

    List<Node> trie = new ArrayList<>();

    public AhoCorasick(List<Pair<String, String>> dictionary) {

        Node root = new Node();
        root.root = true;

        trie.add(root);

        for (Pair<String, String> pair: dictionary) {

            append(pair.getValue(), pair.getKey());
        }

        calculate_suffix_links();
    }

    private void append(String word, String replacement, int index, int node) {

        if (index == word.length()) {

            trie.get(node).replacement = replacement;
            trie.get(node).word = word;
            return;
        }

        Character letter = word.charAt(index);
        if (!trie.get(node).links.containsKey(letter))
        {
            Node nd = new Node();
            nd.letter = letter;
            nd.parent = node;
            trie.add(nd);

            trie.get(node).links.put(letter, trie.size()-1);
        }

        int next_index = trie.get(node).links.get(letter);
        append(word, replacement, index+1, next_index);
    }

    void calculate_suffix_links()
    {
        Queue<Integer> queue = new ArrayDeque<>();
        queue.add(0);

        while (queue.size() > 0)
        {
            int node = queue.poll();

            if (trie.get(node).suffix_link != -1)
                continue;

            if (trie.get(node).root)
            {
                trie.get(node).suffix_link = 0;
            }
            else
            {
                Character letter = trie.get(node).letter;
                int previous = trie.get(trie.get(node).parent).suffix_link;

                while(!trie.get(previous).links.containsKey(letter) && !trie.get(previous).root)
                {
                    previous = trie.get(previous).suffix_link;
                }

                if (!trie.get(previous).links.containsKey(letter) || trie.get(previous).links.get(letter) == node ) {

                    /* Set suffix link to root */
                    trie.get(node).suffix_link = 0;
                }
                else {

                    trie.get(node).suffix_link = trie.get(previous).links.get(letter);
                }
            }

            /* Iterate graph */
            for (Character c : trie.get(node).links.keySet())
            {
                queue.add(trie.get(node).links.get(c));
            }
        }
    }

    private void append(String word, String replacement) {

        if (word.length() > 0)
            append(word, replacement, 0, 0);
    }

    public String correct(String text) {

        StringBuffer buffer = new StringBuffer();

        for(int i = 0, node = 0; i < text.length(); ++i) {

            Character c = text.charAt(i);
            buffer.append(c);

            /* Jump up using suffix links */
            while (!trie.get(node).links.containsKey(c) && !trie.get(node).root)
            {
                node = trie.get(node).suffix_link;
            }

            if (trie.get(node).links.containsKey(c)) {

                node = trie.get(node).links.get(c);
            }

            if (trie.get(node).replacement != null) {

                /* Pop back original and push back replacement */

                buffer.setLength(buffer.length() - trie.get(node).word.length() );
                buffer.append(trie.get(node).replacement);

                /* Set back to root */
                node = 0;
            }
        }

        return buffer.toString();
    }
}
