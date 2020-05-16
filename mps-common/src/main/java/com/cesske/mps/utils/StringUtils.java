package com.cesske.mps.utils;

import com.google.common.base.Charsets;
import com.google.common.base.Joiner;
import com.google.common.base.Preconditions;
import com.google.common.base.Strings;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 字符串工具类
 */
public final class StringUtils extends org.apache.commons.lang.StringUtils {

    private StringUtils() {
    }

    /**
     * 删除字符串两边的空格{@link #trim(String)} 并且检查是否为null或空{@link Strings#isNullOrEmpty(String)}
     *
     * @param string 待处理的字符串
     * @return 字符串 {@code string} 去掉左右两端的空格后的结果{@link #trim(String)}
     * @throws IllegalArgumentException 如果字符串为空 {@code null} 或者字符串为空字符串 {@code empty} 将抛出异常
     */
    public static String trimAndCheckEmpty(String string) {
        String newValue = StringUtils.trim(string);
        Preconditions.checkArgument(!Strings.isNullOrEmpty(newValue));
        return newValue;
    }

    /**
     * 删除字符串两边的空格
     *
     * @param str
     * @return 如果字符串微null则返回null, 否则返回str.trim。
     */
    public static String trim(String str) {
        return str == null ? null : str.trim();
    }

    /**
     * 判断字符串的长度{@code string.getBytes.length}是否在指定的范围内。
     *
     * @param string    待计算长度的字符串
     * @param minLength 字符串的最小长度
     * @param maxLength 字符串的最大长度
     * @return 如果字符串等于 {@code null},并且最小长度 {@code minLength} 小于 {@value 0},返回 true;
     * 如果字符串的长度大于等于最小长度 {@code minLength} 并且小于等于最大长度 {@code maxLength} 返回 {@code true},
     * 否则返回{@code false}.
     * @throws IllegalArgumentException 如果最大值{@code maxLength} 小于最小值{@code inLength} 将抛出异常
     */
    public static boolean checkStringLength(String string, int minLength, int maxLength) {
        Preconditions.checkArgument(minLength <= maxLength);
        if (minLength < 0 && string == null) {
            return true;
        } else {
            int utf8Length = string.getBytes(Charsets.UTF_8).length;
            return utf8Length >= minLength && utf8Length <= maxLength;
        }
    }

    public static String shortenStr(String str) {
        String strs = "";
        if (Strings.isNullOrEmpty(str)) {
            return strs;
        }
        if (str.length() > 64) {
            strs = Joiner.on("").join(str.subSequence(0, 64), "........");
        } else {
            return str;
        }
        return strs;
    }

    final public static String contactStrs(String... strs) {
        return contactStr(strs);
    }

    public static String contactStr(String[] saStr) {
        if (saStr == null || saStr.length <= 0) {
            return null;
        }

        StringBuilder sRet = new StringBuilder();
        for (int i = 0; i < saStr.length; i++) {
            sRet.append(saStr[i]);
        }
        return sRet.toString();
    }

    public static String phoneJiaXing(String phone) {
        if (Strings.isNullOrEmpty(phone)) {
            return null;
        }
        String ss = phone.substring(0, phone.length() - (phone.substring(3)).length()) + "****" + phone.substring(7);
        return ss;
    }

    public static String replaceBlank(String str) {
        String dest = "";
        if (str!=null) {
            Pattern p = Pattern.compile("\\s*|\t|\r|\n");
            Matcher m = p.matcher(str);
            dest = m.replaceAll("");
        }
        return dest;
    }

    //异常转为字符串
    public static String ExpToString(Throwable t) {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);

        try
        {
            t.printStackTrace(pw);
            return replaceBlank(sw.toString());
        }
        finally
        {
            pw.close();
        }
    }

    public static HashMap<Character, Integer> count(String str) {
        //将字符串转化为字符数组
        char[] chars = str.toCharArray();
        //创建一个HashMap名为hm
        HashMap<Character, Integer> hm = new HashMap<Character, Integer>();

        //定义一个字符串c，循环遍历遍历chars数组
        for (char c : chars) {
            hm.put(c, hm.containsKey(c) ? hm.get(c) + 1 : 1);
        }
        return hm;
    }

    public static void main(String[] args) {
        System.out.print(phoneJiaXing("18511982106"));
    }

}
