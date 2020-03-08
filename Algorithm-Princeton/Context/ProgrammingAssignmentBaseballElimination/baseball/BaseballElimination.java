/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.FlowEdge;
import edu.princeton.cs.algs4.FlowNetwork;
import edu.princeton.cs.algs4.FordFulkerson;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.ST;
import edu.princeton.cs.algs4.StdOut;

public class BaseballElimination {
    private final int numberOfTeams;
    private final String[] teams;
    private final int[] w; // win
    private final int[] l; // lost
    private final int[] r; // remainging/left
    private final int[][] g; // game


    // create a baseball division from given filename in format specified below
    /*
        % more teams4.txt
        4
        Atlanta       83 71  8  0 1 6 1
        Philadelphia  80 79  3  1 0 0 2
        New_York      78 78  6  6 0 0 0
        Montreal      77 82  3  1 2 0 0

        % more teams5.txt
        5
        New_York    75 59 28   0 3 8 7 3
        Baltimore   71 63 28   3 0 2 7 7
        Boston      69 66 27   8 2 0 0 3
        Toronto     63 72 27   7 7 0 0 3
        Detroit     49 86 27   3 7 3 3 0

        7
        U.S.A.    14  5  9    0 1 2 3 1 2 0
        England   12  3  7    1 0 2 1 2 1 0
        France    16  2  7    2 2 0 1 1 1 0
        Germany   13  3  5    3 1 1 0 0 0 0
        Ireland   11  3  5    1 2 1 0 0 1 0
        Belgium   12  4  7    2 1 1 0 1 0 2
        China     13  2  2    0 0 0 0 0 2 0
    */
    // Write code to read in the input file and store the data.
    public BaseballElimination(String filename) {
        In in = new In(filename);
        numberOfTeams = in.readInt();
        teams = new String[numberOfTeams];
        w = new int[numberOfTeams];
        l = new int[numberOfTeams];
        r = new int[numberOfTeams];
        g = new int[numberOfTeams][numberOfTeams];

        for (int i = 0; i < numberOfTeams; i++) {
            teams[i] = in.readString();
            w[i] = in.readInt();
            l[i] = in.readInt();
            r[i] = in.readInt();
            for (int j = 0; j < numberOfTeams; j++) {
                g[i][j] = in.readInt();
                // StdOut.print(g[i][j]);
            }
            // StdOut.println();
            // StdOut.println(teams[i] + " " + w[i] + " " + l[i] + " " + r[i]);
        }
    }

    // number of teams
    public int numberOfTeams() {
        return numberOfTeams;
    }

    // all teams
    public Iterable<String> teams() {
        Queue<String> q = new Queue<String>();
        for (int i = 0; i < numberOfTeams; i++) {
            q.enqueue(teams[i]);
        }
        return q;
    }

    private int getTeamIndexInTeams(String team) {
        for (int i = 0; i < numberOfTeams; i++) {
            if (teams[i].equals(team)) {
                return i;
            }
        }
        return -1;
    }

    // number of wins for given team
    public int wins(String team) {
        int i = getTeamIndexInTeams(team);
        if (i == -1) {
            throw new java.lang.IllegalArgumentException("");
        }
        return w[i];
    }

    // number of losses for given team
    public int losses(String team) {
        int i = getTeamIndexInTeams(team);
        if (i == -1) {
            throw new java.lang.IllegalArgumentException("");
        }
        return l[i];
    }

    // number of remaining games for given team
    public int remaining(String team) {
        int i = getTeamIndexInTeams(team);
        if (i == -1) {
            throw new java.lang.IllegalArgumentException("");
        }
        return r[i];
    }

    // number of remaining games between team1 and team2
    public int against(String team1, String team2) {
        int i = getTeamIndexInTeams(team1);
        if (i == -1) {
            throw new java.lang.IllegalArgumentException("");
        }
        int j = getTeamIndexInTeams(team2);
        if (j == -1) {
            throw new java.lang.IllegalArgumentException("");
        }
        return g[i][j];
    }

    // is given team eliminated?
    public boolean isEliminated(String team) {
        if (getTeamIndexInTeams(team) == -1) {
            throw new java.lang.IllegalArgumentException("");
        }
        // Trivial elimination
        // If the maximum number of games team x can win is less than
        // the number of wins of some other team i, then team x is trivially eliminated
        // (as is Montreal in the example above).
        // That is, if w[x] + r[x] < w[i], then team x is mathematically eliminated.
        if (trivialElimination(team) != null) {
            return true;
        }

        // Nontrivial elimination
        if (nonTrivialElimination(team) != null)
            return true;

        return false;
    }

    private Iterable<String> trivialElimination(String team) {

        Queue<String> q = new Queue<String>();
        int teamX = getTeamIndexInTeams(team);

        for (int i = 0; i < numberOfTeams; i++) {
            if (w[teamX] + r[teamX] < w[i]) {
                q.enqueue(teams[i]);
                return q;
            }
        }
        return null;
    }

