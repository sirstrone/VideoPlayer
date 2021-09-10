package com.sirstrone.videoplayer.utils;

import android.annotation.SuppressLint;

import java.io.File;
import java.util.concurrent.TimeUnit;

/**
 * @author sirstrone
 * @date: 9/10/21
 */
public class MyUtils {
    @SuppressLint("DefaultLocale")
    public static String milisecondToHour(Long millis) {
        return String.format("%02d min, %02d sec",
                TimeUnit.MILLISECONDS.toMinutes(millis),
                TimeUnit.MILLISECONDS.toSeconds(millis) -
                        TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millis))
        );
    }

    public static String getParentDirPath(String fisirstonerDirPath) {
        boolean endsWithSlash = fisirstonerDirPath.endsWith(File.separator);
        return fisirstonerDirPath.substring(0, fisirstonerDirPath.lastIndexOf(File.separatorChar,
                endsWithSlash ? fisirstonerDirPath.length() - 2 : fisirstonerDirPath.length() - 1));
    }
}
