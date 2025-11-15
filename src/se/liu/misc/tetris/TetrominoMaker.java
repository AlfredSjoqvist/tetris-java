package se.liu.alfsj019.tetris;

public class TetrominoMaker
{
    public int getNumberOfTypes() {
	return 7;
    }
    public Poly getPoly(int n) {

	/**
	 * This method takes in a poly ID and returns the correct poly
	 * represented in a double array. The arguments for the helper
	 * function build blocks takes in the dimensions of the array
	 * and what coordinates of that array should be part of the
	 * poly and not.
	 */

	switch (SquareType.values()[n+1]) {
	    case I:
		int[] dimensionsI = {4, 4};
		int[][] coordinatesI = {{1, 0}, {1, 1}, {1, 2}, {1, 3}};
		return buildBlock(SquareType.I, dimensionsI, coordinatesI);
	    case J:
		int[] dimensionsJ = {3, 3};
		int[][] coordinatesJ = {{0, 0}, {1, 0}, {1, 1}, {1, 2}};
		return buildBlock(SquareType.J, dimensionsJ, coordinatesJ);
	    case L:
		int[] dimensionsL = {3, 3};
		int[][] coordinatesL = {{0, 2}, {1, 0}, {1, 1}, {1, 2}};
		return buildBlock(SquareType.L, dimensionsL, coordinatesL);
	    case O:
		int[] dimensionsO = {2, 2};
		int[][] coordinatesO = {{0, 0}, {0, 1}, {1, 0}, {1, 1}};
		return buildBlock(SquareType.O, dimensionsO, coordinatesO);
	    case Z:
		int[] dimensionsS = {3, 3};
		int[][] coordinatesS = {{0, 1}, {0, 2}, {1, 0}, {1, 1}};
		return buildBlock(SquareType.S, dimensionsS, coordinatesS);
	    case S:
		int[] dimensionsZ = {3, 3};
		int[][] coordinatesZ = {{0, 0}, {0, 1}, {1, 1}, {1, 2}};
		return buildBlock(SquareType.Z, dimensionsZ, coordinatesZ);
	    case T:
		int[] dimensionsT = {3, 3};
		int[][] coordinatesT = {{0, 1}, {1, 0}, {1, 1}, {1, 2}};
		return buildBlock(SquareType.T, dimensionsT, coordinatesT);
	}
	int[] dimensionsNull = {1, 1};
	int[][] coordinatesNull = {};
	Poly defaultPoly = buildBlock(SquareType.EMPTY, dimensionsNull, coordinatesNull);
	return defaultPoly;
    }

    public Poly buildBlock(SquareType squareType, int[] dimensions, int[][] coordinates) {

	int height = dimensions[0];
	int width = dimensions[1];
	SquareType[][] block = new SquareType[height][width];

	for (int column = 0; column < width; column++) {
	    for (int row = 0; row < height; row++) {
		for (int coordinateIndex = 0; coordinateIndex < coordinates.length; coordinateIndex++) {
		    if ((row == coordinates[coordinateIndex][0]) && (column == coordinates[coordinateIndex][1])) {
			block[row][column] = squareType;
			break;
		    } else {
			block[row][column] = SquareType.EMPTY;
		    }

		}
	    }
	}
	Poly poly = new Poly(block, squareType);
	return poly;
    }



}
