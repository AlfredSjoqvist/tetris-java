package se.liu.alfsj019.tetris;

import java.awt.*;

public class FallThrough implements FallHandler
{

    @Override public boolean hasCollided(Board board) {
	for (Point occupiedCoord : board.getFallingPolyOccupiedCoords()) {
	    if ((board.getSquare((int) occupiedCoord.getY(), (int) occupiedCoord.getX()) == SquareType.OUTSIDE) &&
		!board.getVisitedCoords().contains(occupiedCoord)) {
		return true;
	    }
	}
	return false;

    }
}
