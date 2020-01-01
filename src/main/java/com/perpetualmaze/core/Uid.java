package com.perpetualmaze.core;

import java.util.Random;

public class Uid {
    /* Thread-safe. Lots of contention may cause performance problems, but we're not expecting a lot of contention.
       Not cryptographically secure, but don't really need that level of fidelity here. */
    private static final Random RANDOM = new Random();

    public static String randomUid() {
        return randomUid(8);
    }

    public static String randomUid(int length) {
        StringBuilder uid = new StringBuilder(length);
        byte[] randoms = new byte[length];
        RANDOM.nextBytes(randoms);
        for (int i = 0; i < length; i++) {
            int rand = Math.abs(randoms[i]) % (10 + 26 + 26);
            if (rand < 10) {
                uid.append((char) ('0' + rand));
            } else if (rand < 10 + 26) {
                uid.append((char) ('a' + (rand - 10)));
            } else {
                uid.append((char) ('A' + (rand - 10 - 26)));
            }
        }
        return uid.toString();
    }
}
