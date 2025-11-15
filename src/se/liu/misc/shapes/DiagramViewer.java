package se.liu.alfsj019.shapes;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.Random;


public class DiagramViewer
{
    private final static List<Color> COLORS =
	    List.of(Color.BLACK, Color.RED, Color.GREEN, Color.BLUE,
		    Color.CYAN, Color.YELLOW, Color.MAGENTA);

    // Set a fixed seed 0 so you always get the same
    // shapes (for debugging)
    private final static Random randomNumberGenerator = new Random(0);

    private static Color getRandomColor() {
	return COLORS.get(randomNumberGenerator.nextInt(COLORS.size()));
    }

    private static Circle getRandomCircle() {
	return new Circle(randomNumberGenerator.nextInt(400), randomNumberGenerator.nextInt(400),
			  randomNumberGenerator.nextInt(200), getRandomColor());
    }

    private static Rectangle getRandomRectangle() {
	return new Rectangle(randomNumberGenerator.nextInt(400), randomNumberGenerator.nextInt(400),
			     randomNumberGenerator.nextInt(200), randomNumberGenerator.nextInt(200),
			     getRandomColor());
    }

    private static Text getRandomText() {
	return new Text(randomNumberGenerator.nextInt(400), randomNumberGenerator.nextInt(400),
			randomNumberGenerator.nextInt(50), getRandomColor(), "Hej");
    }

    public static void main(String[] args) {

	DiagramComponent comp = new DiagramComponent();

	final Random randomGenerator = new Random(0);

	for (int i = 0; i < 10; i++) {
	    switch (randomGenerator.nextInt(3)) {
		case 0:
		    comp.addShape(getRandomCircle());
		    break;
		case 1:
		    comp.addShape(getRandomRectangle());
		    break;
		case 2:
		    comp.addShape(getRandomText());
		    break;
	    }
	}

	JFrame frame = new JFrame("Mitt fönster");
	frame.setLayout(new BorderLayout());
	frame.add(comp, BorderLayout.CENTER);
	frame.setSize(800, 600);
	frame.setVisible(true);
    }
}