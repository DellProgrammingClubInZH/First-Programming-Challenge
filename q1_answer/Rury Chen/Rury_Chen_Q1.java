import java.util.Scanner;
/**
 * ����һ���������飬���ҳ�����j-i��ֵ��ʹ��j > i����a[j] > a[i]��
 * �����롿��stdin��
 * ��һ��Ϊһ����������n����������Ԫ�ظ�����
 * 2<=n<=100000.
 * ��������n�У�ÿ�д���������е�һ��Ԫ�ء�
 * ���������stdout��
 * ���һ���������ֱ�ʾ����j-i��ֵ��
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

		// ���÷���
		int result = new T1CalArrayij().calIJ(A, N);
		System.out.println(result);
	}

	public int calIJ(int A[], int N) {
		int m = -1;
		for (int i = 0; i < N; i++) {
			//�ж��Ƿ��ҵ����ֵ
			int m_max=(N-1)-i;
			if(m>=m_max){
				break;
			}
			for (int j = N - 1; j > i; j--) {
				// �Ӻ����������a[j],�ж��Ƿ����a[i]
				if (A[j] > A[i]) {
					int m_temp = j - i;
					if (m_temp > m) {
						m = m_temp;// �������󴫸�m
					}
					break;//�������� 
				}
			}

		}

		return m;
	}
}
