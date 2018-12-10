package com.example.myhttp;

import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ClickableSpan;

import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class StringUtils {
    public static final String NEW_LINE = "\n";//换行符
    public static final String NEW_LINE_MARK = "#@n#";//换行标示
    public static final String NEW_HTML_LINE = "<br/>";

    public static String generateTime(long time) {
        int totalSeconds = (int) (time / 1000);
        int seconds = totalSeconds % 60;
        int minutes = (totalSeconds / 60) % 60;
        int hours = totalSeconds / 3600;

        return hours > 0 ? String.format("%02d:%02d:%02d", hours, minutes, seconds) : String.format("%02d:%02d", minutes, seconds);
    }

    public static boolean isNull(String str) {
        if (str == null || "".equals(str.trim())) {
            return true;
        }
        return false;
    }

    public static boolean notNull(String str) {
        if (str == null || "".equals(str.trim())) {
            return false;
        }
        return true;
    }

    public static String nullToString(String str) {
        if (str == null || "".equals(str.trim())/*||"null".equals(str.trim())*/) {
            return "";
        }
        return str.trim();
    }

    public static String nullToString(Object str) {
        if (str == null) {
            return "";
        }
        return str.toString();
    }

    public static int stringToInt(String str) {
        int tmp = 0;
        if (!isNull(str)) {
            try {
                tmp = Integer.parseInt(str);
            } catch (NumberFormatException e) {
                logger.w(e.getMessage());
            }
        }
        return tmp;
    }

    public static String replaseToNewLine(String str) {
        str = nullToString(str);
        String newStr = str.replaceAll(NEW_LINE_MARK, NEW_LINE);
        return newStr;
    }

    /**
     * 换行
     *
     * @param line 行数
     * @return
     */
    public static String getNewLine(int line) {
        StringBuffer newStr = new StringBuffer();
        for (int i = 0; i < line; i++) {
            newStr.append(NEW_LINE);
        }
        return newStr.toString();
    }

    /**
     * 换行
     *
     * @param line 行数
     * @return
     */
    public static String getNewHtmlLine(int line) {
        StringBuffer newStr = new StringBuffer();
        for (int i = 0; i < line; i++) {
            newStr.append(NEW_HTML_LINE);
        }
        return newStr.toString();
    }

    public static int[] getHourMin(String timeStr) {
        String hour = timeStr.substring(0, timeStr.indexOf(":"));
        String min = timeStr.substring(timeStr.indexOf(":") + 1);
        int h = Integer.parseInt(hour);
        int m = Integer.parseInt(min);
        return new int[]{h, m};
    }

    /**
     * 编码转换
     *
     * @return
     */
    public static String getDecodeText(String Url) {
        String StrUrl = Url;
        try {
            StrUrl = URLDecoder.decode(StrUrl, "UTF-8");
        } catch (Exception e) {
            logger.e("decode---error---error");
        }
        return StrUrl;
    }

    public static String getEncodeText(String Url) {
        String StrUrl = Url;
        try {
            StrUrl = URLEncoder.encode(StrUrl, "UTF-8");
        } catch (Exception e) {
            logger.e("decode---error---error");
        }
        return StrUrl;
    }

    public static boolean isUrl(String text) {
        if (StringUtils.isNull(text)) {
            return false;
        } else {
            String reString = "^((https|http|ftp|rtsp|mms)?://)"
                    + "?(([0-9a-z_!~*'().&=+$%-]+: )?[0-9a-z_!~*'().&=+$%-]+@)?" //ftp的user@
                    + "(([0-9]{1,3}\\.){3}[0-9]{1,3}" // IP形式的URL- 221.2.162.15
                    + "|" // 允许IP和DOMAIN（域名）
                    + "([0-9a-z_!~*'()-]+\\.)*" // 域名- www.
                    + "([0-9a-z][0-9a-z-]{0,61})?[0-9a-z]\\." // 二级域名
                    + "[a-z]{2,6})" // first level domain- .com or .museum
                    + "(:[0-9]{1,4})?" // 端口- :80
                    + "((/?)|" // a slash isn't required if there is no file name
                    + "(/[0-9a-zA-Z_!~*'().;?:@&=+$,%#-]+)+/?)$";
            Pattern pattern = Pattern.compile(reString);
            Matcher matcher = pattern.matcher(text);
            return matcher.matches();
        }
    }

    /**
     * 判断密码是否合法(不能有中文  和 空格)
     */
    public static boolean isOkOfPassWord(String str) {
        if (str.matches("^[\\u4e00-\\u9fa5]*$| ")) {
            return false;
        }
        return true;
    }

    /**
     * 给String 的部分文字添加事件监听
     *
     * @param statementText 要显示的文字
     * @param clickText     要添加监听的文字（它是statement中的的一段文字）
     * @return
     */
    public static SpannableString setStringClick(String statementText, String clickText, ClickableSpan clickableSpan) {
        int star = statementText.length() - clickText.length();
        SpannableString spannableString = new SpannableString(statementText);
        spannableString.setSpan(clickableSpan, star, statementText.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        return spannableString;
    }
}
