package com.example.smarttraffic.voice;

import java.util.regex.Pattern;

/**
 * Created by Administrator on 2015/10/27.
 * Updated by Administrator on 2015/10/27.
 */
public class StringMatch {
    public static final String NUMBER_STRING = "^[0-9\\-.]+$";
    public static final String ENGLISH_STRING = "^[A-Za-z\\-.]+$";
    public static final String NO_UNICODE_STRING = "^[A-Za-z0-9\\-.]+$";

    /**
     * 是否只包含数字及字母
     *
     * @param s
     * @return
     */
    public static boolean isStringReg(String s, String match) {
        if (s == null || s.equals(""))
            return false;

        return Pattern.compile(match).matcher(s).find();
    }

}
