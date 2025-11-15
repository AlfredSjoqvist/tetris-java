package se.liu.alfsj019.tetris;

import java.awt.*;

public class DefaultFallHandler implements FallHandler
{
    @Override public boolean hasCollided(Board board) {
	for (Point occupiedCoord : board.getFallingPolyOccupiedCoords()) {
	    if ((board.getSquare((int)occupiedCoord.getY(), (int)occupiedCoord.getX()) != SquareType.EMPTY) && !board.getVisitedCoords().contains(occupiedCoord)) {
		return true;
	    }
	}
	return false;
    }
}
