/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package musicpro1.pkg1;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import javax.swing.JOptionPane;

/**
 *
 * @author Ganwell
 */
public class KeepAsFavarateWhenRepeat 
{
    String toWriteToFile;
    public KeepAsFavarateWhenRepeat()
    {
        readFavaFile();
    }
    public void writeToFavaFile(String name)
    {
        try
         {
             File file = new File("favasongs.mpr");
             FileWriter fileWriter = new FileWriter(file,true);
             fileWriter.write(name+"\n");
             fileWriter.close();
         }
         catch(Exception c)
         {
             JOptionPane.showMessageDialog(null, "Could not save the save to file");
         }
    }
    public void readFavaFile()
    {
        try
        {
            String aline;
            FileReader fileReader = new FileReader("favasongs.mpr");
            BufferedReader reader = new BufferedReader(fileReader);
            if((aline = reader.readLine())==null)
            {
                writeToFavaFile(MusicProComponents.listModel2.getElementAt(MusicProComponents.list.getSelectedIndex()).toString());
            }
            while((aline= reader.readLine()) !=null)
            {
                System.out.println(aline);
                if(aline.equalsIgnoreCase(MusicProComponents.listModel2.getElementAt(MusicProComponents.list.getSelectedIndex()).toString()))
                {
                    
                }
                else
                {
                    toWriteToFile = MusicProComponents.listModel2.getElementAt(MusicProComponents.list.getSelectedIndex()).toString();
                }
            }
            writeToFavaFile(toWriteToFile);
            fileReader.close();
        }
        catch(IOException d)
        {
            JOptionPane.showMessageDialog(null, "Could not get locate file");
        }
    }
}
