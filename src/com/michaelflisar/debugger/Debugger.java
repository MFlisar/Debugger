
package com.michaelflisar.debugger;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.reflect.Field;

public class Debugger
{
    public enum Mode
    {
        SIMPLE,
        TIME,
        CLASS,
        TIME_AND_CLASS
    };
    
    public enum DebugTarget
    {
        DISABLED,
        LOG,
        FILE,
        BOTH
    }
    
    protected enum Level
    {
        V,
        D,
        I,
        W,
        E
    };
    
    private static final int DEFAULT_CLASS_DEPTH = 4;

    private static DebugTarget mDebugTarget = DebugTarget.LOG;
    private static FileDebugger mFileDebugger = null;
    private static String mAppTag = null;
    private static Mode mMode = Mode.CLASS;

    public static void setDebugTarget(DebugTarget debugTarget)
    {
        mDebugTarget = debugTarget;
    }

    public static void setAppTag(String appTag)
    {
        mAppTag = appTag;
    }
    
    public static void setMode(Mode mode)
    {
        mMode = mode;
    }
    
    public static void setFileDebugger(FileDebugger fileDebugger)
    {
        mFileDebugger = fileDebugger;
    }
    
    public static void clearFileDebugger(int rowsToKeep, int maxRows)
    {
        if (mFileDebugger != null)
            mFileDebugger.clear(rowsToKeep, maxRows);
    }

    protected static DebugTarget getDebugTarget()
    {
        return mDebugTarget;
    }
    
    protected static Mode getMode()
    {
        return mMode;
    }

    protected static String getAppTag()
    {
        return mAppTag;
    }
    
    protected static FileDebugger getFileDebugger()
    {
        return mFileDebugger;
    }

    // --------------------------------------
    // Debugging call functions - DEBUG LEVEL
    // --------------------------------------

    public static void d(String message)
    {
        MainDebugger.debug(true, message, null, Level.D, DEFAULT_CLASS_DEPTH);
    }

    public static void d(String message, String extraTag)
    {
        MainDebugger.debug(true, message, extraTag, Level.D, DEFAULT_CLASS_DEPTH);
    }

    public static void d(boolean debug, String message)
    {
        MainDebugger.debug(debug, message, null, Level.D, DEFAULT_CLASS_DEPTH);
    }

    public static void d(boolean debug, String message, String extraTag)
    {
        MainDebugger.debug(debug, message, extraTag, Level.D, DEFAULT_CLASS_DEPTH);
    }

    // --------------------------------------
    // Debugging call functions - EXCEPTION LEVEL
    // --------------------------------------
    
    public static void e(String message)
    {
        MainDebugger.debug(true, message, null, Level.E, DEFAULT_CLASS_DEPTH);
    }

    public static void e(String message, String extraTag)
    {
        MainDebugger.debug(true, message, extraTag, Level.E, DEFAULT_CLASS_DEPTH);
    }

    public static void e(boolean debug, String message)
    {
        MainDebugger.debug(debug, message, null, Level.E, DEFAULT_CLASS_DEPTH);
    }

    public static void e(boolean debug, String message, String extraTag)
    {
        MainDebugger.debug(debug, message, extraTag, Level.E, DEFAULT_CLASS_DEPTH);
    }
    
    public static void e(Exception exception)
    {
        MainDebugger.debug(true, exceptionToString(exception), null, Level.E, DEFAULT_CLASS_DEPTH);
    }

    public static void e(Exception exception, String extraTag)
    {
        MainDebugger.debug(true, exceptionToString(exception), extraTag, Level.E, DEFAULT_CLASS_DEPTH);
    }

    public static void e(boolean debug, Exception exception)
    {
        MainDebugger.debug(debug, exceptionToString(exception), null, Level.E, DEFAULT_CLASS_DEPTH);
    }

    public static void e(boolean debug, Exception exception, String extraTag)
    {
        
        MainDebugger.debug(debug, exceptionToString(exception), extraTag, Level.E, DEFAULT_CLASS_DEPTH);
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
