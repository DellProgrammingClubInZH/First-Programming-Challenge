// Tricks.cpp : Defines the entry point for the console application.
//
//#define _CRT_SECURE_NO_WARNINGS


#include <stdio.h>
#include <stdlib.h>

int min(int a, int b){ return a > b ? b : a; }
int max(int a, int b){ return a > b ? a : b; }
int maxIndexDiff(int arr[], int n)
{
	int maxDiff;
	int i, j;

	int *LMin = (int *)malloc(sizeof(int)*n);
	int *RMax = (int *)malloc(sizeof(int)*n);

	/* Construct LMin[] such that LMin[i] stores the minimum value
	from (arr[0], arr[1], ... arr[i]) */
	LMin[0] = arr[0];
	for (i = 1; i < n; ++i)
		LMin[i] = min(arr[i], LMin[i - 1]);

	/* Construct RMax[] such that RMax[j] stores the maximum value
	from (arr[j], arr[j+1], ..arr[n-1]) */
	RMax[n - 1] = arr[n - 1];
	for (j = n - 2; j >= 0; --j)
		RMax[j] = max(arr[j], RMax[j + 1]);

	/* Traverse both arrays from left to right to find optimum j - i
	This process is similar to merge() of MergeSort */
	i = 0, j = 0, maxDiff = -1;
	while (j < n && i < n)
	{
		if (LMin[i] < RMax[j])
		{
			maxDiff = max(maxDiff, j - i);
			j = j + 1;
		}
		else
			i = i + 1;
	}

	return maxDiff;
}
int main()
{
	int n = -1;
	int *arr = 0;
	scanf("%d", &n);
	if (n < 2)
	{
		return -1;
	}
	arr = (int *)malloc(sizeof(int)*n);
	if (!arr)
	{
		return -1;
	}
	for (int i = 0; i < n; i++)
	{
		scanf("%d", &arr[i]);
	}
	int result = maxIndexDiff(arr, n);
	printf("%d", result);
	return 0;
}