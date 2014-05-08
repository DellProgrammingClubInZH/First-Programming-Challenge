/*
* Test.java
*
* Copyright 2013 Dell Inc.
* ALL RIGHTS RESERVED.
*
* This software is the confidential and proprietary information of
* Dell Inc. ("Confidential Information").  You shall not
* disclose such Confidential Information and shall use it only in
* accordance with the terms of the license agreement you entered
* into with Dell Inc.
*
* DELL INC. MAKES NO REPRESENTATIONS OR WARRANTIES
* ABOUT THE SUITABILITY OF THE SOFTWARE, EITHER EXPRESS
* OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE IMPLIED
* WARRANTIES OF MERCHANTABILITY, FITNESS FOR A
* PARTICULAR PURPOSE, OR NON-INFRINGEMENT. DELL SHALL
* NOT BE LIABLE FOR ANY DAMAGES SUFFERED BY LICENSEE
* AS A RESULT OF USING, MODIFYING OR DISTRIBUTING
* THIS SOFTWARE OR ITS DERIVATIVES.
*/

package com.quest.monitoring.snmp.agent;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Random;

/**
 *
 */
public class Test {
//    static Random random = new Random();

    public static void main(String[] args) {
//        long begin = System.currentTimeMillis();
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        try {
            //get length
            int length = Integer.parseInt(br.readLine());
            int[] numbers = new int[length];

            //init array
            for (int i = 0; i < length; i++) {
                numbers[i] = Integer.parseInt(br.readLine());
            }

            //compare
            for (int offset = 0; offset < length - 1; offset++) {
                if (compareByOffset(numbers, length, offset)) {
                    System.out.println(length - 1 - offset);
//                    System.out.println("time:" + (System.currentTimeMillis() - begin));
                    return;
                }
            }

            //default output
            System.out.println(-1);

//            System.out.println("time:" + (System.currentTimeMillis() - begin));
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } finally {
            try {
                br.close();
            } catch (IOException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }
        }
    }

    //compare
    private static boolean compareByOffset(int[] numbers, int length, int offset) {
        for (int i = 0; i <= offset; i++) {
            if (numbers[i] < numbers[length - 1 - offset + i])
                return true;
        }
        return false;
    }
}
