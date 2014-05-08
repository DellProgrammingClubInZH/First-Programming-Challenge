// Tricks.cpp : Defines the entry point for the console application.
//

#include "stdafx.h"

#include<iostream>
#include<vector>

using namespace std;


int _tmain(int argc, _TCHAR* argv[])
{
	int number, i, result = -1;
    vector<int> numArray;
    // input
    cin >> number;
    
    numArray.resize(number);
    for (i = 0; i < numArray.size(); i++)
        cin >> numArray[i];
        
    // output
    //for (i = 0; i < numArray.size(); i++)
    //    cout << numArray[i] << endl;
        
    int len, j;
    for ( len = number; len >= 1 && -1 == result; --len)
    {
        for ( i = 0; ; ++i)
        {
            j = i + len;
            if ( j >= number )
               break;
            if ( numArray[j] > numArray[i] )
            {
               result = len;
               break;
            }
        }           
    }
    
    cout << result ;
    //system("pause");

	return 0;
}

