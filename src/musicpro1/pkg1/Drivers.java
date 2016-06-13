package musicpro1.pkg1;

import java.nio.file.FileStore;
import java.nio.file.FileSystems;
import javax.swing.filechooser.FileSystemView;
import java.io.File;
import java.io.IOException;
import javax.swing.DefaultListModel;

public class Drivers
{
    static DefaultListModel listModel,listModel2,listModel3;
    static int index = 0 , listSize,listModelSize,size = 0,ShowMessage=0;
    static String FoundDrivesName,SystemDrivers_Selected,UsedDrive;

    public Drivers() throws IOException
    {
        listModel = new DefaultListModel();
        listModel2 = new DefaultListModel();
        DetectedDrives();
    }
    public static void ListDrives_Method()
    {
        FileSystemView fsv = FileSystemView.getFileSystemView();
        
        Drivers.listModel.clear();
        
        File[] f = File.listRoots();
        for (int i = 0; i < f.length; i++)
        {
            String Drv = f[i]+"";
            FoundDrivesName = Drv.charAt(0)+":/";
            Drivers.listModel.addElement(FoundDrivesName);
        }
        //FoundDrivesName = "";
    }
    public static void DetectedDrives() throws IOException
    {
        String[] letters = new String[]{ "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L"};
        File[] drives = new File[letters.length];
        boolean[] isDrive = new boolean[letters.length];

        // init the file objects and the initial drive state
        for ( int i = 0; i < letters.length; ++i )
        {
            drives[i] = new File(letters[i]+":/");
            isDrive[i] = drives[i].canRead();
        }
        System.out.println("FindDrive: waiting for devices...");

        // loop indefinitely
        while(true)
        {
            // check each drive 
            
            for ( int i = 0; i < letters.length; ++i )
            {
                boolean pluggedIn = drives[i].canRead();

                // if the state has changed output a message
                if ( pluggedIn != isDrive[i] )
                {
                    if ( pluggedIn )
                    {
                        ListDrives_Method();
                        index++;
                        System.out.println(""+index);
                        ShowAutoPlay autoPlay = new ShowAutoPlay();
                        System.out.println(drives[i].getAbsolutePath());
                        System.out.println("Drive "+letters[i]+":/ has been plugged in");
                    }
                    else
                    {
                        //StopPlayIfUSBUnPluggeded.checkIfWasPlaying();
                        StopPlayIfUSBUnPluggeded.returnToPrevPlayMedia();
                        ShowAutoPlay.setWindowInvisible();
                        System.out.println("Drive "+letters[i]+":/ has been unplugged");
                        Drivers.listModel.removeElement(letters[i]+":/");
                        UsedDrive = letters[i]+":";
                        if(index > 0)
                        {
                            index--;
                        }
                    }
                    isDrive[i] = pluggedIn;
                    
                }
            }
            // wait before looping
            try 
            { 
                Thread.sleep(10); 
                MusicPro11.musicWindow.repaint();
            }
            catch (InterruptedException e) 
            { 
                /* do nothing */ 
            }
        }
    }
}