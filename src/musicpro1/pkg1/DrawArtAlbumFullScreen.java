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
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

/**
 *
 * @author Ganwell
 */
public class DrawArtAlbumFullScreen extends JPanel
{
    public void paint(Graphics g)
    {
        Graphics2D gd = (Graphics2D)g;
        
            if(MusicProComponents.listModel2.isEmpty())
            {
                Image image = (Image) new ImageIcon("images/tone.png").getImage();
                gd.drawImage(image, 0, 0, 400, 500, this);
                //gd.drawImage(image, 0, 0, this);
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
                            Image image = (Image) new ImageIcon("images/tone.png").getImage();
                            gd.drawImage(image, 0, 0,400, 500, this);
                        }
                        else
                        {
                            MusicProComponents.image = ImageIO.read(new ByteArrayInputStream(imageData));
                            gd.drawImage(MusicProComponents.image, 0,0,400, 500,this);
                           // System.out.println(""+MusicProComponents.image.getWidth()+" "+MusicProComponents.image.getHeight());
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
    }
}