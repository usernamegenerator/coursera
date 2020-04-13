#include <iostream>
#include <vector>
#include <string>
#include <fstream>
#include <sstream>
#include <algorithm>

using namespace std;

vector<pair<int, int>> readFromFile(string filename)
{
    vector<pair<int, int>> res;
    fstream fin;
    fin.open(filename);
    string line;
    getline(fin, line);
    while (getline(fin, line))
    {
        stringstream ss(line);
        string w;
        ss >> w;
        string l;
        ss >> l;
        res.push_back(make_pair(stoi(w), stoi(l)));
    }
    fin.close();
    return res;
}

int schedualwithMinus(vector<pair<int, int>> schedual)
{
    // pair<w-l,pair<w,l>>
    vector<pair<double, pair<int, int>>> schedual_with_priority;
    for (int i = 0; i < schedual.size(); i++)
    {
        schedual_with_priority.push_back(make_pair(schedual[i].first - schedual[i].second, make_pair(schedual[i].first, schedual[i].second)));
    }
    sort(schedual_with_priority.begin(), schedual_with_priority.end());
    reverse(schedual_with_priority.begin(), schedual_with_priority.end());

    unsigned long long totalC = 0;
    unsigned long long totalWeightedC = 0;
    for (int i = 0; i < schedual_with_priority.size(); i++)
    {
        int thisW = schedual_with_priority[i].second.first;
        int thisC = schedual_with_priority[i].second.second;
        totalC = totalC + thisC;
        totalWeightedC = totalWeightedC + thisW * totalC;
    }
    cout << totalWeightedC << endl;
    return totalWeightedC;
}

int schedualwithDivide(vector<pair<int, int>> schedual)
{
    // pair<w-l,pair<w,l>>
    vector<pair<double, pair<int, int>>> schedual_with_priority;
    for (int i = 0; i < schedual.size(); i++)
    {
        schedual_with_priority.push_back(make_pair((double)schedual[i].first / (double)schedual[i].second, make_pair(schedual[i].first, schedual[i].second)));
    }
    sort(schedual_with_priority.begin(), schedual_with_priority.end());
    reverse(schedual_with_priority.begin(), schedual_with_priority.end());

    unsigned long long totalC = 0;
    unsigned long long totalWeightedC = 0;
    for (int i = 0; i < schedual_with_priority.size(); i++)
    {
        int thisW = schedual_with_priority[i].second.first;
        int thisC = schedual_with_priority[i].second.second;
        totalC = totalC + thisC;
        totalWeightedC = totalWeightedC + thisW * totalC;
    }
    cout << totalWeightedC << endl;
    return totalWeightedC;
}

int main()
{
    // pair<w,l>
    vector<pair<int, int>> schedual = readFromFile("jobs.txt"); // 69119377652 and 67311454237
    // vector<pair<int, int>> schedual = readFromFile("jobstestcase1.txt");  // 31 and 29
    //vector<pair<int, int>> schedual = readFromFile("jobstestcase2.txt");  // 68615 and 67247
    schedualwithMinus(schedual);
    schedualwithDivide(schedual);

    return 0;
}