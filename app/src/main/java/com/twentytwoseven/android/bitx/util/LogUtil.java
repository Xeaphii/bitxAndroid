package com.twentytwoseven.android.bitx.util;

import android.util.Log;
import workflow.xeaphii.com.temp.BuildConfig;

import java.util.MissingFormatArgumentException;
import java.util.UnknownFormatConversionException;

public class LogUtil {

    private static final boolean INFO_ENABLED = BuildConfig.DEBUG;
    private static final boolean WARN_ENABLED = BuildConfig.DEBUG;
    private static final boolean ERRO_ENABLED = BuildConfig.DEBUG;

    private LogUtil() {}

    public static void v(String tag, String message, Object... args) {
        if (BuildConfig.DEBUG || Log.isLoggable(tag, Log.VERBOSE)) {
            Log.v(tag, safeFormat(message, args));
        }
    }

    public static void v(String tag, String message, Throwable cause) {
        if (BuildConfig.DEBUG || Log.isLoggable(tag, Log.VERBOSE)) {
            Log.v(tag, message, cause);
        }
    }

    public static void d(String tag, String message, Object... args) {
        if (BuildConfig.DEBUG || Log.isLoggable(tag, Log.DEBUG)) {
            Log.d(tag, safeFormat(message, args));
        }
    }

    public static void d(String tag, String message, Throwable cause) {
        if (BuildConfig.DEBUG || Log.isLoggable(tag, Log.DEBUG)) {
            Log.d(tag, message, cause);
        }
    }

    public static void i(String tag, String message, Object... args) {
        if (INFO_ENABLED) {
            Log.i(tag, safeFormat(message, args));
        }
    }

    public static void i(String tag, String message, Throwable cause) {
        if (INFO_ENABLED) {
            Log.i(tag, message, cause);
        }
    }

    public static void w(String tag, String message, Object... args) {
        if (WARN_ENABLED) {
            Log.w(tag, safeFormat(message, args));
        }
    }

    public static void w(String tag, String message, Throwable cause) {
        if (WARN_ENABLED) {
            Log.w(tag, message, cause);
        }
    }

    public static void e(String tag, String message, Object... args) {
        if (ERRO_ENABLED) {
            Log.e(tag, safeFormat(message, args));
        }
    }

    public static void e(String tag, String message, Throwable cause) {
        if (ERRO_ENABLED) {
            Log.e(tag, message, cause);
        }
    }
    private static String safeFormat(String message, Object... args) {
        try {
            return String.format(message, args);
        } catch (UnknownFormatConversionException | MissingFormatArgumentException | NullPointerException e) {
            for (Object a : args) {
                message += ", " + a;
            }
            return message;
        }
    }
}
