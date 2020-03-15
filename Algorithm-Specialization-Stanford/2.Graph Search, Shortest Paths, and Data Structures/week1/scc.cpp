// smallSCC.txt from
// https://www.cs.princeton.edu/courses/archive/fall12/cos226/lectures/42DirectedGraphs.pdf
// https://www.coursera.org/lecture/algorithms-part2/strong-components-fC5Yw

#include <iostream>
#include <list>
#include <vector>
#include <fstream>
#include <sstream>
#include <stack>
#include <algorithm>

using namespace std;

void printGraph(vector<list<int>> g)
{
    cout << "==================" << endl;
    int size = g.size();
    for (int i = 0; i < size; i++)
    {
        cout << i << " | ";
        for (list<int>::iterator it = g[i].begin(); it != g[i].end(); it++)
        {
            cout << *it << " ";
        }
        cout << endl;
    }
    cout << "==================" << endl;
    return;
}

void printList(list<int> l)
{
    list<int>::iterator it;
    for (it = l.begin(); it != l.end(); it++)
    {
        cout << *it << " ";
    }
    cout << endl;
}

/*
    1. compute G_reverse
    2. Compute topological order (reverse postorder) in G_reverse.
        DFS in G_reverse, return a list
            the list has the the first done node at the end
                create a stack, run DFS, if a node is done, push to stack
                pop the stack and push_back to the list
                the list is the topology order of G_reverse
    3. Run DFS in G, visiting unmarked vertices in reverse postorder(topological order) of G_reverse
*/
class KosarajuSCC
{
private:
    vector<list<int>> graph;
    vector<list<int>> graph_reverse;
    bool marked[];
    int graphSize;
    vector<list<int>> reverseGraph(vector<list<int>> graph);
    vector<list<int>> buildGraphFromtxt(string filename);
    list<int> TopologyOrder(vector<list<int>> graph);
    void TopologyOrderDFS(vector<list<int>>, int, bool *, stack<int> &);
    void sccDFS(vector<list<int>>, int, bool *, int *, int);

public:
    KosarajuSCC(string filename);
};

KosarajuSCC::KosarajuSCC(string filename)
{
    this->graph = buildGraphFromtxt(filename);
    this->graphSize = this->graph.size();
    // 1. compute G_reverse
    graph_reverse = reverseGraph(graph);

    //printGraph(graph);
    //printGraph(graph_reverse);




    // 2. topology order of the G_reverse
    list<int> topologyorderList = TopologyOrder(graph_reverse);


    // 3.
    bool isMarked[graphSize] = {false};
    int numOfscc = 0;
    int scc[graphSize] = {-1};
    for (list<int>::iterator it = topologyorderList.begin(); it != topologyorderList.end(); it++)
    {
        if (!isMarked[*it])
        {
            sccDFS(graph, *it, isMarked, scc, numOfscc);
            numOfscc++;
        }
    }
    for (int i = 0; i < graphSize; i++)
    {
        cout << scc[i] << " ";
    }
    cout << endl;

    // 4. for this assignment only
    // count the size of each scc
    int sizeOfscc[numOfscc] = {0};
    for (int i = 0; i < graphSize; i++)
    {
        sizeOfscc[scc[i]]++;
    }
    sort(sizeOfscc, sizeOfscc + numOfscc);
    for (int i = 0; i < 5; i++)
    {
        cout << sizeOfscc[numOfscc - 1 - i] << " ";
    }
    cout << endl;
    
    
    
}

list<int> KosarajuSCC::TopologyOrder(vector<list<int>> g)
{
    int graphSize = g.size();
    bool isMarked[graphSize] = {false};
    stack<int> stk;
    list<int> topologyorderList;
    for (int i = 0; i < graphSize; i++)
    {
        if (!isMarked[i])
            TopologyOrderDFS(g, i, isMarked, stk);
    }

    while (!stk.empty())
    {
        topologyorderList.push_back(stk.top());
        stk.pop();
    }

    printList(topologyorderList);

    return topologyorderList;
}

void KosarajuSCC::TopologyOrderDFS(vector<list<int>> g, int u, bool *isMarked, stack<int> &stk)
{
    isMarked[u] = true;
    list<int> uList = g[u];
    for (list<int>::iterator it = uList.begin(); it != uList.end(); it++)
    {
        if (!isMarked[*it])
        {
            TopologyOrderDFS(g, *it, isMarked, stk);
        }
    }
    // no more DFS to run on u, meaning it's done, push to stack
    // cout << " push " << u << endl;
    stk.push(u);
}

void KosarajuSCC::sccDFS(vector<list<int>> g, int u, bool *isMarked, int *scc, int count)
{
    isMarked[u] = true;
    scc[u] = count;
    list<int> uList = g[u];
    for (list<int>::iterator it = uList.begin(); it != uList.end(); it++)
    {
        if (!isMarked[*it])
        {
            sccDFS(g, *it, isMarked, scc, count);
        }
    }
}

vector<list<int>> KosarajuSCC::buildGraphFromtxt(string filename)
{
    ifstream fin;
    fin.open(filename);
    string line;
    // need to get the line to see how many nodes in total
    // u -> v
    int u, v;
    string u_str, v_str;
    while (getline(fin, line))
    {
        stringstream ss(line);
        ss >> u_str;
        ss >> v_str;

        u = stoi(u_str);
        v = stoi(v_str);
    }
    //cout << u << endl;
    // u means the last node, so it's how many nodes in the txt
    vector<list<int>> graph(u);

    fin.clear();
    fin.seekg(0, ios::beg);

    while (getline(fin, line))
    {
        stringstream ss(line);
        ss >> u_str;
        ss >> v_str;
        u = stoi(u_str) - 1;
        v = stoi(v_str) - 1;
        graph[u].push_back(v);
    }
    return graph;
}

vector<list<int>> KosarajuSCC::reverseGraph(vector<list<int>> graph)
{
    int size = graph.size();
    vector<list<int>> graph_reverse(size);
    // reverse from
    // i -> *it
    // to
    // *it -> i
    for (int i = 0; i < size; i++)
    {
        for (list<int>::iterator it = graph[i].begin(); it != graph[i].end(); it++)
        {
            graph_reverse[*it].push_back(i);
        }
    }
    return graph_reverse;
}

int main()
{
    KosarajuSCC k("smallSCC.txt");
    //KosarajuSCC k("SCC.txt");
}