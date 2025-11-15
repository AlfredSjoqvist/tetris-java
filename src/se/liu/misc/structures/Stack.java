package se.liu.alfsj019.lab3;

import se.liu.alfsj019.lab1.Person;

import java.util.List;

public class Stack extends ListManipulator
{

    public void push(Person person) {
        int lastIndex = 0;
        this.add(lastIndex, person);
    }

    public Person pop(Person person) {
        return this.remove(0);
    }
    


}
