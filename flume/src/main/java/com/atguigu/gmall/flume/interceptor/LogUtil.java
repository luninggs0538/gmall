package com.atguigu.gmall.flume.interceptor;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;

public class LogUtil {


    public static boolean validateStart(String log) {

        if (StringUtils.isBlank(log)) {
            return false;
        }

        if (!log.startsWith("{") || !log.endsWith("}")) {
            return false;
        }

        return true;
    }

    public static boolean validateEvent(String log) {

        if (StringUtils.isBlank(log)) {
            return false;
        }

        String[] split = log.split("\\|");
        if (split.length != 2) {
            return false;
        }

        String timeStamp = split[0];
        String json = split[1];

        if (timeStamp.length()!=13){
            return false;
        }

        if(!NumberUtils.isDigits(timeStamp)){
            return false;
        }

        if (!json.startsWith("{") || !json.endsWith("}")) {
            return false;
        }


        return true;
    }
}
