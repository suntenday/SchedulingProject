package com.suntenday.scheduling.utils;

import java.util.List;

/**
 * ArrayUtils
 *
 * @author suntenday
 * @date 2017/9/11 0011.
 */

public class ArrayUtils {

    /**
     * 判断List是否为空
     *
     * @param list
     * @return
     */
    public static boolean isEmpty(List<?> list) {
        if (list != null && list.size() > 0) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * 判断List是否不为空
     *
     * @param list
     * @return
     */
    public static boolean isNotEmpty(List<?> list) {
        if (list != null && list.size() > 0) {
            return true;
        } else {
            return false;
        }
    }
}
