/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package musicpro1.pkg1;

import com.mpatric.mp3agic.InvalidDataException;
import com.mpatric.mp3agic.Mp3File;
import com.mpatric.mp3agic.UnsupportedTagException;
import java.io.*;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.Line;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.Mixer;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 *
 * @author Ganwell
 */
public class ExtendedMethods 
{
    static JFrame frame;
    public static Thread thread;
    public static float  f;// = 0.0f;
    public static Line line;
    public static String artist = "",album = "", track ="",year = "",comments = "", genre = "";
    public static boolean  playingLast =false, mute = false;
    public static void toggleMuteOnOFF() throws Exception
    {
        float currentVol = f;
        if(mute == false)
        {
            f = 1.0f;
            double d = (double)f*100;
            MusicPro11.volValue = (int)d;
            System.out.println("Value of Volume "+MusicPro11.volValue+" "+d);
            MusicProComponents.volSlider.setValue(MusicPro11.volValue);
            saveVolumeValue();
            ExtendedMethods.printMixerDetails();
            MusicProComponents.volSlider.setToolTipText(""+MusicProComponents.volSlider.getValue());
            volumeStatus();
            ExtendedMethods.line.close();
            volumeStatus();
            mute = true;
        }
        else if(mute == true)
        {
            f = (float) 0.0;
            double d = (double)f*100;
            MusicPro11.volValue = (int)d;
            System.out.println("Value of Volume "+MusicPro11.volValue+" "+d);
            MusicProComponents.volSlider.setValue(MusicPro11.volValue);
            saveVolumeValue();
            ExtendedMethods.printMixerDetails();
            MusicProComponents.volSlider.setToolTipText(""+MusicProComponents.volSlider.getValue());
            volumeStatus();
            ExtendedMethods.line.close();
            mute = false;
        }
    }
    public static void volumeStatus()
    {
        try {
            f = getVolumeValue();
        } catch (Exception ex) {
            Logger.getLogger(ExtendedMethods.class.getName()).log(Level.SEVERE, null, ex);
        }
        if(f <= 0.0f)
        {
            
            MusicProComponents.volumeIcon = new ImageIcon("images/mute.png");
            MusicProComponents.volIconHolder.setIcon(MusicProComponents.volumeIcon);
        }
        else if(f>0.0f)
        {
            MusicProComponents.volumeIcon = new ImageIcon("images/volume.png");
            MusicProComponents.volIconHolder.setIcon(MusicProComponents.volumeIcon);
        }
    }
    public static void checkIfIconified()
    {
        if(MusicPro11.musicWindow.getState() == JFrame.ICONIFIED )
        {
            JButton button = new JButton("play");
            button.setBounds(0, 0, 10, 10);
            MusicPro11.musicWindow.add(button);
        }
        if(MusicPro11.musicWindow.getState() == JFrame.MAXIMIZED_BOTH)
        {
            
        }
    }
    public static  void printMixerDetails()
    {
        javax.sound.sampled.Mixer.Info[] mixers = AudioSystem.getMixerInfo();
        System.out.println("There are "+mixers.length+" mixer info objetcs");
        for(int i = 0;i < mixers.length;i++)
        {
            Mixer.Info info = mixers[i];
            System.out.println("Mixer Name: "+info.getName());
            Mixer mixer = AudioSystem.getMixer(info);
            Line.Info[] infos = mixer.getTargetLineInfo();
            for(Line.Info info1:infos)
            {
                System.out.println("line: "+info1);
                try
                {
                    line = mixer.getLine(info1);
                    line.open();
                    if(line.isControlSupported(FloatControl.Type.VOLUME))
                    {
                        FloatControl control = (FloatControl)line.getControl(FloatControl.Type.VOLUME);
                        System.out.println("Volume: "+control.getValue());
                        control.setValue(f);
                        //int value = (int)(control.getValue()*100);
                        /*ProgressBar bar 
                        bar.setValue(value);
                        frame.add(new JLabel(info1.toString()));
                        frame.add(bar);
                        frame.pack();*/
                        line.close();
                    }
                }
                catch(LineUnavailableException exception)
                {
                    //exception.printStackTrace();
                }
            }
        }
    }
    public static void controlVolume()
    {
        Mixer.Info[] mixers = AudioSystem.getMixerInfo();
        for(Mixer.Info info:mixers)
        {
            System.out.println("mixer name: "+info.getName());
            Mixer mixer = AudioSystem.getMixer(info);
            Line.Info[] liInfos = mixer.getTargetLineInfo();
            for(Line.Info info1: liInfos)
            {
                System.out.println("Line.Info: "+liInfos);
                Line line = null;
                boolean opened = true;
                try
                {
                    line = mixer.getLine(info1);
                    opened = line.isOpen() || line instanceof Clip;
                    
                    if(!opened)
                    {
                        line.open();
                    }
                    FloatControl control = (FloatControl)line.getControl(FloatControl.Type.MASTER_GAIN);
                    control.getValue();
                    //control.setValue(0.0f);
                    System.out.println(""+control.getValue());
                }
                catch(LineUnavailableException exception)
                {
                    exception.printStackTrace();
                }
                catch(IllegalArgumentException  iae)
                {
                    iae.printStackTrace();
                }
                finally
                {
                    if(line!=null && !opened)
                    {
                        line.close();
                    }
                }
            }
        }
    }
    public static void saveVolumeValue()
    {
        try
        {
            File file = new File("volume");
            FileWriter fileWriter = new FileWriter(file,false);
            fileWriter.write(f+"");
            fileWriter.close();
        }
        catch(Exception c)
        {
            JOptionPane.showMessageDialog(null, "Generating Sound Settings");
            
        }
    }
    public static String getArtist() throws IOException, UnsupportedTagException, InvalidDataException
    {
       if(MusicProComponents.file == null)
       {
           artist = "Unknown";
       }
       else if(MusicProComponents.file != null)
       {
            Mp3File file = new Mp3File(MusicProComponents.file.getAbsolutePath());
            if(file.getId3v1Tag().getArtist() == null || file.getId3v1Tag().getArtist() == "" || file == null)
            {
                artist = "Unknown";
            }
            else
            {
                artist = file.getId3v1Tag().getArtist();
            }
       }
       else
       {
           artist = "Unknown";
       }
        
        return artist;
    }
    public static void freeMemoryAndRepaint()
    {
        Timer timer = new Timer();
        timer.schedule(new TimerTask() 
        {

            @Override
            public void run()
            {
                MusicPro11.musicWindow.repaint();
                Runtime.getRuntime().gc();
                System.gc();
            }
        }, 1, 2);
    }
    public static String getAlbum() throws IOException, UnsupportedTagException, InvalidDataException
    {
       if(MusicProComponents.file == null)
       {
           album = "Unknown";
       }
       else
       {
            Mp3File file = new Mp3File(MusicProComponents.file.getAbsolutePath());
            album = file.getId3v1Tag().getAlbum();
       }
        
        return album;
    }
    public static String getComments() throws IOException, UnsupportedTagException, InvalidDataException
    {
       if(MusicProComponents.file == null)
       {
           comments = "Unknown";
       }
       else
       {
            Mp3File file = new Mp3File(MusicProComponents.file.getAbsolutePath());
            comments = file.getId3v1Tag().getComment();
       }
        
        return comments;
    }
    public static String getYear() throws IOException, UnsupportedTagException, InvalidDataException
    {
       if(MusicProComponents.file == null)
       {
           year = "Unknown";
       }
       else
       {
            Mp3File file = new Mp3File(MusicProComponents.file.getAbsolutePath());
            year = file.getId3v1Tag().getYear();
       }
        
        return year;
    }
    public static String getGenre() throws IOException, UnsupportedTagException, InvalidDataException
    {
       if(MusicProComponents.file == null)
       {
           genre = "Unknown";
       }
       else
       {
            Mp3File file = new Mp3File(MusicProComponents.file.getAbsolutePath());
            genre = file.getId3v1Tag().getGenreDescription();
       }
        
        return genre;
    }
    public static String getTrack() throws IOException, UnsupportedTagException, InvalidDataException
    {
       if(MusicProComponents.file == null)
       {
           track = "Unknown";
       }
       else
       {
            Mp3File file = new Mp3File(MusicProComponents.file.getAbsolutePath());
            track = file.getId3v1Tag().getTrack();//+"-"+file.getId3v1Tag().getTitle();
            if(track == null)
            {
                track = file.getId3v1Tag().getTitle();
            }
            else
            {
                track = MusicProComponents.file.getName();
            }
       }
        
        return track;
    }
    public static float getVolumeValue() throws Exception
    {
        try
        {
            String aline;
            FileReader fileReader = new FileReader("volume");
            BufferedReader reader = new BufferedReader(fileReader);

            while((aline= reader.readLine()) !=null)
            {
                System.out.println(aline);
                f = Float.parseFloat(aline);
               // MusicProComponents.volSlider.setValue((int)f*100);
            }
            fileReader.close();
        }
        catch(IOException d)
        {
            JOptionPane.showMessageDialog(null, "Generating Sound Settings");
            f = (float) 1.0;
            saveVolumeValue();
            ExtendedMethods.printMixerDetails();
            MusicProComponents.volSlider.setToolTipText(""+MusicProComponents.volSlider.getValue());
            ExtendedMethods.line.close();
        }
        
        return f;
    }
    public static void checkIfIsPlaying()
    {
        if(MusicProComponents.condition == "stop")
        {
           if(MusicProComponents.listModel.getSize() > 0)
           {
                MusicProComponents.reservedListName = new DefaultListModel();
                MusicProComponents.reserveListDirectory = new DefaultListModel();
                for(int i = 0; i < MusicProComponents.listModel.getSize();i++)
                {
                    MusicProComponents.reservedListName.addElement(MusicProComponents.listModel.getElementAt(i));
                    MusicProComponents.reserveListDirectory.addElement(MusicProComponents.listModel2.getElementAt(i));
                    System.out.println("\n"+MusicProComponents.reserveListDirectory.getElementAt(i));
                }
                MusicProComponents.keptIndex = MusicProComponents.index;
                MusicProComponents.listModel.clear();
                MusicProComponents.listModel2.clear();
                MusicPro11.stopPlay();
                MusicProComponents.condition = "play";
                MusicProComponents.playItem.setText(MusicProComponents.condition);
           }
           else
           {
           }
        }
        else
        {
        }
    }
    public static void indexCurrentList() throws IOException
    {
        File indexFile = new File("CASLIndex.idx");
        FileWriter wrtIndex = new FileWriter(indexFile);
        wrtIndex.write(""+MusicProComponents.index);
        wrtIndex.close();
    }
    public static int getIndexOfCurrent() throws FileNotFoundException, IOException
    {
        String readIndex;
        File toReadFile = new File("CASLIndex.idx");
        FileReader fileReader = new FileReader(toReadFile);
        BufferedReader bufferedReader = new BufferedReader(fileReader);

        while((readIndex = bufferedReader.readLine()) != null)
        {
            if(readIndex.isEmpty() || readIndex == null)
            {
                MusicProComponents.index = 0;
                indexCurrentList();
            }
            else
            {
                MusicProComponents.index = Integer.parseInt(readIndex);
            }
        }
        return MusicProComponents.index;
    }
    public static void indexLastPlayed() throws IOException
    {
        File indexFile = new File("LastPlayedIndex.idx");
        FileWriter wrtIndex = new FileWriter(indexFile);
        wrtIndex.write(""+MusicProComponents.index);
        wrtIndex.close();
    }
    public static int getIndexxOfLastPlayed() throws FileNotFoundException, IOException
    {
        String readIndex;
        File toReadFile = new File("LastPlayedIndex.idx");
        FileReader fileReader = new FileReader(toReadFile);
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        
        while((readIndex = bufferedReader.readLine()) != null)
        {
            if(readIndex.isEmpty() || readIndex == null)
            {
                MusicProComponents.index = 0;
                indexLastPlayed();
            }
            else
            {
                MusicProComponents.index = Integer.parseInt(readIndex);
            }
        }
        
        return MusicProComponents.index;
    }
    public static void saveCurrentList() throws IOException
    {
        File lastPlayed = new File("CASL.mpr");
        FileWriter fileWriter = new FileWriter(lastPlayed, false);
        if(!MusicProComponents.listModel2.isEmpty())
        {
            for(int i = 0; i < MusicProComponents.listModel2.getSize() ; i++)
            {
                fileWriter.write(MusicProComponents.listModel2.getElementAt(i)+"\n");
                System.out.println("Songs: "+MusicProComponents.listModel2.getElementAt(i)+"\n");
            }
        }
        else
        {
        }
        fileWriter.close();
        indexCurrentList();
    }
    public static void backToCurrentList() throws FileNotFoundException, IOException
    {
        String song;
        try 
        {
            FileReader fileReader = new FileReader("CASL.mpr");
            BufferedReader bufferedReader = new BufferedReader(fileReader);

            while((song = bufferedReader.readLine()) != null)
            {
                System.out.println("Last Played Songs: "+song+"\n");
                MusicProComponents.listModel2.addElement(new File(song));
                File fileForList = new File(song);
                MusicProComponents.listModel.addElement(fileForList.getName());
            }
            fileReader.close();
            MusicProComponents.file = (File) MusicProComponents.listModel2.getElementAt(getIndexOfCurrent());
            MusicPro11.createPlay();
            MusicProComponents.list.setSelectedIndex(getIndexOfCurrent());
            MusicProComponents.condition = "stop";
            MusicProComponents.playItem.setText(MusicProComponents.condition);
        } 
        catch (Exception e) 
        {
            try 
            {
                doSimplelastPlayed();
            } 
            catch (Exception ex) 
            {
                MusicPro11.openFile();
            }
            
        }
    }
    public static void saveToLastPlayed() throws IOException
    {
        if(!MusicProComponents.listModel2.isEmpty())
        {
            File lastPlayed = new File("lastplayed.mpr");
            FileWriter fileWriter = new FileWriter(lastPlayed, false);
            if(!MusicProComponents.listModel2.isEmpty())
            {
                for(int i = 0; i < MusicProComponents.listModel2.getSize() ; i++)
                {
                    fileWriter.write(MusicProComponents.listModel2.getElementAt(i)+"\n");
                    System.out.println("Songs: "+MusicProComponents.listModel2.getElementAt(i)+"\n");
                }
            }
            else
            {
            }
            indexLastPlayed();
            fileWriter.close();
        }
        else
        {
        }
    }
    public static void doSimplelastPlayed() throws FileNotFoundException, IOException
    {
        MusicProComponents.reserveListDirectory = new DefaultListModel();
        if(!MusicProComponents.listModel2.isEmpty() && MusicProComponents.condition == "stop")
        {
            MusicPro11.stopPlay();
            MusicProComponents.listModel2.clear();
            MusicProComponents.listModel.clear();
            String song;
            FileReader fileReader = new FileReader("lastplayed.mpr");
            BufferedReader bufferedReader = new BufferedReader(fileReader);

            while((song = bufferedReader.readLine()) != null)
            {
                System.out.println("Last Played Songs: "+song+"\n");
                MusicProComponents.listModel2.addElement(new File(song));
                MusicProComponents.reserveListDirectory.addElement(new File(song));
                File fileForList = new File(song);
                MusicProComponents.listModel.addElement(fileForList.getName());
            }
            fileReader.close();
            MusicProComponents.file = (File) MusicProComponents.listModel2.getElementAt(getIndexxOfLastPlayed());
            MusicPro11.createPlay();
            MusicProComponents.list.setSelectedIndex(getIndexxOfLastPlayed());
            MusicProComponents.condition = "stop";
            MusicProComponents.playItem.setText(MusicProComponents.condition);
            playingLast = true;
        }
        else
        {
        }
    }
    public static void openLastPlayedFile() throws FileNotFoundException, IOException
    {
        MusicProComponents.reserveListDirectory = new DefaultListModel();
        if(MusicProComponents.listModel2.isEmpty())
        {
            if(MusicProComponents.condition == "play")
            {
                String song;
                File fileLast = new File("lastplayed.mpr");
                if(fileLast.exists())
                {
                    FileReader fileReader = new FileReader(fileLast);
                    BufferedReader bufferedReader = new BufferedReader(fileReader);

                    while((song = bufferedReader.readLine()) != null)
                    {
                        System.out.println("Last Played Songs: "+song+"\n");
                        MusicProComponents.listModel2.addElement(new File(song));
                        MusicProComponents.reserveListDirectory.addElement(new File(song));
                        File fileForList = new File(song);
                        MusicProComponents.listModel.addElement(fileForList.getName());
                    }
                    fileReader.close();
                    MusicProComponents.file = (File) MusicProComponents.listModel2.getElementAt(getIndexxOfLastPlayed());
                    MusicPro11.createPlay();
                    MusicProComponents.list.setSelectedIndex(getIndexxOfLastPlayed());
                    MusicProComponents.condition = "stop";
                    MusicProComponents.playItem.setText(MusicProComponents.condition);
                    playingLast = true;
                }
                else if(!fileLast.exists())
                {
                    JOptionPane.showMessageDialog(null, "No last played tracks could be found.", "Last Played", JOptionPane.PLAIN_MESSAGE);
                    MusicPro11.openFile();
                }
            }
            else if(MusicProComponents.condition == "stop")
            {
            }
        }
        else if(!MusicProComponents.listModel2.isEmpty())
        {
            if(MusicProComponents.condition == "play")
            {
            }
            else if(MusicProComponents.condition == "stop")
            {
                if(playingLast != true)
                {
                    saveCurrentList();
                    doSimplelastPlayed();
                    playingLast = true;
                }
                else
                {
                    
                    if(MusicProComponents.listModel2.getSize() > MusicProComponents.reserveListDirectory.getSize())
                    {
                        saveToLastPlayed();
                        MusicPro11.stopPlay();
                        MusicProComponents.listModel2.clear();
                        MusicProComponents.listModel.clear();
                        backToCurrentList();
                        playingLast = false;
                    }
                    else
                    {
                        MusicPro11.stopPlay();
                        MusicProComponents.listModel2.clear();
                        MusicProComponents.listModel.clear();
                        backToCurrentList();
                        playingLast = false;
                    }
                }
            }
        }
    }
}