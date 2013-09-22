
package com.michaelflisar.debugger;

import java.text.SimpleDateFormat;
import java.util.Date;

import android.util.Log;

import com.michaelflisar.debugger.Debugger.LEVEL;
import com.michaelflisar.debugger.Debugger.MODE;

public class MainDebugger
{
    private static final SimpleDateFormat TIME_FORMATTER = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");
    
    // --------------------------------------
    // Debugging main function
    // --------------------------------------

    protected static void debug(boolean debug, String message, String extraTag, LEVEL level, int classDepth)
    {
        if (!Debugger.isEnabled() || !debug)
            return;
        
        // 1) add extra tag to message
        if (extraTag != null)
            message = "<<" + extraTag + ">> " + message;
        
        // 2) get current class info
        String tag = getCurrentClassInfo(classDepth);
        
        // 3) create debug string
        String debugMessage = Debugger.getMode() == MODE.APP_TAG ? (TIME_FORMATTER.format(new Date()) + " [" + tag + "] " + message) : message;
        String debugTag = Debugger.getMode() == MODE.APP_TAG ? Debugger.getAppTag() : tag;
        
        // 4) debug
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

    private static String getCurrentClassInfo(int level)
    {
        StackTraceElement[] trace = new Throwable().getStackTrace();
//        level = trace.length - 1;
//        String c = trace[level].getClassName();
//        String x = Debugger.class.getName();
//        while (!(c = trace[level].getClassName()).equals(x))
//        {
//            level--;
//        }
//        level += 2;
        
        String className = trace[level].getClassName();
        String classNameShort = className.substring(className.lastIndexOf(".") + 1, className.length());
        boolean innerClass = classNameShort.contains("$");
        if (innerClass)
            classNameShort = classNameShort.substring(0, classNameShort.indexOf("$"));
        String methodName = trace[level].getMethodName();
        return classNameShort + ":" + trace[level].getLineNumber() + " " + (innerClass ? "..." : "") + methodName;
    }
}
