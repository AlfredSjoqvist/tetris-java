package se.liu.alfsj019.lab1;

import java.lang.reflect.Constructor;
import java.time.LocalDate;
import java.time.Period;

public class Person
{
    private String name;
    private LocalDate birthDay;

    public Person(final String name, final LocalDate birthDay) {
	this.name = name;
	this.birthDay = birthDay;
    }

    public String toString() {
	return this.name + " " + this.getAge();
    }

    public int getAge() {
	return Period.between(this.birthDay, LocalDate.now()).getYears();
    }

    public static void main(String[] args) {

	LocalDate mybirthday = LocalDate.of(2001, 1, 19);
	String myname = "Alfred";

	Person me = new Person(myname, mybirthday);
	Person david = new Person("David", LocalDate.of(2001, 6, 2));
	Person sandra = new Person("Sandra", LocalDate.of(2000, 9, 10));

	System.out.println(me);
	System.out.println(david);
	System.out.println(sandra);

    }
}
