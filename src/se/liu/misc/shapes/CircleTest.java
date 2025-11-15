package se.liu.alfsj019.shapes;

import java.awt.*;
import java.util.ArrayList;

public class CircleTest
{
    public static void main(String[] args) {

	ArrayList<Circle> circles = new ArrayList<>();

	Circle circle1 = new Circle(1, 1, 1, Color.blue);
	Circle circle2 = new Circle(8, 5, 2, Color.yellow);
	Circle circle3 = new Circle(6, 0, 3, Color.red);
	Circle circle4 = new Circle(4, 4, 4, Color.green);

	circles.add(circle1);
	circles.add(circle2);
	circles.add(circle3);
	circles.add(circle4);

	for (Circle circle : circles) {
	    System.out.println(circle.getX());
	    System.out.println(circle.getY());
	}
    }
}
