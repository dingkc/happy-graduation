package com.bttc.HappyGraduation.scheduler.util;

/**
 * Created by jiajt on 2018/11/7.
 */
public class IDUtil {

    private IDUtil(){}

    // 位数，默认是3位
    private final static long W = 1000;

    public static Long createID(){
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(System.nanoTime()/1000L).append((long) ((Math.random() + 1) * W));
        return Long.valueOf(stringBuilder.toString());
    }
}
