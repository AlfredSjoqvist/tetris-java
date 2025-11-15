package se.liu.alfsj019.calendar;

public class TimeSpan
{
    private TimePoint start;
    private TimePoint end;

    public TimeSpan(final TimePoint start, final TimePoint end) {
	this.start = start;
	this.end = end;
    }

    public TimePoint getStart() {
	return start;
    }

    public TimePoint getEnd() {
	return end;
    }

    @Override public String toString() {
	return start + "-" + end;
    }

    public static void main(String[] args) {
	TimePoint time1 = new TimePoint(13, 6);
	TimePoint time2 = new TimePoint(13, 57);
	TimeSpan timespan1 = new TimeSpan(time1, time2);
	System.out.println(timespan1);
    }
}
