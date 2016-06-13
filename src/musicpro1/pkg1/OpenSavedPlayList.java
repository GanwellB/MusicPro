/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package musicpro1.pkg1;

import java.awt.FileDialog;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileFilter;

/**
 *
 * @author Ganwell
 */
public class OpenSavedPlayList 
{
    //FileDialog dialog;
    public static void readSavedListOnDropEvent(File fileToRead)
    {
        try
        {
            String aline;
            FileReader fileReader = new FileReader(fileToRead);
            BufferedReader reader = new BufferedReader(fileReader);

            while((aline= reader.readLine()) !=null)
            {
                System.out.println(aline);
                MusicProComponents.listModel2.addElement(new File(aline));
                File file = new File(aline);
                MusicProComponents.listModel.addElement(file.getName());
            }
            fileReader.close();
            MusicProComponents.index = 0;
            MusicProComponents.file = (File) MusicProComponents.listModel2.getElementAt(MusicProComponents.index);
            MusicPro11.createPlay();
            MusicProComponents.list.setSelectedIndex(MusicProComponents.index);
            MusicProComponents.condition = "stop";
            MusicProComponents.playItem.setText(MusicProComponents.condition);
        }
        catch(IOException d)
        {
            JOptionPane.showMessageDialog(null, "Could not get Data from File");
        }
    }
    public static void readTheSavedPlayingList()
    {
        JFileChooser chooser = new JFileChooser();
        FileFilter fileFilter = new ExtentionFileFilter("mpr", new String[]{"mpr"});
        chooser.setFileFilter(fileFilter);
        chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        int result = chooser.showOpenDialog(null);
        if(result==JFileChooser.CANCEL_OPTION)
        {
        }
        else
        {
            try
            {
                String aline;
                FileReader fileReader = new FileReader(chooser.getSelectedFile());
                BufferedReader reader = new BufferedReader(fileReader);

                while((aline= reader.readLine()) !=null)
                {
                    System.out.println(aline);
                    MusicProComponents.listModel2.addElement(new File(aline));
                    File file = new File(aline);
                    MusicProComponents.listModel.addElement(file.getName());
                }
                fileReader.close();
                MusicProComponents.index = 0;
                MusicProComponents.file = (File) MusicProComponents.listModel2.getElementAt(MusicProComponents.index);
                MusicPro11.createPlay();
                MusicProComponents.list.setSelectedIndex(MusicProComponents.index);
                MusicProComponents.condition = "stop";
                MusicProComponents.playItem.setText(MusicProComponents.condition);
            }
            catch(IOException d)
            {
                JOptionPane.showMessageDialog(null, "Could not read the specified file");
            }
        }
    }
}
