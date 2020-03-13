#include <iostream>
#include <vector>
#include <list>
#include <fstream>
#include <sstream>
#include <ctime>

using namespace std;

vector<list<int>> graph;

void showList(list<int> l)
{
    list<int>::iterator it;
    for (it = l.begin(); it != l.end(); it++)
    {
        cout << *it << " ";
    }
    cout << endl;
}
void showGraph(vector<list<int>> g)
{
    for (int i = 0; i < g.size(); i++)
    {
        cout << i << " | ";
        for (list<int>::iterator it = g[i].begin(); it != g[i].end(); it++)
        {
            cout << *it << " ";
        }
        cout << endl;
    }
    cout << endl;
}

void buildGraphFromFile()
{

    ifstream fin;
    fin.open("kargerMinCut.txt"); // the answer is 17
    //fin.open("mygraph.txt");
    int numOfLines = 0;
    string line;

    while (getline(fin, line))
    {
        stringstream ss(line);
        string numberString;
        list<int> nodeList;

        // take out the root node number
        ss >> numberString;

        // all the numberString here are connected to the root node number
        while (ss >> numberString)
        {
            // all node label - 1, because the index starts with 0
            nodeList.push_back(stoi(numberString) - 1);
        }
        graph.push_back(nodeList);
        numOfLines++;
    }
    fin.close();
    //showGraph(graph);
}



/*
While there are more than 2 vertices:
• pick a remaining edge (u,v) uniformly at random
• merge (or “contract” ) u and v into a single vertex
• remove self-loops
return cut represented by final 2 vertices.
*/
int KargerRandomContraction()
{
    //srand(time(NULL));

    vector<list<int>> graphCopy = graph;

    int numOfNodes = graphCopy.size();
    int remainingNumOfNode = numOfNodes;

    while (remainingNumOfNode > 2)
    {
        // pick random edge
        // by pick random a node u first, then pick a random node v connected to it

        int u = rand() % numOfNodes;
        while (graphCopy[u].empty())
        {
            u = rand() % numOfNodes;
            //cout << " u " << u << endl;
        }

        list<int> nodesLinkedWithu = graphCopy[u];

        int vIndex = rand() % nodesLinkedWithu.size();
        list<int>::iterator vIter = graphCopy[u].begin();
        for (int i = 0; i < vIndex; i++)
        {
            vIter++;
        }
        int v = *vIter;

        //cout << " u " << u << " v " << v << endl;

        // to merge v to u
        // 1. give all nodes that was connected to v to u
        //    put all v's list to u
        // 2. remove u itself from new u list
        //    in step#1, do not add u itself to u
        // 3. set v to invalid (contains 0 element)
        // 4. for every v in the graph, replace it with u.
        // 5. remove self loop in u

        //1 and 2
        for (list<int>::iterator it = graphCopy[v].begin(); it != graphCopy[v].end(); it++)
        {
            if (*it != u)
            {
                graphCopy[u].push_back(*it);
            }
        }
        //3
        graphCopy[v].clear();
        //4
        for (int i = 0; i < graphCopy.size(); i++)
        {
            for (list<int>::iterator it = graphCopy[i].begin(); it != graphCopy[i].end(); it++)
            {
                if (*it == v)
                {
                    *it = u;
                }
            }
        }
        //5
        graphCopy[u].remove(u);

        remainingNumOfNode--;

        //showGraph(graphCopy);
    }

    // return number of cut
    for (int i = 0; i < graphCopy.size(); i++)
    {
        if (!graphCopy[i].empty())
        {
            return graphCopy[i].size();
        }
    }
}

int main()
{
    buildGraphFromFile();
    int runs = 1000;
    int minCuts = INT_MAX;
    while (runs--)
    {
        srand(time(NULL));
        int cuts = KargerRandomContraction();
        if (cuts < minCuts)
        {
            minCuts = cuts;
        }
    }
    cout << "minCuts " << minCuts << endl;
}