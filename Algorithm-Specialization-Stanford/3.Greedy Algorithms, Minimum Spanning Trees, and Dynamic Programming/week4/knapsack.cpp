#include <iostream>
#include <fstream>
#include <vector>
#include <string>
#include <sstream>
#include <algorithm>

using namespace std;

vector<pair<int, int>> readFromFile(string filename, int &W)
{
    fstream fin;
    fin.open(filename);
    string line;
    getline(fin, line);
    stringstream ss(line);
    string W_s;
    string NUM_s;
    ss >> W_s;
    ss >> NUM_s;
    //int W;
    int N;
    W = stoi(W_s);
    N = stoi(NUM_s);
    // cout << W << N << endl;

    vector<pair<int, int>> items;
    items.push_back(make_pair(0, 0));
    while (getline(fin, line))
    {
        stringstream sss(line);
        string v_s;
        string w_s;
        sss >> v_s;
        sss >> w_s;
        int v = stoi(v_s);
        int w = stoi(w_s);
        items.push_back(make_pair(v, w));
    }
    fin.close();
    return items;
}

int knapsack(vector<pair<int, int>> items, int bagSize)
{
    for (int i = 0; i < items.size(); i++)
    {
        cout << items[i].first << " " << items[i].second << endl;
    }
    cout << "================" << endl;
    int rowSize = items.size();
    int colSize = bagSize + 1;
    vector<vector<int>> dp(rowSize, vector<int>(colSize, 0));
    for (int item = 0; item < rowSize; item++)
    {
        dp[item][0] = 0;
    }
    for (int weight = 0; weight < colSize; weight++)
    {
        dp[0][weight] = 0;
    }

    for (int item = 1; item < rowSize; item++)
    {
        for (int weight = 1; weight < colSize; weight++)
        {
            // can't take
            if (items[item].second > weight)
            {
                dp[item][weight] = dp[item - 1][weight];
            }
            else
            {
                dp[item][weight] = max(dp[item - 1][weight], dp[item - 1][weight - items[item].second] + items[item].first);
            }
        }
    }

    for (int i = 0; i < rowSize; i++)
    {
        for (int j = 0; j < colSize; j++)
        {
            cout << dp[i][j] << "\t";
        }
        cout << endl;
    }
    return -1;
}

int main()
{
    int bagSize = 0;
    //vector<pair<int, int>> items = readFromFile("testcase_web.txt", bagSize);
    //vector<pair<int, int>> items = readFromFile("testcase_lecture.txt", bagSize);
    vector<pair<int, int>> items = readFromFile("testcase_14.txt", bagSize);
    knapsack(items, bagSize);
}