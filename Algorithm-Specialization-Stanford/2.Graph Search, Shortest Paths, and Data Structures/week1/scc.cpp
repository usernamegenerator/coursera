// smallSCC.txt from
// https://www.cs.princeton.edu/courses/archive/fall12/cos226/lectures/42DirectedGraphs.pdf
// https://www.coursera.org/lecture/algorithms-part2/strong-components-fC5Yw

// v and w are strongly connected if there is both a directed path from v to w
// and a directed path from w to v

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
        the list has the first done node at the end
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

    printGraph(graph);

    // 1. compute G_reverse
    graph_reverse = reverseGraph(graph);

    
    //printGraph(graph_reverse);

    // 2. topology order of the G_reverse
    list<int> topologyorderList = TopologyOrder(graph_reverse);

    // 3.
    bool isMarked[graphSize] = {false};
    int numOfscc = 0;
    // init to graphSize in case worst case that every node is scc by itself
    // scc[u]'s value is which scc this u belongs to
    int scc[graphSize] = {-1};
    for (list<int>::iterator it = topologyorderList.begin(); it != topologyorderList.end(); it++)
    {
        if (!isMarked[*it])
        {
            sccDFS(graph, *it, isMarked, scc, numOfscc);
            // every time the above scc returns and run to this line.
            // it means start with this node *it, the DFS has exhausted
            // so it means this scc ends. need to increase the numOfscc to record next
            numOfscc++;
        }
    }
    /*
    for (int i = 0; i < graphSize; i++)
    {
        cout << scc[i] << " ";
    }
    cout << endl;
    */

    // 4. for this assignment only
    // count the size of each scc
    int sizeOfscc[numOfscc] = {0};
    for (int i = 0; i < graphSize; i++)
    {
        // scc[i] means i belongs to which scc
        sizeOfscc[scc[i]]++;
    }
    sort(sizeOfscc, sizeOfscc + numOfscc);
    if (numOfscc >= 5)
    {
        for (int i = 0; i < 5; i++)
        {
            cout << sizeOfscc[numOfscc - 1 - i] << " ";
        }
    }
    else
    {
        int i = 0;
        for (; i < numOfscc; i++)
        {
            cout << sizeOfscc[numOfscc - 1 - i] << " ";
        }
        for(;i<5;i++)
        {
            cout << "0" << " ";
        }
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

    // printList(topologyorderList);

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
        cout << "line" << line << endl;
        ss >> u_str;
        cout << u_str << endl;
        ss >> v_str;
        cout << v_str << endl;
        u = stoi(u_str) - 1;
        cout << u << endl;
        v = stoi(v_str) - 1;
        cout << v << endl;
        cout << u << "->" << v << " ";
        graph[u].push_back(v);
        cout << u << "->" << v << endl;
    }
    printGraph(graph);
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
    //KosarajuSCC k("smallSCC54211.txt");
    //KosarajuSCC k("33300.txt");
    //KosarajuSCC k("33200.txt");
    //KosarajuSCC k("63210.txt");
    

    KosarajuSCC k("33110.txt");
    //KosarajuSCC k("71000.txt");
    //KosarajuSCC k("SCC.txt");
}