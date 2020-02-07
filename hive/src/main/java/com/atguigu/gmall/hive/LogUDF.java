package com.atguigu.gmall.hive;

import org.apache.commons.lang.StringUtils;
import org.apache.hadoop.hive.ql.exec.UDF;
import org.json.JSONException;
import org.json.JSONObject;

public class LogUDF extends UDF {

    public String evaluate(String line, String key) throws JSONException {

        if (StringUtils.isBlank(line)) {
            return "";
        }

        String[] split = line.split("\\|");
        if (split.length != 2) {
            return "";
        }

        String serverTime = split[0];
        String baseJson = split[1];
        JSONObject base = new JSONObject(baseJson);

        if ("st".equals(key)) {
            return serverTime;
        } else if ("et".equals(key)) {
            if (base.has("et")) {
                return base.getString("et");
            }
        }else {
            if(base.has("cm")){
                JSONObject cm = base.getJSONObject("cm");
                if(cm.has(key)){
                    return cm.getString(key);
                }
            }
        }

        return "";
    }
}
