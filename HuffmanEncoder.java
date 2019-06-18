import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;

/**
 * Created by YFateen on 4/27/17.
 */
public class HuffmanEncoder {
    public static Map<Character, Integer> buildFrequencyTable(char[] inputSymbols) {
        int maxVal = inputSymbols[0];

        for (int i = 0; i < inputSymbols.length; i++) {
            if (inputSymbols[i] > maxVal) {
                maxVal = inputSymbols[i];
            }
        }
        int[] countArray = new int[maxVal+1];
        char[] charValue = new char[maxVal+1];
        for (char c: inputSymbols) {
            countArray[c]++;
            charValue[c] = c;
        }
        Map<Character, Integer> toReturn = new HashMap<Character, Integer>();
        for (int i = 0; i < countArray.length; i++) {
            if (countArray[i] == 0) { }
            else {
                toReturn.put(charValue[i], countArray[i]);
            }
        }
        return toReturn;
    }
    public static void main(String[] args) {

        char[] input = FileUtils.readFile(args[0]);
        ObjectWriter theWriter = new ObjectWriter(args[0] + ".huf");

        Map<Character, Integer> frequencyTable = buildFrequencyTable(input);
        BinaryTrie binaryDecodingTrie = new BinaryTrie(frequencyTable);

        Map<Character, BitSequence> bitSequenceLookupTable = binaryDecodingTrie.buildLookupTable();
//        List<BitSequence> bitSequencelistAll = new ArrayList<>(bitSequenceLookupTable.values());
//        List<Character> characterList = new ArrayList<>(bitSequenceLookupTable.keySet());

//        BitSequence cumulativeSequence = BitSequence.assemble(bitSequencelistAll);

//        theWriter.writeObject(cumulativeSequence.toString());
//        theWriter.writeObject(characterList.toString())
        theWriter.writeObject(binaryDecodingTrie);


        List bitSequenceList = new ArrayList<BitSequence>();

        BitSequence tempBitSequence;
        for (Character c: input) {
            tempBitSequence = bitSequenceLookupTable.get(c);
            bitSequenceList.add(tempBitSequence);
        }

        BitSequence toAdd = BitSequence.assemble(bitSequenceList);
        theWriter.writeObject(toAdd);
//        theWrite.writeObject();
//        char[] sample = new char[11];
//        sample[0] = 'a';
//        sample[1] = 'a';
//        sample[2] = 'a';
//        sample[3] = 'b';
//        sample[4] = 'c';
//        sample[5] = 'B';
//        sample[6] = 'A';
//        sample[7] = 'A';
//        sample[8] = 'f';
//        sample[9] = 'g';
//        sample[10] = 'z';
//
//        Map<Character, Integer> sampleMap = buildFrequencyTable(sample);
//        System.out.println("Number of a's: " + sampleMap.get('a'));
//        System.out.println("Number of b's: " + sampleMap.get('b'));
//        System.out.println("Number of c's: " + sampleMap.get('c'));
//        System.out.println("Number of A's: " + sampleMap.get('A'));
//        System.out.println("Number of z's: " + sampleMap.get('z'));
    }
}
