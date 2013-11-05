
package com.michaelflisar.debugger;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.LineNumberReader;

import com.michaelflisar.debugger.Debugger.Level;

public class FileDebugger
{
    private int mBuffer = 8192;
    private File mFile = null;
    private BufferedWriter mOut = null;

    public FileDebugger(File file)
    {
        init(file);
    }

    public FileDebugger(File file, int buffer)
    {
        mBuffer = buffer;
        init(file);
    }

    private boolean init(File file)
    {
        mFile = file;
        try
        {
            if (!file.exists())
            {
                file.getParentFile().mkdirs();
                file.createNewFile();
            }
            mOut = new BufferedWriter(new FileWriter(mFile, true), mBuffer);
            return true;
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        return false;
    }

    public void clear(int rowsToKeep, int maxRows)
    {
        try
        {
            if (mOut != null)
                mOut.close();
            if (mFile != null)
            {
                if (rowsToKeep <= 0)
                    mFile.delete();
                else
                {
                    int rows = getRowCount();
                    if (rows > maxRows)
                    {
                        String newline = System.getProperty("line.separator");
                        int rowsToDelete = rows - rowsToKeep;
                        File tmp = new File(mFile.getAbsolutePath() + ".tmp");
                        BufferedWriter writer = new BufferedWriter(new FileWriter(tmp), mBuffer);
                        LineNumberReader reader = new LineNumberReader(new FileReader(mFile));
                        String line = null;
                        while ((line = reader.readLine()) != null)
                        {
                            if (reader.getLineNumber() > rowsToDelete)
                            {
                                writer.write(line);
                                writer.write(newline);
                            }
                        }
                        reader.close();
                        writer.close();
                        mFile.delete();
                        tmp.renameTo(mFile.getAbsoluteFile());
                    }
                }
                init(mFile);
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    private int getRowCount()
    {
        int rows = 0;
        try
        {
            LineNumberReader lineNumberReader = new LineNumberReader(new FileReader(mFile));
            lineNumberReader.skip(Long.MAX_VALUE);
            rows = lineNumberReader.getLineNumber();
            lineNumberReader.close();
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        return rows;
    }

    public void log(Level level, String classTag, String time, String extraTag, String message)
    {
        try
        {
            mOut.write(time);
            mOut.write("\t[");
            mOut.write(classTag);
            mOut.write("]\t");
            if (extraTag != null)
                mOut.write(extraTag);
            mOut.write("\t");
            mOut.write(message);
            mOut.newLine();
            mOut.flush();
        }
        catch (IOException e)
        {
        }
    }
}
