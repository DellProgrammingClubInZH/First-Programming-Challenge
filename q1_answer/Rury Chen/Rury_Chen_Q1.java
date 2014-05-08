import java.util.Scanner;
/**
 * 给定一个无序数组，求找出最大的j-i的值，使得j > i并且a[j] > a[i]。
 * 【输入】（stdin）
 * 第一行为一个整型数字n代表该数组的元素个数。
 * 2<=n<=100000.
 * 接下来有n行，每行代表该数组中的一个元素。
 * 【输出】（stdout）
 * 输出一个整型数字表示最大的j-i的值。
 *  @author rchen2
 *
 */
public class T1CalArrayij {
	public static void main(String[] args) {

		Scanner cin = new Scanner(System.in);
		int N = cin.nextInt();
		if(N<=2||N>=100000){
			System.err.println("Please input correct number (2<=n<=100000 ). Now n =" +N);
			return;
		}
		int A[] = new int[N];
		for (int i = 0; i < N; i++) {
			A[i] = cin.nextInt();
		}

		// 调用方法
		int result = new T1CalArrayij().calIJ(A, N);
		System.out.println(result);
	}

	public int calIJ(int A[], int N) {
		int m = -1;
		for (int i = 0; i < N; i++) {
			//判断是否找到最大值
			int m_max=(N-1)-i;
			if(m>=m_max){
				break;
			}
			for (int j = N - 1; j > i; j--) {
				// 从后面查找数据a[j],判断是否大于a[i]
				if (A[j] > A[i]) {
					int m_temp = j - i;
					if (m_temp > m) {
						m = m_temp;// 把相隔最大传给m
					}
					break;//跳出查找 
				}
			}

		}

		return m;
	}
}
