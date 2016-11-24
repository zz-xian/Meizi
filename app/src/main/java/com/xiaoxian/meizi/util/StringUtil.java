package com.xiaoxian.meizi.util;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 字符串工具类
 * Created by Administrator on 2016/11/4.
 */

public class StringUtil {
    private StringUtil() {
        throw new AssertionError();
    }

    /**
     * @param str
     * @return (null/size = 0/made by space)为true, 反之false
     */
    public static boolean isBlank(String str) {
        return (str == null || str.trim().length() == 0);
    }

    /**
     * @param chars
     * @return (null/size = 0)为true， 反之false
     */
    public static boolean isEmpty(CharSequence chars) {
        return (chars == null || chars.length() == 0);
    }

    /**
     * @param chars
     * @return 0 or chars.length()
     */
    public static int length(CharSequence chars) {
        return chars == null ? 0 : chars.length();
    }

    /**
     * @param str
     * @return String
     */
    public static String nullStrToEmpty(Object str) {
        return (str == null ? "" : (str instanceof String ? (String) str : str.toString()));
    }

    /**
     * @param str
     * @return String
     */
    public static String capitalizeFirstLetter(String str) {
        if (isEmpty(str)) {
            return str;
        }
        char c = str.charAt(0);
        return (!Character.isLetter(c) || Character.isUpperCase(c)) ? str : new StringBuilder(str.length())
                .append(Character.toUpperCase(c))
                .append(str.substring(1))
                .toString();
    }

    /**
     * @param str
     * @return 返回一个utf8字符串
     */
    public static String utf8Encode(String str) {
        if (!isEmpty(str) && str.getBytes().length != str.length()) {
            try {
                return URLEncoder.encode(str, "UTF-8");
            } catch (UnsupportedEncodingException e) {
                throw new RuntimeException("UnsupportedEncodingException occurred.", e);
            }
        }
        return str;
    }

    /**
     * @param href
     * @return 返回一个html
     */
    public static String getHrefInnerHtml(String href) {
        if (isEmpty(href)) {
            return "";
        }
        String hrefReg = ".*<[\\s]*a[\\s]*.*>(.+?)<[\\s]*/a[\\s]*>.*";
        Pattern hrefPattern = Pattern.compile(hrefReg,Pattern.CASE_INSENSITIVE);
        Matcher hrefMatcher = hrefPattern.matcher(href);
        if (hrefMatcher.matches()) {
            return hrefMatcher.group(1);
        }
        return href;
    }

    /**
     * @param source
     * @return 返回htmL到字符串
     */
    public static String htmlEscapeCharsToString(String source) {
        return StringUtil.isEmpty(source) ? source : source
                .replaceAll("&lt;", "<")
                .replaceAll("&gt;", ">")
                .replaceAll("&amp;", "&")
                .replaceAll("&quot;", "\"");
    }

    /**
     * @param s
     * @return String
     */
    public static String fullWidthToHalfWidth(String s) {
        if (isEmpty(s)) {
            return s;
        }
        char[] source = s.toCharArray();
        for (int i = 0; i < source.length; i++) {
            if (source[i] == 12288) {
                source[i] = ' ';
            } else if (source[i] >= 65281 && source[i] <= 65374) {
                source[i] = (char) (source[i] - 65248);
            } else {
                source[i] = source[i];
            }
        }
        return new String(source);
    }

    /**
     * @param s
     * @return 返回数值
     */
    public static String halfWidthToFullWidth(String s) {
        if (isEmpty(s)) {
            return s;
        }
        char[] source = s.toCharArray();
        for (int i = 0; i < source.length; i++) {
            if (source[i] == ' ') {
                source[i] = (char) 12288;
            } else if (source[i] >= 33 && source[i] <= 126) {
                source[i] = (char) (source[i] + 65248);
            } else {
                source[i] = source[i];
            }
        }
        return new String(source);
    }

    /**
     * @param str
     * @return 特殊字符串切换
     */
    public static String replaceBlanktihuan(String str) {
        String dest = "";
        if (str != null) {
            Pattern p = Pattern.compile("\\s*|\t|\r|\n");
            Matcher m = p.matcher(str);
            dest = m.replaceAll("");
        }
        return dest;
    }

    /**
     * 判断给定字符串是否为null或空
     * @param str
     */
    public static boolean isEmpty(String str) {
        return str == null || "".equals(str.trim());
    }

    /**
     * 判断给定字符串是否不为null且不为空
     * @param str
     */
    public static boolean isNotEmpty(String str) {
        return !isEmpty(str);
    }

