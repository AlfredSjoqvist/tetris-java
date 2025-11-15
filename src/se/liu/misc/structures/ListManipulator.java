package se.liu.alfsj019.lab3;

import se.liu.alfsj019.lab1.Person;

import java.util.List;

public class ListManipulator
{
    protected List<Person> elements = null;

    public int size() {
	return elements.size();
    }

    public boolean isEmpty() {
	return elements.isEmpty();
    }

    public boolean contains(final Object o) {
	return elements.contains(o);
    }

    public void add(int index, Person element) { elements.add(index, element); }

    public Person remove(int index) { return elements.remove(index); }

    public void clear() {
	elements.clear();
    }
}
