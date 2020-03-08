#include <iostream>
#include <vector>
#include <ctime>
using namespace std;

class sort
{
private:
    void merge(vector<int> &, int, int, int);
    void mergesort(vector<int> &, int, int);
    void printArray(vector<int>);
    int partition(vector<int> &, int, int);
    void quicksort(vector<int> &, int, int);
    void randomize(vector<int> &);

public:
    sort();
    ~sort();
    void mergeSort(vector<int> &);
    void insertionSort(vector<int> &);
    void quickSort(vector<int> &);
};
sort::sort()
{
}
sort::~sort()
{
}

void sort::randomize(vector<int> &arr)
{
    for (int i = 0; i < arr.size(); i++)
    {
        srand(time(NULL));
        int j = rand() % arr.size();
        swap(arr[i], arr[j]);
    }
}
void sort::printArray(vector<int> arr)
{
    for (int i = 0; i < arr.size(); i++)
    {
        cout << arr[i] << " ";
    }
    cout << endl;
}

void sort::mergeSort(vector<int> &nums)
{
    mergesort(nums, 0, nums.size() - 1);
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
//merge two arrays: nums[low,mid], nums[mid+1,high]
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

void sort::insertionSort(vector<int> &nums)
{
    for (int i = 1; i < nums.size(); i++)
    {
        int key = nums[i];
        int j = i - 1;
        while (j >= 0 && nums[j] > key)
        {
            nums[j + 1] = nums[j];
            j--;
        }
        nums[j + 1] = key;
    }
}

void sort::quickSort(vector<int> &nums)
{
    randomize(nums);
    quicksort(nums, 0, nums.size() - 1);
}

void sort::quicksort(vector<int> &nums, int low, int high)
{
    if (low >= high)
        return;
    int partit = partition(nums, low, high);
    quicksort(nums, low, partit - 1);
    quicksort(nums, partit + 1, high);
}
int sort::partition(vector<int> &nums, int low, int high)
{
    int pivot = nums[high];
    int i = low - 1;
    for (int j = low; j <= high - 1; j++)
    {
        if (nums[j] < pivot)
        {
            i++;
            swap(nums[i], nums[j]);
        }
    }
    swap(nums[i + 1], nums[high]);
    return i + 1;
}

int main()
{
    sort s;
    // vector<int> arr{38, 27, 43, 3, 9, 82, 10};
    vector<int> arr{38, 27, 43, 10, 9, 82, 10};
    //vector<int> arr{6, 7, 3, 5, 4, 8, 9};
    // vector<int> arr{};
    // vector<int> arr{1};
    //s.mergeSort(arr);
    //s.insertionSort(arr);
    s.quickSort(arr);
    for (int i = 0; i < arr.size(); i++)
    {
        cout << arr[i] << " ";
    }
    cout << endl;
}