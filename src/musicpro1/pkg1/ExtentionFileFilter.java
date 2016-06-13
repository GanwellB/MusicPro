/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package musicpro1.pkg1;

import java.io.File;
import javax.swing.filechooser.FileFilter;

/**
 *
 * @author Ganwell
 */
public class ExtentionFileFilter extends FileFilter
{
    String description;
    String extensions[];

    public ExtentionFileFilter(String description, String extension) 
    {
        this(description,new String[] {extension});
    }
    public ExtentionFileFilter(String description, String extensions[])
    {
        if(description == null)
        {
            this.description = extensions[0];
        }
        else
        {
            this.description = description;
        }
        this.extensions = (String[])extensions.clone();
        toLower(this.extensions);
    }
    private void toLower(String array[])
    {
        for(int i = 0, j = array.length; i<j;i++)
        {
            array[i] = array[i].toLowerCase();
        }
    }
    @Override
    public boolean accept(File f)
    {
        if(f.isDirectory())
        {
            return true;
        }
        else
        {
            String path = f.getAbsolutePath().toLowerCase();
            for(int i = 0, n = extensions.length; i<n;i++)
            {
                String extension = extensions[i];
                if(path.endsWith(extension)&& (path.charAt(path.length()-extension.length()-1)=='.'))
                {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public String getDescription() 
    {
        return  description;
    }
    
}
