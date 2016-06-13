/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package musicpro1.pkg1;

import com.mpatric.mp3agic.InvalidDataException;
import com.mpatric.mp3agic.UnsupportedTagException;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.media.Manager;
import javax.swing.*;

/**
 *
 * @author Developer
 */
public class MakeMProFullScreen implements ActionListener
{
    private static Dimension dimension;
    private static Toolkit toolkit = Toolkit.getDefaultToolkit();
    public static JFrame musicproFullScreen;
    public static JButton fullScreenBtn, playBtn, nxtBtn, prvBtn;
    public static JLabel artistName, trackName;
    public static Font displayFont = new Font(null, Font.BOLD, 35);
    DrawArtAlbumFullScreen artAlbum;
    public MakeMProFullScreen()
    {
        //makeScreenFull();
    }
    public final void makeScreenFull()
    {
        dimension = toolkit.getScreenSize();
        musicproFullScreen = new JFrame();
        musicproFullScreen.setUndecorated(true);
        musicproFullScreen.setSize(dimension);
        musicproFullScreen.setVisible(true);
        musicproFullScreen.getContentPane().setLayout(null);
        musicproFullScreen.getContentPane().setBackground(Color.black);
        musicproFullScreen.addMouseMotionListener(new MouseMotionListener() 
        {

            @Override
            public void mouseDragged(MouseEvent e) 
            {
            }

            @Override
            public void mouseMoved(MouseEvent e) 
            {
                prvBtn.setVisible(true);
                if(e.getID() != MouseEvent.MOUSE_MOVED)
                {
                    prvBtn.setVisible(false);
                }
            }
        });
        Image icon = new ImageIcon("images/windowIcon.png").getImage();
        musicproFullScreen.setIconImage(icon);
        
        artAlbum = new DrawArtAlbumFullScreen();
        artAlbum.setBounds(((dimension.width / 2)-200), 5, 400, 500);
        
        ImageIcon flscreenIcon = new ImageIcon("images/restscreen.png");
        fullScreenBtn  = new JButton(flscreenIcon);
        fullScreenBtn.setBounds(dimension.width - 100, 22, flscreenIcon.getIconWidth()+10,flscreenIcon.getIconHeight()+ 10);
        fullScreenBtn.setBackground(Color.white);
        fullScreenBtn.addMouseListener(new MouseAdapter() 
        {
            @Override
            public void mouseEntered(MouseEvent me)
            {
                fullScreenBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
            }
            @Override
            public void mouseExited(MouseEvent e)
            {
                fullScreenBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
            }
        });
        fullScreenBtn.addActionListener(this);
        //
        ImageIcon prvIcon = new ImageIcon("images/previous.jpg");
        prvBtn = new JButton(prvIcon);
        prvBtn.setBackground(Color.white);
        prvBtn.setVisible(false);
        prvBtn.setBounds(20, dimension.height - prvIcon.getIconHeight() - 20, prvIcon.getIconWidth() + 10, prvIcon.getIconHeight() + 5);
        prvBtn.addActionListener(this);
        prvBtn.addMouseListener(new MouseAdapter() 
        {
            @Override
            public void mouseEntered(MouseEvent me)
            {
                prvBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
            }
        });
        
        //
        ImageIcon playIcon = new ImageIcon("images/playp.jpg");
        playBtn = new JButton(playIcon);
        playBtn.setBackground(Color.white);
        playBtn.setBounds(prvBtn.getX() + prvBtn.getWidth() + 20, dimension.height - prvIcon.getIconHeight() - 20, prvIcon.getIconWidth() + 10, prvIcon.getIconHeight() + 5);
        playBtn.addActionListener(this);
        playBtn.addMouseListener(new MouseAdapter() 
        {
            @Override
            public void mouseEntered(MouseEvent me)
            {
                playBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
            }
        });
        //
        
        ImageIcon nextIcon = new ImageIcon("images/nxt.jpg");
        nxtBtn = new JButton(nextIcon);
        nxtBtn.setBackground(Color.white);
        nxtBtn.setBounds(playBtn.getX() + playBtn.getWidth() + 20,  dimension.height - prvIcon.getIconHeight() - 20, nextIcon.getIconWidth() + 10, nextIcon.getIconHeight() + 5);
        nxtBtn.addActionListener(this);
        nxtBtn.addMouseListener(new MouseAdapter() 
        {
            @Override
            public void mouseEntered(MouseEvent me)
            {
                nxtBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
            }
        });
        musicproFullScreen.add(nxtBtn);
        musicproFullScreen.add(prvBtn);
        musicproFullScreen.add(playBtn);
        try 
        {
            showArtistAndTrack();
        } 
        catch (IOException ex) 
        {
            Logger.getLogger(MakeMProFullScreen.class.getName()).log(Level.SEVERE, null, ex);
        } 
        catch (UnsupportedTagException ex)
        {
            Logger.getLogger(MakeMProFullScreen.class.getName()).log(Level.SEVERE, null, ex);
        } 
        catch (InvalidDataException ex) 
        {
            Logger.getLogger(MakeMProFullScreen.class.getName()).log(Level.SEVERE, null, ex);
        }
        musicproFullScreen.add(artAlbum);
        musicproFullScreen.add(fullScreenBtn);
        
        
    }
    public void showArtistAndTrack() throws IOException, UnsupportedTagException, InvalidDataException
    {
        artistName = new JLabel();
        artistName.setText(ExtendedMethods.getArtist());
        artistName.setFont(displayFont);
        artistName.setForeground(new Color(123, 12, 234));//005ffa71
        artistName.setBounds(dimension.width - ((dimension.width / 2) + ((dimension.width / 2) - 100)), 260, dimension.width / 2, 60);
        
        trackName = new JLabel();
        trackName.setText(ExtendedMethods.getTrack());
        trackName.setFont(displayFont);
        trackName.setForeground(new Color(123, 12, 234));
        trackName.setBounds(artistName.getX(), (artistName.getY() + artistName.getHeight()) + 20, dimension.width / 2, 60);
        
        musicproFullScreen.add(artistName);
        musicproFullScreen.add(trackName);
    }
    public void playNextTrack()
    {
        if(!MusicProComponents.listModel2.isEmpty())
        {
            MusicProComponents.index++;
            System.out.print(MusicProComponents.listModel.getSize()-1);
            if(MusicProComponents.index>(MusicProComponents.listModel.getSize()-1))
            {
                MusicProComponents.index=0;
                MusicProComponents.list.setSelectedIndex(MusicProComponents.index);
                MusicProComponents.file=(File)MusicProComponents.listModel2.getElementAt(MusicProComponents.index);
                if(MusicProComponents.condition=="stop")
                {
                    //MusicProComponents.file= (File)MusicProComponents.listModel2.getElementAt(index);
                    //MusicProComponents.playButton.setText(MusicProComponents.condition);
                    MusicProComponents.playItem.setText(MusicProComponents.condition);
                    MusicPro11.stopPlay();
                    createPlay();
                    MusicProComponents.list.setSelectedIndex(MusicProComponents.index);
                }
                else if(MusicProComponents.condition=="play")
                {
                    //MusicProComponents.playButton.setText(MusicProComponents.condition="stop");
                    MusicProComponents.playItem.setText(MusicProComponents.condition="stop");
                    createPlay();
                    MusicProComponents.list.setSelectedIndex(MusicProComponents.index);
                }

            }

            else
            {
                MusicProComponents.list.setSelectedIndex(MusicProComponents.index);
                MusicProComponents.file = (File)MusicProComponents.listModel2.getElementAt(MusicProComponents.index);

                if(MusicProComponents.condition=="stop")
                {
                    //MusicProComponents.playButton.setText(MusicProComponents.condition);
                    MusicProComponents.playItem.setText(MusicProComponents.condition);
                    MusicPro11.stopPlay();
                    createPlay();
                    MusicProComponents.list.setSelectedIndex(MusicProComponents.index);
                }
                else if(MusicProComponents.condition=="play")
                {
                    //MusicProComponents.playButton.setText(MusicProComponents.condition="stop");
                    MusicProComponents.playItem.setText(MusicProComponents.condition="stop");
                    createPlay();
                    MusicProComponents.list.setSelectedIndex(MusicProComponents.index);
                }
            }
        }
        else
        {
        }
        musicproFullScreen.repaint();
    }
    public void playPreviousTrack()
    {
        if(!MusicProComponents.listModel2.isEmpty())
        {
            MusicProComponents.index--;
            System.out.println(MusicProComponents.listModel.getSize()-1);
            if(MusicProComponents.index<0)
            {
                MusicProComponents.index=MusicProComponents.listModel.getSize()-1;
                MusicProComponents.list.setSelectedIndex(MusicProComponents.index);
                MusicProComponents.file=(File)MusicProComponents.listModel2.getElementAt(MusicProComponents.index);

                if(MusicProComponents.condition=="stop")
                {
                    //MusicProComponents.playButton.setText(MusicProComponents.condition);
                    MusicProComponents.playItem.setText(MusicProComponents.condition);
                    MusicPro11.stopPlay();
                    createPlay();
                    MusicProComponents.list.setSelectedIndex(MusicProComponents.index);
                }
                else if(MusicProComponents.condition=="play")
                {
                    //MusicProComponents.playButton.setText(MusicProComponents.condition="stop");
                    MusicProComponents.playItem.setText(MusicProComponents.condition="stop");
                    createPlay();
                    MusicProComponents.list.setSelectedIndex(MusicProComponents.index);
                }
            }
            else
            {
                MusicProComponents.list.setSelectedIndex(MusicProComponents.index);
                MusicProComponents.file = (File)MusicProComponents.listModel2.getElementAt(MusicProComponents.index);
                if(MusicProComponents.condition=="stop")
                {
                    //MusicProComponents.playButton.setText(MusicProComponents.condition);
                    MusicProComponents.playItem.setText(MusicProComponents.condition);
                    MusicPro11.stopPlay();
                    createPlay();
                    MusicProComponents.list.setSelectedIndex(MusicProComponents.index);
                }
                else if(MusicProComponents.condition=="play")
                {
                    //MusicProComponents.playButton.setText(MusicProComponents.condition="stop");
                    MusicProComponents.playItem.setText(MusicProComponents.condition="stop");
                    createPlay();
                    MusicProComponents.list.setSelectedIndex(MusicProComponents.index);
                }
            }
        }
        else
        {
        }
        musicproFullScreen.repaint();
    }
    public static void createPlay()
    {
        if(MusicProComponents.file==null)
        return;
        
        try
        {
            //MusicProComponents.file = (File) list.getSelectedValue();
            //list.setSelectedIndex(index);
            MusicProComponents.player = Manager.createPlayer(MusicProComponents.file.toURI().toURL());
            MusicProComponents.player.addControllerListener(new EventHandler());
            MusicProComponents.player.start();
            //player.syncStart(new Time(player.getMediaNanoseconds()));
            
        }
        catch(Exception e)
        {
            JOptionPane.showMessageDialog(null, "Invalid File or Location","Error Loading File",JOptionPane.ERROR_MESSAGE);
        }
        try 
        {
            artistName.setText(ExtendedMethods.getArtist());
            trackName.setText(ExtendedMethods.getTrack());
            MusicProComponents.trackDur.setText(TrackDuration.getTrackDuration());
            MusicProComponents.timeElapsed.setText(IncrementingTrackTime.displayTimeElapsed());
            IncrementingTrackTime.incrementTime();
            
        } catch (IOException ex) {
            Logger.getLogger(MusicPro11.class.getName()).log(Level.SEVERE, null, ex);
        } catch (UnsupportedTagException ex) {
            Logger.getLogger(MusicPro11.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InvalidDataException ex) {
            Logger.getLogger(MusicPro11.class.getName()).log(Level.SEVERE, null, ex);
        }
        MusicPro11.maxTime = (int)MusicProComponents.player.getDuration().getSeconds();
    }
    public static void playMusic()
    {
        if(!MusicProComponents.listModel2.isEmpty())
           {
                if(MusicProComponents.condition.equals("play"))
                { 
                    MusicProComponents.list.setSelectedIndex(MusicProComponents.index);
                    if(MusicProComponents.file==null || MusicProComponents.file!=null)
                    {
                          MusicProComponents.index= MusicProComponents.list.getSelectedIndex();
                          MusicProComponents.file = (File) MusicProComponents.listModel2.getElementAt(MusicProComponents.index);
                    }
                    createPlay();
                    MusicProComponents.condition = "stop";
                    //MusicProComponents.playButton.setText(MusicProComponents.condition);
                    MusicProComponents.playItem.setText(MusicProComponents.condition);
                }
                else if(MusicProComponents.condition.equals("stop"))
                {
                    MusicPro11.pauseTrack();
                    MusicProComponents.condition = "play";
                    //MusicProComponents.playButton.setText(MusicProComponents.condition);
                    MusicProComponents.playItem.setText(MusicProComponents.condition);
                }
           }
           else
           {
               
           }
    }
    @Override
    public void actionPerformed(ActionEvent e) 
    {
        Object o = e.getSource();
        
        if(o.equals(fullScreenBtn))
        {
            musicproFullScreen.dispose();
            MusicPro11.musicWindow.show();
        }
        if(o.equals(playBtn))
        {
            playMusic();
        }
        if(o.equals(prvBtn))
        {
            playPreviousTrack();
        }
        if(o.equals(nxtBtn))
        {
            playNextTrack();
        }
    }
}
