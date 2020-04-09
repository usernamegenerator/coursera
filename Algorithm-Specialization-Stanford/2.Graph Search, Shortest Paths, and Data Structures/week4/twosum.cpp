#include <iostream>
#include <fstream>
#include <string>
#include <vector>
#include <unordered_map>

using namespace std;

vector<long long> readFiletoArray(string filename)
{
    fstream fin;
    fin.open(filename);
    int numOfLines = 0;
    string line;
    vector<long long> input;
    while (getline(fin, line))
    {
        numOfLines++;
        input.push_back(stoll(line));
    }
    fin.close();
    return input;
}

unordered_map<long long, int> convertArrToMap(vector<long long> arr)
{
    unordered_map<long long, int> m;
    for (int i = 0; i < arr.size(); i++)
    {
        if (m.find(arr[i]) == m.end())
        {
            m.insert(pair<long long, int>(arr[i], 1));
        }
        else
        {
            m[arr[i]]++;
        }
    }
    return m;
}


// don't understand what the problem description is
long long numOfSums(vector<long long> arr, int lower, int upper)
{
    unordered_map<long long, int> m = convertArrToMap(arr);
    long long numOfTargetValues = 0;
    for (int target = lower; target <= upper; target++)
    {
        cout << target << ": ";
        //for (int i = 0; i < arr.size(); i++)
        for (unordered_map<long long, int>::iterator it = m.begin(); it != m.end(); it++)
        {
            int value = it->first;
            long long toFind = target - value;
            if (toFind == value)
                continue;
            if (m.find(toFind) != m.end())
            {
                cout << "<" << value << "," << toFind << ">; ";
                numOfTargetValues++;
            }
        }
        cout << numOfTargetValues << endl;
    }
    return numOfTargetValues;
}

int main()
{
    //vector<long long> arr = readFiletoArray("algo1-programming_prob-2sum.txt");
    vector<long long> arr = readFiletoArray("testcase1.txt");
    cout << numOfSums(arr, 3, 10) << endl;
}