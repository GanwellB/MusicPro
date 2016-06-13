/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package musicpro1.pkg1;

import java.awt.FileDialog;
import java.io.File;
import java.io.FileWriter;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileFilter;

/**
 *
 * @author Ganwell
 */
public class SavingPlayList 
{
    FileDialog dialog;
    public static void readTheCurrentPlayingList()
    {
        JFileChooser chooser = new JFileChooser();
        FileFilter fileFilter = new ExtentionFileFilter("mpr", new String[]{"mpr"});
        chooser.setFileFilter(fileFilter);
        chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        int result = chooser.showSaveDialog(null);
        if(result==JFileChooser.CANCEL_OPTION)
        {
        }
        else
        {
            try
            {
                File file = chooser.getSelectedFile();
                FileWriter fileWriter = new FileWriter(file,false);
                for(int i = 0; i < MusicProComponents.listModel2.getSize(); i++)
                {
                    fileWriter.write(MusicProComponents.listModel2.getElementAt(i)+"\n");
                    System.out.println("Songs: "+MusicProComponents.listModel2.getElementAt(i));
                }
                fileWriter.close();
            }
            catch(Exception c)
            {
                JOptionPane.showMessageDialog(null, "Could not save playlist");
            }
        }
    }
}
