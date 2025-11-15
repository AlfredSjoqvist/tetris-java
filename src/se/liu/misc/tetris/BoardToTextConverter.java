package se.liu.alfsj019.tetris;

import java.awt.*;

public class BoardToTextConverter
{
    public String convertToText(Board board) {

	StringBuilder builder = new StringBuilder();

	int width = board.getWidth();
	int height = board.getHeight();

	Poly falling = board.getFalling();
	int fallingHeight = falling.getPolyHeight();
	int fallingWidth = falling.getPolyWidth();
	Point fallingCoord = board.getFallingPolyCoord();
	int fallingX = (int)fallingCoord.getX();
	int fallingY = (int)fallingCoord.getY();


	for (int i = 0; i < height; i++) {
	    for (int j = 0; j < width; j++) {
		if ((fallingY <= i) && (i < fallingHeight+fallingY) && (fallingX <= j) && (j < fallingWidth + fallingX)) {
		    switch (falling.getSquare(i - fallingY, j - fallingX)) {
			case L:
			    builder.append("#");
			    break;
			case J:
			    builder.append("%");
			    break;
			case Z:
			    builder.append("&");
			    break;
			case S:
			    builder.append("+");
			    break;
			case T:
			    builder.append("@");
			    break;
			case O:
			    builder.append("$");
			    break;
			case I:
			    builder.append("0");
			    break;
			case EMPTY:
			    builder.append("-");
			    break;
			case OUTSIDE:
			    builder.append("F");
		    }
		} else {
		    switch (board.getOldSquare(i, j)) {
			case L:
			    builder.append("#");
			    break;
			case J:
			    builder.append("%");
			    break;
			case Z:
			    builder.append("&");
			    break;
			case S:
			    builder.append("+");
			    break;
			case T:
			    builder.append("@");
			    break;
			case O:
			    builder.append("$");
			    break;
			case I:
			    builder.append("0");
			    break;
			case EMPTY:
			    builder.append("-");
			    break;
		    }
		}
	    }
	    builder.append("\n");
	}
	String result = builder.toString();
	return result;
    }

}
