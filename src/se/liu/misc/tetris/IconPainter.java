package se.liu.alfsj019.tetris;

import javax.swing.*;
import java.awt.*;

public class IconPainter extends JComponent
{
    final private ImageIcon icon = new ImageIcon(ClassLoader.getSystemResource("images/introimage.jpeg"));

    public void paintComponent(final Graphics g) {
	final Graphics2D g2d = (Graphics2D) g;
	g2d.setRenderingHint(	RenderingHints.KEY_ANTIALIASING,
				     RenderingHints.VALUE_ANTIALIAS_ON);

	icon.paintIcon(this, g, 0, 0);
    }

    public static void main(String[] args) {
	final JFrame frame = new JFrame("Graphics2D Test");
	frame.setLayout(new GridLayout(1,1));
	frame.add(new IconPainter());
	frame.setSize(1000, 1000);
	frame.setVisible(true);
    }
}
