/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package musicpro1.pkg1;

import com.mpatric.mp3agic.InvalidDataException;
import com.mpatric.mp3agic.UnsupportedTagException;
import com.sun.java.swing.plaf.motif.MotifBorders;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextArea;

/**
 *
 * @author Ganwell
 */
public class ShowFileDetails implements ActionListener
{
    DrawAlbumArtForFileDetails albumArtForFileDetails;
    public static JFrame detailsWindow;
    static JButton okToCancel, editDetails;
    static JLabel artist,track, album, genre,year;
    static JTextArea comments;
    static String iamShowing = "no";
    public ShowFileDetails() throws IOException, UnsupportedTagException, InvalidDataException 
    {
        detailsWindow = new JFrame(ExtendedMethods.getArtist()+"-"+ExtendedMethods.getAlbum());
        detailsWindow.setUndecorated(true);
        detailsWindow.setSize(400, 300);
        detailsWindow.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        detailsWindow.setResizable(false);
        detailsWindow.setLocationRelativeTo(MusicPro11.musicWindow);
        detailsWindow.getContentPane().setBackground(Color.white);
        detailsWindow.getContentPane().setLayout(null);
        detailsWindow.setAlwaysOnTop(true);
        detailsWindow.setVisible(true);
        detailsWindow.setIconImage(MusicPro11.icon);
        
        okToCancel = new JButton("Ok");
        okToCancel.setBounds(320, 10, 80, 20);
        okToCancel.setContentAreaFilled(false);
        okToCancel.addMouseListener(new MouseAdapter() 
        {
            @Override
            public void mouseEntered(MouseEvent event)
            {
                okToCancel.setCursor(new Cursor(Cursor.HAND_CURSOR));
            }
        });
        okToCancel.setBorder(new GeneralRoundingOfButtons(20));
        okToCancel.addActionListener(this);
        
        editDetails = new JButton("Edit");
        editDetails.setBounds(10, 10, 80, 20);
        editDetails.setContentAreaFilled(false);
        editDetails.addMouseListener(new MouseAdapter() 
        {
            @Override
            public void mouseEntered(MouseEvent event)
            {
                editDetails.setCursor(new Cursor(Cursor.HAND_CURSOR));
            }
        });
        editDetails.setBorder(new GeneralRoundingOfButtons(20));
        editDetails.addActionListener(this);
        
        artist = new JLabel(ExtendedMethods.getArtist());
        artist.setBounds(20, 40, 360, 40);
        artist.setForeground(Color.cyan);
        artist.setFont(MusicProComponents.artistFont);
        
        album = new JLabel(ExtendedMethods.getAlbum());
        album.setBounds(20, 70, 360, 40);
        album.setForeground(Color.cyan);
        album.setFont(MusicProComponents.albumFont);
        
        track = new JLabel(ExtendedMethods.getTrack());
        track.setFont(MusicProComponents.font);
        track.setForeground(Color.cyan);
        track.setBounds(20, 110, 360, 40);
        
        genre = new JLabel(ExtendedMethods.getGenre());
        genre.setBounds(20, 150, 200, 20);
        genre.setForeground(Color.cyan);
        
        year = new JLabel(ExtendedMethods.getYear());
        year.setBounds(20, 170, 200, 20);
        year.setForeground(Color.cyan);
        
        comments = new JTextArea(ExtendedMethods.getComments());
        comments.setBounds(10, 215, 380, 75);
        comments.setForeground(Color.cyan);
        comments.setBorder(new GeneralRoundingOfButtons(20));
        comments.setEditable(false);
        
        albumArtForFileDetails = new DrawAlbumArtForFileDetails();
        albumArtForFileDetails.setBorder(new MotifBorders.BevelBorder(true, Color.blue.brighter(), Color.blue.brighter()));
        albumArtForFileDetails.setBounds(100, 10, 200, 200);
        
        detailsWindow.add(okToCancel);
        detailsWindow.add(editDetails);
        detailsWindow.add(year);
        detailsWindow.add(comments);
        detailsWindow.add(genre);
        detailsWindow.add(album);
        detailsWindow.add(track);
        detailsWindow.add(artist);
        detailsWindow.add(albumArtForFileDetails);
        detailsWindow.repaint();
        iamShowing = "yes";
    }

    @Override
    public void actionPerformed(ActionEvent e)
    {
        Object o = e.getSource();
        if(o.equals(okToCancel))
        {
            detailsWindow.dispose();
            iamShowing = "no";
        }
        if(o.equals(editDetails))
        {
            try 
            {
                EditTrackDetails.editorWindow();
                MusicPro11.musicWindow.setEnabled(false);
                detailsWindow.hide();
            }
            catch (IOException ex) 
            {
                Logger.getLogger(ShowFileDetails.class.getName()).log(Level.SEVERE, null, ex);
            }
            catch (UnsupportedTagException ex) 
            {
                Logger.getLogger(ShowFileDetails.class.getName()).log(Level.SEVERE, null, ex);
            } 
            catch (InvalidDataException ex) 
            {
                Logger.getLogger(ShowFileDetails.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
