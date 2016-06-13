package musicpro1.pkg1;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JPanel;

/**
 *
 * @author Ganwell
 */
public class TrackProgressCounter extends  JPanel
{
    public static int moving_x = 2, sixFilled = 5;
    public void paint(Graphics g)
    {
        Graphics2D d = (Graphics2D)g;
        d.drawRoundRect(5, 2, 295, 5, 5, 5);
        d.setColor(Color.green.darker());
        d.fillOval(moving_x, -1, 12, 12);
        d.setColor(Color.blue.brighter().brighter());
        d.setColor(Color.blue.darker());
        d.fillRoundRect(5, 2, 5, 5, 5, 5);
    }
}
