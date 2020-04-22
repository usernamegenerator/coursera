#include <vector>
#include <iostream>
#include <string>
#include <fstream>
#include <algorithm>

using namespace std;

vector<int> readFromFile(string filename)
{
    vector<int> v;
    fstream fin;
    fin.open(filename);
    string line;
    getline(fin, line);
    while (getline(fin, line))
    {
        v.push_back(stoi(line));
    }
    return v;
}

vector<int> getMax(vector<int> v)
{
    vector<int> dp(v.size());
    dp[0] = v[0];
    dp[1] = max(v[0], v[1]);

    for (int i = 2; i < dp.size(); i++)
    {
        dp[i] = max(dp[i - 1], dp[i - 2] + v[i]);
    }
    return dp;
}

vector<int> reconstruct(vector<int> dp, vector<int> v)
{
    vector<int> picked;
    int i = dp.size() - 1;
    while (i >= 0)
    {
        // didn't take i
        if (dp[i - 1] > dp[i - 2] + v[i])
        {
            i = i - 1;
        }
        else // took i, step back 2, because we can't take i-1
        {
            picked.push_back(i);
            i = i - 2;
        }
    }
    return picked;
}

int main()
{
    //Max sum: 2616
    //Chosen points (position): [2, 4, 6, 8, 10]
    vector<int> v = readFromFile("dptest1.txt");
    //test case
    //https://www.coursera.org/learn/algorithms-greedy/discussions/weeks/3/threads/OjYyJd58Eea2mQ7cbimPbg
    vector<int> dp = getMax(v);
    cout << "dp ";
    for (int i = 0; i < dp.size(); i++)
    {
        cout << dp[i] << ",";
    }
    cout << endl;
    vector<int> picked = reconstruct(dp,v);
    for (int i = 0; i < picked.size(); i++)
    {
        cout << picked[i] << ",";
    }
    cout << "max " <<  dp[dp.size()-1] << endl;
}
