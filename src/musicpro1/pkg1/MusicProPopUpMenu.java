/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package musicpro1.pkg1;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import javax.swing.Action;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import static musicpro1.pkg1.MusicPro11.createPlay;
import static musicpro1.pkg1.MusicPro11.stopPlay;

/**
 *
 * @author Ganwell
 */
public class MusicProPopUpMenu 
{
    public static JPopupMenu popupMenu;
    public static JMenuItem  repeatTrack, removeTrack, showTrackDetails, clearPlayList;
    
    public MusicProPopUpMenu()
    {
        popupMenu = new JPopupMenu("List Menu");
        
        repeatTrack = new JMenuItem("Repeat Track");
        repeatTrack.addActionListener(new ActionListener()
        {

            @Override
            public void actionPerformed(ActionEvent e)
            {
                repeatFromPopUp();
            }
        });
        
        removeTrack = new JMenuItem("Remove Track");
        removeTrack.addActionListener(new ActionListener() 
        {

            @Override
            public void actionPerformed(ActionEvent e) 
            {
                removeTrackFromList();
            }
        });
        
        showTrackDetails = new JMenuItem("Show Details");
        showTrackDetails.addActionListener(new ActionListener() 
        {

            @Override
            public void actionPerformed(ActionEvent e)
            {
            }
        });
        
        clearPlayList = new JMenuItem("Clear List");
        clearPlayList.addActionListener(new ActionListener() 
        {

            @Override
            public void actionPerformed(ActionEvent e) 
            {
            }
        });
        
        popupMenu.add(repeatTrack);
        popupMenu.add(removeTrack);
        popupMenu.add(showTrackDetails);
        popupMenu.add(clearPlayList);
    }
    public static void repeatFromPopUp()
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
    public static void removeTrackFromList()
    {
        int currentIndex = MusicProComponents.index;
        int remebIndex = currentIndex;
        if(MusicProComponents.list.getSelectedIndex() >= MusicProComponents.listModel.getSize())
        {
            int removalIndex = MusicProComponents.list.getSelectedIndex();
            MusicProComponents.listModel.removeElementAt(removalIndex);
            MusicProComponents.listModel2.removeElementAt(removalIndex);
            MusicProComponents.list.repaint();
            currentIndex = 0;
            MusicProComponents.index = currentIndex;
            MusicProComponents.list.setSelectedIndex(MusicProComponents.index);
            
            if(MusicProComponents.condition.equals("play"))
            { 

                if(MusicProComponents.file==null || MusicProComponents.file!=null)
                {
                      MusicProComponents.file = (File) MusicProComponents.listModel2.getElementAt(MusicProComponents.index);
                }
                createPlay();
                MusicProComponents.condition = "stop";
                //MusicProComponents.playButton.setText(MusicProComponents.condition);
                MusicProComponents.playItem.setText(MusicProComponents.condition);
            }
            else if(MusicProComponents.condition.equals("stop"))
            {
                MusicProComponents.list.setSelectedIndex(remebIndex);
            }
        }
        else if(MusicProComponents.list.getSelectedIndex() < MusicProComponents.listModel.getSize())
        {
            int removalIndex = MusicProComponents.list.getSelectedIndex();
            MusicProComponents.listModel.removeElementAt(removalIndex);
            MusicProComponents.listModel2.removeElementAt(removalIndex);
            MusicProComponents.list.repaint();
            
            currentIndex++;
            MusicProComponents.index = currentIndex;
            MusicProComponents.list.setSelectedIndex(MusicProComponents.index);
            
            if(MusicProComponents.condition.equals("play"))
            { 

                if(MusicProComponents.file==null || MusicProComponents.file!=null)
                {
                      MusicProComponents.file = (File) MusicProComponents.listModel2.getElementAt(MusicProComponents.index);
                }
                createPlay();
                MusicProComponents.condition = "stop";
                //MusicProComponents.playButton.setText(MusicProComponents.condition);
                MusicProComponents.playItem.setText(MusicProComponents.condition);
            }
            else if(MusicProComponents.condition.equals("stop"))
            {
                MusicProComponents.list.setSelectedIndex(remebIndex);
            }
        }
    }
    public static void allowTrackToPlayAfterRemoval()
    {
        if(!MusicProComponents.listModel2.isEmpty())
        {
             if(MusicProComponents.condition.equals("play"))
             { 

                 if(MusicProComponents.file==null || MusicProComponents.file!=null)
                 {
                       MusicProComponents.file = (File) MusicProComponents.listModel2.getElementAt(MusicProComponents.index);
                 }
                 createPlay();
                 MusicProComponents.condition = "stop";
                 //MusicProComponents.playButton.setText(MusicProComponents.condition);
                 MusicProComponents.playItem.setText(MusicProComponents.condition);
             }
             else if(MusicProComponents.condition.equals("stop"))
             {
                 stopPlay();
                 MusicProComponents.condition = "play";
                 //MusicProComponents.playButton.setText(MusicProComponents.condition);
                 MusicProComponents.playItem.setText(MusicProComponents.condition);
             }
        }
        else
        {

        }
    }
}
