package se.liu.alfsj019.lab3;

import se.liu.alfsj019.lab1.Person;

public class Queue extends ListManipulator
{

    public void enqueue(Person person) {
        int lastIndex = elements.size() - 1 ;
        this.add(lastIndex, person);
    }

    public Person dequeue(Person person) {
        return this.remove(0);
    }



}
