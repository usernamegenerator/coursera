#include <iostream>
#include <vector>
#include <string>
#include <fstream>
using namespace std;
/* // for index
      0
     / \
    1   2
   / \ / \
  3  4 5  6
*/
class minHeap
{
private:
    vector<int> arr;
    void swimUp(int idx);
    void swimDown(int idx);
    int parentIdx(int idx);
    int leftChildIdx(int idx);
    int rightChildIdx(int idx);

public:
    void insert(int x);
    int top();
    void pop();
    int size();
    void show();
    bool isEmpty();
};

class maxHeap
{
private:
    vector<int> arr;
    void swimUp(int idx);
    void swimDown(int idx);
    int parentIdx(int idx);
    int leftChildIdx(int idx);
    int rightChildIdx(int idx);

public:
    void insert(int x);
    int top();
    void pop();
    int size();
    void show();
    bool isEmpty();
};

int minHeap::parentIdx(int idx)
{
    return idx / 2;
}

int maxHeap::parentIdx(int idx)
{
    return idx / 2;
}

int minHeap::leftChildIdx(int idx)
{
    return idx * 2 + 1;
}

int maxHeap::leftChildIdx(int idx)
{
    return idx * 2 + 1;
}

int minHeap::rightChildIdx(int idx)
{
    return idx * 2 + 2;
}

int maxHeap::rightChildIdx(int idx)
{
    return idx * 2 + 2;
}

void minHeap::insert(int x)
{
    arr.push_back(x);
    swimUp(arr.size() - 1);
}

void maxHeap::insert(int x)
{
    arr.push_back(x);
    swimUp(arr.size() - 1);
}

int minHeap::size()
{
    return arr.size();
}
int maxHeap::size()
{
    return arr.size();
}

void minHeap::swimUp(int idx)
{
    if (idx == 0)
        return;
    int pIdx = parentIdx(idx);
    if (arr[idx] < arr[pIdx])
    {
        swap(arr[idx], arr[pIdx]);
        swimUp(pIdx);
    }
    else
    {
        return;
    }
}

void maxHeap::swimUp(int idx)
{
    if (idx == 0)
        return;
    int pIdx = parentIdx(idx);
    if (arr[idx] > arr[pIdx])
    {
        swap(arr[idx], arr[pIdx]);
        swimUp(pIdx);
    }
    else
    {
        return;
    }
}

void minHeap::swimDown(int idx)
{
    int left = leftChildIdx(idx);
    int right = rightChildIdx(idx);
    int min = idx;
    if (left < size() && arr[left] < arr[min])
    {
        min = left;
    }
    if (right < size() && arr[right] < arr[min])
    {
        min = right;
    }
    if (arr[min] != arr[idx])
    {
        swap(arr[min], arr[idx]);
        swimDown(min);
    }
}

void maxHeap::swimDown(int idx)
{
    int left = leftChildIdx(idx);
    int right = rightChildIdx(idx);
    int max = idx;
    if (left < size() && arr[left] > arr[max])
    {
        max = left;
    }
    if (right < size() && arr[right] > arr[max])
    {
        max = right;
    }
    if (arr[max] != arr[idx])
    {
        swap(arr[max], arr[idx]);
        swimDown(max);
    }
}

int minHeap::top()
{
    return arr[0];
}

int maxHeap::top()
{
    return arr[0];
}

void minHeap::pop()
{
    swap(arr[0], arr[arr.size() - 1]);
    arr.pop_back();
    swimDown(0);
}

void maxHeap::pop()
{
    swap(arr[0], arr[arr.size() - 1]);
    arr.pop_back();
    swimDown(0);
}

void minHeap::show()
{
    cout << "[";
    for (int i = 0; i < size(); i++)
    {
        cout << arr[i] << ",";
    }
    cout << "]";
    cout << endl;
}

void maxHeap::show()
{
    cout << "[";
    for (int i = 0; i < size(); i++)
    {
        cout << arr[i] << ",";
    }
    cout << "]";
    cout << endl;
}
bool minHeap::isEmpty()
{
    return (size() == 0);
}

bool maxHeap::isEmpty()
{
    return (size() == 0);
}

vector<int> readFromFile(string filename)
{
    fstream fin;
    fin.open(filename);
    vector<int> v;
    string line;
    while (getline(fin, line))
    {
        v.push_back(stoi(line));
    }
    fin.close();
    return v;
}

