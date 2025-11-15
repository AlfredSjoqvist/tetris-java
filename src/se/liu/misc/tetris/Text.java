package se.liu.alfsj019.tetris;

import se.liu.alfsj019.shapes.Shape;

import java.awt.*;

public class Text implements Shape
{

    private int x;
    private int y;
    private String text;
    private Color color;
    private int size;
    private static final String FONT = "serif";

    public Text(final int x, final int y, final String text, final Color color, final int size) {
	this.x = x;
	this.y = y;
	this.text = text;
	this.color = color;
	this.size = size;
    }

    public String getText() {
	return text;
    }

    public int getSize() {
	return size;
    }

    @Override public int getX() {
	return this.x;
    }

    @Override public int getY() {
	return this.y;
    }

    @Override public Color getColor() {
	return this.color;
    }

    public void setX(final int x) {
	this.x = x;
    }

    public void setY(final int y) {
	this.y = y;
    }

    public void setText(final String text) {
	this.text = text;
    }

    public void setColor(final Color color) {
	this.color = color;
    }

    public void setSize(final int size) {
	this.size = size;
    }

    @Override public void draw(final Graphics g) {
	g.setColor(color);
	g.setFont(new Font(FONT, Font.PLAIN, size));
	g.drawString(text, x, y);
    }


}
