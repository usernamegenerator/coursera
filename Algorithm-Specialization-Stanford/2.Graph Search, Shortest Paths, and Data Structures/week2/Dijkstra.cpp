#include <iostream>
#include <fstream>
#include <sstream>
#include <string>
#include <cstring>
#include <vector>
#include <list>
#include <queue>
#include <algorithm>

using namespace std;

// pair<weight,vetex>

void printGraph(vector<list<pair<int, int>>> g)
{
    cout << "==================" << endl;
    int size = g.size();
    for (int i = 0; i < size; i++)
    {
        cout << i << " | ";
        for (list<pair<int, int>>::iterator it = g[i].begin(); it != g[i].end(); it++)
        {
            cout << "[" << (*it).second << "," << (*it).first << "]";
        }
        cout << endl;
    }
    cout << "==================" << endl;
    return;
}
vector<list<pair<int, int>>> buildGraphFromFile(string filename)
{
    ifstream ifile;
    ifile.open(filename);
    string line;
    int v;
    int w;
    int numOfLines = 0;
    while (getline(ifile, line))
    {
        numOfLines++;
    }

    vector<list<pair<int, int>>> graph(numOfLines);

    ifile.clear();
    ifile.seekg(0, ios::beg);

    while (getline(ifile, line))
    {
        stringstream ss(line);
        ss >> v;
        string structString;
        while (ss >> structString)
        {
            size_t pos = structString.find(",");
            string nodeStr = structString.substr(0, pos);
            string lenStr = structString.substr(pos + 1);
            //cout << nodeStr << " " << lenStr << endl;
            int nodeInt = stoi(nodeStr) - 1;
            int lenInt = stoi(lenStr);
            graph[v - 1].push_back(make_pair(lenInt, nodeInt));
        }
    }

    return graph;
}

void Dijkstra(vector<list<pair<int, int>>> graph)
{
    //distance from this vertex to the start vertex
    vector<int> distToStart(graph.size());
    for (int i = 0; i < graph.size(); i++)
    {
        distToStart[i] = INT_MAX;
    }
    // priority_queue stores all the adjacent to the visited nodes
    priority_queue<pair<int, int>> pq;

    // start from vertex 0, distance is 0;
    pq.push(make_pair(0, 0));
    distToStart[0] = 0;

    // pq stores all the vertex that already has updated shortest distance
    // use the vertex in the pq to optimize the adjacent vertex, if they're not updated already
    // how to know which is updated and which is not? :
    // if (distToStart[w] > distToStart[u] + distWtoU)
    while (!pq.empty())
    {
        pair<int, int> node = pq.top();
        pq.pop();
        // visit all the adjacent vertex to this node
        // node.first is the weight
        // node.second is the vertex index
        int u = node.second;
        // cout << "pq get " << u << " weight " << node.first << endl;
        // u -> w
        for (list<pair<int, int>>::iterator it = graph[u].begin(); it != graph[u].end(); it++)
        {
            int w = (*it).second;
            int distWtoU = (*it).first;
            // cout << " check node " << w << " weight is " << distToStart[w] << endl;
            if (distToStart[w] > distToStart[u] + distWtoU)
            {
                distToStart[w] = distToStart[u] + distWtoU;
                // cout << " update node " << w << " to " << distToStart[w] << endl;
                // suppose to insert w with distToStart[w] if it's not already in the queue
                // and update w with distToStart[w] if it's already in the queue
                // but the c++ priority queue operation doesn't allow update
                // so just push a new
                // the redundant is a don't care because when it pop out, it fails if (distToStart[w] > distToStart[u] + distWtoU)
                // so it's just pop and abandoned.
                pq.push(make_pair(distToStart[w], w));
            }
        }
    }

    for (int i = 0; i < graph.size(); i++)
    {
        cout << "v " << i << " distToStart " << distToStart[i] << endl;
    }
    cout << endl;

/*
    // for the output of this assignment
    sort(distToStart.begin(), distToStart.end());
    for (int i = 1; i < 11; i++)
    {
        cout << distToStart[i] << ",";
    }
    cout << endl;
*/
}

int main()
{
    vector<list<pair<int, int>>> graph = buildGraphFromFile("dijkstraData.txt");

    //https://www.coursera.org/lecture/algorithms-part2/dijkstras-algorithm-2e9Ic
    //vector<list<pair<int, int>>> graph = buildGraphFromFile("smalldijkstraData.txt");

    printGraph(graph);
    Dijkstra(graph);
}