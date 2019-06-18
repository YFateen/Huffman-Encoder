/**
 * Created by YFateen on 4/28/17.
 */
public class HuffmanDecoder {
    public static void main(String[] args) {

        ObjectReader fileReader = new ObjectReader(args[0]);
        BinaryTrie theTrie = (BinaryTrie) fileReader.readObject();
        BitSequence toWrite = new BitSequence((BitSequence) fileReader.readObject());

        Match tempSeq;
        int count = 0;
        char[] toStore = new char[toWrite.length()];
        while (toWrite.length() != 0) {
            tempSeq = theTrie.longestPrefixMatch(toWrite);
            toStore[count] = tempSeq.getSymbol();
            toWrite = toWrite.lastNBits(toWrite.length()-tempSeq.getSequence().length());
            count++;
        }
        char[] toReturn = new char[count];
        for (int i = 0; i < count; i++) {
            toReturn[i] = toStore[i];
        }
        FileUtils.writeCharArray(args[1], toReturn);

    }
}
