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

// Karatsuba algorithm for fast multiplication
// assume num1.size() == num2.size() and the size is always a power of 2
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

class Karatsuba
{
private:
    void reverseArr(vector<int> &num);

public:
    vector<int> multiply(vector<int>, vector<int>);
    vector<int> add(vector<int>, vector<int>);
    vector<int> sub(vector<int>, vector<int>);
};

void Karatsuba::reverseArr(vector<int> &num)
{
    for (int i = 0, j = num.size() - 1; i < j; i++, j--)
    {
        int temp = num[i];
        num[i] = num[j];
        num[j] = temp;
    }
}

vector<int> Karatsuba::multiply(vector<int> num1, vector<int> num2)
{

    // base case
    if (num1.size() == 1 && num2.size() == 1)
    {
        vector<int> r;
        int digit = num1[0] * num2[0];
        int carry = digit / 10;
        digit = digit % 10;
        if (carry == 0)
        {
            r.push_back(digit);
        }
        else
        {
            r.push_back(carry);
            r.push_back(digit);
        }
        return r;
    }

    // padding front zeros
    while (num2.size() < num1.size())
    {
        num2.insert(num2.begin(), 0);
    }
    while (num1.size() < num2.size())
    {
        num1.insert(num1.begin(), 0);
    }

    // now sizes are the same;
    // get the n, n has to even numbers
    int n = num1.size();
    if (n % 2 == 1)
        n += 1;

    // half the numbers
    vector<int> firstHalfNum1;
    for (int i = 0; i < num1.size() / 2; i++)
    {
        firstHalfNum1.push_back(num1[i]);
    }
    vector<int> secondHalfNum1;
    for (int i = num1.size() / 2; i < num1.size(); i++)
    {
        secondHalfNum1.push_back(num1[i]);
    }
    vector<int> firstHalfNum2;
    for (int i = 0; i < num2.size() / 2; i++)
    {
        firstHalfNum2.push_back(num2[i]);
    }
    vector<int> secondHalfNum2;
    for (int i = num2.size() / 2; i < num2.size(); i++)
    {
        secondHalfNum2.push_back(num2[i]);
    }

    // recusion
    vector<int> step1 = multiply(firstHalfNum1, firstHalfNum2);
    vector<int> step2 = multiply(secondHalfNum1, secondHalfNum2);
    vector<int> aAndb = add(firstHalfNum1, secondHalfNum1);
    vector<int> cAndd = add(firstHalfNum2, secondHalfNum2);
    vector<int> step3 = multiply(aAndb, cAndd);
    vector<int> temp = sub(step3, step2);
    vector<int> step4 = sub(temp, step1);
    // step 5: final calculation
    // num1,num2 has a size of n/2
    // pad step 1 with number n of zeros
    // pad step 4 with number of n/2 of zeros
    // add step 1, step2 and step 4
    for (int i = 0; i < n; i++)
    {
        step1.push_back(0);
    }
    for (int i = 0; i < n / 2; i++)
    {
        step4.push_back(0);
    }
    vector<int> step5;
    step5 = add(step1, step2);
    step5 = add(step5, step4);
    // remove the leading zeros
    for (vector<int>::iterator it = step5.begin(); it < step5.end(); it++)
    {
        if (*it == 0)
        {
            step5.erase(it);
            it--;
        }
        else
        {
            break;
        }
    }
    return step5;
}

vector<int> Karatsuba::add(vector<int> num1, vector<int> num2)
{
    vector<int> r;
    int carry = 0;
    int digit = 0;
    int i = num1.size() - 1;
    int j = num2.size() - 1;
    while (i >= 0 && j >= 0)
    {
        digit = num1[i] + num2[j] + carry;
        carry = digit / 10;
        digit = digit % 10;
        r.push_back(digit);
        i--;
        j--;
    }
    // printArr(r);
    while (i >= 0)
    {
        digit = num1[i] + carry;
        carry = digit / 10;
        digit = digit % 10;
        r.push_back(digit);
        i--;
    }
    while (j >= 0)
    {
        digit = num2[j] + carry;
        carry = digit / 10;
        digit = digit % 10;
        r.push_back(digit);
        j--;
    }
    if (carry != 0)
        r.push_back(carry);

    reverseArr(r);

    return r;
}

vector<int> Karatsuba::sub(vector<int> num1, vector<int> num2)
{
    // num1 - num2
    vector<int> r;
    int carry = 0;
    int digit = 0;
    int i = 0;
    int j = 0;
    for (i = num2.size() - 1, j = num1.size() - 1; i >= 0; i--, j--)
    {
        int newNum = 0;
        if (num1[j] - carry < num2[i])
        {
            newNum = num1[j] + 10;
            digit = newNum - carry - num2[i];
            carry = 1;
        }
        else
        {
            newNum = num1[j];
            digit = newNum - carry - num2[i];
            carry = 0;
        }
        r.push_back(digit);
    }

    while (j >= 0)
    {
        int newNum = 0;
        if (num1[j] < carry)
        {
            newNum = num1[j] + 10;
            digit = newNum - carry;
            carry = 1;
        }
        else
        {
            newNum = num1[j];
            digit = newNum - carry;
            carry = 0;
        }

        r.push_back(digit);
        j--;
    }
    reverseArr(r);
    return r;
}

int main()
{
    // 9876539012346
    //vector<int> num1{9,9,9,9,9,9,9};
    //vector<int> num2{9,8,7,6,5,4};

    vector<int> num1{9, 8, 7, 6, 5, 4};
    vector<int> num2{9, 9, 9, 9, 9, 9, 9};
    //vector<int> num1{5, 6, 7, 8};
    //vector<int> num2{1, 2, 3, 4};
    //vector<int> num1{1, 3, 4};
    //vector<int> num2{4, 6};
    //vector<int> num1{3, 4};
    //vector<int> num2{4, 6};
    //vector<int> num1{3, 5};
    //vector<int> num2{4, 6};
    //vector<int> num1{7};
    //vector<int> num2{1,0};
    vector<int> res;
    Karatsuba k;
    res = k.multiply(num1, num2);
    printArr(res);
}