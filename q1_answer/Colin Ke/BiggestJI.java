package cn.cke.ex.ProgrammingClub;

import java.util.Random;
import java.util.Scanner;

/**
 * User: cke
 * Date: 4/27/14
 * Time: 10:17 AM
 */
public class BiggestJI {

    private int[] a;
    private int n;
    private int max = -1, maxi, maxj;

    public void findBiggest() {
        max = -1;
        for (int i = 0; i < n; ++i) {

            if(max!=-1 && a[i] >= a[maxi])
                continue;

            for (int j = n - 1; j >= i; --j) {
                if (a[j] > a[i]) {
                    if (j - i > max) {
                        max = j - i;
                        maxi = i;
                        maxj = j;
                    }
                    break;
                }
            }
        }
    }

    public void initData() {
        Scanner scr = new Scanner(System.in);
        n = scr.nextInt();
        a = new int[n];
        for (int i = 0; i < n; ++i)
            a[i] = scr.nextInt();
        scr.close();
    }

    public void outputResult() {
//        if (max != -1) {
//            System.out.print("i=" + maxi + " j=" + maxj + " max=");
            System.out.println(max);
//        }
//        System.out.println(a[maxj]);
    }

    private void testData(){
        n = 1000000;
        a = new int[n];
        Random random = new Random();
        for(int i=0;i<n;++i){
            a[i] = random.nextInt(1000000);
            //a[i] = n-i;
        }
    }


    public static void main(String[] args) {
        BiggestJI obj = new BiggestJI();
        long start, end;
        obj.initData();
//        obj.testData();
        start = System.currentTimeMillis();
        obj.findBiggest();
        end = System.currentTimeMillis();
        obj.outputResult();
//        System.out.println(end-start);

    }
}