    private Iterable<String> nonTrivialElimination(String team) {
        // numberOfVertex = C(n,r) combination
        // C(n,r) = n! / (r! * (n-r)!)
        // C(n,2) = n! / (2 * (n-2)!) = n * (n-1) / 2
        // C(n-1,2) = (n-1) * (n-2) / 2
        int numberOfMatchVertex = (numberOfTeams - 1) * (numberOfTeams - 2) / 2;
        int numberOfTeamVertex = numberOfTeams - 1;
        int numberOfVertex = numberOfMatchVertex + numberOfTeamVertex + 2;

        // convert String team to the team's index
        int teamX = getTeamIndexInTeams(team);

        FlowNetwork fn = new FlowNetwork(numberOfVertex);

        // map team i in teams[] to the index of the vertex
        ST<Integer, Integer> st = new ST<Integer, Integer>();
        // map the index of the vertex to team i in teams[]
        // ST<Integer, Integer> stReverse = new ST<Integer, Integer>();

        for (int i = 0, j = 1; i < numberOfTeams; i++, j++) {
            // do not need to map teamX
            if (i == teamX) {
                j--;
                continue;
            }
            st.put(i, numberOfMatchVertex + j);
            // stReverse.put(numberOfMatchVertex + j, i);
        }


        // add game vertex to the network
        int vGameIndex = 1; // game vertex start from 1. 0 is the source

        // go through the teams[]
        for (int i = 0; i < numberOfTeams; i++) {
            if (i == teamX) {
                continue;
            }
            for (int j = i + 1; j < numberOfTeams; j++) {
                if (j == teamX) {
                    continue;
                }
                FlowEdge e;
                e = new FlowEdge(0, vGameIndex, g[i][j]);
                fn.addEdge(e);
                e = new FlowEdge(vGameIndex, st.get(i), Double.POSITIVE_INFINITY);
                fn.addEdge(e);
                e = new FlowEdge(vGameIndex, st.get(j), Double.POSITIVE_INFINITY);
                fn.addEdge(e);
                vGameIndex++;
            }
        }
        assert (vGameIndex == numberOfMatchVertex);

        for (int i = 0; i < numberOfTeams; i++) {
            if (i == teamX) {
                continue;
            }
            FlowEdge e = new FlowEdge(st.get(i), fn.V() - 1, w[teamX] + r[teamX] - w[i]);
            fn.addEdge(e);
        }
        assert (numberOfVertex == fn.V());
        /*
        if (team.equals("Ghaddafi")) {
            StdOut.println(st.get(0));
            StdOut.println(st.get(2));
            StdOut.println(st.get(3));

            StdOut.println(fn.toString());
        }
        */
        FordFulkerson ff = new FordFulkerson(fn, 0, fn.V() - 1);
        boolean isEliminated = false;
        for (FlowEdge e : fn.adj(0)) {
            // if there is an edge that is not the max flow, means team X is eliminated
            // then we need to get the return iterable
            // which is the certificateOfElimination
            // the minimum cut
            // "you can always find such a subset R by choosing the team vertices
            // on the source side of a min s-t cut in the baseball elimination network"
            if (e.flow() != e.capacity()) {
                isEliminated = true;
                break;
            }
        }

        if (isEliminated) {
            Queue<String> q = new Queue<String>();
            for (int v = 0; v < numberOfTeams; v++) {
                if (v == teamX)
                    continue;

                if (ff.inCut(st.get(v))) {
                    q.enqueue(teams[v]);
                }
            }
            return q;
        }
        else
            return null;
    }

    // subset R of teams that eliminates given team; null if not eliminated
    public Iterable<String> certificateOfElimination(String team) {
        int teamX = getTeamIndexInTeams(team);
        if (teamX == -1)
            throw new java.lang.IllegalArgumentException("");

        Queue<String> q = new Queue<String>();
        q = (Queue<String>) trivialElimination(team);
        if (q != null) {
            return q;
        }
        q = (Queue<String>) nonTrivialElimination(team);
        if (q != null) {
            return q;
        }
        return null;
    }

    /*
        % java-algs4 BaseballElimination teams4.txt
        Atlanta is not eliminated
        Philadelphia is eliminated by the subset R = { Atlanta New_York }
        New_York is not eliminated
        Montreal is eliminated by the subset R = { Atlanta }

        % java-algs4 BaseballElimination teams5.txt
        New_York is not eliminated
        Baltimore is not eliminated
        Boston is not eliminated
        Toronto is not eliminated
        Detroit is eliminated by the subset R = { New_York Baltimore Boston Toronto }
    */
    public static void main(String[] args) {
        BaseballElimination division = new BaseballElimination(args[0]);

        for (String team : division.teams()) {
            if (division.isEliminated(team)) {
                StdOut.print(team + " is eliminated by the subset R = { ");
                for (String t : division.certificateOfElimination(team)) {
                    StdOut.print(t + " ");
                }
                StdOut.println("}");
            }
            else {
                StdOut.println(team + " is not eliminated");
            }
        }


        // division.certificateOfElimination("Montreal");


    }
}
