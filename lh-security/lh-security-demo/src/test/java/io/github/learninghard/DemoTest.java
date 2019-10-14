package io.github.learninghard;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * \* Created with IntelliJ IDEA.
 * \* User: Learning-Hard
 * \* Date: 2019-09-23
 * \* Time: 15:59
 * \* To change this template use File | Settings | File Templates.
 * \* Description:
 * \
 */

@RunWith(JUnit4.class)
public class DemoTest {

    public double findMedianSortedArrays(int[] nums1, int[] nums2) {
        ArrayList<Integer> arrayList = new ArrayList();
        for(int i : nums1){
            arrayList.add(i);
        }
        for(int j : nums2){
            arrayList.add(j);
        }
        arrayList.sort(new Comparator() {
            @Override
            public int compare(Object o1, Object o2) {
                return (int) o1 - (int) o2;
            }
        });
        if(arrayList.size()%2==0){
            int index = arrayList.size()/2;
            int i = arrayList.get(index) + arrayList.get(index - 1);
            return (double) i/2;
        }
        else{
            return arrayList.get(arrayList.size()/2);
        }
    }

    public String longestPalindrome(String s) {
        //转数组
        char[] chars = s.toCharArray();
        int index = 0;
        //[b,a,c,a,b]
        //b,a,b
        HashMap<Character,Integer> hashMap = new HashMap<>();
        HashMap<Integer,Integer> kv = new HashMap<>();
        for (char c : chars){
            if(hashMap.containsValue(c)){
                kv.put(hashMap.get(c), index);
                //key<value
            }
            hashMap.put(c,index);
            index++;
        }
        ArrayList<Integer> arrayList = new ArrayList<>(kv.keySet());

        //从小到大,
        arrayList.sort(new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                return o1 - o2;
            }
        });
        HashMap<Integer,Integer> max = new HashMap<>();
        //[1,9][2,8][3,7],[4,6][5]
        for(Integer i : arrayList){
            Integer result = (kv.get(i) - i - 1) / 2;
            if(result>1){
                boolean flag = true;
                while(result > 0){
                    if (!kv.get(i + result).equals(kv.get(i) - result)){
                        flag = false;
                        break;
                    }
                    result --;
                }
                if(flag){
                    max.put(i,kv.get(i));
                }
            }else{
                max.put(i,kv.get(i));
            }
        }

        for(Map.Entry<Integer,Integer> entry : max.entrySet()){
        }


        return null;
    }

    @Test
    public void test1(){
//        longestPalindrome("12345678");
        ArrayList<Integer> arrayList = new ArrayList<>();
        arrayList.add(1);
        arrayList.add(5);
        arrayList.add(3);
        arrayList.add(2);
        arrayList.add(4);
        arrayList.add(9);
        arrayList.add(7);
        arrayList.add(5);
        arrayList.sort(new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                return o1-o2;
            }
        });

        System.out.println(1/2);
        System.out.println(arrayList);
    }

    @Test
    public void test2() throws ParseException {
        char s ='\u0639';
        Date now = new Date();


        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date data1= simpleDateFormat.parse("2019-09-26 17:24:20");
        Date data2= simpleDateFormat.parse("2019-09-26 19:34:58");
        System.out.println(data2.getTime());
        //1569497698000
        //1569497696052



    }


    @Test
    public void test3() {
        int split = split(12);
        System.out.println("返回值为" + split);
    }
    public static int split(int number) {
        if (number > 1) {
            if (number % 2 != 0) {
                int temp = (number + 1) / 2;
//                System.out.println("split(" + temp + ")");
                System.out.println(split(temp));
            }
            int temp = number / 2;
//            System.out.println("split(" + temp + ")");
            System.out.println(split(number / 2));
        }
        return number;
    }


}
