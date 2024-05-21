package org.fullstack4.utils;

import java.security.SecureRandom;
import java.util.Date;

public class CommonUtil {
    public String makeTempPassword(int len) {
        SecureRandom sr = new SecureRandom();
        StringBuilder sb = new StringBuilder();

        String strPool = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz" +
                "0123456789!@#$%^&*()-+";
        //시드값 설정
        sr.setSeed(System.currentTimeMillis());

        for (int i = 0; i < len; i++) {
            int char_index = sr.nextInt(strPool.length());
            sb.append(strPool.charAt(char_index));
        }

        return sb.toString();
    }

}
