package com.javafxgrid.utils;

public class StringManipulators {
    
    public static String fromSecondsToFormat(final int totalSeconds) {
        return String.format("%02d:%02d", totalSeconds / 60 % 60, totalSeconds % 60);
    }
}
