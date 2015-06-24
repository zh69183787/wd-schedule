package com.wonders.task.excel.util;

/**
 * Created with IntelliJ IDEA.
 * User: zhoushun
 * Date: 2014/12/7
 * Time: 3:13
 * To change this template use File | Settings | File Templates.
 */

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexTest {

    public static void printMatched(String regex, String source) {
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(source);
        while (m.find()) {
            for (int i = 0; i <= m.groupCount(); i++) {
                System.out.println(m.group(i));
            }
        }
    }

    public static void main(String[] arg) {
        RegexTest.printMatched("<(\\w+)>.*</(\\1)>",
                "<table><td>sdjfjfiweif</td></table><cd>sdfsdf</cd>");

    }
}