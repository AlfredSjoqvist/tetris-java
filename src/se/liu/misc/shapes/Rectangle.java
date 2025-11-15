package se.liu.alfsj019.shapes;

import java.awt.*;

public class Rectangle extends AbstractShape
{

    private int width;
    private int height;


    public Rectangle(final int x, final int y, final int width, final int height, final Color color) {
        super(x, y, color);
        this.width = width;
        this.height = height;
    }

    @Override public String toString() {
        return "Rectangle{" + "x=" + x + ", y=" + y + ", width=" + width + ", height=" + height + ", color=" + color + '}';
    }

    @Override public void draw(final Graphics g) {
        g.setColor(color);
        g.drawRect(x, y, width, height);
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
