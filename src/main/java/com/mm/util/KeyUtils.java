package com.mm.util;

import java.util.Random;

public class KeyUtils {
    public static synchronized String genUniqueKey() {
        return (System.currentTimeMillis() + String.valueOf(new Random().nextInt(99999999) + 1000000));
    }
}
