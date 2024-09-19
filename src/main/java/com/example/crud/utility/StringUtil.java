package com.example.crud.utility;

import org.apache.commons.text.CaseUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StringUtil {
    public static List<Map<String, Object>> toCamelCase(List<Map<String, Object>> dataList) {
        return dataList.stream().map(data -> {
            Map<String, Object> m = new HashMap<>();
            data.forEach((k, v) -> m.put(CaseUtils.toCamelCase(k, false, '_'), v));
            return m;
        }).toList();
    }
}
