package com.harsha.windowbuilder;
import java.io.File;
import java.io.FilenameFilter;

import javax.swing.filechooser.FileFilter;

public class TextFileFilter implements FilenameFilter
{
    public boolean accept(File dir, String name)
    {
        if(name != null && name.endsWith(".rgb"))
        {
            return true;
        }
        return false;
    }

}