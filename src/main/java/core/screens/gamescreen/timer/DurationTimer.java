package core.screens.gamescreen.timer;

import com.badlogic.gdx.utils.TimeUtils;
import core.screens.navigation.NavigationBar;

public class DurationTimer {

    private static Long startTime = TimeUtils.nanoTime();
    private static int hours, minutes, seconds;

    public static void timer() {
        long timer = 1000000000L;
        if (TimeUtils.timeSinceNanos(startTime) >= timer) {
            createTime();
            startTime = TimeUtils.nanoTime();
        }
    }

    private static void createTime() {
        seconds++;
        if (seconds > 59) {
            minutes++;
            seconds = 0;
        }
        if (minutes > 59) {
            hours++;
            minutes = 0;
        }

        String h, m, s;

        if (seconds < 10) s = "0" + seconds;
        else s = String.valueOf(seconds);
        if (minutes < 10) m = "0" + minutes;
        else m = String.valueOf(minutes);
        if (hours < 10) h = "0" + hours;
        h = String.valueOf(hours);

        String time = h + ":" + m + ":" + s;
        NavigationBar.updateTimer(time);
    }
}