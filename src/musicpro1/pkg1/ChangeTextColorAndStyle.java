/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package musicpro1.pkg1;

import java.awt.Color;
import java.awt.Component;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import javax.swing.JColorChooser;
import javax.swing.JFrame;

/**
 *
 * @author Ganwell
 */
public class ChangeTextColorAndStyle extends Frame
{
    static Color color;
    public ChangeTextColorAndStyle()
    {
        super("JColorChooser Test Frame");
        setSize(200,100);
    }
    public static void changeColor(ActionEvent ae)
    {
        color = JColorChooser.showDialog(((Component)ae.getSource()).getParent(), "Change Text Color",Color.orange.brighter());
        MusicProComponents.album.setForeground(color);
        MusicProComponents.artist.setForeground(color);
        if(ShowFileDetails.iamShowing == "yes")
        {
            ShowFileDetails.album.setForeground(color);
            ShowFileDetails.artist.setForeground(color);
            ShowFileDetails.comments.setForeground(color);
            ShowFileDetails.editDetails.setForeground(color);
            ShowFileDetails.genre.setForeground(color);
            ShowFileDetails.okToCancel.setForeground(color);
            ShowFileDetails.track.setForeground(color);
            ShowFileDetails.year.setForeground(color);
        }
        else
        {
        }
        System.out.println("Color Selected: "+color.toString());
    }
}
