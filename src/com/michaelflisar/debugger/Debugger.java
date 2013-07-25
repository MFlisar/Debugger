
package com.michaelflisar.debugger;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.reflect.Field;

public class Debugger
{
    public enum MODE
    {
        APP_TAG, // logs with application tag
        CUSTOM_TAG // logs with custom tag
    };

    protected enum LEVEL
    {
        V,
        D,
        I,
        W,
        E
    };
    
    private static final int DEFAULT_CLASS_DEPTH = 4;

    private static String mAppTag = "";
    private static MODE mMode = MODE.CUSTOM_TAG;
    private static boolean mDebuggingEnabled = true;

    public static void setEnabled(boolean enabled)
    {
        mDebuggingEnabled = enabled;
    }

    public static void setMode(MODE mode)
    {
        mMode = mode;
    }

    public static void setAppTag(String appTag)
    {
        mAppTag = appTag;
    }

    protected static boolean isEnabled()
    {
        return mDebuggingEnabled;
    }

    protected static MODE getMode()
    {
        return mMode;
    }

    protected static String getAppTag()
    {
        return mAppTag;
    }

    // --------------------------------------
    // Debugging call functions - DEBUG LEVEL
    // --------------------------------------

    public static void d(String message)
    {
        d(true, message);
    }

    public static void d(String message, String extraTag)
    {
        d(true, message, extraTag);
    }

    public static void d(boolean debug, String message)
    {
        d(debug, message, null);
    }

    public static void d(boolean debug, String message, String extraTag)
    {
        MainDebugger.debug(debug, message, extraTag, LEVEL.D, DEFAULT_CLASS_DEPTH);
    }

    // --------------------------------------
    // Debugging call functions - EXCEPTION LEVEL
    // --------------------------------------
    
    public static void e(String message)
    {
        e(true, message);
    }

    public static void e(String message, String extraTag)
    {
        e(true, message, extraTag);
    }

    public static void e(boolean debug, String message)
    {
        e(debug, message, null);
    }

    public static void e(boolean debug, String message, String extraTag)
    {
        MainDebugger.debug(debug, message, extraTag, LEVEL.E, DEFAULT_CLASS_DEPTH);
    }
    
    public static void e(Exception exception)
    {
        e(true, exception);
    }

    public static void e(Exception exception, String extraTag)
    {
        e(true, exception, extraTag);
    }

    public static void e(boolean debug, Exception exception)
    {
        e(debug, exception, null);
    }

    public static void e(boolean debug, Exception exception, String extraTag)
    {
        
        MainDebugger.debug(debug, exceptionToString(exception), extraTag, LEVEL.E, DEFAULT_CLASS_DEPTH - 1);
    }

    // --------------------------------------
    // Debugging String helper functions
    // --------------------------------------

    public static String exceptionToString(Exception exception)
    {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        exception.printStackTrace(pw);
        return exception.getMessage() + "\nStacktrace: " + sw.toString();
    }
    public static String classToString(Object object, Long id, Field[] fields, Object[] fieldValues)
    {
        StringBuilder result = new StringBuilder();
        String newLine = System.getProperty("line.separator");

        result.append(object.getClass().getName());
        result.append(" {");
        result.append(newLine);

        if (id != null)
        {
            result.append("   id: ");
            result.append(id);
            result.append(newLine);
        }
        
        // print field names paired with their values
        for (int i = 0; i < fields.length; i++)
        {
            result.append("  ");
            result.append(fields[i].getName());
            result.append(": ");
            result.append(fieldValues[i]);
            result.append(newLine);
        }
        result.append("}");
        return result.toString();
    }
}