// heaphigh is the min heap, store all the elements that is greater than the median
// heaplow is the max heap, store all the elements that is less than the median
// we define the heaplow top stores the median if it's odd number of elements
// and heaplow's size will only be equal to (even number case) or one grater(odd number case) to heaphigh
double MedianMaintenance(minHeap &higher, maxHeap &lower, int x)
{
    // very first element, start with lower
    if (higher.isEmpty() && lower.isEmpty())
    {
        lower.insert(x);
        cout << "adding number " << x << endl;
        cout << "MaxHeap lower: ";
        lower.show();
        cout << "MinHeap higher: ";
        higher.show();
        cout << "Median is " << x << endl;
        cout << endl;
        return x;
    }

    int totalSize = higher.size() + lower.size();
    double curMedian = 0;
    // if there are total odd numbers
    // the median before inserting x is the max in lower
    if (totalSize % 2 == 1)
    {
        int preMedian = lower.top();
        // after putting in x, it becomes even number
        // need to check where to put x
        // if x is greater than preMedian
        // put it to higher

        // otherwise put it to lower
        // but since lower always stores equal or more element than higher
        // need to pop out lowertop and put it to higher
        if (x > preMedian)
        {
            higher.insert(x);
            // after this the two heaps are balanced
        }
        else
        {
            lower.insert(x);
            int temp = lower.top();
            lower.pop();
            higher.insert(temp);
        }

        // now it's balanced even number

        // note the homework requires the median to be (k+1)/2 th element, rather than the math calculation
        // curMedian = (lower.top() + higher.top()) / 2.0;
        curMedian = lower.top();
    }
    // if there are total even numbers
    // the median is the lowertop after insert (lowertop)
    else
    {
        // if x is less than the lowertop, meaning don't need to take x into account
        // to calculate the current median
        // so just insert it to the lower
        // now it's the odd number, the median is the lowertop
        if (x < lower.top())
        {
            lower.insert(x);
        }

        // if x is greater than the highertop, meaning don't need to take x into account
        // to calculate the current median
        // so just insert it to the higher
        // now it's the odd number, next time we see odd number we default to use lowertop
        // so the size of higher has to be less or equal to size of lower
        // we insert x to higher, and have to take away one element from higher and put it to lower
        else if (x > higher.top())
        {
            higher.insert(x);
            lower.insert(higher.top());
            higher.pop();
        }

        // if x is in between lowertop and highertop, meaning x is the median
        // insert x to lowertop, for next time the odd number useage;
        else if (x >= lower.top() && x <= higher.top())
        {
            // insert x to lowertop
            lower.insert(x);
        }
        curMedian = lower.top();
    }
    cout << "adding number " << x << endl;
    cout << "MaxHeap lower: ";
    lower.show();
    cout << "MinHeap higher: ";
    higher.show();
    cout << "Median is " << curMedian << endl;
    cout << endl;
    return curMedian;
}

int main()
{ /*
    minHeap minh;
    minh.insert(4);
    minh.insert(3);
    minh.insert(1);
    minh.pop();
    minh.pop();
    minh.pop();
    minh.insert(2);
    minh.insert(7);
    minh.insert(9);
    minh.insert(5);
    minh.insert(6);
    minh.insert(8);
    cout << minh.top() << endl;

    minh.pop();
    minh.pop();
    minh.pop();
    minh.pop();
    minh.pop();

    minh.show();

    maxHeap maxh;
    maxh.insert(4);
    maxh.insert(3);
    maxh.insert(1);
    maxh.insert(2);
    maxh.insert(7);
    maxh.insert(9);
    maxh.insert(5);
    maxh.insert(6);
    maxh.insert(8);
    cout << maxh.top() << endl;
    maxh.pop();
    maxh.pop();
    maxh.pop();
    maxh.pop();
    maxh.pop();
    maxh.pop();
    maxh.pop();
    maxh.pop();
    maxh.pop();
    maxh.pop();
    maxh.show();
*/
    minHeap heaphigh;
    maxHeap heaplow;
    //vector<int> inputArr = readFromFile("smallMedian1.txt");

    //vector<int> inputArr = readFromFile("smallMedian.txt");
    //vector<int> inputArr = readFromFile("Median.txt");
    //vector<int> inputArr = readFromFile("testcase1.txt");
    //vector<int> inputArr = readFromFile("testcase2.txt");
    vector<int> inputArr = readFromFile("testcase3.txt");

    vector<double> medianArr(inputArr.size());
    for (int i = 0; i < inputArr.size(); i++)
    {
        medianArr[i] = MedianMaintenance(heaphigh, heaplow, inputArr[i]);
    }

    for (int i = 0; i < medianArr.size(); i++)
    {
        //medianArr[i] = MedianMaintenance(heaphigh, heaplow, inputArr[i]);
        cout << medianArr[i] << endl;
    }

    long long sum = 0;
    for (int i = 0; i < medianArr.size(); i++)
    {
        sum += medianArr[i];
    }
    cout << "res " << sum % 10000 << endl;
}