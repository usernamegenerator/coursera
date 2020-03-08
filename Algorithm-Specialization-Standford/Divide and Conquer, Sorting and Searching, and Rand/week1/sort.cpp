#include <iostream>
#include <vector>
using namespace std;

class sort
{
private:
    void merge(vector<int> &, int, int, int);
    void printArray(vector<int>);

public:
    sort();
    ~sort();
    void mergesort(vector<int> &, int, int);
};
sort::sort()
{
}
sort::~sort()
{
}
void sort::printArray(vector<int> arr)
{
    for (int i = 0; i < arr.size(); i++)
    {
        cout << arr[i] << " ";
    }
    cout << endl;
}

void sort::mergesort(vector<int> &nums, int low, int high)
{
    if (low >= high)
        return;

    int mid = low + (high - low) / 2;
    // cout << "low " << low << " mid " << mid << " high " << high << endl;
    mergesort(nums, low, mid);
    mergesort(nums, mid + 1, high);
    merge(nums, low, mid, high);
}
//merge two arrays: nums[low,mid-1], nums[mid,high]
void sort::merge(vector<int> &nums, int low, int mid, int high)
{
    vector<int> left;
    vector<int> right;
    for (int i = low; i <= mid; i++)
    {
        left.push_back(nums[i]);
    }
    for (int i = mid + 1; i <= high; i++)
    {
        right.push_back(nums[i]);
    }
    int i = 0;
    int j = 0;
    int k = low;
    while (i < left.size() && j < right.size())
    {
        if (left[i] < right[j])
        {
            nums[k++] = left[i++];
        }
        else
        {
            nums[k++] = right[j++];
        }
    }
    while (i < left.size())
    {
        nums[k++] = left[i++];
    }
    while (j < right.size())
    {
        nums[k++] = right[j++];
    }
}

int main()
{
    sort s;
    // vector<int> arr{38, 27, 43, 3, 9, 82, 10};
    vector<int> arr{38, 27, 43, 10, 9, 82, 10};
    // vector<int> arr{};
    // vector<int> arr{1};
    s.mergesort(arr, 0, arr.size() - 1);
    for (int i = 0; i < arr.size(); i++)
    {
        cout << arr[i] << " ";
    }
    cout << endl;
}