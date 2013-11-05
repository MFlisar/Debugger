
package com.michaelflisar.debugger;

import java.text.SimpleDateFormat;
import java.util.Date;

import android.util.Log;

import com.michaelflisar.debugger.Debugger.DebugTarget;
import com.michaelflisar.debugger.Debugger.Mode;
import com.michaelflisar.debugger.Debugger.Level;

public class MainDebugger
{
    private static final SimpleDateFormat TIME_FORMATTER = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    // --------------------------------------
    // Debugging main function
    // --------------------------------------

    protected static void debug(boolean debug, String message, String extraTag, Level level, int classDepth)
    {
        if (Debugger.getDebugTarget() == DebugTarget.DISABLED || !debug)
            return;
        
        // 1) prepare data
        String classTag = null;
        if (Debugger.getMode() == Mode.CLASS || Debugger.getMode() == Mode.TIME_AND_CLASS)
            classTag = getCurrentClassInfo(classDepth);
        
        String time = null;
        if (Debugger.getMode() == Mode.TIME || Debugger.getMode() == Mode.TIME_AND_CLASS)
            time = TIME_FORMATTER.format(new Date());
        
        // 2) debug
        if (Debugger.getDebugTarget() == DebugTarget.BOTH || Debugger.getDebugTarget() == DebugTarget.LOG)
        {
         // add extra tag to message
            String totalMessage = message;
            if (extraTag != null)
                totalMessage = "<<" + extraTag + ">> " + message;

            // create debug string
            String debugMessage = "";
            if (time != null)
                debugMessage += time + " ";
            if (classTag != null)
                debugMessage += "[" + classTag + "] ";
            debugMessage += totalMessage;
            String debugTag = Debugger.getAppTag() != null ? Debugger.getAppTag() : classTag;
            
            switch (level)
            {
                case V:
                    Log.v(debugTag, debugMessage);
                    break;
                case D:
                    Log.d(debugTag, debugMessage);
                    break;
                case I:
                    Log.i(debugTag, debugMessage);
                    break;
                case W:
                    Log.w(debugTag, debugMessage);
                    break;

                case E:
                    Log.e(debugTag, debugMessage);
                    break;
                default:
                    break;
            }
        }
        if (Debugger.getDebugTarget() == DebugTarget.BOTH || Debugger.getDebugTarget() == DebugTarget.FILE)
        {
            if (classTag == null)
                classTag = getCurrentClassInfo(classDepth);
            if (time == null)
                time = TIME_FORMATTER.format(new Date());
            
            FileDebugger debugger = Debugger.getFileDebugger();
            if (debugger != null)
                debugger.log(level, classTag, time, extraTag, message);
        }
    }

    private static String getCurrentClassInfo(int level)
    {
        StackTraceElement[] trace = new Throwable().getStackTrace();
        // level = trace.length - 1;
        // String c = trace[level].getClassName();
        // String x = Debugger.class.getName();
        // while (!(c = trace[level].getClassName()).equals(x))
        // {
        // level--;
        // }
        // level += 2;

        String className = trace[level].getClassName();
        String classNameShort = className.substring(className.lastIndexOf(".") + 1, className.length());
        boolean innerClass = classNameShort.contains("$");
        if (innerClass)
            classNameShort = classNameShort.substring(0, classNameShort.indexOf("$"));
        String methodName = trace[level].getMethodName();
        return classNameShort + ":" + trace[level].getLineNumber() + " " + (innerClass ? "..." : "") + methodName;
    }
}
