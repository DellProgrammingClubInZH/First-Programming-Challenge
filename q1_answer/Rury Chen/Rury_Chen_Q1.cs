using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace CalArrayij_donet
{
    class CalArrayij_donet
    {
        static void Main(string[] args)
        {
            int N = Int32.Parse(Console.ReadLine());
            long[] A = new long[N];
            for (int i = 0; i < N; i++)
            {
                A[i] = Int64.Parse(Console.ReadLine());
            }
            int m = -1;
            for (int i = 0; i < N; i++)
            {

                // 判断是否找到最大值
                if (m >= ((N - 1) - i))
                {
                    break;
                }
                for (int j = N - 1; j > i; j--)
                {
                    // 从后面查找数据a[j],判断是否大于a[i]
                    if (A[j] > A[i])
                    {
                        if ((j - i) > m)
                        {
                            m = j - i;// 把相隔最大传给m
                        }
                        break;// 跳出查找
                    }
                    // 判断当前最大值
                    if (m >= (j - i))
                    {
                        break;// 跳出查找
                    }
                }

            }
            Console.WriteLine(m);


        }
    }
}
