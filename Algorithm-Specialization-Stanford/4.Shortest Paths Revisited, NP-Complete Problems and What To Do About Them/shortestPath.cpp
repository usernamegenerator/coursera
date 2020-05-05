#include <iostream>
#include <fstream>
#include <sstream>
#include <string>
#include <vector>

using namespace std;

vector<vector<int>> readFromFile(string filename, int &numOfV, int &numOfE)
{
    fstream fin;
    fin.open(filename);
    string line;
    getline(fin, line);
    string s_numOfV, s_numOfE;
    stringstream ss(line);
    ss >> s_numOfV >> s_numOfE;
    numOfV = stoi(s_numOfV);
    numOfE = stoi(s_numOfE);
    vector<vector<int>> v(numOfV, vector<int>(numOfV, INT_MAX));
    while (getline(fin, line))
    {
        string s_head;
        string s_tail;
        string s_weight;
        int head;
        int tail;
        int weight;
        stringstream sss(line);
        sss >> s_head >> s_tail >> s_weight;
        head = stoi(s_head) - 1;
        tail = stoi(s_tail) - 1;
        weight = stoi(s_weight);
        //v.push_back(make_pair(weight, make_pair(head, tail)));
        v[head][tail] = weight;
        //v[tail][head] = weight;
    }
    for (int i = 0; i < numOfV; i++)
    {
        v[i][i] = 0;
    }
    return v;
}

int floydWarshall(vector<vector<int>> graph)
{
    vector<vector<int>> dp = graph;
    int size = graph.size();
    for (int k = 0; k < size; k++)
    {
        for (int i = 0; i < size; i++)
        {
            for (int j = 0; j < size; j++)
            {
                if (dp[i][j] > dp[i][k] + dp[k][j] && dp[i][k] != INT_MAX && dp[k][j] != INT_MAX)
                {
                    
                    dp[i][j] = dp[i][k] + dp[k][j];
                    cout << i << " to " << j << " greater than " << i << " to " << k << " + " << k << " to " << j << " = " <<  dp[i][j]<< endl;
                }
            }
        }
    }

    for (int i = 0; i < size; i++)
    {
        for (int j = 0; j < size; j++)
        {
            cout << dp[i][j] << " ";
        }
        cout << endl;
    }
    cout << endl;

    for (int i = 0; i < size; i++)
    {
        if (dp[i][i] < 0)
        {
            cout << "i" << i << endl;
            return INT_MIN;
        }
    }
    int min = INT_MAX;
    for (int i = 0; i < size; i++)
    {
        for (int j = 0; j < size; j++)
        {
            if (dp[i][j] < min)
            {
                min = dp[i][j];
            }
        }
    }
    return min;
}
int main()
{

    int numOfV, numOfE;
    vector<vector<int>> graph;
    //graph = readFromFile("g1.txt", numOfV, numOfE);
    graph = readFromFile("testcase1.txt", numOfV, numOfE); // negative cycle
    //graph = readFromFile("testcase2.txt", numOfV, numOfE); // -2
    /*
    vector<vector<int>> graph{
        {0, 5, INT_MAX, 7},
        {INT_MAX, 0, 4, 2},
        {3, 3, 0, 2},
        {INT_MAX, INT_MAX, 1, 0}};
    */
    /*
    for (int i = 0; i < numOfV; i++)
    {
        for (int j = 0; j < numOfV; j++)
        {
            cout << graph[i][j] << " ";
        }
        cout << endl;
    }
    cout << endl;
    */
    cout << floydWarshall(graph) << endl;
}