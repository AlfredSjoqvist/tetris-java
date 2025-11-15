package se.liu.alfsj019.shapes;

import java.awt.*;

public class Text extends AbstractShape
{
    private int size;
    private String text;

    public Text(final int x, final int y, final int size, final Color color, final String text) {
	super(x, y, color);
	this.size = size;
	this.text = text;
    }

    @Override public String toString() {
	return "Text{" + "x=" + x + ", y=" + y + ", size=" + size + ", color=" + color + ", text='" + text + '\'' + '}';
    }


    @Override public void draw(final Graphics g) {
	g.setColor(color);
	g.setFont(new Font("serif", Font.PLAIN, size));
	g.drawString(text, x, y);
    }


    public void setX(int x) {
	this.x = x;
    }


    public void setY(int y) {
	this.y = y;
    }


    public void setColor(Color color) {
	this.color = color;
    }
}
