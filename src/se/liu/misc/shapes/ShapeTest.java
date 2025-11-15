package se.liu.alfsj019.shapes;

import java.awt.*;
import java.util.ArrayList;

public class ShapeTest
{
    public static void main(String[] args) {

	ArrayList<Shape> shapes = new ArrayList<>();

	Circle circle5 = new Circle(1, 1, 1, Color.blue);
	Circle circle6 = new Circle(8, 5, 2, Color.yellow);
	Circle circle7 = new Circle(6, 0, 3, Color.red);
	Circle circle8 = new Circle(4, 4, 4, Color.green);

	shapes.add(circle5);
	shapes.add(circle6);
	shapes.add(circle7);
	shapes.add(circle8);

	Rectangle rectangle5 = new Rectangle(2, 2, 3, 4, Color.pink);
	Rectangle rectangle6 = new Rectangle(5, 7, 1, 6, Color.black);

	shapes.add(rectangle5);
	shapes.add(rectangle6);

	Text text1 = new Text(3, 2, 10, Color.black, "HEjehej");
	Text text2 = new Text(4, 4, 16, Color.red, "sdfdadsfasdf");

	shapes.add(text1);
	shapes.add(text2);

	for (Shape shape : shapes) {
	    /*shape.draw();*/
	}
    }
}
