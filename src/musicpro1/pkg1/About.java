/*
 * ****************************************************************************
 * --------------------------MusicPro--------Version-1-1-4---------------------
 * ****************************************************************************
 * This is a Zerone Systems Creation, you may not use any part of this code   *
 * or application without a written agreement from the company. Use of any    *
 * part of this Media Player without permission can result in legal actions   *
 * being taken against you.                                                   *
 *                                                                            *
 * Copyright© Zerone Systems 2014.                                            *
 * ****************************************************************************
 * ----------------------------------------------------------------------------
 * ****************************************************************************
 */
package musicpro1.pkg1;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

/**
 *
 * @author BANDA G.J
 * 078 849 5107
 * #22492186
 * 
 */
public class About extends JFrame implements ActionListener
{
    JTabbedPane pane;
    JPanel about,thanks,authors,licence;
    JTextArea aText,tText,auText,liText;
    JButton close;
    JLabel abt,thnx, authr,licn;
    Font font = new Font("engravers mt", Font.BOLD, 12);
    Font intFont = new Font("Times New Roman", Font.ROMAN_BASELINE, 15);
    
    Font intFont1 = new Font(null, Font.ROMAN_BASELINE, 15);
    public About()
    {
        super("About the MusicPro Application v1.1.4.");
        setSize(640, 520);
        setLocationRelativeTo(this);
        setDefaultCloseOperation(HIDE_ON_CLOSE);
        
        Image image = getToolkit().createImage("images/playmusic.png");
        setIconImage(image);
        
        pane = new JTabbedPane();
        pane.setSize(605, 400);
        pane.setLocation(10, 10);
        getContentPane().setLayout(null);
        about = new JPanel();
        about.setLayout(null);
        about.setBackground(Color.white);
        JLabel blm = new JLabel("MusicPro 1.1.4 Beta");
        blm.setFont(font);
        String s = new String(
                "\nMusicPro is a media player can read mp3 files Only.\n"
                + "This version of MusicPro was compiled by Banda G.J\n"
                + "You are using the BLM2 Interface v2.\n"
                + "\nCopyright© 2014 by the MusicPro Team.\n"
                + "http://www.Zerone/MusicProTeam.com \n");
        
        Image image1 = getToolkit().createImage("images/icon.png");
        
        abt = new JLabel(new ImageIcon(image1));
        abt.setBounds(200, 180, image1.getWidth(abt)+20, image1.getHeight(abt));
        blm.setBounds(180, 160, 260, 20);
        about.add(blm);
        about.add(abt);
        aText = new JTextArea();
        aText.setBounds(0, 0, 605, 180);
        aText.setText(s);
        aText.setSelectedTextColor(Color.cyan.brighter());
        aText.setFont(intFont);
        aText.setEditable(false);
        aText.setFocusable(false);
        about.add(aText,BorderLayout.CENTER);
        
        thanks = new JPanel();
        thanks.setLayout(null);
        thanks.setBackground(Color.white);
        
        
        tText = new JTextArea();
        tText.setText("\nThanks to the MusicPro11 Team and Zerone Systems Company\n"
                + "for hosting the project.\n"
                + "We would also like to thank all the users who are\n"
                + "using MusicPro Application."
                + "\n"
                + "\nMusicPro Applcation plugins uses external libraries and\n"
                + "makes extensive use of the following persons' or companies' codes:"
                + "\n"
                + "\n"
                + "-----------------------------------------------------------------\n"
                + "FFmpeg - Copyright (c) 2000-2010 the FFmpeg developers.\n"
                + "mp3agic-0.8.1\n"
                + "derbynet\n"
                + "derbyclient\n"
                + "derby\n");
        tText.setBounds(0, 0, 605, 520);
        tText.setFocusable(false);
        tText.setFont(intFont);
        tText.setEditable(false);
        tText.setSelectedTextColor(Color.red.brighter());
        thanks.add(tText,BorderLayout.CENTER);
        
        JLabel me = new JLabel(new ImageIcon("images/me.jpg"));
        me.setBounds(150, 140, 300, 260);
        
        
        authors = new JPanel();
        authors.setLayout(null);
        authors.setBackground(Color.white);
        auText = new JTextArea();
        auText.setText("\nThe programming for this version was done by Banda G.J\n"
                + "together with all the Artwork.\n"
                + "Banda G.J a.k.a JB is a Computer Science Graduate from the \n"
                + "North West University South Africa.\n"
                + "\nHe is currently working at Zerone Systems as a Chief Programer\n"
                + "and He is also the Founder of the Company.");
        auText.setBounds(0, 0, 605, 160);
        auText.setEditable(false);
        auText.setFont(intFont);
        auText.setSelectedTextColor(Color.green.brighter());
        authors.add(auText,BorderLayout.CENTER);
        auText.setFocusable(false);
        authors.add(me);
        licence = new JPanel();
        licence.setLayout(null);
        liText = new JTextArea();
        liText.setText(  "* **************************************************************\n"
                        +"* --------------------MusicPro--Version-1-1-4----------------------*\n"
                        +"* **************************************************************\n"
                        +"This is a Zerone Systems Creation, you may not use any \n part of"
                        +"this code                                                    \n"
                        +"or application without a written agreement from the company. \n"
                        +"Use of any                                                   \n"
                        +"part of this Media Player without permission can result in  \n"
                        +"legal actions                                                \n"
                        +"being taken against you.                                     \n"
                        +"                                                             \n"
                        +"                                \n"
                        +"* **************************************************************\n"
                        +"* --------------Copyright© Zerone Systems 2014----------------*\n"
                        +"* **************************************************************");
        liText.setBounds(0, 0, 605, 400);
        liText.setEditable(false);
        liText.setFocusable(false);
        liText.setFont(intFont1);
        liText.setSelectedTextColor(Color.red.brighter());
        licence.add(liText,BorderLayout.CENTER);
        
        close = new JButton("Close");
        close.setBounds(515, 430, 100, 40);
        close.addActionListener(this);
        close.setFocusPainted(false);
        close.setDoubleBuffered(true);
        close.setBackground(Color.cyan.darker());
        close.setForeground(Color.red.brighter());
        add(close);
        
        JTextField field = new JTextField();
        field.setBackground(Color.blue.brighter());
        field.setBounds(0, 450, 516, 5);
        field.setEditable(false);
        add(field);
        
        pane.add("Version",about);
        pane.add("Authors",authors);
        pane.add("Thanks",thanks);
        pane.add("Licence",licence);
        add(pane, BorderLayout.CENTER);
        setVisible(true);
        setResizable(false);
    }
    
    @Override
    public void actionPerformed(ActionEvent e) 
    {
        Object obj = e.getSource();
        if(obj==close)
        {
            dispose();
            MusicPro11.musicWindow.setEnabled(true);
            MusicPro11.musicWindow.setVisible(true);
        }
    }
}
