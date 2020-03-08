/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

public class Outcast {
    private final WordNet wn;

    // constructor takes a WordNet object
    public Outcast(WordNet wordnet) {
        wn = wordnet;
    }

    // given an array of WordNet nouns, return an outcast
    public String outcast(String[] nouns) {
        int lengthOfnouns = nouns.length;
        int[] distance = new int[lengthOfnouns];
        for (int i = 0; i < lengthOfnouns; i++) {
            for (int j = 0; j < lengthOfnouns; j++) {
                if (i == j) {
                    continue;
                }
                distance[i] += wn.distance(nouns[i], nouns[j]);
            }
        }
        int maxDistance = 0;
        int maxIndex = 0;
        for (int i = 0; i < lengthOfnouns; i++) {
            if (distance[i] > maxDistance) {
                maxDistance = distance[i];
                maxIndex = i;
            }
        }

        return nouns[maxIndex];
    }

    // see test client below
    public static void main(String[] args) {
        // WordNet wordnet = new WordNet(args[0], args[1]);
        WordNet wordnet = new WordNet("synsets.txt", "hypernyms.txt");
        Outcast outcast = new Outcast(wordnet);
        // for (int t = 2; t < args.length; t++) {
        for (int t = 0; t < args.length; t++) {
            In in = new In(args[t]);
            String[] nouns = in.readAllStrings();
            StdOut.println(args[t] + ": " + outcast.outcast(nouns));
        }
    }
}
