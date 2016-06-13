package musicpro1.pkg1;

import com.mpatric.mp3agic.InvalidDataException;
import com.mpatric.mp3agic.UnsupportedTagException;
import com.sun.java.swing.plaf.motif.MotifBorders;
import java.awt.*;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetDragEvent;
import java.awt.dnd.DropTargetDropEvent;
import java.awt.dnd.DropTargetEvent;
import java.awt.dnd.DropTargetListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Calendar;
import java.util.Iterator;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.media.ControllerEvent;
import javax.media.ControllerListener;
import javax.media.EndOfMediaEvent;
import javax.media.Manager;
import javax.media.RealizeCompleteEvent;
import javax.media.Time;
import javax.media.bean.playerbean.MediaPlayerResource;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.KeyStroke;
import javax.swing.ListSelectionModel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.filechooser.FileFilter;
import static musicpro1.pkg1.MusicPro11.createPlay;

/**
 *
 * @author Ganwell
 */
public class MusicPro11 implements ActionListener,Runnable
{
    public static JFrame musicWindow;
    DrawArtAlbum artAlbum;
    static int x =0 ,volValue = 0, y = 0, wid = 100, hei = 100, cMin = 0, cSec = 0;
    Timer timer;
    public static int timerValue = 0, maxTime = 0;
    static Image icon;
    public static boolean trackPaused  = false;
    public static Time mediaTime;
    public MusicPro11()
    {
        UserInterFace();
        try 
        {
            Drivers drivers = new Drivers();
            ConnectToTheDatabase cttd = new ConnectToTheDatabase();
        }
        catch (IOException ex)
        {
            Logger.getLogger(MusicPro11.class.getName()).log(Level.SEVERE, null, ex);
        } 
        musicWindow.repaint();
    }
    public void UserInterFace()
    {
        musicWindow = new JFrame("Music Pro");
        musicWindow.setSize(345, 400);
        musicWindow.setResizable(false);
        musicWindow.setLocationRelativeTo(null);
        musicWindow.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        musicWindow.getContentPane().setLayout(null);
        musicWindow.getContentPane().setBackground(Color.white);
        musicWindow.addWindowListener(new WindowAdapter() 
        {
            @Override
            public void windowClosing(WindowEvent event)
            {
                if(MusicProComponents.listShower == "showing")
                {
                    try 
                    { 
                        ExtendedMethods.saveToLastPlayed();
                        ExtendedMethods.saveVolumeValue();
                        System.exit(0);
                    } 
                    catch (IOException ex) 
                    {
                        Logger.getLogger(MusicPro11.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                else if(MusicProComponents.listShower == "notshowing")
                {
                    MusicProComponents.listShower = "showing";
                    MusicProComponents.playlist.setText(MusicProComponents.lister="Playlist");
                    musicWindow.setSize(345, 400);
                    musicWindow.setLocationRelativeTo(null);
                }
            }
        });
        icon = new ImageIcon("images/windowIcon.png").getImage();
        musicWindow.setIconImage(icon);
        musicWindow.setVisible(true);
        GUIComponents();
    }
    private void GUIComponents()
    {
        MusicProComponents.playButton = new JButton(MusicProComponents.playIcon);
        MusicProComponents.playButton.addActionListener(this);
        MusicProComponents.playButton.setBackground(Color.white);
        MusicProComponents.playButton.setBounds(205, 330, 40, 20);
        
        MusicProComponents.searchButton = new JButton(MusicProComponents.searchIcon);
        MusicProComponents.searchButton.addActionListener(this);
        MusicProComponents.searchButton.setVisible(true);
        MusicProComponents.searchButton.setBackground(Color.white);
        MusicProComponents.searchButton.setBounds(10, 22, 25, 20);
        MusicProComponents.searchButton.addMouseListener(new MouseAdapter() 
        {
            @Override
            public void mouseEntered(MouseEvent me)
            {
                MusicProComponents.searchButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
                MusicProComponents.searchButton.setVisible(true);
            }
            @Override
            public void mouseExited(MouseEvent e)
            {
                MusicProComponents.searchButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
                MusicProComponents.searchButton.setVisible(false);
            }
        });
        
        MusicProComponents.stopButton = new JButton(MusicProComponents.stopIcon);
        MusicProComponents.stopButton.addActionListener(this);
        MusicProComponents.stopButton.setBackground(Color.white);
        MusicProComponents.stopButton.setBounds(205, 350, 40, 20);
        
        MusicProComponents.prevButton = new JButton(MusicProComponents.prevIcon);
        MusicProComponents.prevButton.addActionListener(this);
        MusicProComponents.prevButton.setBackground(Color.white);
        MusicProComponents.prevButton.setBounds(165, 340, 40, 20);
        
        MusicProComponents.nextButton = new JButton(MusicProComponents.nextIcon);
        MusicProComponents.nextButton.addActionListener(this);
        MusicProComponents.nextButton.setBackground(Color.white);
        MusicProComponents.nextButton.setBounds(245, 340, 40, 20);
        
        MusicProComponents.hide = new JButton(MusicProComponents.hideIcon);
        MusicProComponents.hide.addActionListener(this);
        MusicProComponents.hide.setBackground(Color.white);
        MusicProComponents.hide.setBounds(285, 340, 40, 20);
        
        MusicProComponents.component = new list();
        MusicProComponents.component.setBounds(345, 20, 240, 340);
        MusicProComponents.component.setAutoscrolls(true);
        MusicProComponents.component.setBorder(new MotifBorders.BevelBorder(true, Color.blue, Color.blue));
        musicWindow.add(MusicProComponents.component);
        MusicProComponents.component.setVisible(true);
        
        MusicProComponents.bar = new JMenuBar();
        MusicProComponents.bar.setBounds(0, 0, 660, 20);
        MusicProComponents.bar.setVisible(false);
        MusicProComponents.bar.addMouseListener(new MouseAdapter() 
        {
            public void mouseEntered(MouseEvent e)
            {
                MusicProComponents.bar.setVisible(true);
            }
            public void mouseExited(MouseEvent e)
            {
                MusicProComponents.bar.setVisible(false);
            }
        });
        ImageIcon flscreenIcon = new ImageIcon("images/fullscreen.png");
        MusicProComponents.fullScreenBtn  = new JButton(flscreenIcon);
        MusicProComponents.fullScreenBtn.setBounds(280, 22, flscreenIcon.getIconWidth()+10,flscreenIcon.getIconHeight()+ 10);
        MusicProComponents.fullScreenBtn.setBackground(Color.white);
        MusicProComponents.fullScreenBtn.setVisible(true);
        MusicProComponents.fullScreenBtn.addMouseListener(new MouseAdapter() 
        {
            @Override
            public void mouseEntered(MouseEvent me)
            {
                MusicProComponents.fullScreenBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
                MusicProComponents.fullScreenBtn.setVisible(true);
            }
            @Override
            public void mouseExited(MouseEvent e)
            {
                MusicProComponents.fullScreenBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
                MusicProComponents.fullScreenBtn.setVisible(false);
            }
        });
        MusicProComponents.fullScreenBtn.addActionListener(this);
        
        MusicProComponents.savePlaylist = new JMenuItem("Save Playlist");
        MusicProComponents.savePlaylist.addActionListener(this);
        MusicProComponents.savePlaylist.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S,ActionEvent.CTRL_MASK));
        MusicProComponents.savePlaylist.addMouseListener(new MouseAdapter() 
        {
            public void mouseEntered(MouseEvent e)
            {
                MusicProComponents.bar.setVisible(true);
            }
            public void mouseExited(MouseEvent e)
            {
                MusicProComponents.bar.setVisible(false);
            }
        });
        
        MusicProComponents.OpenPlaylist = new JMenuItem("Open Playlist");
        MusicProComponents.OpenPlaylist.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O,ActionEvent.CTRL_MASK));
        MusicProComponents.OpenPlaylist.addActionListener(this);
        MusicProComponents.OpenPlaylist.addMouseListener(new MouseAdapter()
        {
            public void mouseEntered(MouseEvent e)
            {
                MusicProComponents.bar.setVisible(true);
            }
            public void mouseExited(MouseEvent e)
            {
                MusicProComponents.bar.setVisible(false);
            }
        });
        
