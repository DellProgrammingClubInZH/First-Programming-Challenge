#include <iostream>
#include <algorithm>
#include <cstdio>
#include <time.h>
using namespace std;

int m=-1;
int N=100000;
long a[100000];
int main(){
	  scanf("%lld",&N);
	  for(int i=0;i<N;i++){
		  scanf("%lld",&a[i]);
	  }
	  
	  for (int i = 0; i < N; i++) {

			// �ж��Ƿ��ҵ����ֵ
			if (m >= ((N - 1) - i)) {
				break;
			}
			for (int j = N - 1; j > i; j--) {
				// �Ӻ����������a[j],�ж��Ƿ����a[i]
				if (a[j] > a[i]) {
					//int m_temp = j - i;
					if ((j - i) > m) {
						m = j - i;// �������󴫸�m
					}
					break;// ��������
				}
				// �жϵ�ǰ���ֵ
				if (m >= (j - i)) {
					break;// ��������
				}
			}

	   }
	  cout << m<<endl;;
    return 0;
}