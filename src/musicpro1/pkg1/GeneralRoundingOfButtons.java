package musicpro1.pkg1;

import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import javax.swing.border.Border;

/**
 *
 * @author Banda
 */
public class GeneralRoundingOfButtons implements Border
{
    int radius;
    public GeneralRoundingOfButtons(int radius)
    {
        this.radius = radius;
    }
    @Override
    public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) 
    {
        Graphics2D d = (Graphics2D)g;
        //d.setColor(Color.blue.brighter());
        d.drawRoundRect(x, y, width-1, height-1, radius, radius);
    }

    @Override
    public Insets getBorderInsets(Component c) 
    {
        return new Insets(this.radius+1, this.radius+1, this.radius+2, this.radius);
    }

    @Override
    public boolean isBorderOpaque() 
    {
        return true;
    }
}
