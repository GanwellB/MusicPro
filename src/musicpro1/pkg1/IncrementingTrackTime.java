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
import static musicpro1.pkg1.TrackDuration.duraxn;
import static musicpro1.pkg1.TrackDuration.menutes;
import static musicpro1.pkg1.TrackDuration.rate;
import static musicpro1.pkg1.TrackDuration.seconds;

/**
 *
 * @author Ganwell
 */
public class IncrementingTrackTime 
{
    static int inc_sec = 0, inc_min = 0;
    static String timeMoved;
    static Timer timer;
    public IncrementingTrackTime() throws IOException, UnsupportedTagException, InvalidDataException
    {
        Mp3File file = new Mp3File(MusicProComponents.file.getAbsolutePath());
        menutes = (int)duraxn/60;
        rate = file.getBitrate()*10;
        seconds = (int)duraxn - (menutes*60);
    }
    public static String displayTimeElapsed()
    {
        timeMoved = ((inc_min < 10) ? ("0"+inc_min):(""+inc_min)) +":"+((inc_sec < 10) ? ("0"+inc_sec):(""+inc_sec));
        
        return timeMoved;
    }
    public static void incrementTime()
    {
        timer = new Timer();
        timer.schedule(new TimerTask() 
        {

            @Override
            public void run()
            {
                inc_sec = inc_sec + 1;
                if(inc_min == menutes && inc_sec >= seconds)
                {
                    inc_min = 0;
                    inc_sec = 0;
                    MusicProComponents.timeElapsed.setText(timeMoved);
                    timer.cancel();
                }
                if(inc_sec == 60)
                {
                    inc_min = inc_min + 1;
                    inc_sec = 0;
                    timer.cancel();
                    incrementTime();
                }
                timeMoved = ((inc_min < 10) ? ("0"+inc_min):(""+inc_min)) +":"+((inc_sec < 10) ? ("0"+inc_sec):(""+inc_sec));
                MusicProComponents.timeElapsed.setText(timeMoved);
            }
        }, 1000, 1000);
    }
}
