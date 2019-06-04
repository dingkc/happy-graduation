package com.bttc.HappyGraduation.scheduler.util;

import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * Created by jiajt on 2018/12/3.
 */
public class ExceptionUtil {

    private ExceptionUtil(){}

    public static String getStackTrace(Throwable throwable)
    {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);

        try
        {
            throwable.printStackTrace(pw);
            return sw.toString();
        } finally
        {
            pw.close();
        }
    }
}
