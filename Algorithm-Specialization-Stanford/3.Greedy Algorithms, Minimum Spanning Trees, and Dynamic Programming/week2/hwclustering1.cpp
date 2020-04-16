#include <iostream>
#include <string>
#include <fstream>
#include <sstream>
#include <list>
#include <vector>
#include <algorithm>

using namespace std;

void showGraph(vector<list<pair<int, int>>> g)
{
    for (int i = 0; i < g.size(); i++)
    {
        cout << i << ": ";
        for (list<pair<int, int>>::iterator it = g[i].begin(); it != g[i].end(); it++)
        {
            cout << it->second << ", " << it->first << "; ";
        }
        cout << endl;
    }
    cout << endl;
}
/*
// pair<weight,otherNode>
vector<list<pair<int, int>>> buildGraphFromFile(string filename)
{
    fstream fin;
    fin.open(filename);
    string line;
    getline(fin, line);
    stringstream ss(line);
    string s_numOfNodes;
    ss >> s_numOfNodes;
    int numOfNode = stoi(s_numOfNodes);
    vector<list<pair<int, int>>> graph(numOfNode);
    while (getline(fin, line))
    {
        string s_thisNode;
        string s_thatNode;
        string s_weight;
        stringstream sss(line);
        sss >> s_thisNode;
        sss >> s_thatNode;
        sss >> s_weight;
        int thisNode = stoi(s_thisNode) - 1;
        int thatNode = stoi(s_thatNode) - 1;
        int weight = stoi(s_weight);
        graph[thisNode].push_back(make_pair(weight, thatNode));
        graph[thatNode].push_back(make_pair(weight, thisNode));
    }
    fin.close();
    return graph;
}
*/
// pair<weight,<pair<thisNode,thatNode>>>
vector<pair<int, pair<int, int>>> getEdgesFromFile(string filename, int &numOfNode)
{
    fstream fin;
    fin.open(filename);
    string line;
    getline(fin, line);
    stringstream ss(line);
    string s_numOfNodes;
    ss >> s_numOfNodes;
    numOfNode = stoi(s_numOfNodes);
    vector<pair<int, pair<int, int>>> edges;
    while (getline(fin, line))
    {
        string s_thisNode;
        string s_thatNode;
        string s_weight;
        stringstream sss(line);
        sss >> s_thisNode;
        sss >> s_thatNode;
        sss >> s_weight;
        int thisNode = stoi(s_thisNode);
        int thatNode = stoi(s_thatNode);
        int weight = stoi(s_weight);
        edges.push_back(make_pair(weight, make_pair(thisNode, thatNode)));
    }

    fin.close();
    return edges;
}

int _find(int p, vector<int> cluster)
{
    // p is connnected to other
    while (p != cluster[p])
    {
        // find the node its connected to
        p = cluster[p];
    }
    // return the lead node;
    return p;
}

void _union(int p, int q, vector<int> &cluster, int &numOfClusters)
{
    int pRoot = _find(p, cluster);
    int qRoot = _find(q, cluster);
    if (pRoot != qRoot)
    {
        // connect pRoot to upstream qRoot
        cluster[pRoot] = qRoot;
        numOfClusters--;
    }
}
/*
- Initially, each point in a separate cluster
- Repeat until only k clusters:
    - Let p; q = closest pair of separated points (determines the current spacing)
    - Merge the clusters containing p & q into a single cluster.
*/
vector<int> kclustering(vector<pair<int, pair<int, int>>> &edges, int numOfNode, int targetCluster)
{
    //cout << numOfNode << endl;
    // vector<int> cluster(edges.size());
    vector<int> cluster(numOfNode + 1);
    // every node is on its own cluster
    int numOfClusters = numOfNode;
    cluster[0] = -1;
    for (int i = 1; i < cluster.size(); i++)
    {
        cluster[i] = i;
    }

    sort(edges.begin(), edges.end());

    for (int i = 0; i < edges.size(); i++)
    {
        // cout << "get edge " << edges[i].second.first << " " << edges[i].second.second << " " << edges[i].first << endl;
        if (numOfClusters == targetCluster)
        {
            break;
        }

        int p = edges[i].second.first;
        int q = edges[i].second.second;
        // now p and q is in the same cluster so the weight is 0
        edges[i].first = 0;

        _union(p, q, cluster, numOfClusters);
    }

    for (int i = 0; i < edges.size(); i++)
    {
        int p = edges[i].second.first;
        int q = edges[i].second.second;
        if (_find(p, cluster) == _find(q, cluster))
        {
            edges[i].first = 0;
        }
    }

    cout << " cluster " << endl;
    for (int i = 0; i < cluster.size(); i++)
    {
        cout << i << " " << cluster[i] << endl;
    }
    return cluster;
}

int main()
{
    int numOfNode = 0;
    vector<pair<int, pair<int, int>>> edges = getEdgesFromFile("clustering1.txt", numOfNode);
    vector<int> cluster = kclustering(edges, numOfNode, 4);

/*    
    //https://www.coursera.org/learn/algorithms-greedy/discussions/weeks/2/threads/n-G_4s6gEeaLXA6EMuj8Jg
    vector<pair<int, pair<int, int>>> edges = getEdgesFromFile("hw1testcase1.txt", numOfNode);
    vector<int> cluster = kclustering(edges, numOfNode, 3);
*/    

/*
    // https://www.coursera.org/learn/algorithms-greedy/discussions/weeks/2/threads/iC2LwBGIEeeLRw7GFY8sFA
    vector<pair<int, pair<int, int>>> edges = getEdgesFromFile("hw1testcase2.txt", numOfNode);
    vector<int> cluster = kclustering(edges, numOfNode, 2);
*/

    cout << "result" << endl;
    for (int i = 0; i < edges.size(); i++)
    {
        //if (edges[i].first != 0)
        cout << edges[i].second.first << " " << edges[i].second.second << " " << edges[i].first << endl;
    }

    vector<int> maxSpacing;
    for (int i = 0; i < edges.size(); i++)
    {
        if (edges[i].first != 0 && (find(maxSpacing.begin(), maxSpacing.end(), edges[i].first) == maxSpacing.end()))
            maxSpacing.push_back(edges[i].first);
    }

    for (int i = 0; i < maxSpacing.size(); i++)
    {
        cout << maxSpacing[i] << endl;
    }
    sort(maxSpacing.begin(), maxSpacing.end());
    cout << "maxSpacing: " << maxSpacing[0] << endl;
}