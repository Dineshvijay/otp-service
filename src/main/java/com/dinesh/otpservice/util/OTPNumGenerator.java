package com.dinesh.otpservice.util;

public class OTPNumGenerator {
    public static int generate() {
      int min = 10000;
      int max = 90000;
      return generate(min, max);
    }
    private static int generate(int min, int max) {
        int range = (max - min) + 1;
        return (int)(Math.random() * range) + min;
    }
}
