package com.common.utils;


import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

/**
 * 字符串工具
 */
public class StringUtil {


    /**
     * 根据整形数组得到sql条件in里面的字符串
     *
     * @return
     */
    public static String getInFromList(List<Integer> list) {
        StringBuffer sb = new StringBuffer("");
        if (list != null && list.size() > 0) {
            for (Integer num : list) {
                sb.append(num + ",");
            }
            sb.deleteCharAt(sb.length() - 1);
        }
        return sb.toString();
    }


    /**
     * null 0 0.0返回空 界面需要
     *
     * @return
     */
    public static Object getIsNull(Object object) {
        if (object == null || Integer.valueOf(0).equals(object) || Double.valueOf(0.0).equals(object)) {
            return "";
        } else {
            return object;
        }
    }

    /**
     * null 0 0.0返回空 界面需要
     *
     * @return
     */
    public static String getDoubleNull(Double object) {
        if (object == null || object == 0 || object == 0.0) {
            return "";
        } else {
            return String.valueOf(object);
        }
    }

    /**
     * null 0 0.0返回空 界面需要
     *
     * @return
     */
    public static String getStringIsNull(Integer object) {
        if (object == null || object == 0) {
            return "";
        } else {
            return String.valueOf(object);
        }
    }

    /**
     * null 0 0.0返回空 界面需要
     *
     * @return
     */
    public static String getStringIsNull(Object object) {
        if (object == null || Integer.valueOf(0).equals(object) || Double.valueOf(0.0).equals(object)) {
            return "";
        } else {
            return String.valueOf(object);
        }
    }

    /**
     * null 0 0.0返回空 2位小数
     *
     * @return
     */
    public static String getStringRound(Integer object) {
        if (object == null || object == 0) {
            return "";
        } else {
            return String.valueOf(Math.round(object / 100.0) / 100.0);
        }
    }

    /**
     * null 0 0.0返回空 界面需要
     *
     * @return
     */
    public static Integer setNullIsZero(Integer object) {
        if (object == null) {
            return 0;
        } else {
            return object;
        }
    }

    public static void setListNull(Object[] arr) {
        for (int i = 0; i < arr.length; i++) {
            if ("0.0".equals(arr[i].toString()) || arr[i] == null) {
                arr[i] = "";
            }
        }
    }

    public static String formatString(String str) {
        if (str == null)
            return "";
        str = str.trim();
        if (str.equals("") || str.equals("0") || str.equals("0.0") || str.equals("0.00"))
            return "";
        return str;
    }

    public static String getStringIsNullObj(Object obj) {
        if (obj == null) {
            return "";
        } else if (String.valueOf(obj).equals("0") || String.valueOf(obj).equals("0.0")) {
            return "";
        } else {
            return String.valueOf(obj);
        }
    }

    public static String getSbfToStr(StringBuffer sb) {
        if (!"".equals(sb.toString()) && sb.length() > 0) {
            sb.deleteCharAt(sb.length() - 1);
            return sb.toString();
        } else {
            return "";
        }
    }

    public static double getDoubleScale(Object obj) {
        double ret = 0;
        if (obj == null) {
            return ret;
        } else if (String.valueOf(obj).equals("0") || String.valueOf(obj).equals("0.0")) {
            return ret;
        } else {
            BigDecimal bd = new BigDecimal((Double.parseDouble(String.valueOf(obj)) / 100) / 100.0);
            return bd.setScale(2, RoundingMode.HALF_UP).doubleValue();
        }
    }

    public static String jgPercent(String s) {
        String ret = "";
        if (s != null && !"".equals(s)) {
            int idx = s.indexOf("%");
            if (idx == -1) {
                double d = Double.parseDouble(s);
                if (d < 1) {
                    d = d * 100;
                }
                ret = String.valueOf((int)d) + "%";
            } else {
                ret = s;
            }
        }
        return ret;
    }
}
