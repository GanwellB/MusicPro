/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package musicpro1.pkg1;

import java.awt.Color;
import java.awt.Desktop;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import static musicpro1.pkg1.Drivers.FoundDrivesName;

/**
 *
 * @author Ganwell
 */
public class ShowAutoPlay implements ActionListener
{
    public static JFrame autoPlayWindow;
    public static JButton openExplorer, playAudioFiles, takeNoAction;
    public static ImageIcon not_actionIcon = new ImageIcon("images/noaction.png"),
            explorerFolder = new ImageIcon("images/music.png"),
            playMusic = new ImageIcon("images/playmusic.png");
    public static String showing = "no";
    public ShowAutoPlay()
    {
        autoPlayWindow = new JFrame(Drivers.FoundDrivesName);
        autoPlayWindow.setSize(200, 120);
        autoPlayWindow.setUndecorated(true);
        autoPlayWindow.setOpacity(0.4f);
        autoPlayWindow.setAlwaysOnTop(true);
        autoPlayWindow.getContentPane().setLayout(null);
        autoPlayWindow.setResizable(false);
        autoPlayWindow.setBackground(Color.white);
        autoPlayWindow.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        autoPlayWindow.setVisible(true);
        autoPlayWindow.setLocationRelativeTo(MusicPro11.musicWindow);
        
        playAudioFiles = new JButton("Play Files",playMusic);
        playAudioFiles.addActionListener(this);
        playAudioFiles.setBounds(0, 0, 200, 40);
        
        openExplorer = new JButton("Open Explorer",explorerFolder);
        openExplorer.addActionListener(this);
        openExplorer.setBounds(0, 40, 200, 40);
        
        takeNoAction = new JButton("Take No Action",not_actionIcon);
        takeNoAction.addActionListener(this);
        takeNoAction.setBounds(0, 80, 200, 40);
        
        autoPlayWindow.add(playAudioFiles);
        autoPlayWindow.add(takeNoAction);
        autoPlayWindow.add(openExplorer);
        
        showing = "yes";
    }
    @Override
    public void actionPerformed(ActionEvent e)
    {
        Object o = e.getSource();
        if(o.equals(playAudioFiles))
        {
            try 
            {
                ExtendedMethods.checkIfIsPlaying();
                PlayAllMP3FilesFromUSB.getSongsFromUSBDrive();
            }
            catch (IOException ex) 
            {
                Logger.getLogger(ShowAutoPlay.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        if(o.equals(takeNoAction))
        {
            setWindowInvisible();
        }
        if(o.equals(openExplorer))
        {
            try 
            {
                if(Drivers.listModel.getSize() <=0)
                {
                    Desktop.getDesktop().open(new File(Drivers.listModel.getElementAt(Drivers.listModel.getSize()).toString()));
                    Drivers.FoundDrivesName = "";
                    autoPlayWindow.dispose();
                }
                else
                {
                    Desktop.getDesktop().open(new File(Drivers.listModel.getElementAt(Drivers.index+1).toString()));
                    Drivers.FoundDrivesName = "";
                    autoPlayWindow.dispose();
                }
               /* Desktop.getDesktop().open(new File(Drivers.listModel.getElementAt(Drivers.index).toString()));
                Drivers.FoundDrivesName = "";
                autoPlayWindow.dispose();*/
            }
            catch (IOException ex) 
            {
                Logger.getLogger(ShowAutoPlay.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    public static void setWindowInvisible()
    {
        if(showing == "yes")
        {
            autoPlayWindow.dispose();
            showing = "no";
        }
        else
        {
        }
    }
}