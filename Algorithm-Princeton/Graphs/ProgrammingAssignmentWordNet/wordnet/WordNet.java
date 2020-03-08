import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.TreeMap;

/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */
public class WordNet {

    private Digraph hyper;

    // key is string, which is the noun
    // value is a list of index (line numbers) that contains this noun
    private TreeMap<String, ArrayList<Integer>> nouns;
    private final ArrayList<String> synsetsListByLineNumber;
    private final SAP s;
    private int synsetSize;

    // constructor takes the name of the two input files
    public WordNet(String synsets, String hypernyms) {
        if (synsets == null || hypernyms == null)
            throw new java.lang.IllegalArgumentException();
        synsetsListByLineNumber = new ArrayList<String>();
        readSynsets(synsets);
        readHypernyms(hypernyms);
        s = new SAP(hyper);
    }

    private void readSynsets(String synsets) {
        ArrayList<String> records = new ArrayList<String>();

        In in = new In(synsets);
        while (in.hasNextLine()) {
            records.add(in.readLine());
        }
        synsetSize = records.size();

        nouns = new TreeMap<String, ArrayList<Integer>>();

        // StdOut.println(records.get(34));
        int i = 0; // line number
        for (String record : records) {
            String[] strings = record.split(",");
            // ignore the first one, which is the index
            // assert strings.length == 3;

            // strings[1] is a synset which has a list of nouns, separate by space
            // The individual nouns that constitute a synset are separated by spaces
            String[] nounsList = strings[1].split(" ");
            synsetsListByLineNumber.add(strings[1]);
            // go through the nouns
            for (int j = 0; j < nounsList.length; j++) {
                /*
                if (i == 34) {
                    StdOut.println(nounsList[j]);
                }
                */
                // if this noun is already in the data structure
                if (nouns.containsKey(nounsList[j])) {
                    // nouns.get(nounsList[j]) gets the arrayList of this noun
                    // which stores all the line numbers that has this noun
                    nouns.get(nounsList[j]).add(i);
                }
                // if this is a new noun, create an ArrayList for it
                else {
                    ArrayList<Integer> list = new ArrayList<Integer>();
                    list.add(i);
                    nouns.put(nounsList[j], list);
                }

            }

            i++;
        }
    }


    private void readHypernyms(String hypernyms) {
        ArrayList<String> records = new ArrayList<String>();

        In in = new In(hypernyms);
        while (in.hasNextLine()) {
            records.add(in.readLine());
        }

        hyper = new Digraph(synsetSize);

        for (String record : records) {
            String[] numbers = record.split(",");
            int v = Integer.parseInt(numbers[0]);
            for (int i = 1; i < numbers.length; i++) {
                int w = Integer.parseInt(numbers[i]);
                hyper.addEdge(v, w);
            }
        }
        // StdOut.println(hyper.V()); // 82192
        // StdOut.println(hyper.E()); // 84505
    }

    // returns all WordNet nouns
    public Iterable<String> nouns() {
        return nouns.keySet();
    }

    // is the word a WordNet noun?
    public boolean isNoun(String word) {
        if (word == null)
            throw new java.lang.IllegalArgumentException();
        return nouns.containsKey(word);
    }

    // distance between nounA and nounB (defined below)
    // length of shortest ancestral path of subsets A and B
    public int distance(String nounA, String nounB) {
        if (nounA == null || nounB == null)
            throw new java.lang.IllegalArgumentException();
        // A = set of synsets in which x appears
        ArrayList<Integer> aList = nouns.get(nounA);
        // B = set of synsets in which y appears
        ArrayList<Integer> bList = nouns.get(nounB);

        return s.length(aList, bList);
    }

    // a synset (second field of synsets.txt) that is the common ancestor of nounA and nounB
    // in a shortest ancestral path (defined below)
    // a shortest common ancestor of subsets A and B
    public String sap(String nounA, String nounB) {
        if (nounA == null || nounB == null)
            throw new java.lang.IllegalArgumentException();
        ArrayList<Integer> aList = nouns.get(nounA);
        ArrayList<Integer> bList = nouns.get(nounB);
        return synsetsListByLineNumber.get(s.ancestor(aList, bList));
    }

    // do unit testing of this class
    public static void main(String[] args) {
        // WordNet wn = new WordNet("synsets6.txt", "hypernyms6InvalidCycle+Path.txt");
        WordNet wn = new WordNet("synsets.txt", "hypernyms.txt");
        StdOut.println(wn.nouns.size()); // 119,188
        StdOut.println(wn.sap("worm", "bird")); // animal animate_being beast brute creature fauna
        StdOut.println(wn.distance("worm", "bird")); // 5
        StdOut.println(wn.nouns.get("bird")); // [24306, 24307, 25293, 33764, 70067]
        StdOut.println(wn.nouns.get("worm")); // [81679, 81680, 81681, 81682]
    }
}