        MusicProComponents.OpenItem = new JMenuItem("Open File");
        MusicProComponents.OpenItem.addActionListener(this);
        MusicProComponents.OpenItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O,0));
        MusicProComponents.OpenItem.addMouseListener(new MouseAdapter()
        {
            public void mouseEntered(MouseEvent e)
            {
                MusicProComponents.bar.setVisible(true);
            }
            public void mouseExited(MouseEvent e)
            {
                MusicProComponents.bar.setVisible(false);
            }
        });
        
        MusicProComponents.Playback = new JMenu("Playback");
        MusicProComponents.Playback.addMouseListener(new MouseAdapter()
        {
            public void mouseEntered(MouseEvent e)
            {
                MusicProComponents.bar.setVisible(true);
                MusicProComponents.Playback.setForeground(Color.cyan.brighter());
            }
            public void mouseExited(MouseEvent e)
            {
                MusicProComponents.bar.setVisible(false);
                MusicProComponents.Playback.setForeground(Color.black);
            }
        });
        
        MusicProComponents.viewSet = new JMenu("Help");
        MusicProComponents.viewSet.addMouseListener(new MouseAdapter()
        {
            public void mouseEntered(MouseEvent e)
            {
                MusicProComponents.bar.setVisible(true);
                MusicProComponents.viewSet.setForeground(Color.cyan.brighter());
            }
            public void mouseExited(MouseEvent e)
            {
                MusicProComponents.bar.setVisible(false);
                MusicProComponents.viewSet.setForeground(Color.black);
            }
        }
        );
        
        MusicProComponents.menu = new JMenu("Media");
        MusicProComponents.menu.addMouseListener(new MouseAdapter() 
        {
            public void mouseEntered(MouseEvent e)
            {
                MusicProComponents.bar.setVisible(true);
                MusicProComponents.menu.setForeground(Color.cyan.brighter());
            }
            public void mouseExited(MouseEvent e)
            {
                MusicProComponents.bar.setVisible(false);
                MusicProComponents.menu.setForeground(Color.black);
            }
        });
        MusicProComponents.playItem = new JMenuItem("Pause");
        MusicProComponents.playItem.addActionListener(this);
        MusicProComponents.playItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_SPACE,0));
        MusicProComponents.playItem.addMouseListener(new MouseAdapter() 
        {
            public void mouseEntered(MouseEvent e)
            {
                MusicProComponents.bar.setVisible(true);
            }
            public void mouseExited(MouseEvent e)
            {
                MusicProComponents.bar.setVisible(false);
            }
        });
        
        MusicProComponents.trackDetails = new JMenuItem("Track Details");
        MusicProComponents.trackDetails.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_T,ActionEvent.CTRL_MASK));
        MusicProComponents.trackDetails.addActionListener(this);
        MusicProComponents.trackDetails.addMouseListener(new MouseAdapter() 
        {
             public void mouseEntered(MouseEvent e)
            {
                MusicProComponents.bar.setVisible(true);
            }
            public void mouseExited(MouseEvent e)
            {
                MusicProComponents.bar.setVisible(false);
            }
        });
        
        MusicProComponents.prevItem = new JMenuItem("Previous");
        MusicProComponents.prevItem.addActionListener(this);
        MusicProComponents.prevItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_B,0));
        MusicProComponents.prevItem.addMouseListener(new MouseAdapter() 
        {
            public void mouseEntered(MouseEvent e)
            {
                MusicProComponents.bar.setVisible(true);
            }
            public void mouseExited(MouseEvent e)
            {
                MusicProComponents.bar.setVisible(false);
            }
        });
        MusicProComponents.nextItem = new JMenuItem("Next");
        MusicProComponents.nextItem.addActionListener(this);
        MusicProComponents.nextItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N,0));
        MusicProComponents.nextItem.addMouseListener(new MouseAdapter() 
        {
            public void mouseEntered(MouseEvent e)
            {
                MusicProComponents.bar.setVisible(true);
            }
            public void mouseExited(MouseEvent e)
            {
                MusicProComponents.bar.setVisible(false);
            }
        });
        MusicProComponents.repeatItem= new JMenuItem(MusicProComponents.repeatCondition);
        MusicProComponents.repeatItem.addActionListener(this);
        MusicProComponents.repeatItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_R,ActionEvent.CTRL_MASK));
        MusicProComponents.repeatItem.addMouseListener(new MouseAdapter() 
        {
            public void mouseEntered(MouseEvent e)
            {
                MusicProComponents.bar.setVisible(true);
            }
            public void mouseExited(MouseEvent e)
            {
                MusicProComponents.bar.setVisible(false);
            }
        });
        
        MusicProComponents.openLastPlayed= new JMenuItem("Last Played");
        MusicProComponents.openLastPlayed.addActionListener(this);
        MusicProComponents.openLastPlayed.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_H,ActionEvent.CTRL_MASK));
        MusicProComponents.openLastPlayed.addMouseListener(new MouseAdapter() 
        {
            public void mouseEntered(MouseEvent e)
            {
                MusicProComponents.bar.setVisible(true);
            }
            public void mouseExited(MouseEvent e)
            {
                MusicProComponents.bar.setVisible(false);
            }
        });
        MusicProComponents.playlist = new JMenuItem(MusicProComponents.lister);
        MusicProComponents.playlist.addActionListener(new ActionListener()
        {

            @Override
            public void actionPerformed(ActionEvent e) 
            {
                showPlayList();
            }
        });
        MusicProComponents.playlist.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_P,0));
        MusicProComponents.playlist.addMouseListener(new MouseAdapter() 
        {
            public void mouseEntered(MouseEvent e)
            {
                MusicProComponents.bar.setVisible(true);
            }
            public void mouseExited(MouseEvent e)
            {
                MusicProComponents.bar.setVisible(false);
            }
        });
        
        MusicProComponents.changeTextColor = new JMenuItem("Help");
        MusicProComponents.changeTextColor.addActionListener(this);
        MusicProComponents.changeTextColor.setToolTipText("Application guide");
        MusicProComponents.changeTextColor.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F1,0));
        MusicProComponents.changeTextColor.addMouseListener(new MouseAdapter()
        {
            public void mouseEntered(MouseEvent e)
            {
                MusicProComponents.bar.setVisible(true);
            }
            public void mouseExited(MouseEvent e)
            {
                MusicProComponents.bar.setVisible(false);
            }
        });
        
        MusicProComponents.changeTextStyle = new JMenuItem("About");
        MusicProComponents.changeTextStyle.addActionListener(this);
        MusicProComponents.changeTextStyle.setToolTipText("About the developers");
        MusicProComponents.changeTextStyle.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F4,ActionEvent.SHIFT_MASK));
        MusicProComponents.changeTextStyle.addMouseListener(new MouseAdapter()
        {
            public void mouseEntered(MouseEvent e)
            {
                MusicProComponents.bar.setVisible(true);
            }
            public void mouseExited(MouseEvent e)
            {
                MusicProComponents.bar.setVisible(false);
            }
        });
        
        MusicProComponents.viewSet.add(MusicProComponents.changeTextColor);
        MusicProComponents.viewSet.add(MusicProComponents.changeTextStyle);
        
        MusicProComponents.menu.add(MusicProComponents.OpenItem);
        MusicProComponents.menu.add(MusicProComponents.OpenPlaylist);
        MusicProComponents.menu.add(MusicProComponents.trackDetails);
        MusicProComponents.menu.add(MusicProComponents.playlist);
        MusicProComponents.menu.add(MusicProComponents.savePlaylist);
        
        MusicProComponents.Playback.add(MusicProComponents.playItem);
        MusicProComponents.Playback.add(MusicProComponents.prevItem);
        MusicProComponents.Playback.add(MusicProComponents.nextItem);
        MusicProComponents.Playback.add(MusicProComponents.repeatItem);
        MusicProComponents.Playback.add(MusicProComponents.openLastPlayed);
        
        MusicProComponents.bar.add(MusicProComponents.menu);
        MusicProComponents.bar.add(MusicProComponents.Playback);
        MusicProComponents.bar.add(MusicProComponents.viewSet);
        musicWindow.addMouseListener(new MouseAdapter()
        {
            public void mouseEntered(MouseEvent e)
            {
                MusicProComponents.bar.setVisible(true);
                MusicProComponents.searchButton.setVisible(true);
                MusicProComponents.fullScreenBtn.setVisible(true);
            }
            public void mouseExited(MouseEvent e)
            {
                MusicProComponents.bar.setVisible(false);
                MusicProComponents.searchButton.setVisible(false);
                MusicProComponents.fullScreenBtn.setVisible(false);
            }
        });
        
        DropListener dropListener = new DropListener();
        MusicProComponents.dropController = new JLabel();
        MusicProComponents.dropController.setBounds(0, 0, 660, 400);
        new DropTarget(MusicProComponents.dropController, dropListener);
        
        MusicProComponents.track = new JLabel();
        MusicProComponents.track.setBounds(40, 35, 220, 20);
        MusicProComponents.track.setForeground(Color.cyan);
        MusicProComponents.track.setFont(MusicProComponents.font);

        MusicProComponents.album = new JLabel();
        MusicProComponents.album.setBounds(40, 60, 220, 20);
        MusicProComponents.album.setForeground(Color.cyan);
        MusicProComponents.album.setFont(MusicProComponents.albumFont);

        MusicProComponents.artist = new JLabel();
        MusicProComponents.artist.setBounds(40, 85, 280, 30);
        MusicProComponents.artist.setForeground(Color.cyan);
        MusicProComponents.artist.setFont(MusicProComponents.artistFont);
        
        MusicProComponents.trackDur = new JLabel();
        MusicProComponents.trackDur.setBounds(280, 300, 280, 20);
        MusicProComponents.trackDur.setForeground(Color.red);
        MusicProComponents.trackDur.setFont(MusicProComponents.albumFont);
        musicWindow.add(MusicProComponents.trackDur);
        
        MusicProComponents.timeElapsed = new JLabel();
        MusicProComponents.timeElapsed.setBounds(180, 300, 280, 20);
        MusicProComponents.timeElapsed.setForeground(Color.green);
        MusicProComponents.timeElapsed.setFont(MusicProComponents.albumFont);
        musicWindow.add(MusicProComponents.timeElapsed);
        
        artAlbum = new DrawArtAlbum();
        artAlbum.setBounds(x, y, 340, 390);
        SmallAlbumArts.createSmallAlbumArt();
        
        MusicProComponents.volSlider = new JSlider(JSlider.HORIZONTAL);
        MusicProComponents.volSlider.setValueIsAdjusting(true);
        MusicProComponents.volSlider.setMinimum(0);
        MusicProComponents.volSlider.setToolTipText(""+MusicProComponents.volSlider.getValue());
        MusicProComponents.volSlider.setMaximum(100);
        MusicProComponents.volSlider.setBounds(30, 340, 120, 20);
        try 
        {
            
            float f = ExtendedMethods.getVolumeValue();
            double d = (double)f*100;
            volValue = (int)d;
            System.out.println("Value of Volume "+volValue+" "+d);
            MusicProComponents.volSlider.setValue(volValue);
            MusicProComponents.volSlider.setToolTipText(""+MusicProComponents.volSlider.getValue());
        }
        catch (Exception ex)
        {
            Logger.getLogger(MusicPro11.class.getName()).log(Level.SEVERE, null, ex);
        }
        MusicProComponents.volSlider.addMouseListener(new MouseAdapter() 
        {
            @Override
            public void mouseEntered(MouseEvent me)
            {
                MusicProComponents.volSlider.setCursor(new Cursor(Cursor.HAND_CURSOR));
            }
        });
        MusicProComponents.volSlider.addChangeListener(new ChangeListener() 
        {

            @Override
            public void stateChanged(ChangeEvent e)
            {
                ExtendedMethods.f = (float)MusicProComponents.volSlider.getValue()/100;
                ExtendedMethods.saveVolumeValue();
                ExtendedMethods.printMixerDetails();
                ExtendedMethods.volumeStatus();
                MusicProComponents.volSlider.setToolTipText(""+MusicProComponents.volSlider.getValue());
                ExtendedMethods.line.close();
            }
        });
        //JSpinner jSpinner = new JSpinner();
        //jSpinner.setBounds(20,20, 120, 120);
        MusicProComponents.volIconHolder = new JLabel();
        MusicProComponents.volIconHolder.setLocation(5, 343);
        ExtendedMethods.volumeStatus();
        MusicProComponents.volIconHolder.setSize( MusicProComponents.volumeIcon.getIconWidth(), MusicProComponents.volumeIcon.getIconHeight());
        MusicProComponents.volIconHolder.addMouseListener(new MouseAdapter() 
        {
            @Override
            public void mouseEntered(MouseEvent event)
            {
                MusicProComponents.volIconHolder.setCursor(new Cursor(Cursor.HAND_CURSOR));
            }
            @Override
            public void mouseClicked(MouseEvent me)
            {
                try {
                    ExtendedMethods.toggleMuteOnOFF();
                } catch (Exception ex) {
                    Logger.getLogger(MusicPro11.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
       // ClockPanel clockPanel = new ClockPanel();
        //clockPanel.setBounds(120, 300, 200, 40);
        musicWindow.add(MusicProComponents.dropController);
        musicWindow.add(MusicProComponents.fullScreenBtn);
        musicWindow.add(MusicProComponents.prevButton);
        musicWindow.add(MusicProComponents.nextButton);
        musicWindow.add(MusicProComponents.playButton);
        musicWindow.add(MusicProComponents.playButton);
        musicWindow.add(MusicProComponents.searchButton);
        musicWindow.add(MusicProComponents.stopButton);
        musicWindow.add(MusicProComponents.bar);
        musicWindow.add(MusicProComponents.volSlider);
        musicWindow.add(MusicProComponents.volIconHolder);
        musicWindow.add(MusicProComponents.track);
        musicWindow.add(MusicProComponents.artist);
        musicWindow.add(MusicProComponents.album);
        musicWindow.add(MusicProComponents.hide);
        musicWindow.add(artAlbum);
        
        musicWindow.repaint();
        //musicWindow.add(clockPanel);
    }
    public static void openFile()
    {
         JFileChooser chooser = new JFileChooser("C://Users");
         FileFilter fileFilter = new ExtentionFileFilter("mp3", new String[]{"mp3"});
         chooser.setFileFilter(fileFilter);
         chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
         
         int result = chooser.showOpenDialog(null);
         if(result==JFileChooser.CANCEL_OPTION)
         {
             MusicProComponents.file =null;
         }
         else
         {
                MusicProComponents.file = chooser.getSelectedFile();
                double o =(double) MusicProComponents.file.length()/1048576;
                System.out.println(String.format("%.2f", o)+" MB");
                if(MusicProComponents.condition.equals("play"))
                {
                    //-directry of song playing
                    //System.out.print("\n\nindex no is: "+index);
                    //System.out.print("\nindex of song direectry is :"+(File)MusicProComponents.listModel.getElementAt(index)+"\n");


                    //list.setSelectedIndex(index);
                    MusicProComponents.listModel.addElement(MusicProComponents.file.getName());
                    MusicProComponents.listModel2.addElement(MusicProComponents.file.getAbsoluteFile());
                    MusicProComponents.condition = "stop";
                    MusicProComponents.playButton.setText(MusicProComponents.condition);
                    createPlay();
                    MusicProComponents.playItem.setText(MusicProComponents.condition);
                }
                else if(MusicProComponents.condition=="stop")
                {
                    MusicProComponents.listModel.addElement(MusicProComponents.file.getName());
                    MusicProComponents.listModel2.addElement(MusicProComponents.file.getAbsoluteFile());
                }
             
         }
         MusicProComponents.list.setSelectedIndex(MusicProComponents.index);
         MusicProComponents.list.repaint();
    }
    public static void pauseTrack()
    {
        if(trackPaused == true)
        {
            MusicProComponents.player.setMediaTime(mediaTime);
            MusicProComponents.player.start();
            MusicProComponents.playItem.setText("Pause");
            IncrementingTrackTime.inc_min = cMin;
            IncrementingTrackTime.inc_sec = cSec;
            trackPaused = false;
            
            System.out.println("Track has resumed");
        }
        else if(trackPaused == false)
        {
            mediaTime = new Time(MusicProComponents.player.getMediaTime().getSeconds());
            cMin = IncrementingTrackTime.inc_min;
            cSec = IncrementingTrackTime.inc_sec;
            MusicProComponents.player.stop();
            MusicProComponents.playItem.setText("Play");
            trackPaused = true;
            System.out.println("Track has paused");
           // System.gc();
        }
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
            musicWindow.setTitle(MusicProComponents.file.getName()+"-MusicPro");
            //player.syncStart(new Time(player.getMediaNanoseconds()));
            
        }
        catch(Exception e)
        {
            JOptionPane.showMessageDialog(null, "Invalid File or Location","Error Loading File",JOptionPane.ERROR_MESSAGE);
        }
        try 
        {
            MusicProComponents.artist.setText(ExtendedMethods.getArtist());
            MusicProComponents.album.setText(ExtendedMethods.getAlbum());
            MusicProComponents.track.setText(ExtendedMethods.getTrack());
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
        musicWindow.repaint();
        maxTime = (int)MusicProComponents.player.getDuration().getSeconds();
        
       // TrackDuration duration = new TrackDuration();
    }
    public static void stopPlay()
    {
        
        
        try
        {
            
            musicWindow.setTitle("MusicPro");
            MusicProComponents.player.stop();
            
        }
        catch(Exception e)
        {
        }
        MusicProComponents.artist.setText("");
        MusicProComponents.album.setText("");
        MusicProComponents.track.setText("");
        MusicProComponents.timeElapsed.setText("");
        MusicProComponents.trackDur.setText("");
        IncrementingTrackTime.inc_min = 0;
        IncrementingTrackTime.inc_sec = 0;
        IncrementingTrackTime.timer.cancel();
        MusicProComponents.timer=0;
    }
    public static  void SeparateDirectry()
{
         for (String word : MusicProComponents.words)  
         {  
             //System.out.println(word);  
             MusicProComponents.NoOfWords++;
         }
         int p = 0;
        switch(MusicProComponents.NoOfWords)
        {
            case 3:
                System.out.print("case 3");
                MusicProComponents.words = MusicProComponents.sentence.split("/"); 
                for (String word : MusicProComponents.words)
                {  
                    if(p == 1)
                    {
                        MusicProComponents.SongDirec = ""+word;
                    }
                    else if(p < 1)
                    {
                        MusicProComponents.SongDirec = MusicProComponents.SongDirec+"/"+word+"/"+word; 
                    }
                    else if(p == 1)
                    {
                        MusicProComponents.SongDirec = MusicProComponents.SongDirec+"/"+word; 
                    }
                    else if(p==2)
                    {
                        MusicProComponents.SongNa = MusicProComponents.SongDirec+"/"+word;
                    }
                    p = p+1;
                    MusicProComponents.NoOfWords++;
                }
                break;
            case 4:
                System.out.print("case 4");
                MusicProComponents.words = MusicProComponents.sentence.split("/"); 
                for (String word : MusicProComponents.words){  
                    if(p ==1){
                        MusicProComponents.SongDirec = ""+word;
                     }
                     else if(p < 2){
                        MusicProComponents.SongDirec = MusicProComponents.SongDirec+"/"+word; 
                     }
                     else if(p == 2){
                        MusicProComponents.SongDirec = MusicProComponents.SongDirec+"/"+word; 
                     }
                     else if(p==3){
                        MusicProComponents.SongNa = MusicProComponents.SongDirec+"/"+word;
                     }
                     p = p+1;
                     MusicProComponents.NoOfWords++;
                 }
                 break;
            case 5:
                System.out.print("case 5");
                MusicProComponents.words = MusicProComponents.sentence.split("/"); 
                for (String word : MusicProComponents.words){  
                    if(p ==1){
                        MusicProComponents.SongDirec = ""+word;
                    }
                    else if(p < 3){
                        MusicProComponents.SongDirec = MusicProComponents.SongDirec+"/"+word; 
                    }
                    else if(p == 3){
                        MusicProComponents.SongDirec = MusicProComponents.SongDirec+"/"+word; 
                    }
                    else if(p==4){
                        MusicProComponents.SongNa = MusicProComponents.SongDirec+"/"+word;
                    }
                    p = p+1;
                    MusicProComponents.NoOfWords++;
                }
                break;
            case 6:
                System.out.print("case 6");
                MusicProComponents.words = MusicProComponents.sentence.split("/"); 
                for (String word : MusicProComponents.words){  
                    if(p ==1){
                        MusicProComponents.SongDirec = ""+word;
                    }
                    else if(p < 4){
                        MusicProComponents.SongDirec = MusicProComponents.SongDirec+"/"+word; 
                    }
                    else if(p == 4){
                        MusicProComponents.SongDirec = MusicProComponents.SongDirec+"/"+word; 
                    }
                    else if(p==5){
                        MusicProComponents.SongNa = MusicProComponents.SongDirec+"/"+word;
                    }
                    p = p+1;
                    MusicProComponents.NoOfWords++;
                }
                break;
            case 7:
                System.out.print("case 7");
                MusicProComponents.words = MusicProComponents.sentence.split("/"); 
                for (String word : MusicProComponents.words){  
                    if(p ==1){
                        MusicProComponents.SongDirec = ""+word;
                    }
                    else if(p < 5){
                        MusicProComponents.SongDirec = MusicProComponents.SongDirec+"/"+word; 
                    }
                    else if(p == 5){
                        MusicProComponents.SongDirec = MusicProComponents.SongDirec+"/"+word; 
                    }
                    else if(p==6){
                        MusicProComponents.SongNa = MusicProComponents.SongDirec+"/"+word;
                    }
                    p = p+1;
                    MusicProComponents.NoOfWords++;
                 }
                break;
            case 8:
                System.out.print("case 8");
                MusicProComponents.words = MusicProComponents.sentence.split("/"); 
            
                for (String word : MusicProComponents.words)  {  
                    if(p ==1){
                        MusicProComponents.SongDirec = ""+word;
                    }
                    else if(p < 6){
                        MusicProComponents.SongDirec = MusicProComponents.SongDirec+"/"+word; 
                    }
                    else if(p == 6){
                        MusicProComponents.SongDirec = MusicProComponents.SongDirec+"/"+word; 
                    }
                    else if(p==7){
                        MusicProComponents.SongNa = MusicProComponents.SongDirec+"/"+word;
                    }
                    p = p+1;
                    MusicProComponents.NoOfWords++;
                }
                break;
            case 9:
                System.out.print("case 9");
                MusicProComponents.words = MusicProComponents.sentence.split("/"); 
                for (String word : MusicProComponents.words)  {  
                    if(p ==1){
                        MusicProComponents.SongDirec = ""+word;
                    }
                    else if(p < 7){
                        MusicProComponents.SongDirec = MusicProComponents.SongDirec+"/"+word; 
                    }
                    else if(p == 7){
                        MusicProComponents.SongDirec = MusicProComponents.SongDirec+"/"+word; 
                    }
                    else if(p==8){
                        MusicProComponents.SongNa = MusicProComponents.SongDirec+"/"+word;
                    }
                    p = p+1;
                    MusicProComponents.NoOfWords++;
                }
                break;
             case 10:
                 System.out.print("case 10");
                 MusicProComponents.words = MusicProComponents.sentence.split("/"); 
                 for (String word : MusicProComponents.words)  {  
                     if(p ==1){ 
                         MusicProComponents.SongDirec = ""+word;
                     }
                     else if(p < 8){
                         MusicProComponents.SongDirec = MusicProComponents.SongDirec+"/"+word; 
                     }
                     else if(p == 8){
                         MusicProComponents.SongDirec = MusicProComponents.SongDirec+"/"+word; 
                     }
                     else if(p==9){
                         MusicProComponents.SongNa = MusicProComponents.SongDirec+"/"+word;
                     }
                     p = p+1;
                     MusicProComponents.NoOfWords++;
                 }
                 break;
             case 11:
                 System.out.print("case 11");
                 MusicProComponents.words = MusicProComponents.sentence.split("/"); 
                 for (String word : MusicProComponents.words)  {  
                     if(p ==1){
                         MusicProComponents.SongDirec = ""+word;
                     }
                     else if(p < 9){
                         MusicProComponents.SongDirec = MusicProComponents.SongDirec+"/"+word; 
                     }
                     else if(p == 9){
                         MusicProComponents.SongDirec = MusicProComponents.SongDirec+"/"+word; 
                     }
                     else if(p==10){
                         MusicProComponents.SongNa = MusicProComponents.SongDirec+"/"+word;
                     }
                     p = p+1;
                     MusicProComponents.NoOfWords++;
                 }
                 break;
             case 12:
                 System.out.print("case 12");
                 MusicProComponents.words = MusicProComponents.sentence.split("/"); 
                 for (String word : MusicProComponents.words)  {  
                     if(p ==1){
                         MusicProComponents.SongDirec = ""+word;
                     }
                     else if(p < 10){
                         MusicProComponents.SongDirec = MusicProComponents.SongDirec+"/"+word; 
                     }
                     else if(p == 10){
                         MusicProComponents.SongDirec = MusicProComponents.SongDirec+"/"+word; 
                     }
                     else if(p==11){
                         MusicProComponents.SongNa = MusicProComponents.SongDirec+"/"+word;
                     }
                     p = p+1;
                     MusicProComponents.NoOfWords++;
                 }
                 break;
             case 13:
                 System.out.print("case 13");
                 MusicProComponents.words = MusicProComponents.sentence.split("/"); 
                 for (String word : MusicProComponents.words)  {  
                     if(p ==1){
                         MusicProComponents.SongDirec = ""+word;
                     }
                     else if(p < 11){
                         MusicProComponents.SongDirec = MusicProComponents.SongDirec+"/"+word; 
                     }
                     else if(p == 11){
                         MusicProComponents.SongDirec = MusicProComponents.SongDirec+"/"+word; 
                     }
                     else if(p==12){
                         MusicProComponents.SongNa = MusicProComponents.SongDirec+"/"+word;
                     }
                     p = p+1;
                     MusicProComponents.NoOfWords++;
                 }
                 break;
             case 14:
                 System.out.print("case 14");
                 MusicProComponents.words = MusicProComponents.sentence.split("/"); 
                 for (String word : MusicProComponents.words)  {  
                     if(p ==1){
                         MusicProComponents.SongDirec = ""+word;
                     }
                     else if(p < 12){
                         MusicProComponents.SongDirec = MusicProComponents.SongDirec+"/"+word; 
                     }
                     else if(p == 12){
                         MusicProComponents.SongDirec = MusicProComponents.SongDirec+"/"+word; 
                     }
                     else if(p==13){
                         MusicProComponents.SongNa = MusicProComponents.SongDirec+"/"+word;
                     }
                     p = p+1;
                     MusicProComponents.NoOfWords++;
                 }
                 break;
             case 15:
                 System.out.print("case 15");
                 MusicProComponents.words = MusicProComponents.sentence.split("/"); 
                 for (String word : MusicProComponents.words)  {  
                     if(p ==1){
                         MusicProComponents.SongDirec = ""+word;
                     }
                     else if(p < 13){
                         MusicProComponents.SongDirec = MusicProComponents.SongDirec+"/"+word; 
                     }
                     else if(p == 13){
                         MusicProComponents.SongDirec = MusicProComponents.SongDirec+"/"+word; 
                     }
                     else if(p==14){
                         MusicProComponents.SongNa = MusicProComponents.SongDirec+"/"+word;
                     }
                     p = p+1;
                     MusicProComponents.NoOfWords++;
                 }
                 break;
             case 16:
                 System.out.print("case 16");
                 MusicProComponents.words = MusicProComponents.sentence.split("/"); 
                 for (String word : MusicProComponents.words)  {  
                     if(p ==1){
                         MusicProComponents.SongDirec = ""+word;
                     }
                     else if(p < 14){
                        MusicProComponents.SongDirec = MusicProComponents.SongDirec+"/"+word; 
                     }
                     else if(p == 14){
                        MusicProComponents.SongDirec = MusicProComponents.SongDirec+"/"+word; 
                     }
                     else if(p==15){
                        MusicProComponents.SongNa = MusicProComponents.SongDirec+"/"+word;
                     }
                     p = p+1;
                     MusicProComponents.NoOfWords++;
                 }
                 break;
             case 17:
                 System.out.print("case 17");
                 MusicProComponents.words = MusicProComponents.sentence.split("/"); 
                 for (String word : MusicProComponents.words)  {  
                     if(p ==1){
                         MusicProComponents.SongDirec = ""+word;
                     }
                     else if(p < 15){
                         MusicProComponents.SongDirec = MusicProComponents.SongDirec+"/"+word; 
                     }
                     else  if(p == 15){
                         MusicProComponents.SongDirec = MusicProComponents.SongDirec+"/"+word; 
                     }
                     else if(p==16){
                         MusicProComponents.SongNa = MusicProComponents.SongDirec+"/"+word;
                     }
                     p = p+1;
                     MusicProComponents.NoOfWords++;
                 }
                 break;
             case 18:
                 System.out.print("case 18");
                 MusicProComponents.words = MusicProComponents.sentence.split("/"); 
                 for (String word : MusicProComponents.words)  {  
                     if(p ==1){
                         MusicProComponents.SongDirec = ""+word;
                     }
                     else if(p < 16){
                         MusicProComponents.SongDirec = MusicProComponents.SongDirec+"/"+word; 
                     }
                     else if(p == 16){
                         MusicProComponents.SongDirec = MusicProComponents.SongDirec+"/"+word; 
                     }
                     else if(p==17){
                         MusicProComponents.SongNa = MusicProComponents.SongDirec+"/"+word;
                     }
                     p = p+1;
                     MusicProComponents.NoOfWords++;
                 }
                 break;
             case 19:
                 System.out.print("case 19");
                 MusicProComponents.words = MusicProComponents.sentence.split("/"); 
                 for (String word : MusicProComponents.words)  {  
                     if(p ==1){
                         MusicProComponents.SongDirec = ""+word;
                     }
                     else if(p < 17){
                         MusicProComponents.SongDirec = MusicProComponents.SongDirec+"/"+word; 
                     }
                     else if(p == 17){
                         MusicProComponents.SongDirec = MusicProComponents.SongDirec+"/"+word; 
                     }
                     else if(p==18){
                         MusicProComponents.SongNa = MusicProComponents.SongDirec+"/"+word;
                     }
                     p = p+1;
                     MusicProComponents.NoOfWords++;
                 }
                 break;
             case 20:
                 System.out.print("case 20");
                 MusicProComponents.words = MusicProComponents.sentence.split("/"); 
                 for (String word : MusicProComponents.words)  {  
                     if(p ==1){
                         MusicProComponents.SongDirec = ""+word;
                     }
                     else if(p < 18){
                         MusicProComponents.SongDirec = MusicProComponents.SongDirec+"/"+word; 
                     }
                     else if(p == 18){
                         MusicProComponents.SongDirec = MusicProComponents.SongDirec+"/"+word; 
                     }
                     else if(p==19){
                         MusicProComponents.SongNa = MusicProComponents.SongDirec+"/"+word;
                     }
                     p = p+1;
                     MusicProComponents.NoOfWords++;
                 }
                 break;
             case 21:
                 System.out.print("case 21");
                 MusicProComponents.words = MusicProComponents.sentence.split("/"); 
                 for (String word : MusicProComponents.words)  {  
                     if(p ==1){
                         MusicProComponents.SongDirec = ""+word;
                     }
                     else if(p < 19){
                         MusicProComponents.SongDirec = MusicProComponents.SongDirec+"/"+word; 
                     }
                     else if(p == 19){
                         MusicProComponents.SongDirec = MusicProComponents.SongDirec+"/"+word; 
                     }
                     else if(p==20){
                         MusicProComponents.SongNa = MusicProComponents.SongDirec+"/"+word;
                     }
                     p = p+1;
                     MusicProComponents.NoOfWords++;
                 }
                 break;
        }
        MusicProComponents.words=null;
        MusicProComponents.NoOfWords=0;
    }
    public  void createSmallAlbumArt()
    {
        artAlbum = new DrawArtAlbum();
        artAlbum.setBounds(x, y, wid, hei);
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
                    artAlbum.setBounds(x, y, wid, hei);
                    musicWindow.add(artAlbum);
                }
                if(x>100 && y <= 0)
                {
                    y = x+100;
                    wid = 100;
                    hei = 100;
                    artAlbum.setBounds(x, y, wid, hei);
                    musicWindow.add(artAlbum);
                }
                if(x >= 400 || y >= 400)
                {
                    x = 0;
                    y = 0;
                    wid = 340;
                    hei = 378;
                    artAlbum.setBounds(x, y, wid, hei);
                    musicWindow.add(artAlbum);
                }
            }
        }, 10, 10);
    }
    public static void showPlayList()
    {
        if(MusicProComponents.listShower == "showing")
        {
            MusicProComponents.listShower = "notshowing";
            MusicProComponents.playlist.setText(MusicProComponents.lister="Hide List");
            musicWindow.setSize(600, 400);
            musicWindow.setLocationRelativeTo(null);
        }
        else if(MusicProComponents.listShower == "notshowing")
        {
            MusicProComponents.listShower = "showing";
            MusicProComponents.playlist.setText(MusicProComponents.lister="Playlist");
            musicWindow.setSize(345, 400);
            musicWindow.setLocationRelativeTo(null);
        }
    }
    public static void main(String[] args) throws ClassNotFoundException, InstantiationException, IllegalAccessException, UnsupportedLookAndFeelException
    {
        UIManager.LookAndFeelInfo[] lookAndFeelInfo = UIManager.getInstalledLookAndFeels();
        String setTheme = lookAndFeelInfo[1].getClassName();
        UIManager.setLookAndFeel(setTheme);
        MusicProPopUpMenu popUpMenu = new MusicProPopUpMenu();
        //ConnectToTheDatabase connectToTheDatabase = new ConnectToTheDatabase();
        MusicPro11 musicPro = new MusicPro11();
    }
    @Override
    public void actionPerformed(ActionEvent e)
    {
        Object o = e.getSource();
        
        if(o.equals(MusicProComponents.stopButton))
        {
            if(MusicProComponents.condition == "stop" || trackPaused == true)
            {
                stopPlay();
                MusicProComponents.condition = "play";
            }
            else
            {
            }
        }
        /*if(o.equals(MusicProComponents.pauseButton))
        {
            pauseTrack();
        }*/
        if(o.equals(MusicProComponents.openLastPlayed))
        {
            try 
            {
                ExtendedMethods.openLastPlayedFile();
            }
            catch (FileNotFoundException ex)
            {
                Logger.getLogger(MusicPro11.class.getName()).log(Level.SEVERE, null, ex);
            }
            catch (IOException ex) 
            {
                Logger.getLogger(MusicPro11.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        if(o.equals(MusicProComponents.fullScreenBtn))
        {
            musicWindow.dispose();
            MakeMProFullScreen fullScreen = new MakeMProFullScreen();
            fullScreen.makeScreenFull();
        }
        if(o.equals(MusicProComponents.changeTextColor))
        {
            try 
            {
                MyBrawser brawser = new MyBrawser();
            } catch (IOException ex)
            {
                Logger.getLogger(MusicPro11.class.getName()).log(Level.SEVERE, null, ex);
            } 
            //Help help = new Help();
            musicWindow.setEnabled(false);
        }
        if(o.equals(MusicProComponents.changeTextStyle))
        {
            About about = new About();
            musicWindow.setEnabled(false);
        }
        if(MusicProComponents.trackDetails.equals(e.getSource()))
        {
            try 
            {
                ShowFileDetails fileDetails = new ShowFileDetails();
                fileDetails.albumArtForFileDetails.repaint();
            }
            catch (IOException ex) 
            {
                Logger.getLogger(MusicPro11.class.getName()).log(Level.SEVERE, null, ex);
            }
            catch (UnsupportedTagException ex)
            {
                Logger.getLogger(MusicPro11.class.getName()).log(Level.SEVERE, null, ex);
            }
            catch (InvalidDataException ex) 
            {
                Logger.getLogger(MusicPro11.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        if(MusicProComponents.playButton.equals(e.getSource()) || MusicProComponents.playItem.equals(e.getSource()))
        {
            
            //GainControl control = player.getGainControl();
            //menu.add(component);
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
                    MusicProComponents.playItem.setText("Pause");
                }
                else if(MusicProComponents.condition.equals("stop"))
                {
                    pauseTrack();// to be enabled for togggle button, if play and pause are on the same button
                    //stopPlay(); to be enabled for togggle button, if play and stop are on the same button
                    //MusicProComponents.condition = "stop";
                    //MusicProComponents.playButton.setText(MusicProComponents.condition);
                }
           }
           else
           {
               
           }
             
        }
        if(MusicProComponents.savePlaylist.equals(e.getSource()))
        {
            SavingPlayList.readTheCurrentPlayingList();
        }
        if(MusicProComponents.OpenPlaylist.equals(e.getSource()))
        {
            OpenSavedPlayList.readTheSavedPlayingList();
        }
        if(MusicProComponents.OpenItem.equals(e.getSource()))
        {
            openFile();
        }
        if(MusicProComponents.hide.equals(e.getSource()))
        {
            showPlayList();
        }
        if(MusicProComponents.prevButton.equals(e.getSource()) || MusicProComponents.prevItem.equals(e.getSource()))
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
                        stopPlay();
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
                        stopPlay();
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
            
        }
        if(MusicProComponents.repeatItem.equals(e.getSource()))
        {
            if(!MusicProComponents.listModel2.isEmpty())
            {
                if(MusicProComponents.repeatCondition.equals("Repeat On"))
                {
                    MusicProComponents.repeater = true;
                    MusicProComponents.repeatCondition="Repeat Off";
                    MusicProComponents.repeatItem.setText(MusicProComponents.repeatCondition);
                    KeepAsFavarateWhenRepeat asFavarateWhenRepeat = new KeepAsFavarateWhenRepeat();

                }
                else if(MusicProComponents.repeatCondition=="Repeat Off")
                {
                    MusicProComponents.repeater =false;
                    MusicProComponents.repeatCondition = "Repeat On";
                    //MusicProComponents.repeat.setText(MusicProComponents.repeatCondition);
                    MusicProComponents.repeatItem.setText(MusicProComponents.repeatCondition);
                }
            }
            else
            {
            }
        }
        if(MusicProComponents.nextButton.equals(e.getSource()) || MusicProComponents.nextItem.equals(e.getSource()))
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
                        stopPlay();
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
                        stopPlay();
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
        }
    }
    public void start()
    {
        if(MusicProComponents.runner == null) MusicProComponents.runner = new Thread(this);
        MusicProComponents.runner.start();
    }
    public void incrementTimer()
    {
        MusicProComponents.timer++;
    }
    @Override
    public void run() 
    {
        while(MusicProComponents.runner == Thread.currentThread())
        {
            musicWindow.repaint();
            try
            {
                
                Thread.sleep(1000);
            }
            catch(InterruptedException e)
            {
            }
        }
    }
}
class ClockPanel extends JPanel
{
    public void paintComponent(Graphics painter)
    {
        MusicProComponents.clockFont = new Font("Alien Encounter", Font.BOLD, 28);
        painter.setColor(Color.green);
        painter.setFont(MusicProComponents.clockFont);
        painter.setColor(new Color(.4f,.4f,.9f,.9f));
        painter.drawString(timeNow(), 10, 40);
    }
    public String timeNow()
    {
        Calendar now = Calendar.getInstance();
        int hrs = now.get(Calendar.HOUR_OF_DAY);
        int min = now.get(Calendar.MINUTE);
        int sec = now.get(Calendar.SECOND);
        String time = zero(hrs) + ":" + zero(min) + ":" + zero(sec);
        return time;
    }
    public String zero(int num)
    {
        String number = (num < 10) ? ("0"+num) : (""+num);
        return number;
    }
}
class EventHandler implements ControllerListener 
{
    @Override
    public void controllerUpdate(ControllerEvent ce) 
    {
    if(ce instanceof RealizeCompleteEvent)
    {
        
    }
    else  if(ce instanceof EndOfMediaEvent)
    {
        if(MusicProComponents.repeater==true)
        {
            System.out.println("repeat on");
            MusicProComponents.file = (File)MusicProComponents.listModel2.getElementAt(MusicProComponents.index);
            MusicProComponents.list.setSelectedIndex(MusicProComponents.index);
            createPlay();
            MusicProComponents.timer=0;
        }
        else
        {
            //Repeat the same track if there are no other tracks in the list.....
            if(MusicProComponents.listModel.getSize()==0 && MusicProComponents.listModel2.getSize()==0)
            {
                MusicProComponents.list.setSelectedIndex(MusicProComponents.index);
                MusicProComponents.player.setMediaTime(new Time(0));
                createPlay();
                MusicProComponents.timer=0;
            }
            //Go back to the top of the list if we are at the end of the list.....
            //--------checked and working correctly no exception
            else if(MusicProComponents.list.getSelectedIndex()==(MusicProComponents.listModel.getSize()-1) && MusicProComponents.index==(MusicProComponents.listModel2.getSize()-1))
            {
                MusicProComponents.index=0;
                MusicProComponents.file = (File)MusicProComponents.listModel2.getElementAt(MusicProComponents.index);
                MusicProComponents.list.setSelectedIndex(MusicProComponents.index);
                createPlay();
                MusicProComponents.timer=0;
            }
            //---checked correctly
            //Go to the next track on the list, if we are having a list and the index is less than the list size
            else if(MusicProComponents.index<MusicProComponents.listModel.getSize() && MusicProComponents.index<MusicProComponents.listModel2.getSize())
            {
                MusicProComponents.index++;
                MusicProComponents.file = (File) MusicProComponents.listModel2.getElementAt(MusicProComponents.index);
                MusicProComponents.list.setSelectedIndex(MusicProComponents.index);
                createPlay();
                MusicProComponents.timer=0;
            }
            //otherwise play the track at index 0(zero)..........
            else
            {
                MusicProComponents.index=0;
                MusicProComponents.file = (File)MusicProComponents.listModel2.getElementAt(MusicProComponents.index);
                MusicProComponents.list.setSelectedIndex(MusicProComponents.index);
                createPlay();
                MusicProComponents.timer=0;
            }
        }

    }  
    }

}
class Listing 
{        
     String stk = "";       
     File filek;      
     File fk;
    private String track;

     public Listing(File f) 
     { 
         this.filek = f;   
         this.fk = f;     
     }      
     public void displayFiles()
     { 
         findFiles(fk); 
     }        
     public void findFiles(File f) 
     {     
         if (f.isDirectory()) 
         {   
             stk = getFiles(f);    
             System.out.println(stk + f.getName());  
             File files[] = f.listFiles();    
             for (File ff :files)
             {    
                 findFiles(ff);          
             } 
         }
         else if (f.isFile()) 
         {   
            // System.out.println(stk + "  " + f.getName());  

             track = f.toString().endsWith("mp3")+"";
             if(track.equals("true"))
             {
                 MusicProComponents.listModel.addElement(f.getName());
                 MusicProComponents.listModel2.addElement(f.getAbsoluteFile());
             }
         }
     }        
     private String getFiles(File f)
     {      
         String originalPath = filek.getAbsolutePath();     
         String path = f.getAbsolutePath();            
         String subString = path.substring(originalPath.length(), path.length());    
         String st = "";           

         for (int index = 0; index < subString.length(); index++) 
         {      
             char ch = subString.charAt(index);          
             if (ch == File.separatorChar) 
             {              
                 st = st + "  ";                 
             }           
         }   

         return st;    
     }
}
class list extends JPanel implements ListSelectionListener
{
    public list()
    {
        super(new BorderLayout());
        
        MusicProComponents.listModel = new DefaultListModel();
        MusicProComponents.listModel2 = new DefaultListModel();
        
        MusicProComponents.list = new JList(MusicProComponents.listModel);
        MusicProComponents.list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        MusicProComponents.list.setSelectedIndex(0);
        MusicProComponents.list.addListSelectionListener(this);
        MusicProComponents.list.setDragEnabled(true);
        MusicProComponents.list.setVisibleRowCount(5);
        MusicProComponents.list.setAutoscrolls(true);
        MusicProComponents.list.setVisible(true);
        MusicProComponents.list.setForeground(Color.black.darker());
        MusicProComponents.list.setFont(new Font(null, Font.TRUETYPE_FONT, 12));
        MusicProComponents.list.addMouseListener(new MouseAdapter()
        {
            @Override
            public void mouseClicked(MouseEvent e)
            {
                if(!MusicProComponents.listModel.isEmpty())
                {
                    /*if(e.getClickCount() == 1)
                    {
                        if(!MusicProComponents.listModel.isEmpty())
                        {
                            MusicProComponents.index = MusicProComponents.list.getSelectedIndex();
                            MusicProComponents.file = (File) MusicProComponents.listModel2.getElementAt(MusicProComponents.index);

                            MusicProComponents.list.setSelectedIndex(MusicProComponents.index);
                            if(MusicProComponents.condition=="stop")
                            {
                                MusicProComponents.timer=0;
                                //incrementTimer();
                                MusicPro.stopPlay();
                                createPlay();

                            }
                            else if("play".equals(MusicProComponents.condition))
                            {

                               createPlay();
                            }
                        }
                        else if(e.getClickCount() == 2)
                        {
                        }
                       
                    }
                    else
                    {
                        MusicProPopUpMenu.popupMenu.show(e.getComponent(), e.getX(), e.getY());
                        MusicProComponents.index = MusicProComponents.list.getSelectedIndex();
                        MusicProComponents.file = (File) MusicProComponents.listModel2.getElementAt(MusicProComponents.index);
                    }*/
                    MusicProPopUpMenu.popupMenu.show(e.getComponent(), e.getX(), e.getY());
                }
                else
                {
                }
            }
            @Override
            public void mouseReleased(MouseEvent e)
            {
                /*if(!MusicProComponents.listModel.isEmpty())
                {
                    if(e.isPopupTrigger())
                    {
                        MusicProPopUpMenu.popupMenu.show(e.getComponent(), e.getX(), e.getY());
                    }
                }
                else
                {
                }*/
            }
        });
        
        MusicProComponents.scrollPane = new JScrollPane(MusicProComponents.list);
        MusicProComponents.scrollPane.setVisible(true);
        MusicProComponents.scrollPane.setBorder(new MotifBorders.BevelBorder(true, Color.green.brighter(), Color.green.brighter()));
       // MusicProComponents.scrollPane.setBackground(new Color(.5f,.5f,.1f,.1f));
        MusicProComponents.scrollPane.setWheelScrollingEnabled(true);
        MusicProComponents.scrollPane.setAutoscrolls(true);
        add(MusicProComponents.scrollPane,BorderLayout.CENTER);
    }
   @Override
   public void valueChanged(ListSelectionEvent e) 
   {
   }
}
class DropListener implements DropTargetListener
{
    private URL filenameD;
    private String track;
    private int TrueTracks;
    private String p;

    public DropListener() 
    {
    }
    @Override
    public void dragEnter(DropTargetDragEvent dtde) 
    {
    }

    @Override
    public void dragOver(DropTargetDragEvent dtde) 
    {
    }

    @Override
    public void dropActionChanged(DropTargetDragEvent dtde) 
    {
    }
    @Override
    public void dragExit(DropTargetEvent dte)
    {
    }
    @Override
    public void drop(DropTargetDropEvent event) 
    {
        // Accept copy drops
    event.acceptDrop(DnDConstants.ACTION_COPY);

    // Get the transfer which can provide the dropped item data
    Transferable transferable = event.getTransferable();

    // Get the data formats of the dropped item
    DataFlavor[] flavors = transferable.getTransferDataFlavors();

    // Loop through the flavors
    for (DataFlavor flavor : flavors) {

        try {

            // If the drop items are MusicProComponents.files
            if (flavor.isFlavorJavaFileListType()) {

                // Get all of the dropped MusicProComponents.files
                java.util.List files = (java.util.List) transferable.getTransferData(flavor);
                    for (Iterator it = files.iterator(); it.hasNext();)
                    {


                        //System.out.println("File path is '" + MusicProComponents.file.getPath());
                      //  MusicProComponents.filenameD = MusicProComponents.file.toURL();
                       // sentence = MusicProComponents.filenameD+"";
                        //words = sentence.split("/");
                        //SeparateDirectry();

                        //System.out.print(fi
                        MusicProComponents.file = (File) it.next();
                        if(MusicProComponents.file.isFile())
                        {
                            System.out.println("File (s)");

                            track = MusicProComponents.file.toString().endsWith("mp3")+"";
                            //track = MusicProComponents.file.toString().endsWith("wav")+"";
                            if(track.equals("true"))
                            {
                                System.out.println(MusicProComponents.file.getName());
                                MusicProComponents.listModel.addElement(MusicProComponents.file.getName());
                                MusicProComponents.listModel2.addElement(MusicProComponents.file.getAbsoluteFile());
                                TrueTracks++;
                            }
                            else if(track.equals("false"))
                            {
                                track = MusicProComponents.file.toString().endsWith("mpr")+"";
                                if(track.equals("true"))
                                {
                                    OpenSavedPlayList.readSavedListOnDropEvent(MusicProComponents.file);
                                }
                            }
                            else
                            {
                            }

                        if(MusicProComponents.condition.equals("play"))
                        {
                            //-directry of song playing
                            //System.out.print("\n\nindex no is: "+index);
                            //System.out.print("\nindex of song direectry is :"+(File)MusicProComponents.listModel.getElementAt(index)+"\n");

                            createPlay();
                            MusicProComponents.list.setSelectedIndex(MusicProComponents.index);
                            MusicProComponents.condition = "stop";
                            //MusicProComponents.playButton.setText(MusicProComponents.condition);
                            MusicProComponents.playItem.setText(MusicProComponents.condition);
                        }

                        }
                        else
                        {
                            System.out.println("Folder(s)");
                            MusicProComponents.sentence = MusicProComponents.file.toURL()+"";
                            MusicProComponents.words = MusicProComponents.sentence.split("/");
                            MusicPro11.SeparateDirectry();
                            p = MusicProComponents.SongNa;

                            Listing liste = new Listing(new File(p));       
                            liste.displayFiles();  
                            p="";
                            MusicProComponents.sentence = "";
                           MusicProComponents.file = (File) MusicProComponents.listModel2.getElementAt(MusicProComponents.index);
                        }
                         if(MusicProComponents.condition=="play")
                            {

                                createPlay();
                                MusicProComponents.list.setSelectedIndex(MusicProComponents.index);
                               // MusicProComponents.playButton.setText(MusicProComponents.condition="stop");
                                MusicProComponents.playItem.setText(MusicProComponents.condition="stop");
                            }
                    }

            }

        } catch (Exception e) {

            // Print out the error stack
            //e.printStackTrace();

        }
    }

    // Inform that the drop is complete
    event.dropComplete(true);

    }
}