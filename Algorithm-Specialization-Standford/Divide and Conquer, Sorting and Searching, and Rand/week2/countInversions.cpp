/*
.Question 1
Download the following text file:

IntegerArray.txt
This file contains all of the 100,000 integers between 1 and 100,000 (inclusive) in some order, with no integer repeated.

Your task is to compute the number of inversions in the file given, where the i th row of the file indicates the i th entry of an array.

Because of the large size of this array, you should implement the fast divide-and-conquer algorithm covered in the video lectures.

The numeric answer for the given input file should be typed in the space below.

So if your answer is 1198233847, then just type 1198233847 in the space provided without any space / commas / any other punctuation marks. You can make up to 5 attempts, and we'll use the best one for grading.

(We do not require you to submit your code, so feel free to use any programming language you want --- just type the final numeric answer in the following space.)

[TIP: before submitting, first test the correctness of your program on some small test files or your own devising. Then post your best test cases to the discussion forums to help your fellow students!]

Enter answer here

*/

/*
Sort-and-Count (array A, length n)
    if n=1,
        return 0 and the array
    else
        Divide A into half
        B,X = Sort-and-Count (1st half of A, n/2)
        C,Y = Sort-and-Count (2nd half of A, n/2)
        D,Z = CountSplitInv(A,n)

    return x+y+z and the sorted D

Where B is the sorted 1st half of A
C is the sorted 2nd half of A
D is the sorted A

We can use count without sort to do the first two recusion
But use the sort and count instead of count is to make CountSplitInv easier

CountSplitInv(A,n) is to look at B and C. When an element y of C is merged to D, 
the number of inversions are the numbers left in B
For example:
B                   C
3,7,10,14,18,19     2,11,16,17,23,25

1. compare 3 and 2. Merge 2. # of inversions is 6, which is number of elements left in B (3,7,10,14,18,19 are all inversions)
2. compare 3 and 11. Merge 3. No inversions
3. compare 7 and 11. Merge 7. No inversions
4. compare 10 and 11. Merge 10. No inversions
5. compare 14 and 11. Merge 11. # of inversions is 3, which is 14,18,19.
6. compare 14 and 16. Merge 14. No inversion
7. compare 18 and 16. Merge 16. # of inversions is 2, which is 18 and 19.
8. compare 18 and 17. Merge 17. # of inversions is 2, which is 18 and 19.
9. compare 18 and 23. Merge 18. No inversion
10. compare 19 and 23. Merge 19. No inversion
11. merge 23. No inversion
12. merge 25. No inversion
Total # of inversions = 6+3+2+2
The merged array is automatical sorted by the merge process

*/

#include <iostream>
#include <vector>
#include <fstream>
using namespace std;

unsigned long int countInvBrutalForce(vector<int> arr)
{
    unsigned long int cnt = 0;
    for (int i = 0; i < arr.size() - 1; i++)
    {
        for (int j = i + 1; j < arr.size(); j++)
        {
            if (arr[j] < arr[i])
            {
                cnt++;
            }
        }
    }
    return cnt;
}
unsigned long int mergeAndCountTheSplit(vector<int> &arr, int low, int mid, int high)
{
    // merge sort and count the inversions from array A[low:mid], and B[mid+1,high]
    int size = high - low + 1;
    vector<int> temp(size, 0);

    unsigned long int cnt = 0;

    int i = low;
    int j = mid + 1;
    int k = 0;
    while (i <= mid && j <= high)
    {
        // merge from A, no inversion
        if (arr[i] <= arr[j])
        {
            temp[k++] = arr[i++];
        }
        else // merge from B and count the inversion, which is the number of elements left in A
        {
            temp[k++] = arr[j++];
            cnt += (mid - i + 1); // i the 1st of the elements left in the array, mid is the last
        }
    }
    // nothing left in B
    while (i <= mid)
    {
        temp[k++] = arr[i++];
    }
    // nothing left in A
    while (j <= high)
    {
        temp[k++] = arr[j++];
    }

    // make sure to modify arr;
    int idy = low;
    for (int idx = 0; idx < size; idx++)
    {
        arr[idy++] = temp[idx];
    }

    return cnt;
}

unsigned long int sortAndCount(vector<int> &arr, int low, int high)
{
    if (low >= high)
        return 0;
    int mid = low + (high - low) / 2;
    unsigned long int x = sortAndCount(arr, low, mid);      // A is from low to mid
    unsigned long int y = sortAndCount(arr, mid + 1, high); // B is from mid+1 to high
    unsigned long int z = mergeAndCountTheSplit(arr, low, mid, high);
    return x + y + z;
}

vector<int> readFromFile()
{
    ifstream inFile;
    inFile.open("IntegerArray.txt");
    if(!inFile)
    {
        cerr << "unable to open file";
        exit(1);
    }

    vector<int> arr;
    int a;
    while(inFile >> a)
    {
        arr.push_back(a);
    }

    inFile.close();

    return arr;
}

int main()
{
    //vector<int> arr{1, 5, 4, 8, 10, 2, 6, 9, 12, 11, 3, 7}; // answer is 22
    // vector<int> arr{1, 20, 6, 4, 5}; // 5
    vector<int> arr = readFromFile();    
    
    cout << countInvBrutalForce(arr) << endl;
    // note arr will be modified to sorted after this function call
    cout << sortAndCount(arr, 0, arr.size() - 1) << endl;
    
    
}