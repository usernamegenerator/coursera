/*
1.Question 1
In this programming assignment you will implement one or more of the integer multiplication algorithms described in lecture.

To get the most out of this assignment, your program should restrict itself to multiplying only pairs of single-digit numbers. You can implement the grade-school algorithm if you want, but to get the most out of the assignment you'll want to implement recursive integer multiplication and/or Karatsuba's algorithm.

So: what's the product of the following two 64-digit numbers?

3141592653589793238462643383279502884197169399375105820974944592

2718281828459045235360287471352662497757247093699959574966967627

[TIP: before submitting, first test the correctness of your program on some small test cases of your own devising. Then post your best test cases to the discussion forums to help your fellow students!]

[Food for thought: the number of digits in each input number is a power of 2. Does this make your life easier? Does it depend on which algorithm you're implementing?]

The numeric answer should be typed in the space below. So if your answer is 1198233847, then just type 1198233847 in the space provided without any space / commas / any other punctuation marks.

(We do not require you to submit your code, so feel free to use any programming language you want --- just type the final numeric answer in the following space.)
*/

#include <iostream>
#include <vector>
#include <string>
using namespace std;

void printArr(vector<int> arr)
{
    for (int i = 0; i < arr.size(); i++)
    {
        cout << arr[i];
    }
    cout << endl;
}

/*
    5678    // num1
*   1234    // num2
    -----
 00022712    // row
 00170340
 01135600
 05678000
 ---------
 07006652  // res
*/
class gradeSchoolMultiply
{
private:
    string s1;
    string s2;
    vector<int> num1;
    vector<int> num2;
    int size1;
    int size2;
    int resSize;
    int rowSize;
    vector<int> res;
    vector<int> singleDigitMul(int x, int endZeros);
    void addingRowToRes(vector<int> row);

public:
    gradeSchoolMultiply(string s1, string s2);
    vector<int> gradeSchool();
};

gradeSchoolMultiply::gradeSchoolMultiply(string s1, string s2)
{
    this->s1 = s1;
    this->s1 = s2;

    size1 = s1.size();
    size2 = s2.size();

    for (int i = 0; i < size1; i++)
    {
        num1.push_back(s1[i] - '0');
    }
    for (int i = 0; i < size2; i++)
    {
        num2.push_back(s2[i] - '0');
    }

    resSize = num1.size() + num2.size();
    rowSize = resSize;

    res = vector<int>(resSize, 0);
}

vector<int> gradeSchoolMultiply::singleDigitMul(int x, int endZeros)
{
    vector<int> row(rowSize, 0);
    //cout << endZeros << rowSize << endl;
    int k = rowSize - 1 - endZeros; // for vector row;
    int digit = 0;
    int carry = 0;
    for (int j = size1 - 1; j >= 0; j--) // for num1, 5678
    {
        digit = num1[j] * x;
        digit += carry;
        carry = digit / 10;
        digit = digit % 10;
        row[k] = digit;
        k--;
    }
    if (carry != 0)
    {
        row[k] += carry;
    }
    return row;
}

void gradeSchoolMultiply::addingRowToRes(vector<int> row)
{
    int carry = 0;
    int digit = 0;
    int i = row.size() - 1;
    for (; i >= 0; i--)
    {
        digit = row[i] + res[i];
        digit += carry;
        carry = digit / 10;
        digit = digit % 10;
        res[i] = digit;
    }
    if (carry != 0)
    {
        res[i] += carry;
    }
}

vector<int> gradeSchoolMultiply::gradeSchool()
{
    vector<int> row(rowSize, 0); // to store temp results on each row

    // use a single digit to multiply the other number
    // take the res of the row and added the res
    for (int i = size2 - 1; i >= 0; i--) // for num2, 1234
    {
        row = singleDigitMul(num2[i], size2 - 1 - i);
        addingRowToRes(row);
    }
    return res;
}

int main()
{
    string s1 = "3141592653589793238462643383279502884197169399375105820974944592"; string s2 = "2718281828459045235360287471352662497757247093699959574966967627";
    // 08539734222673567065463550869546574495034888535765114961879601127067743044893204848617875072216249073013374895871952806582723184
    
    //string s1 = "987654"; string s2 = "9999999"; // 9876539012346

    vector<int> res;
    gradeSchoolMultiply gsm(s1, s2);
    res = gsm.gradeSchool();
    printArr(res);

    return 0;
}