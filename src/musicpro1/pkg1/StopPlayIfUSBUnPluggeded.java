/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package musicpro1.pkg1;

import java.io.File;
import javax.swing.DefaultListModel;
import static musicpro1.pkg1.MusicPro11.createPlay;

/**
 *
 * @author Ganwell
 */
public class StopPlayIfUSBUnPluggeded 
{
    public static void checkIfWasPlaying()
    {
        if(MusicProComponents.condition == "stop" && MusicProComponents.fromUSB == "yes")
        {
            MusicProComponents.fromUSB = "no";
            MusicProComponents.condition = "play";
            MusicPro11.stopPlay();
            MusicProComponents.listModel.clear();
            MusicProComponents.listModel2.clear();
            MusicProComponents.playItem.setText(MusicProComponents.condition);
        }
    }
    public static void returnToPrevPlayMedia()
    {
        if(MusicProComponents.condition == "stop" && MusicProComponents.fromUSB == "yes")
        {
            //MusicProComponents.reservedListName = new DefaultListModel();
            //MusicProComponents.reserveListDirectory = new DefaultListModel();
            MusicPro11.stopPlay();
            MusicProComponents.condition = "play";
            MusicProComponents.playItem.setText(MusicProComponents.condition);
            MusicProComponents.listModel.clear();
            MusicProComponents.listModel2.clear();
            if(MusicProComponents.keptIndex >= 0)
            {
                for(int i = 0; i < MusicProComponents.reservedListName.getSize();i++)
                {
                    MusicProComponents.listModel.addElement(MusicProComponents.reservedListName.getElementAt(i));
                    MusicProComponents.listModel2.addElement(MusicProComponents.reserveListDirectory.getElementAt(i));
                    System.out.println("\n"+MusicProComponents.listModel2.getElementAt(i));
                }
                MusicProComponents.index = MusicProComponents.keptIndex;
                MusicProComponents.file = (File) MusicProComponents.listModel2.getElementAt(MusicProComponents.index);
                createPlay();
                MusicProComponents.list.setSelectedIndex(MusicProComponents.index);
                MusicProComponents.condition = "stop";
                MusicProComponents.playItem.setText(MusicProComponents.condition);
                MusicProComponents.fromUSB = "no";
            }
            else
            {
            }
        }
        else
        {
        }
    }
}
