/*
 * ****************************************************************************
 * --------------------------MusicPro--Version-1-0-2---------------------
 * ****************************************************************************
 * This is a MusicPro Creation, you may not use any part of this code     *
 * or application without a written agreement from the company. Use of any    *
 * part of this Media Player without permission can result in legal actions   *
 * being taken against you.                                                   *
 *                                                                            *
 * Copyright© Zerone Systems 2012.                                              *
 * ****************************************************************************
 * ----------------------------------------------------------------------------
 * ****************************************************************************
 */
package musicpro1.pkg1;

import java.awt.Color;
import java.awt.HeadlessException;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.border.EmptyBorder;

/**
 *
 * @author BANDA G.J
 * 078 849 5107
 * #22492186
 * 
 */
public class Help extends JFrame implements ActionListener
{
    //JTextArea helper;
    JEditorPane helper;
    JButton close;
    public Help()
    {
        setSize(605, 705);
        setDefaultCloseOperation(HIDE_ON_CLOSE);
        setLocationRelativeTo(this);
        
        Image icon = getToolkit().createImage("icon.png");
        setIconImage(icon);
        
        helper = new JTextPane();
        helper.setBounds(15, 10, 560, 600);
        helper.setEditable(false);
        try {
            helper.setPage("helpgage.html");
        } catch (IOException ex) {
            Logger.getLogger(Help.class.getName()).log(Level.SEVERE, null, ex);
        }
        helper.setText(""
                + "<!DOCTYPE html>\n" +
"<!--\n" +
"To change this license header, choose License Headers in Project Properties.\n" +
"To change this template file, choose Tools | Templates\n" +
"and open the template in the editor.\n" +
"-->\n" +
"<html>\n" +
"    <head>\n" +
"        <title>MusicPro Help Guide</title>\n" +
"        <meta charset=\"UTF-8\">\n" +
"        <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n" +
"    </head>\n" +
"    <body>\n" +
"        <div>\n" +
"            <h1>MusicPro User's Guide</h1>\n" +
"            <img src=\"media.png\" alt=\"MusicPro\">\n" +
"            <p>Welcome to MusicPro User's Guide, this will help you to understand the functionalities that come\n" +
"            with MusicPro. MusicPro is currently programmed to play mp3 files only, and do understand that this is\n" +
"            the Beta version of the program. Since MusicPro is under development, it comes with some malfunctions \n" +
"            and others features which are not functional at the moment.</p>\n" +
"        </div>\n" +
"        <div>\n" +
"            <h3>Supported Files</h3>\n" +
"            <p>MusicPro currently supports the playback of mp3 files ONLY, more file types to be included in the coming\n" +
"            versions of the program.</p>\n" +
"        </div>\n" +
"        <hr>\n" +
"        <div>\n" +
"            <h3>Playback</h3>\n" +
"            <img src=\"playback.png\" alt=\"playback\">\n" +
"            <p>MusicPro has 4 main playback functionalities, previous,play and stop(integrated on one button),and next.\n" +
"            The other button on the far right on the above picture is for viewing and hiding playlist.</p>\n" +
"            <img src=\"repeat.png\" alt=\"Repeat\">\n" +
"            <p>On the playback menu shown above, you can choose to repeat the track(song) that is currently playing. Shortcuts to\n" +
"            all the other functionalities are also shown.</p>\n" +
"        </div>\n" +
"        <hr>\n" +
"        <div>\n" +
"            <h3>Media Menu</h3>\n" +
"            <img src=\"mediaop.png\" alt=\"Media Menu\">\n" +
"            <p>To make sure that you don't find yourself making the same playlist over and over again, MusicPro has an option\n" +
"            for you were you can save the playlist you are currently playing and/or the playlist that you saved previously.\n" +
"            You can also locate the file(mp3) that you want to play by clicking the \"Open File\" option on the Media Menu.</p>\n" +
"        </div>\n" +
"        <hr>\n" +
"        <div>\n" +
"            <h3>Help and About Developers</h3>\n" +
"            <img src=\"help.png\" alt=\"Help_About\">\n" +
"            <p>To help you enjoy the use of the program(application), MusicPro comes with User's guide which explains the different functionalities that\n" +
"            come with the program. Making MusicPro user friendly and its use more efficient.</p>\n" +
"            <p>To help you keep in touch with developers of MusicPro, for suggestions and detailed help about the application, MusicPro has \"about developers\"\n" +
"            option. Here you can get to know the developers of MusicPro, be in contact with them, making detailed help a click away.</p>\n" +
"        </div>\n" +
"    </body>\n" +
"</html>\n" +
"");
        getContentPane().setLayout(null);
        add(helper);
        
        close = new JButton("Close");
        close.setFocusPainted(false);
        close.setDoubleBuffered(true);
        close.addActionListener(this);
        close.setBounds(478, 620, 100, 40);
        close.setForeground(Color.red.brighter());
        close.setBackground(Color.cyan.darker());
        
        JTextField fil = new JTextField();
        fil.setBounds(0, 638, 478, 5);
        fil.setEditable(false);
        fil.setBackground(Color.blue.brighter());
        add(fil);
        
        add(close);
        
        setVisible(true);
        setResizable(false);
    }
    
    @Override
    public void actionPerformed(ActionEvent e) 
    {
        if(e.getSource()==close)
        {
            dispose();
            MusicPro11.musicWindow.setEnabled(true);
            MusicPro11.musicWindow.setVisible(true);
        }
    }
    
}
