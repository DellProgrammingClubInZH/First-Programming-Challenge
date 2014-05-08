#include<iostream>
using namespace std;
void main()

{
	int a;
	cin>>a;
	int *b =new int[a];
	for (int i=0;i<a;i++)
	{
		cin>>b[i];
	}
	int l=0;//MAX j-i founded
	for (int i=0;i+l<a;i++)
	{
		for (int j=a-1;j-i>l;j--)
		{
			if(b[j]>b[i])
				{
					l=j-i;
					break;
				}
		}
	}
	cout<<l;
}
