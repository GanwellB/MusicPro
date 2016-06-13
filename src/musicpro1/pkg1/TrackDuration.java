/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package musicpro1.pkg1;

import com.mpatric.mp3agic.InvalidDataException;
import com.mpatric.mp3agic.Mp3File;
import com.mpatric.mp3agic.UnsupportedTagException;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;
//import static musicpro.ExtendedMethods.track;

/**
 *
 * @author Ganwell
 */
public class TrackDuration
{
    static long duraxn = 0;
    static String trackDu;
    static int menutes ;
    static int seconds,rate;
    
    public TrackDuration() throws IOException, UnsupportedTagException, InvalidDataException 
    {
        getTrackDuration(); 
    }
    public static String getTrackDuration() throws IOException, UnsupportedTagException, InvalidDataException
    {
        Mp3File file = new Mp3File(MusicProComponents.file.getAbsolutePath());
        duraxn = file.getLengthInSeconds();
        rate = file.getBitrate()*10;
        menutes = (int)duraxn/60;
        seconds = (int)duraxn - (menutes*60);
        System.out.println("Rate "+rate);
        
        trackDu = ((menutes < 10) ? ("0"+menutes):(""+menutes)) +":"+((seconds < 10) ? ("0"+seconds):(""+seconds));
        
        return trackDu;
    }
    public static void updateTrackTimeAtStart() throws IOException, UnsupportedTagException, InvalidDataException
    {
        final Timer timer = new Timer();
        Mp3File file = new Mp3File(MusicProComponents.file.getAbsolutePath());
        duraxn = file.getLengthInSeconds();
        menutes = (int)duraxn/60;
        seconds = (int)duraxn - (menutes*60);
        
        timer.schedule(new TimerTask()
        {

            @Override
            public void run() 
            {
                if(seconds <= 0)
                {
                    if(menutes == 0)
                    {
                        timer.cancel();
                    }
                    else
                    {
                        try 
                        {
                            seconds = 59;
                            timer.cancel();
                            updateTrackDurAtSecondsEqualZero();
                        }
                        catch (IOException ex)
                        {
                            Logger.getLogger(TrackDuration.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        catch (UnsupportedTagException ex)
                        {
                            Logger.getLogger(TrackDuration.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        catch (InvalidDataException ex)
                        {
                            Logger.getLogger(TrackDuration.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                    
                }
                else
                {
                    seconds = seconds - 1;
                }
                trackDu = menutes+":"+((seconds < 10) ? ("0"+seconds):(""+seconds));
                MusicProComponents.trackDur.setText(trackDu);
            }
        }, rate, rate);
    }
    public static void updateTrackTimeFromThird() throws IOException, UnsupportedTagException, InvalidDataException
    {
        final Timer tim = new Timer();
       /* Mp3File file = new Mp3File(MusicProComponents.file.getAbsolutePath());
        duraxn = file.getLengthInSeconds();
        menutes = (int)(duraxn/60)-2;*/
        
         tim.schedule(new TimerTask()
        {

            @Override
            public void run() 
            {
                seconds = seconds - 1;
                trackDu = menutes+":"+((seconds < 10) ? ("0"+seconds):(""+seconds));
                MusicProComponents.trackDur.setText(trackDu);
            }
        }, rate, rate);
    }
    public static void updateTrackTimeToFourthMinute() throws IOException, UnsupportedTagException, InvalidDataException
    {
        final Timer tim = new Timer();
        Mp3File file = new Mp3File(MusicProComponents.file.getAbsolutePath());
        duraxn = file.getLengthInSeconds();
        menutes = (int)(duraxn/60)-3;
         seconds = 59;
         tim.schedule(new TimerTask()
        {

            @Override
            public void run() 
            {
                if(seconds <= 0)
                {
                    try 
                    {
                        seconds = 59;
                        tim.cancel();
                        updateTrackDurAtSecondsEqualZero();
                    }
                    catch (IOException ex)
                    {
                        Logger.getLogger(TrackDuration.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    catch (UnsupportedTagException ex)
                    {
                        Logger.getLogger(TrackDuration.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    catch (InvalidDataException ex)
                    {
                        Logger.getLogger(TrackDuration.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                else
                {
                    seconds = seconds - 1;
                }
                trackDu = menutes+":"+((seconds < 10) ? ("0"+seconds):(""+seconds));
                MusicProComponents.trackDur.setText(trackDu);
            }
        }, rate, rate);
    }
    public static void updateTrackDurAtSecondsEqualZero() throws IOException, UnsupportedTagException, InvalidDataException
    {
        final Timer time = new Timer();
        Mp3File file = new Mp3File(MusicProComponents.file.getAbsolutePath());
        duraxn = file.getLengthInSeconds();
        menutes = (int)(duraxn/60)-1;
        
        time.schedule(new TimerTask()
        {

            @Override
            public void run() 
            {
                seconds = seconds - 1;
                if(seconds <= 0)
                {
                    try
                    {
                        if(menutes > 0)
                        {
                            time.cancel();
                            seconds = 59;
                            menutes = menutes -1;
                            updateTrackTimeFromThird();
                        }
                        else
                        {
                            time.cancel();
                        }
                    }
                    catch (IOException ex)
                    {
                        Logger.getLogger(TrackDuration.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    catch (UnsupportedTagException ex) 
                    {
                        Logger.getLogger(TrackDuration.class.getName()).log(Level.SEVERE, null, ex);
                    } 
                    catch (InvalidDataException ex) 
                    {
                        Logger.getLogger(TrackDuration.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                if(menutes == 0)
                {
                    if(seconds == 0)
                    {
                        time.cancel();
                        try 
                        {
                            updateTrackTimeAtStart();
                        } catch (IOException ex) {
                            Logger.getLogger(TrackDuration.class.getName()).log(Level.SEVERE, null, ex);
                        } catch (UnsupportedTagException ex) {
                            Logger.getLogger(TrackDuration.class.getName()).log(Level.SEVERE, null, ex);
                        } catch (InvalidDataException ex) {
                            Logger.getLogger(TrackDuration.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                    else
                    {
                        try 
                        {
                            time.cancel();
                            updateTrackTimeFromThird();
                        } 
                        catch (IOException ex) 
                        {
                            Logger.getLogger(TrackDuration.class.getName()).log(Level.SEVERE, null, ex);
                        } 
                        catch (UnsupportedTagException ex) 
                        {
                            Logger.getLogger(TrackDuration.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        catch (InvalidDataException ex) 
                        {
                            Logger.getLogger(TrackDuration.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                }
                trackDu = menutes+":"+((seconds < 10) ? ("0"+seconds):(""+seconds));
                MusicProComponents.trackDur.setText(trackDu);
            }
        }, rate, rate);
    }
    public static void updateTrackTimeBySwitch()
    {
        switch(menutes)
        {
            case 0:
            {
                System.out.println("Case 0");
                
                if(menutes <= 0)
                {
                    try {
                        updateTrackTimeAtStart();
                    } catch (IOException ex) {
                        Logger.getLogger(TrackDuration.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (UnsupportedTagException ex) {
                        Logger.getLogger(TrackDuration.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (InvalidDataException ex) {
                        Logger.getLogger(TrackDuration.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
            case 1:
            {
                System.out.println("Case 1");
                if(menutes == 1 && seconds <= 0)
                {
                    try {
                        updateTrackTimeAtStart();
                    } catch (IOException ex) {
                        Logger.getLogger(TrackDuration.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (UnsupportedTagException ex) {
                        Logger.getLogger(TrackDuration.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (InvalidDataException ex) {
                        Logger.getLogger(TrackDuration.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                if(menutes == 1 && seconds > 0)
                {
                    try {
                        updateTrackTimeAtStart();
                    } catch (IOException ex) {
                        Logger.getLogger(TrackDuration.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (UnsupportedTagException ex) {
                        Logger.getLogger(TrackDuration.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (InvalidDataException ex) {
                        Logger.getLogger(TrackDuration.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
            case 2:
            {
                System.out.println("Case 2");
                if(menutes == 2 && seconds <= 0)
                {
                    try {
                        updateTrackTimeAtStart();
                    } catch (IOException ex) {
                        Logger.getLogger(TrackDuration.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (UnsupportedTagException ex) {
                        Logger.getLogger(TrackDuration.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (InvalidDataException ex) {
                        Logger.getLogger(TrackDuration.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                if(menutes <= 2 && seconds > 0)
                {
                    try {
                        updateTrackTimeAtStart();
                    } catch (IOException ex) {
                        Logger.getLogger(TrackDuration.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (UnsupportedTagException ex) {
                        Logger.getLogger(TrackDuration.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (InvalidDataException ex) {
                        Logger.getLogger(TrackDuration.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        }
    }
}
