package com.Cloud4;

/**
 * Created by Cloud4 on 10/22/2016.
 */

/* This class helps to debug
 * by showing the following:
 *     1) project
 *     2) class
 *     3) method name
 *     4) line number
 * Usage: MySte.location()
 */

public class MySte {
    public static StackTraceElement location() {
        StackTraceElement ste;
        ste = Thread.currentThread().getStackTrace()[2];
        return ste;
    }
}
