package se.liu.alfsj019.calendar;

public class SimpleDate
{
    private int year;
    private Month month;
    private int day;

    public SimpleDate(final int year, final Month month, final int day) {
	this.year = year;
	this.month = month;
	this.day = day;
    }

    @Override public String toString() {
	return day + " " + month + " " + year;
    }

    public int getYear() {
	return year;
    }

    public Month getMonth() {
	return month;
    }

    public int getDay() {
	return day;
    }


}
