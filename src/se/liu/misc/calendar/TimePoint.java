package se.liu.alfsj019.calendar;

public class TimePoint
{
    private int hour;
    private int minute;

    public TimePoint(final int hour, final int minute) {
	this.hour = hour;
	this.minute = minute;
    }

    @Override public String toString() {
	return String.format("%02d:%02d", hour, minute);
    }

    public int getHour() {
	return hour;
    }

    public int getMinute() {
	return minute;
    }

    public static void main(String[] args) {

	TimePoint time = new TimePoint(13, 6);
	System.out.println(time);
    }
}
