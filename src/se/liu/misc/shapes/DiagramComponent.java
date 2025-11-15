package se.liu.alfsj019.shapes;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class DiagramComponent extends JComponent
{
    private List<Shape> shapes;

    public DiagramComponent() {
	this.shapes = new ArrayList<>();
    }

    public void addShape(Shape shape) {
	this.shapes.add(shape);
    }
    @Override protected void paintComponent(final Graphics g) {
	super.paintComponent(g);
	for (Shape shape : shapes) {
	    shape.draw(g);
	}

	// Senare ska vi rita upp alla former här!
    }


}
