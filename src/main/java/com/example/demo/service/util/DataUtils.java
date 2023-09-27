package com.example.demo.service.util;

import lombok.experimental.UtilityClass;

import java.util.List;
import java.util.Map;

@UtilityClass
public class DataUtils {
    public boolean isNullOrEmpty(String str) {
        return (str == null || str.length() == 0);
    }


    public boolean isNullOrEmpty(List<?> lst) {
        return (lst == null || lst.size() == 0);
    }

    public boolean isNullOrEmpty(Map<?, ?> map) {
        return (map == null || map.isEmpty());
    }

    public boolean isNullObject(Object obj) {
        return (obj == null);
    }

    public <T> boolean isNullOrEqual(T obj, T equal) {
        return (obj == null || obj.equals(equal));
    }

    public boolean isNullOrZero(Integer val) {
        return (val == null || val == 0);
    }

    public boolean isAllNullObjects(List<Object> objs) {
        if (objs == null || objs.isEmpty()) {
            return true;
        }
        for (Object obj : objs) {
            if (obj != null)
                return false;
        }
        return true;
    }

    public boolean isAnyNullObject(List<Object> objs) {
        if (objs == null || objs.isEmpty()) {
            return true;
        }
        for (Object obj : objs) {
            if (obj == null)
                return true;
        }
        return false;
    }

    public boolean equalNonNull(Object obj1, Object obj2) {
        if (obj1 == null && obj2 == null) {
            return true;
        } else if (obj1 == null || obj2 == null) {
            return false;
        } else {
            return obj1.equals(obj2);
        }
    }
}