    /**
     * 判断给定字符串数组中所有字符串是否都为null或空
     * @param strs
     */
    public static boolean isEmpty(String... strs) {
        boolean result = true;
        for (String str : strs) {
            if (isNotEmpty(str)) {
                result = false;
                break;
            }
        }
        return result;
    }

    /**
     * 判断给定字符串数组中是否全部不为null且不为空
     * @param strs
     */
    public static boolean isNotEmpty(String... strs) {
        boolean result = true;
        for (String str : strs) {
            if (isEmpty(str)) {
                result = false;
                break;
            }
        }
        return result;
    }

    /**
     * 如果字符串是null或空，返回""
     */
    public static String filterEmpty(String str) {
        return StringUtil.isNotEmpty(str) ? str : "";
    }

    /**
     * 给定字符串中，用新字符替换旧字符
     * @param str,oldchar,newchar
     * @return 替换后字符串
     */
    public static String replace(String str, char oldchar, char newchar) {
        char chars[] = str.toCharArray();
        for (int w = 0; w < chars.length; w++) {
            if (chars[w] == oldchar) {
                chars[w] = newchar;
                break;
            }
        }
        return new String(chars);
    }

    /**
     * 给定字符串用给定字符分割
     * @param str,c
     * @return 分割后字符串数组
     */
    public static String[] split(String str, char c) {
        ArrayList<String> stringList = new ArrayList<String>();
        char chars[] = str.toCharArray();
        int nextStart = 0;
        for (int w = 0; w < chars.length; w++) {
            if (c == chars[w]) {
                stringList.add(new String(chars, nextStart, w - nextStart));
                nextStart = w + 1;
                if (nextStart == chars.length) {// 最后一位是分割符的话，就再添加一个空字符串到分割数组中
                    stringList.add("");
                }
            }
        }
        // 最后一位不是分隔符的话，就将最后一个分割符到最后一个字符中间左右字符串作为一个字符串添加到分割数组中
        if (nextStart < chars.length) {
            stringList.add(new String(chars, nextStart, chars.length - 1 - nextStart + 1));
        }
        return stringList.toArray(new String[stringList.size()]);
    }

    /**
     * 计算给定字符串长度，计算规则：一个汉字长度为2，一个字符长度为1
     * @param str
     * @return 长度
     */
    public static int countLength(String str) {
        int length = 0;
        char[] chars = str.toCharArray();
        for (int w = 0; w < str.length(); w++) {
            char c = chars[w];
            if (c >= '\u0391' && c <= '\uFFE5') {
                length++;
                length++;
            } else {
                length++;
            }
        }
        return length;
    }

    private static char[] getChars(char[] chars, int startIndex) {
        int endIndex = startIndex + 1;
        //如果第一个是数字
        if (Character.isDigit(chars[startIndex])) {
            //如果下一个是数字
            while (endIndex < chars.length && Character.isDigit(chars[endIndex])) {
                endIndex++;
            }
        }
        char[] resultChars = new char[endIndex - startIndex];
        System.arraycopy(chars, startIndex, resultChars, 0, resultChars.length);
        return resultChars;
    }

    /**
     * 是否全是数字
     */
    public static boolean isAllDigital(char[] chars) {
        boolean result = true;
        for (int w = 0; w < chars.length; w++) {
            if (!Character.isDigit(chars[w])) {
                result = false;
                break;
            }
        }
        return result;
    }

    /**
     * 删除给定字符串中所有旧字符
     * @param str,c
     * @return 删除后字符串
     */
    public static String removeChar(String str, char c) {
        StringBuffer sb = new StringBuffer();
        for (char cha : str.toCharArray()) {
            if (cha != '-') {
                sb.append(cha);
            }
        }
        return sb.toString();
    }

    /**
     * 删除给定字符串中给定位置处字符
     * @param str
     * @param index
     */
    public static String removeChar(String str, int index) {
        String result = null;
        char[] chars = str.toCharArray();
        if (index == 0) {
            result = new String(chars, 1, chars.length - 1);
        } else if (index == chars.length - 1) {
            result = new String(chars, 0, chars.length - 1);
        } else {
            result = new String(chars, 0, index) + new String(chars, index + 1, chars.length - index);
        }
        return result;
    }


    /**
     * 删除给定字符串中给定位置处字符
     * @param str,index
     * @param c 如果同给定位置处字符相同，则将给定位置处的字符删除
     */
    public static String removeChar(String str, int index, char c) {
        String result = null;
        char[] chars = str.toCharArray();
        if (chars.length > 0 && chars[index] == c) {
            if (index == 0) {
                result = new String(chars, 1, chars.length - 1);
            } else if (index == chars.length - 1) {
                result = new String(chars, 0, chars.length - 1);
            } else {
                result = new String(chars, 0, index) + new String(chars, index + 1, chars.length - index);
            }
        } else {
            result = str;
        }
        return result;
    }

