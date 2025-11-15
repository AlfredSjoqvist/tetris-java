package se.liu.alfsj019.tetris;

import java.awt.*;
import java.util.ArrayList;

public class HeavyFall implements FallHandler
{
    @Override public boolean hasCollided(Board board) {

	ArrayList<Point> lowestCoords = this.checkSupport(board);

	for (Point occupiedCoord : board.getFallingPolyOccupiedCoords()) {
	    if ((board.getSquare((int)occupiedCoord.getY(), (int)occupiedCoord.getX()) != SquareType.EMPTY) && !board.getVisitedCoords().contains(occupiedCoord)) {
		if ((board.getSquare((int)occupiedCoord.getY(), (int)occupiedCoord.getX()) != SquareType.OUTSIDE) && (!board.getVisitedCoords().contains(occupiedCoord)) && (board.getLatestMovementDirection() == Direction.DOWN))
		    if (lowestCoords != null) {
			board.compressSquares(lowestCoords);
			return false;
		    }
		return true;
	    }
	}
	return false;
    }

    public ArrayList<Point> checkSupport(Board board) {
	ArrayList<Integer> lowestXints = new ArrayList<>();
	ArrayList<Point> lowestCoords = new ArrayList<>();

	for (Point occupiedCoord : board.getFallingPolyOccupiedCoords()) {
	    if (!lowestXints.contains((int)occupiedCoord.getX())) {
		lowestCoords.add(occupiedCoord);
		lowestXints.add((int)occupiedCoord.getX());
	    } else {
		for (Point lowCoord : lowestCoords) {
		    if (((int)lowCoord.getY() < (int)occupiedCoord.getY()) && ((int)occupiedCoord.getX() == (int)lowCoord.getX())) {
			lowestCoords.remove(lowCoord);
			lowestCoords.add(occupiedCoord);
			break;
		    }
		}
	    }
	}

	boolean someColumnsSupported = false;
	boolean columnSupported = true;

	for (Point topCoord: lowestCoords) {
	    columnSupported = true;
	    for (int y = (int)topCoord.getY(); y <= board.getHeight(); y++) {
		if (board.getSquare(y, (int)topCoord.getX()) == SquareType.EMPTY) {
		    columnSupported = false;
		}

	    }
	    if (columnSupported) {
		someColumnsSupported = true;
	    }
	}

	if (!someColumnsSupported) {
	    return lowestCoords;
	} else {
	    return null;
	}

    }
}
