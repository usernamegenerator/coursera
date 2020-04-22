#include <iostream>
#include <fstream>
#include <vector>
#include <string>
#include <queue>
#include <unordered_map>

using namespace std;

typedef struct Node
{
    Node *left;
    Node *right;
    int symbolIndex;
    int weight;
} Node;

// pair<weight,symbol>
vector<pair<int, int>> readFromFile(string filename)
{
    fstream fin;
    fin.open(filename);
    string line;

    getline(fin, line);
    int count = 1;
    vector<pair<int, int>> v;
    while (getline(fin, line))
    {
        v.push_back(make_pair(stoi(line), count));
        count++;
    }
    return v;
}

// Node version of greater
struct comp
{
    bool operator()(Node *l, Node *r)
    {
        return l->weight > r->weight;
    }
};

Node *buildHuffmanTree(vector<pair<int, int>> v)
{

    priority_queue<Node *, vector<Node *>, comp> pq;
    for (int i = 0; i < v.size(); i++)
    {
        Node *newNode = new Node();
        newNode->left = NULL;
        newNode->right = NULL;
        newNode->weight = v[i].first;
        newNode->symbolIndex = v[i].second;
        pq.push(newNode);
    }

    // make sure at least two nodes left
    while (pq.size() > 1)
    {
        Node *newNode = new Node();
        Node *l = pq.top();
        pq.pop();
        Node *r = pq.top();
        pq.pop();
        newNode->left = l;
        newNode->right = r;
        newNode->symbolIndex = -1;
        newNode->weight = l->weight + r->weight;
        pq.push(newNode);
    }
    Node *root = pq.top();
    pq.pop();
    return root;
}

int getMax(Node *root)
{
    if (root == NULL)
    {
        return 0;
    }
    return (max(getMax(root->left), getMax(root->right)) + 1);
}

int getMin(Node *root)
{
    if (root == NULL)
    {
        return 0;
    }
    return (min(getMin(root->left), getMin(root->right)) + 1);
}
/*
//<symbol,coding>
void encode(Node *root, unordered_map<int, string> code, string str)
{
    if (root == NULL)
        return;

    if (!root->left && !root->right)
    {
        code[root->symbolIndex] = str;
    }
    encode(root->left, code, str + "0");
    encode(root->right, code, str + "1");
}
*/

int main()
{
    vector<pair<int, int>> v;
    //v = readFromFile("huffmanTestCase1_min2_max5.txt");
    v = readFromFile("huffmanTestCase1_min3_max6.txt");
    Node *root = buildHuffmanTree(v);
    // getMax and getMin return the number of nodes
    // the problem actually asks the number of connections
    // so need to minus 1
    int max = getMax(root) - 1;
    cout << "max " << max << endl;
    int min = getMin(root) - 1;
    cout << "min " << min << endl;
    //unordered_map<int, string> code;
}