/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package musicpro1.pkg1;

import java.io.File;
import java.io.IOException;
import static musicpro1.pkg1.MusicPro11.createPlay;
import static musicpro1.pkg1.MusicProComponents.listModel;
import static musicpro1.pkg1.MusicProComponents.listModel2;
/**
 *
 * @author Ganwell
 */
public class PlayAllMP3FilesFromUSB
{
    static final File folder = new File(Drivers.listModel.getElementAt(Drivers.index).toString());
    public static void getSongsFromUSBDrive() throws IOException
    {
         if(MusicProComponents.condition.equals("play"))
        {
            if(Drivers.listModel.getSize() <=0)
            {
                MusicProComponents.sentence =folder.toURL()+"";
                MusicProComponents.words = MusicProComponents.sentence.split("/");
                MusicPro11.SeparateDirectry();
                Listing listing = new Listing(new File(Drivers.listModel.getElementAt(Drivers.listModel.getSize()).toString()));
                listing.displayFiles();
                Drivers.FoundDrivesName = "";
                MusicProComponents.index = 0;
                MusicProComponents.file = (File) MusicProComponents.listModel2.getElementAt(MusicProComponents.index);
                createPlay();
                MusicProComponents.list.setSelectedIndex(MusicProComponents.index);
                MusicProComponents.condition = "stop";
                MusicProComponents.playItem.setText(MusicProComponents.condition);
                MusicProComponents.fromUSB = "yes";
            }
            else
            {
                MusicProComponents.sentence =folder.toURL()+"";
                MusicProComponents.words = MusicProComponents.sentence.split("/");
                MusicPro11.SeparateDirectry();
                Listing listing = new Listing(new File(Drivers.listModel.getElementAt(Drivers.index+1).toString()));
                listing.displayFiles();
                Drivers.FoundDrivesName = "";
                MusicProComponents.index = 0;
                MusicProComponents.file = (File) MusicProComponents.listModel2.getElementAt(MusicProComponents.index);
                createPlay();
                MusicProComponents.list.setSelectedIndex(MusicProComponents.index);
                MusicProComponents.condition = "stop";
                MusicProComponents.playItem.setText(MusicProComponents.condition);
                MusicProComponents.fromUSB = "yes";
            }
        }
        else if(MusicProComponents.condition=="stop")
        {
            if(Drivers.listModel.getSize() <=0)
            {
                MusicProComponents.sentence =folder.toURL()+"";
                MusicProComponents.words = MusicProComponents.sentence.split("/");
                MusicPro11.SeparateDirectry();
                Listing listing = new Listing(new File(Drivers.listModel.getElementAt(Drivers.listModel.getSize()).toString()));
                listing.displayFiles();
                Drivers.FoundDrivesName = "";
                /*MusicProComponents.file = (File) MusicProComponents.listModel2.getElementAt(MusicProComponents.index);
                createPlay();
                MusicProComponents.list.setSelectedIndex(MusicProComponents.index);
                MusicProComponents.playItem.setText(MusicProComponents.condition="stop");*/
            }
            else
            {
                MusicProComponents.sentence =folder.toURL()+"";
                MusicProComponents.words = MusicProComponents.sentence.split("/");
                MusicPro11.SeparateDirectry();
                Listing listing = new Listing(new File(Drivers.listModel.getElementAt(Drivers.index+1).toString()));
                listing.displayFiles();
                Drivers.FoundDrivesName = "";
                /*MusicProComponents.file = (File) MusicProComponents.listModel2.getElementAt(MusicProComponents.index);
                createPlay();
                MusicProComponents.list.setSelectedIndex(MusicProComponents.index);
                MusicProComponents.playItem.setText(MusicProComponents.condition="stop");*/
            }
        }
        ShowAutoPlay.setWindowInvisible();
    }
}