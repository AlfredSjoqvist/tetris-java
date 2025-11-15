package se.liu.alfsj019.shapes;

import java.awt.*;

public class Circle extends AbstractShape
{
    private int radius;

    public Circle(final int x, final int y, final int radius, final Color color) {
	super(x, y, color);

	if (radius < 0) {
	    throw new IllegalArgumentException("Negativ radie!");
	}

	this.radius = radius;
    }

    @Override public void draw(final Graphics g) {
	g.setColor(color);
	g.drawOval(x, y, radius*2, radius*2);
    }

    @Override public String toString() {
	return "Circle{" + "x=" + getX() + ", y=" + getY() + ", radius=" + radius + ", color=" + getColor() + '}';
    }

    public int getRadius() {
	return radius;
    }

}
