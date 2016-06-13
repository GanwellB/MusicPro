package musicpro1.pkg1;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.net.*;
import java.util.Timer;
import java.util.TimerTask;
import javax.swing.JButton;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.border.Border;
import javax.swing.event.HyperlinkEvent;
import javax.swing.event.HyperlinkListener;
import javax.swing.text.html.HTMLDocument;
import javax.swing.text.html.HTMLFrameHyperlinkEvent;

/**
 *
 * @author Banda
 * 22492186
 * 0788495107
 */
public class MyBrawser implements ActionListener, HyperlinkListener
{
    Toolkit toolkit = Toolkit.getDefaultToolkit();
    Dimension dimension = toolkit.getScreenSize();
    JFrame brawserWindow, frame;
    JEditorPane viewSiteBrawsed;
    JTextField addressField;
    JTabbedPane tabbedPane;
    JPanel componentPanel,panel1;
    JToolBar toolBar;
    String homepage = "http://www.google.com/";
    JButton addNewTab,previousPage,nextPage,closeTab,closeTabX,closeAllTabs;
    JLabel msgLabel;
    int index = 0;
    Timer timer;
    
    public MyBrawser() throws IOException,MalformedURLException
    {
        showGUI();
        cleanGraphicsAndFreemMemory();
    }
    public final void showGUI() throws IOException, MalformedURLException
    {
        brawserWindow = new JFrame("My Brawser");
        brawserWindow.setVisible(true);
        brawserWindow.setSize(dimension);
        brawserWindow.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        brawserWindow.setLayout(null);
        brawserWindow.getContentPane().setBackground(new Color(.7f, .9f, .9f, .8f));
        brawserWindow.addWindowListener(new WindowAdapter() 
        {
            @Override
            public void windowClosing(WindowEvent event)
            {
                if(index > 0)
                {
                    closingTabsFromWindow();
                }
                else
                {
                    int resuI = JOptionPane.showConfirmDialog(null, "Are you sure you want to close the brawser.?", "My Brawser", JOptionPane.YES_NO_OPTION);
                    if(resuI == JOptionPane.YES_OPTION)
                    {
                        System.exit(0);
                    }
                    else
                    {
                        
                    }
                }
            }
        });
        
        ///Toolbar Components
        
        previousPage = new JButton("<");
        previousPage.addActionListener(this);
        previousPage.setBackground(new Color(.7f, .9f, .9f, .8f));
        previousPage.setBounds(10, 2, 50, 50);
        previousPage.setFocusPainted(false);
        previousPage.setBorder(new RoundeBorder(60));
        
        nextPage = new JButton(">");
        nextPage.setBackground(new Color(.7f, .9f, .9f, .8f));
        nextPage.setBounds(65, 15, 30, 30);
        nextPage.addActionListener(this);
        nextPage.setFocusPainted(false);
        nextPage.setBorder(new RoundeBorder(30));
        
        addressField = new JTextField(homepage);
        addressField.setLocation(120, 20);
        addressField.setBackground(new Color(.7f, .9f, .2f, .8f));
        addressField.setSize(660, 25);
        
        addNewTab = new JButton("new");
        addNewTab.setBackground(new Color(.7f, .9f, .9f, .8f));
        addNewTab.setFocusPainted(false);
        addNewTab.setBounds(800, 20, 50, 25);
        addNewTab.addActionListener(this);
        addNewTab.setBorder( new RoundeBorder(12));
        
        closeTab = new JButton("close");
        closeTab.setFocusPainted(false);
        closeTab.setBounds(860, 20, 50, 25);
        closeTab.addActionListener(this);
        closeTab.setBackground(new Color(.7f, .9f, .9f, .8f));
        closeTab.setBorder( new RoundeBorder(12));
        
        toolBar = new JToolBar();
        toolBar.setLocation(0, 0);
        toolBar.setLayout(null);
        toolBar.setFloatable(false);
        toolBar.setBackground(new Color(.7f, .9f, .9f, .8f));
        toolBar.setSize(dimension.width, 60);
        
        toolBar.add(addressField);
        toolBar.add(addNewTab);
        toolBar.add(previousPage);
        toolBar.add(nextPage);
        toolBar.add(closeTab);
        //Page viewing components
        viewSiteBrawsed = new JEditorPane();
        viewSiteBrawsed.setEditable(false);
        viewSiteBrawsed.addHyperlinkListener(this);
        //viewSiteBrawsed.setPage(new URL(addressField.getText()));
        
        JScrollPane scrollPane = new JScrollPane(viewSiteBrawsed);
        scrollPane.setBounds(0, 0, dimension.width, dimension.height);
        
        componentPanel = new JPanel();
        componentPanel.setLayout(new GridLayout());
        componentPanel.setSize(dimension);
        componentPanel.setBackground(new Color(.7f, .9f, .9f, .8f));
        componentPanel.add(scrollPane);
        
        
        tabbedPane = new JTabbedPane(JTabbedPane.TOP, JTabbedPane.WRAP_TAB_LAYOUT);
        tabbedPane.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
        tabbedPane.setLocation(0, 61);
        tabbedPane.setBackground(new Color(.7f, .9f, .9f, .8f));
        tabbedPane.setSize(dimension);
        
        String tabName = deIntegrateAddress(homepage);
        
        tabbedPane.add(componentPanel, tabName);
        
        brawserWindow.add(toolBar);
        brawserWindow.add(tabbedPane);
        
        brawserWindow.repaint();
        Runtime.getRuntime().gc();
    }
    public void addNewTabToPane()
    {
        index++;
        viewSiteBrawsed = new JEditorPane();
        viewSiteBrawsed.setEditable(false);
        viewSiteBrawsed.addHyperlinkListener(this);
        //viewSiteBrawsed.setPage(new URL(addressField.getText()));
        
        JScrollPane scrollPane = new JScrollPane(viewSiteBrawsed);
        scrollPane.setBounds(0, 0, dimension.width, dimension.height);
        
        componentPanel = new JPanel();
        componentPanel.setLayout(new GridLayout());
        componentPanel.setSize(dimension);
        componentPanel.setBackground(new Color(.7f, .9f, .9f, .8f));
        componentPanel.add(scrollPane);
        
        addressField.setText("");
        
        tabbedPane.add(componentPanel, "New Tab");
        tabbedPane.setBackground(new Color(.7f, .9f, .9f, .8f));
        tabbedPane.setSelectedIndex(index);
        
        brawserWindow.repaint();
        Runtime.getRuntime().gc();
    }
    public void closeCurrentTab()
    {
        if(index > 0)
        {
            tabbedPane.remove(index);
            index--;
            tabbedPane.setSelectedIndex(index);
        }
        else
        {
            System.exit(0);
        }
        brawserWindow.repaint();
        Runtime.getRuntime().gc();
    }
    public void closingTabsFromWindow()
    {
        frame = new JFrame("My Brawser");
        frame.setVisible(true);
        frame.setSize(400, 200);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.getContentPane().setLayout(null);
        
        msgLabel = new JLabel("Do you want to close all tabs or the current tab.?");
        msgLabel.setBounds(80, 20, 250, 40);
        
        closeAllTabs = new JButton("Close All Tabs");
        closeAllTabs.addActionListener(this);
        closeAllTabs.setBounds(60, 100, 120, 25);
        
        closeTabX = new JButton("Close Current Tab");
        closeTabX.addActionListener(this);
        closeTabX.setBounds(190,100,120,25);
        
        frame.add(closeAllTabs);
        frame.add(msgLabel);
        frame.add(closeTabX);
        brawserWindow.repaint();
        Runtime.getRuntime().gc();
    }
    @Override
    public void actionPerformed(ActionEvent e) 
    {
        Object o = e.getSource();
        if(o.equals(addNewTab))
        {
            addNewTabToPane();
            brawserWindow.repaint();
        }
        if(o.equals(closeTab))
        {
            closeCurrentTab();
        }
        if(o.equals(closeAllTabs))
        {
            System.exit(0);
        }
        if(o.equals(closeTabX))
        {
            closeCurrentTab();
            frame.dispose();
        }
    }
    public final void cleanGraphicsAndFreemMemory()
    {
        timer = new Timer();
        timer.schedule(new TimerTask()
        {

            @Override
            public void run() 
            {
                brawserWindow.repaint();
                Runtime.getRuntime().gc();
            }
        }, 100,200);
    }
    @Override
    public void hyperlinkUpdate(HyperlinkEvent e)
    {
        if(e.getEventType() == HyperlinkEvent.EventType.ACTIVATED)
        {
            JEditorPane editorPane = (JEditorPane)e.getSource();
            if(e instanceof HTMLFrameHyperlinkEvent)
            {
                HTMLFrameHyperlinkEvent event = (HTMLFrameHyperlinkEvent)e;
                HTMLDocument document = (HTMLDocument)editorPane.getDocument();
                document.processHTMLFrameHyperlinkEvent(event);
            }
            else
            {
                try 
                {
                    editorPane.setPage(e.getURL());
                    addressField.setText(e.getURL().toString());
                }
                catch (Exception ex) 
                {
                    ex.printStackTrace();
                }
            }
        }
    }
    class RoundeBorder implements Border
    {
        int radius;

        public RoundeBorder(int  radius) 
        {
            this.radius = radius;
        }
        @Override
        public void paintBorder(Component c, Graphics g, int x, int y, int width, int height)
        {
            g.drawRoundRect(x, y, width-1, height-1, radius, radius);
        }

        @Override
        public Insets getBorderInsets(Component c) 
        {
            return new Insets(this.radius +1 , this.radius + 1, this.radius+2, this.radius);
        }

        @Override
        public boolean isBorderOpaque() 
        {
            return true;
        }
    }
    public String deIntegrateAddress(String webName)
    {
        String address = addressField.getText();
        int i = address.indexOf("www");
        webName = address.substring(i);
        
        return webName;
    }
}