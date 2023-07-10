/**
 * Copyright © 2018 mall Info. Tech Ltd. All rights reserved.
 *
 * @Package: com.doit.mall.utils
 * @author: Herry
 * @date: 2018年5月4日 下午1:35:58
 * @Description:
 */
package com.cbrc.dashboard.utils;

import java.util.Random;
import java.util.UUID;

/**
 * @author Herry
 *
 */
public class RandomGenerator {

    public static final String chars = "abcdefghijklmnopqrstuvwxyz0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";

    /**
     * @param args
     */
    public static void main(String[] args) {
        System.out.println(RandomGenerator.randomUrl());
    }

    public static String UUIdGen() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }

    public static String randomUrl() {
        Random random = new Random();
        int len = random.nextInt(10) + 5;
        return randomStr(len) + "@163.com";
    }

    public static String randomStr(int len) {
        Random random = new Random();
        char[] text = new char[len];
        for (int i = 0; i < len; i++) {
            text[i] = chars.charAt(random.nextInt(chars.length()));
        }
        return new String(text);
    }


}
