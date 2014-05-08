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

			// 判断是否找到最大值
			if (m >= ((N - 1) - i)) {
				break;
			}
			for (int j = N - 1; j > i; j--) {
				// 从后面查找数据a[j],判断是否大于a[i]
				if (a[j] > a[i]) {
					//int m_temp = j - i;
					if ((j - i) > m) {
						m = j - i;// 把相隔最大传给m
					}
					break;// 跳出查找
				}
				// 判断当前最大值
				if (m >= (j - i)) {
					break;// 跳出查找
				}
			}

	   }
	  cout << m<<endl;;
    return 0;
}