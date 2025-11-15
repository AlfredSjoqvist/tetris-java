package se.liu.alfsj019.calendar;

import java.util.ArrayList;
import java.util.List;

import static se.liu.alfsj019.calendar.Month.getMonthDays;
import static se.liu.alfsj019.calendar.Month.getMonthNumber;
import static se.liu.alfsj019.calendar.Month.isValidMonth;

public class Cal
{
    private List<Appointment> appointments;

    public Cal(final List<Appointment> appointments) {

	this.appointments = appointments;
    }

    public void show() {
	for (Appointment appointment : this.appointments) {
	    System.out.println(appointment);
	}
    }

    public void book(int year, String monthName, int day,
		     int startHour, int startMinute, int endHour,
		     int endMinute, String subject) {
	if (year < 1970) {
	    throw new IllegalArgumentException("Invalid year");
	} else if (startHour >23 || startHour < 0 || startMinute > 59 || startMinute < 0) {
	    throw new IllegalArgumentException("Invalid start time");
	} else if (endHour > 23 || endHour < 0 || endMinute > 59 || endMinute < 0) {
	    throw new IllegalArgumentException("Invalid end time");
	} else if (!isValidMonth(monthName)) {
	    throw new IllegalArgumentException("Invalid month");
	} else {

	    int monthNumber = getMonthNumber(monthName);
	    int monthDays = getMonthDays(monthName);
	    Month monthObject = new Month(monthName, monthNumber, monthDays);
	    SimpleDate dateObject = new SimpleDate(year, monthObject, day);

	    TimePoint startPoint = new TimePoint(startHour, startMinute);
	    TimePoint endPoint = new TimePoint(endHour, endMinute);
	    TimeSpan timeSpanObject = new TimeSpan(startPoint, endPoint);

	    Appointment appointment = new Appointment(subject, dateObject, timeSpanObject);
	    this.appointments.add(appointment);

	}
    }

    public static void main(String[] args) {
	List<Appointment> appointments = new ArrayList<>();
	Cal testCalendar = new Cal(appointments);
	testCalendar.book(2023, "january", 19, 11, 20, 12, 0, "Birthday celebration");
	testCalendar.book(2019, "december", 14, 0, 1, 1, 0, "Christmas celebration");
	testCalendar.book(2020, "april", 17, 19, 47, 21, 0, "Study session");
	testCalendar.book(2021, "march", 31, 3, 30, 17, 0, "Go to the movies");
	testCalendar.book(2023, "september", 1, 8, 0, 9, 0, "Eat breakfast");
	testCalendar.show();
    }
}
