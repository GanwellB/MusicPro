package musicpro1.pkg1;

import com.sun.media.rtsp.protocol.PauseMessage;
import java.awt.Font;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Calendar;
import java.util.GregorianCalendar;
import javax.media.Player;
import javax.media.format.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JSlider;
import javax.swing.JTextField;

/**
 *
 * @author Ganwell
 */
public class MusicProComponents 
{
    public static JButton playButton, nextButton, prevButton, stopButton, searchButton;
    public static JSeparator horizontalSep;
    public static JLabel fileIcon,volIconHolder;
    public static AudioInputStream audioInputStream =null;
    public static AudioFormat format;
    public static Font font = new Font("Arial", Font.BOLD, 16);
    public static Font albumFont = new Font("Arial", Font.BOLD, 20);
    public static Font artistFont = new Font("Arial", Font.BOLD, 26);
    public static JLabel contents, artist,album,track, trackDur,timeElapsed;
    public static Calendar calendar = new GregorianCalendar();
    public static Thread runner;
    public static Font clockFont;
    public static ImageIcon icon,volumeIcon, playIcon = new ImageIcon("images/playpause.png"),nextIcon = new ImageIcon("images/next.png")
            ,prevIcon = new ImageIcon("images/prev.png") ,hideIcon = new ImageIcon("images/list.png"),
            stopIcon = new ImageIcon("images/stop.png"), searchIcon = new ImageIcon("images/search.png");
    public static String condition = "play", repeatCondition="Repeat On", listShower = "showing",fromUSB = "no";
    public static JButton dateButton;
    static int index, keptIndex = -1;
    static BufferedImage image;
    public static int wide = 170, play_x = 480, next_x = 560, prev_x = 400;
    public static int   yaxis = 10 , prev_Size = 80;
    public static Font dateFont;
    static JScrollPane scrollPane ;
    public static String days,lister="Playlist";
    public static JComponent component;
    public static JLabel dropController ;
    static JMenuBar bar;
    static JMenu menu,Playback, viewSet;
    static JMenuItem playlist, playItem,repeatItem, openLastPlayed,nextItem,prevItem,hideItem,OpenItem,trackDetails,savePlaylist,OpenPlaylist
            ,changeTextColor,changeTextStyle;
    public static Player player;
    public static int dayer;
    public static JButton play,next,prev,hide,remove,repeat,fullScreenBtn;
    static boolean repeater =false, listCondition = false;
    public static JTextField  field;
    public static JTextField fade ;
    public static File file;
    static int progress =10, timer=0;
    public static  JList list,list2s;
    public static DefaultListModel listModel,listModel2,reservedListName, reserveListDirectory;
    public static AudioSystem system;
    public static int NoOfWords = 0;
    public static String sentence;
    public static String[] words;
    public static String SongDirec;
    public static String SongNa;
    public static JSlider volSlider;
}