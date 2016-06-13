/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package musicpro1.pkg1;

import com.mpatric.mp3agic.InvalidDataException;
import com.mpatric.mp3agic.Mp3File;
import com.mpatric.mp3agic.UnsupportedTagException;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JSeparator;
import javax.swing.JTextArea;
import javax.swing.JTextField;

/**
 *
 * @author Ganwell
 */
public class EditTrackDetails 
{
    public static JFrame editorWindow;
    public static JTextArea  commentsField;
    public static JTextField artistField, albumField, trackField,artistField2, albumField2, trackField2,commentsField2, genreField,
            genreField2, yearField, yearField2;
    public static JLabel labelArtist, albLabel, trackLabel, comLabel,labelArtist2, albLabel2, trackLabel2,comLabel2, genre, genre2,year, year2;
    public static JButton applyChanges, cancelBtn;
    public static JSeparator verticalSeparator, horizontalSeparator;
    public static Font editorFont = new Font("Comic Sans MS", Font.PLAIN, 16);
    public static DrawAlbumArtForFileDetails drawAlbumArtForFileDetails;
    public static void editorWindow() throws IOException, UnsupportedTagException, InvalidDataException
    {
        editorWindow = new JFrame("MusicPro Id3v1Tag Editor");
        editorWindow.setSize(400, 400);
        editorWindow.setLocationRelativeTo(null);
        editorWindow.setResizable(false);
        editorWindow.getContentPane().setLayout(null);
        editorWindow.setVisible(true);
        
        verticalSeparator = new JSeparator(JSeparator.VERTICAL);
        verticalSeparator.setBounds(195, 0, 5, 155);
        
        labelArtist = new JLabel("Currrent Artist Name");
        labelArtist.setBounds(5, 5, 240, 20);
        labelArtist.setFont(editorFont);
        
        labelArtist2 = new JLabel("New Artist Name");
        labelArtist2.setBounds(205, 5, 240, 20);
        labelArtist2.setFont(editorFont);
        
        artistField = new JTextField(ExtendedMethods.getArtist());
        artistField.setBounds(5, 30, 180, 30);
        artistField.setFont(editorFont);
        artistField.setEditable(false);
        
        artistField2 = new JTextField();
        artistField2.setFont(editorFont);
        artistField2.setBounds(205, 30, 180, 30);
        
        albLabel = new JLabel("Current Album Name");
        albLabel.setBounds(5, 60, 240, 20);
        albLabel.setFont(editorFont);
        
        albLabel2 = new JLabel("New Album Name");
        albLabel2.setBounds(205, 60, 240, 20);
        albLabel2.setFont(editorFont);
        
        albumField = new JTextField(ExtendedMethods.getAlbum());
        albumField.setBounds(5, 85, 180, 30);
        albumField.setEditable(false);
        albumField.setFont(editorFont);
        
        albumField2 = new JTextField();
        albumField2.setBounds(205, 85, 180, 30);
        albumField2.setFont(editorFont);
        
        trackLabel = new JLabel("Current Track Name");
        trackLabel.setBounds(5, 110, 240, 20);
        trackLabel.setFont(editorFont);
        
        trackLabel2 = new JLabel("New Track Name");
        trackLabel2.setBounds(205, 110, 240, 20);
        trackLabel2.setFont(editorFont);
        
        trackField = new JTextField(ExtendedMethods.getTrack());
        trackField.setBounds(5, 135,180, 30);
        trackField.setEditable(false);
        trackField.setFont(editorFont);
        
        trackField2 = new JTextField();
        trackField2.setBounds(205, 135, 180, 30);
        trackField2.setFont(editorFont);
        
        genre = new JLabel("Current Genre");
        genre.setBounds(5, 160, 240, 20);
        genre.setFont(editorFont);
        
        genre2 = new JLabel("New Genre");
        genre2.setBounds(205, 160, 240, 20);
        genre2.setFont(editorFont);
        
        genreField = new JTextField(ExtendedMethods.getGenre());
        genreField.setBounds(5, 185, 180, 30);
        genreField.setFont(editorFont);
        genreField.setEditable(false);
        
        genreField2 = new JTextField();
        genreField2.setBounds(205, 185, 180, 30);
        genreField2.setFont(editorFont);
        
        year = new JLabel("Current Set Year");
        year.setBounds(5, 210, 240, 20);
        year.setFont(editorFont);
        
        year2 = new JLabel("Set New Year");
        year2.setBounds(205, 210, 240, 20);
        year2.setFont(editorFont);
        
        yearField = new JTextField(ExtendedMethods.getYear());
        yearField.setBounds(5, 235, 180, 30);
        yearField.setFont(editorFont);
        yearField.setEditable(false);
        
        yearField2 = new JTextField();
        yearField2.setBounds(205, 235, 180, 30);
        yearField2.setFont(editorFont);
        
        comLabel = new JLabel("Comments");
        comLabel.setBounds(155, 260, 240, 20);
        comLabel.setFont(editorFont);
        
        commentsField = new JTextArea();
        commentsField.setBounds(5, 285, 380, 55);
        
        applyChanges = new JButton("Save");
        applyChanges.setBounds(20, 345, 80, 20);
        applyChanges.addActionListener(new ActionListener() 
        {

            @Override
            public void actionPerformed(ActionEvent e) 
            {
                try 
                {
                    setNewTrackDetails();
                    editorWindow.dispose();
                    ShowFileDetails.detailsWindow.show();
                    MusicPro11.musicWindow.setEnabled(true);
                }
                catch (IOException ex)
                {
                    Logger.getLogger(EditTrackDetails.class.getName()).log(Level.SEVERE, null, ex);
                }
                catch (UnsupportedTagException ex) 
                {
                    Logger.getLogger(EditTrackDetails.class.getName()).log(Level.SEVERE, null, ex);
                }
                catch (InvalidDataException ex) 
                {
                    Logger.getLogger(EditTrackDetails.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        
        cancelBtn = new JButton("Cancel");
        cancelBtn.setBounds(290, 345, 80, 20);
        cancelBtn.addActionListener(new ActionListener()
        {

            @Override
            public void actionPerformed(ActionEvent e) 
            {
                editorWindow.dispose();
                ShowFileDetails.detailsWindow.show();
                MusicPro11.musicWindow.setEnabled(true);
            }
        });
        
        editorWindow.add(verticalSeparator);
        editorWindow.add(applyChanges);
        editorWindow.add(cancelBtn);
        editorWindow.add(labelArtist);
        editorWindow.add(labelArtist2);
        editorWindow.add(artistField);
        editorWindow.add(artistField2);
        editorWindow.add(albLabel);
        editorWindow.add(albLabel2);
        editorWindow.add(albumField);
        editorWindow.add(albumField2);
        editorWindow.add(trackLabel);
        editorWindow.add(trackLabel2);
        editorWindow.add(trackField);
        editorWindow.add(trackField2);
        editorWindow.add(year);
        editorWindow.add(year2);
        editorWindow.add(genre);
        editorWindow.add(genre2);
        editorWindow.add(genreField);
        editorWindow.add(genreField2);
        editorWindow.add(yearField);
        editorWindow.add(yearField2);
        editorWindow.add(comLabel);
        editorWindow.add(commentsField);
    }
    public static void setNewTrackDetails() throws IOException, UnsupportedTagException, InvalidDataException
    {
        Mp3File file = new Mp3File(MusicProComponents.file.getAbsolutePath());
        
        if(artistField2.getText().isEmpty())
        {
            file.getId3v1Tag().setArtist(artistField.getText());
        }
        else if(!artistField2.getText().isEmpty())
        {
            file.getId3v1Tag().setArtist(artistField2.getText());
        }
        if(albumField2.getText().isEmpty())
        {
            file.getId3v1Tag().setAlbum(albumField.getText());
        }
        else if(!albumField2.getText().isEmpty())
        {
            file.getId3v1Tag().setAlbum(albumField2.getText());
        }
        if(trackField2.getText().isEmpty())
        {
            file.getId3v1Tag().setTrack(trackField.getText());
        }
        else if(!trackField2.getText().isEmpty())
        {
            file.getId3v1Tag().setTitle(trackField2.getText());
        }
        if(genreField2.getText().isEmpty())
        {
           // file.getId3v1Tag().setGenre(Integer.parseInt(genreField.getText()));
        }
        else if(!genreField2.getText().isEmpty())
        {
            file.getId3v1Tag().setGenre(Integer.parseInt(genreField2.getText()));
        }
        if(yearField2.getText().isEmpty())
        {
            file.getId3v1Tag().setYear(yearField.getText());
        }
        else if(!yearField2.getText().isEmpty())
        {
            file.getId3v1Tag().setYear(yearField2.getText());
        }
        if(commentsField.getText().isEmpty())
        {
            
        }
        else if(!commentsField.getText().isEmpty())
        {
            file.getId3v1Tag().setComment(commentsField.getText());
        }
    }
}