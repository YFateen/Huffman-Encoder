import edu.princeton.cs.algs4.MinPQ;
import java.io.Serializable;
import java.util.Set;
import java.util.Map;
import java.util.HashMap;

/**
 * Created by YFateen on 4/27/17.
 *
 * The following class was heavily influenced by Princeton's
 * implementation of the Huffman Data Compression Algorithm
 * shown at the start of the HW7 spec.
 *
 * */

public class BinaryTrie implements Serializable {
    private Node theRoot;
    Map<Character, BitSequence> tableReturn;

    public BinaryTrie(Map<Character, Integer> frequencyTable) {
        MinPQ<Node> thePQ = new MinPQ<>();
        Set<Character> theSet = frequencyTable.keySet();
        for (Character c: theSet) {
            int tempFreq = frequencyTable.get(c);
            if (tempFreq > 0) {
                thePQ.insert(new Node(c, tempFreq, null, null ));
            }
        }

        while (thePQ.size() > 1) {
            Node left  = thePQ.delMin();
            Node right = thePQ.delMin();
            Node parent = new Node('\0', left.freq + right.freq, left, right);
            thePQ.insert(parent);
        }
        this.theRoot = thePQ.delMin();
    }

    public Match longestPrefixMatch(BitSequence querySequence) {
        Node tempNode = theRoot;
        String bitString = "";
        Character toReturnChar = null;
        for (int i = 0; tempNode.left != null && tempNode.right != null && i < querySequence.length(); i++) {
            if (querySequence.bitAt(i) == 1 && tempNode.right == null) {break ;}
            if (querySequence.bitAt(i) == 0 && tempNode.right == null) {break ;}
            else {
                if (querySequence.bitAt(i) == 1) {
                    bitString = bitString + "1";
                    tempNode = tempNode.right;
                    toReturnChar = tempNode.ch;
                }
                if (querySequence.bitAt(i) == 0) {
                    bitString = bitString + "0";
                    tempNode = tempNode.left;
                    toReturnChar = tempNode.ch;
                }
            }
        }

        BitSequence toReturnBits = new BitSequence(bitString);
        Match toReturn = new Match(toReturnBits, toReturnChar);
        return toReturn;
    }

    public Map<Character, BitSequence> buildLookupTable() {
        tableReturn = new HashMap<>();
        mapBuildingHelper(theRoot, "");
        return tableReturn;
    }

    private void mapBuildingHelper(Node input, String bitString) {
        if (input.right == null && input.left == null) {
            tableReturn.put(input.ch, new BitSequence(bitString));
        }
        else {
            mapBuildingHelper(input.left, bitString + '0');
            mapBuildingHelper(input.right, bitString + '1');
        }
    }

    private class Node implements Comparable<Node> , Serializable {
        private final char ch;
        private final int freq;
        private final Node left, right;

        Node(char ch, int freq, Node left, Node right) {
            this.ch = ch;
            this.freq = freq;
            this.left = left;
            this.right = right;
        }
        public int compareTo(Node that) {
            return this.freq - that.freq;
        }
    }
}
