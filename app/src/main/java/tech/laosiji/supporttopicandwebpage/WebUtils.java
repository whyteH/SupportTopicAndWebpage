package tech.laosiji.supporttopicandwebpage;

import android.text.TextUtils;
import android.util.Patterns;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Whyte on 2017/7/19.
 */

public class WebUtils {

    private static final String regex_special = "*.?+$^[](){}|\\/";

    private WebUtils() {

    }

    public static final String OFFLINE_URL_REG = "((?i)((ed2k|thunder)://)|(magnet):\\?)[^\\s]+";
    public static String STR_PATTERN_DOMAIN_LINK = "(([Cc][Oo][Mm])|([Cc][Nn])|([Nn][Ee][Tt])|([Oo][Rr][Gg])|([Aa][Ss][Ii][Aa])|" +
            "([Aa][Ss][Ii][Aa])|([Cc]{2})|([Bb][Ii][Zz])|([Tt][Vv])|([Mm][Ee])|([Pp][Ww])|([Ww][Aa][Nn][Gg])|([Ii][Mm])|([Hh][Kk])|" +
            "([Tt][Vv])|([Ii][Nn][Ff][Oo])|([Mm][Oo][Bb][Ii])|([Nn][Aa][Mm][Ee])|([Gg][Oo][Vv])|([Ff][Mm])|([tT][eE][cC][hH]))";
    private static final String STR_PATTERN_LINK1 = "([hH][tT][tT][pP]([sS])?://)([a-zA-Z0-9_-]+\\.)+[a-zA-Z0-9_-]{2,}([a-zA-Z0-9_~/.\\*\\-?%&amp;=:#|!+]*)?";
    private static final String STR_PATTERN_LINK2 = "([a-zA-Z0-9_-]+\\.)+" + STR_PATTERN_DOMAIN_LINK
            + "(/[a-zA-Z0-9_/.\\-?%&amp;=:#|!+]*)?";
    public static final String STR_PATTERN_LINK = STR_PATTERN_LINK1 + "|" + STR_PATTERN_LINK2;

    public static final String DYNAMIC_URL_REGEX = (Patterns.EMAIL_ADDRESS + "|" + STR_PATTERN_LINK + "|" + OFFLINE_URL_REG);


    /**
     * 判断是否邮箱
     *
     * @param email
     * @return
     */
    public static boolean isEmail(String email) {
        String str = "^([a-zA-Z0-9_\\-.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$";
        Pattern p = Pattern.compile(str);
        Matcher m = p.matcher(email);

        return m.matches();
    }

    /**
     * 特殊字符，添加转义\\
     */
    public static String regexSpecial(String regex) {
        if (TextUtils.isEmpty(regex)) return regex;
        StringBuilder builder = new StringBuilder();
        for (char c : regex.toCharArray()) {
            boolean isSpecial = false;
            for (char c1 : regex_special.toCharArray()) {
                if (c == c1) {
                    isSpecial = true;
                    break;
                }
            }
            if (isSpecial) {
                builder.append("\\");
            }
            builder.append(c);
        }
        return builder.toString();
    }
}
