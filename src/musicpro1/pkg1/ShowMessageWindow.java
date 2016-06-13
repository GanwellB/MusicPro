package musicpro1.pkg1;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JWindow;

public class ShowMessageWindow extends JWindow {
    private int X = 0;
    private int Y = 0;

    JPanel Background = new JPanel();
    static JTextArea Exceptionboard = new JTextArea();
    static JLabel DeviceDriverName = new JLabel();
    JScrollPane ExceptionboardScroll = new JScrollPane(Exceptionboard);
    Font Styling = new Font(null, Font.BOLD, 15);
    JButton CloseMessage = new JButton("Close");
    
    public ShowMessageWindow() {
        setBounds(1000, 20, 300, 200);
	addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                System.exit(0); // An Exit Listener
            }
        });
        // Print (X,Y) coordinates on Mouse Click
        addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                X = e.getX();
		Y = e.getY();
		System.out.println("The (X,Y) coordinate of window is ("+ X + "," + Y + ")");}
        });
        addMouseMotionListener(new MouseMotionAdapter() {
            public void mouseDragged(MouseEvent e) {
                setLocation(getLocation().x + (e.getX() - X),
		getLocation().y + (e.getY() - Y));}
        });
        setVisible(true);
        
        Background.setLayout(null);
        Background.setBackground(Color.yellow);
        Background.setBounds(0, 0, 300, 200);
        add(Background);
        
        DeviceDriverName.setBounds(10, 10, 270, 30);
        DeviceDriverName.setFont(Styling);
        DeviceDriverName.setBackground(Color.yellow);
        Background.add(DeviceDriverName);
        
        ExceptionboardScroll.setBounds(10, 40, 280, 130);
        Exceptionboard.setFont(Styling);
        Exceptionboard.setBackground(Color.yellow);
        Background.add(ExceptionboardScroll);
        
        CloseMessage.setBounds(180, 170, 100, 20);
        CloseMessage.setFont(Styling);
        CloseMessage.setBackground(Color.yellow);
        CloseMessage.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent ae) {
                dispose();
            }
        });
        Background.add(CloseMessage);
        
        repaint();
    }
} 