    /**
     * 对给定字符串进行空白过滤
     * @param str
     * @return 如果给定字符串是一个空白字符串，返回null；否则返回本身
     */
    public static String filterBlank(String str) {
        if ("".equals(str)) {
            return null;
        } else {
            return str;
        }
    }

    /**
     * 给定字符串中给定区域字符转换成小写
     * @param str
     * @param beginIndex 开始索引（包括）
     * @param endIndex 结束索引（不包括）
     * @return 新字符串
     */
    public static String toLowerCase(String str, int beginIndex, int endIndex) {
        return str.replaceFirst(str.substring(beginIndex, endIndex), str.substring(beginIndex, endIndex).toLowerCase(Locale.getDefault()));
    }

    /**
     * 给定字符串中给定区域字符转换成大写
     * @param str
     * @param beginIndex 开始索引（包括）
     * @param endIndex 结束索引（不包括）
     * @return 新字符串
     */
    public static String toUpperCase(String str, int beginIndex, int endIndex) {
        return str.replaceFirst(str.substring(beginIndex, endIndex), str.substring(beginIndex, endIndex).toUpperCase(Locale.getDefault()));
    }

    /**
     * 给定字符串首字母转为小写
     * @param str
     * @return 新字符串
     */
    public static String firstLetterToLowerCase(String str) {
        return toLowerCase(str, 0, 1);
    }

    /**
     * 给定字符串首字母转为大写
     * @param str
     * @return 新字符串
     */
    public static String firstLetterToUpperCase(String str) {
        return toUpperCase(str, 0, 1);
    }

    /**
     * 给定字符串MD5加密
     * @param str
     * @return MD5加密后生成字符串
     */
    public static String MD5(String str) {
        String result = null;
        try {
            char[] charArray = str.toCharArray();
            byte[] byteArray = new byte[charArray.length];
            for (int i = 0; i < charArray.length; i++) {
                byteArray[i] = (byte) charArray[i];
            }
            StringBuffer hexValue = new StringBuffer();
            byte[] md5Bytes = MessageDigest.getInstance("MD5").digest(byteArray);
            for (int i = 0; i < md5Bytes.length; i++) {
                int val = ((int) md5Bytes[i]) & 0xff;
                if (val < 16) {
                    hexValue.append("0");
                }
                hexValue.append(Integer.toHexString(val));
            }
            result = hexValue.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 判断给定字符串是否以一个特定字符串开头，忽略大小写
     * @param sourceString
     * @param newString 一个特定字符串
     */
    public static boolean startsWithIgnoreCase(String sourceString, String newString) {
        int newLength = newString.length();
        int sourceLength = sourceString.length();
        if (newLength == sourceLength) {
            return newString.equalsIgnoreCase(sourceString);
        } else if (newLength < sourceLength) {
            char[] newChars = new char[newLength];
            sourceString.getChars(0, newLength, newChars, 0);
            return newString.equalsIgnoreCase(String.valueOf(newChars));
        } else {
            return false;
        }
    }

    /**
     * 判断给定字符串是否以一个特定字符串结尾，忽略大小写
     * @param sourceString
     * @param newString 一个特定字符串
     */
    public static boolean endsWithIgnoreCase(String sourceString, String newString) {
        int newLength = newString.length();
        int sourceLength = sourceString.length();
        if (newLength == sourceLength) {
            return newString.equalsIgnoreCase(sourceString);
        } else if (newLength < sourceLength) {
            char[] newChars = new char[newLength];
            sourceString.getChars(sourceLength - newLength, sourceLength, newChars, 0);
            return newString.equalsIgnoreCase(String.valueOf(newChars));
        } else {
            return false;
        }
    }

    /**
     * 检查字符串长度，如果长度超过maxLength，就截取前maxLength个字符串并在末尾拼上appendString
     */
    public static String checkLength(String str, int maxLength, String appendString) {
        if (str.length() > maxLength) {
            str = str.substring(0, maxLength);
            if (appendString != null) {
                str += appendString;
            }
        }
        return str;
    }

    /**
     * 检查字符串长度，如果字符串长度超过maxLength，就截取前maxLength个字符串并在末尾拼上…
     */
    public static String checkLength(String str, int maxLength) {
        return checkLength(str, maxLength, "…");
    }
}
