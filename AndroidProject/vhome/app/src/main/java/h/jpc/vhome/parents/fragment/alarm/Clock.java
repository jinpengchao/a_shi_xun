package h.jpc.vhome.parents.fragment.alarm;

import androidx.annotation.NonNull;

public class Clock{
    public static final int clock_open = 1;
    public static final int clock_close = 0;

    String hour;
    String minute;
    String content;
    int  ClockType;

    public String getMinute() {
        return minute;
    }

    public String getHour() {
        return hour;
    }

    public void setHour(String hour) {
        this.hour = hour;
    }

    public void setMinute(String minute) {
        this.minute = minute;
    }

    public int getClockType() {
        return ClockType;
    }

    public void setClockType(int clockType) {
        ClockType = clockType;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "Clock{" +
                "hour='" + hour + '\'' +
                ", minute='" + minute + '\'' +
                ", content='" + content + '\'' +
                ", ClockType=" + ClockType +
                '}';
    }
}
