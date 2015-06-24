package com.wonders.task.excel.util;

import org.apache.poi.ss.usermodel.DateUtil;

import java.util.Calendar;

/**
 * Created with IntelliJ IDEA.
 * User: zhoushun
 * Date: 2014/12/7
 * Time: 0:36
 * To change this template use File | Settings | File Templates.
 */
public class XSSFDateUtil extends DateUtil {
    protected static int absoluteDay(Calendar cal, boolean use1904windowing) {
        return DateUtil.absoluteDay(cal, use1904windowing);
    }
}