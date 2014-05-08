#include <iostream>
using namespace std;

/*
*	Structure Definition
*/
struct Element
{
	double value;
	int preSmallest;
};

/*
*	Main
*/
int main()
{
	int n = 0;
	int i = 0;

	// Prepare
	cin>>n;
	Element** array = new Element*[n];
	for(i=0; i<n; i++)
	{
		Element* newElement = new Element();
		cin>>newElement->value;
		newElement->preSmallest = -1;
		array[i] = newElement;
	}

	// Find the result
	int preSmallest = 0;
	array[0]->preSmallest = -1;
	for(i=1; i<n; i++)
	{
		// Find the smallest value.
		if( array[i]->value < array[preSmallest]->value ) 
		{
			array[i]->preSmallest = preSmallest;
			preSmallest = i;
		}
	}

	// preSmallest is pointing at the smallest value.
	int currentSmallest = preSmallest;
	int currentBiggest = n-1;
	int result = -1;
	
	while( currentSmallest != -1 && currentBiggest != -1 )
	{
		double bigValue = array[currentBiggest]->value;
		double smallValue = array[currentSmallest]->value;

		if( bigValue > smallValue )
		{
			int tmp = currentBiggest - currentSmallest;
			if( result < tmp ) result = tmp;
			
			// Get pre smallest value, it's getting bigger.
			currentSmallest = array[currentSmallest]->preSmallest;
		} else {
			// Find next biggest value, it's getting bigger.
			int current = currentBiggest - 1;
			while( current >= 0 )
			{
				if( array[current]->value > bigValue ) break;
				else current--;
			}
			currentBiggest = current;
		}
	}

	cout<<result<<endl;

	// Release memory
	for(i=0; i<n; i++) delete array[i];
	delete[] array;
	return 0;
}