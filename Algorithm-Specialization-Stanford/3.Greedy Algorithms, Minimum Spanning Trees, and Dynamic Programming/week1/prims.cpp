#include <iostream>
#include <string>
#include <fstream>
#include <sstream>
#include <vector>
#include <list>
#include <queue>

using namespace std;

void displayGraph(vector<list<pair<int, int>>> graph)
{
    for (int i = 0; i < graph.size(); i++)
    {
        cout << "node " << i << ": ";
        for (list<pair<int, int>>::iterator it = graph[i].begin(); it != graph[i].end(); it++)
        {
            cout << it->second << "," << it->first << " ; ";
        }
        cout << endl;
    }
}

// return a vector of lists(vertexes)
// each vertex is a list of pairs<weight,the connectted vertex>
vector<list<pair<int, int>>> buildGraphFromFile(string filename)
{
    fstream fin;
    fin.open(filename);
    string line;
    getline(fin, line);
    stringstream ss(line);
    string s_numOfNodes;
    string s_numOfEdges;
    ss >> s_numOfNodes;
    ss >> s_numOfEdges;
    int numOfNodes = stoi(s_numOfNodes);
    int numOfEdges = stoi(s_numOfEdges);
    vector<list<pair<int, int>>> graph(numOfNodes);
    while (getline(fin, line))
    {
        string s_v;
        string s_u;
        string s_weight;
        stringstream sss(line);
        //ss << line;
        sss >> s_v;
        sss >> s_u;
        sss >> s_weight;
        //cout << s_v << "," << s_u << "," << s_weight << endl;
        int v = stoi(s_v) - 1; // -1 to make node index align with the vector index
        int u = stoi(s_u) - 1;
        int weight = stoi(s_weight);
        graph[v].push_back(make_pair(weight, u));
        graph[u].push_back(make_pair(weight, v));
    }
    return graph;
}

long long prims(vector<list<pair<int, int>>> graph)
{
    long long totalCostofMST = 0;
    // <weight,visiting node>
    priority_queue<pair<int, int>, vector<pair<int, int>>, greater<pair<int, int>>> pq;
    int numOfNodes = graph.size();
    bool marked[numOfNodes] = {false};
    // visit node 0
    marked[0] = true;
    list<pair<int, int>> nodeList = graph[0];
    for (list<pair<int, int>>::iterator it = graph[0].begin(); it != graph[0].end(); it++)
    {
        // adding connected node u to pq
        if (!marked[it->second])
            pq.push(*it);
    }
    // end visiting node 0

    while (!pq.empty())
    {
        pair<int, int> visitingNodeWithWeight = pq.top();
        pq.pop();
        int weight = visitingNodeWithWeight.first;
        int node = visitingNodeWithWeight.second;
        cout << "visiting " << node << "," << weight << endl;
        if (marked[node])
        {
            cout << "visitied " << node << endl;
            continue;
        }
        else // visit this node
        {
            //cout << "visiting " << node << endl;
            marked[node] = true;
            totalCostofMST += weight;
            list<pair<int, int>> nodeList = graph[node];
            for (list<pair<int, int>>::iterator it = graph[node].begin(); it != graph[node].end(); it++)
            {
                // adding connected node u to pq
                if (!marked[it->second])
                    pq.push(*it);
            }
        }
    }
    return totalCostofMST;
}

int main()
{
    //vector<list<pair<int, int>>> graph = buildGraphFromFile("edges.txt");
    //vector<list<pair<int, int>>> graph = buildGraphFromFile("primtestcase1.txt"); // 7
    vector<list<pair<int, int>>> graph = buildGraphFromFile("primtestcase2.txt");  // 14
    //displayGraph(graph);
    long long totalCostofMST = prims(graph);
    cout << totalCostofMST << endl;
}