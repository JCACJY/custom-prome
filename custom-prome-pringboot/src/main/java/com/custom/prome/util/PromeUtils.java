package com.custom.prome.util;

public final class PromeUtils {
    private PromeUtils(){}

    public static boolean strIsEmpty(String str){
        if("".equals(str) || str == null){
            return true;
        }
        return false;
    }

}
