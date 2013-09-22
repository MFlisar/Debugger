
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
        MainDebugger.debug(true, message, null, LEVEL.D, DEFAULT_CLASS_DEPTH);
    }

    public static void d(String message, String extraTag)
    {
        MainDebugger.debug(true, message, extraTag, LEVEL.D, DEFAULT_CLASS_DEPTH);
    }

    public static void d(boolean debug, String message)
    {
        MainDebugger.debug(debug, message, null, LEVEL.D, DEFAULT_CLASS_DEPTH);
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
        MainDebugger.debug(true, message, null, LEVEL.E, DEFAULT_CLASS_DEPTH);
    }

    public static void e(String message, String extraTag)
    {
        MainDebugger.debug(true, message, extraTag, LEVEL.E, DEFAULT_CLASS_DEPTH);
    }

    public static void e(boolean debug, String message)
    {
        MainDebugger.debug(debug, message, null, LEVEL.E, DEFAULT_CLASS_DEPTH);
    }

    public static void e(boolean debug, String message, String extraTag)
    {
        MainDebugger.debug(debug, message, extraTag, LEVEL.E, DEFAULT_CLASS_DEPTH);
    }
    
    public static void e(Exception exception)
    {
        MainDebugger.debug(true, exceptionToString(exception), null, LEVEL.E, DEFAULT_CLASS_DEPTH);
    }

    public static void e(Exception exception, String extraTag)
    {
        MainDebugger.debug(true, exceptionToString(exception), extraTag, LEVEL.E, DEFAULT_CLASS_DEPTH);
    }

    public static void e(boolean debug, Exception exception)
    {
        MainDebugger.debug(debug, exceptionToString(exception), null, LEVEL.E, DEFAULT_CLASS_DEPTH);
    }

    public static void e(boolean debug, Exception exception, String extraTag)
    {
        
        MainDebugger.debug(debug, exceptionToString(exception), extraTag, LEVEL.E, DEFAULT_CLASS_DEPTH);
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
