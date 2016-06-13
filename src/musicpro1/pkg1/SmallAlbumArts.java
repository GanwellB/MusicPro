/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package musicpro1.pkg1;

import com.mpatric.mp3agic.ID3v2;
import com.mpatric.mp3agic.InvalidDataException;
import com.mpatric.mp3agic.Mp3File;
import com.mpatric.mp3agic.UnsupportedTagException;
import java.awt.Image;
import javax.swing.Icon;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import static musicpro1.pkg1.MusicPro11.musicWindow;

/**
 *
 * @author Ganwell
 */
public class SmallAlbumArts extends JPanel
{
    static JLabel label;
    static int x =0, y = 0, wid = 100, hei = 100;
    static Timer timer;
    
    public static void painter()
    {
        label = new JLabel();
        if(MusicProComponents.listModel2.isEmpty())
        {
        }
        else
        {
            try 
            {
                Mp3File song = new Mp3File(MusicProComponents.listModel2.getElementAt(MusicProComponents.index).toString());
                if(song.hasId3v2Tag())
                {
                    ID3v2 d3v2 = song.getId3v2Tag();
                    byte[] imageData = d3v2.getAlbumImage();
                    if(imageData == null || imageData.length<=0)
                    {
                        ImageIcon image =  new ImageIcon("images/tone.png");
                        label.setIcon(image);
                    }
                    else
                    {
                        Icon icon = (Icon)ImageIO.read(new ByteArrayInputStream(imageData));
                        MusicProComponents.image = ImageIO.read(new ByteArrayInputStream(imageData));
                        label.setIcon(icon);
                    }
                }
            }
            catch (IOException ex) 
            {
            }
            catch (UnsupportedTagException ex) 
            {
            }
            catch (InvalidDataException ex) 
            {
            }
        }
        MusicPro11.musicWindow.add(label);
    }
    public static  void createSmallAlbumArt()
    {
        painter();
        timer = new Timer();
        timer.schedule(new TimerTask() 
        {

            @Override
            public void run()
            {
                x = x+100;
                if(x == 100)
                {
                    y = 0;
                    wid = 100;
                    hei = 100;
                    label.setBounds(x, y, wid, hei);
                }
                if(x>100 && y <= 0)
                {
                    y = x+100;
                    wid = 100;
                    hei = 100;
                    label.setBounds(x, y, wid, hei);
                }
                if(x >= 400 || y >= 400)
                {
                    x = 0;
                    y = 0;
                    wid = 340;
                    hei = 378;
                    label.setBounds(x, y, wid, hei);
                }
            }
        }, 10, 10);
    }
}