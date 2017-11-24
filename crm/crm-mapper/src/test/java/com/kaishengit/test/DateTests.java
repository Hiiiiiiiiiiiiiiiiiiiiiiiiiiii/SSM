package com.kaishengit.test;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class DateTests {
    public static void main(String [] args){
        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("YYYY年MM月dd日");
        String newdate = simpleDateFormat.format(date);
        System.out.println(newdate);
    }
}
