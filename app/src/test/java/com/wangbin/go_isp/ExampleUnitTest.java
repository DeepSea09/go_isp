package com.wangbin.go_isp;

import android.util.Log;

import com.alibaba.fastjson.JSON;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() {
        assertEquals(4, 2 + 2);
//

//        String aa = "[\"q111\",\"q222\",\"w111\",\"b111\",\"a111\",\"qqqqqq\",\"wwwwww\",\"eeeeee\"]";
//
//        String bb = aa.replace("\"", "");
//      //  String s = Arrays.asList(aa).get(0);
//       // List<String> demoList = Arrays.asList(s);
//       // List<String> users2 = JSON.parseArray(s,String.class);
//        String demosub = bb.substring(1,bb.length()-1).replace("\"", "");
//        String demoArray[] = demosub.split(",");
//        List<String> demoList = Arrays.asList(demoArray);
//        for (String aaa : demoList){
//            System.out.println(aaa);
//        }
//        List<String> aaa = new ArrayList<>();
//        aaa.addAll(demoList);
//        for (String bb : aaa){
//            System.out.println(bb);
//        }
        List<String> firList = new ArrayList<String>();
//        firList.add("1");
//        firList.add("2");
//        firList.add("2");
//        firList.add("3");
//        firList.add("3");
//        firList.add("2");
//        firList.add("4");
//        List<String> twoList = new ArrayList<String>();
//        twoList.add("1");
//        twoList.add("2");
//        twoList.add("4");
//        twoList.removeAll(firList);
//        System.out.println(twoList.toString());
        Set set = new HashSet();
        List<String> listNew=new ArrayList<>();
        set.addAll(firList);
        listNew.addAll(set);
        System.out.println(listNew );
        System.out.println(firList );
    }

    private class User {
        private String name;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}