package se.liu.alfsj019.tetris;

public class Poly
{
    public SquareType[][] configuration;
    private SquareType type;

    public Poly(final SquareType[][] configuration, final SquareType type) {
	this.configuration = configuration;
	this.type = type;
    }

    public SquareType getType() {
	return type;
    }

    public void printPoly() {
	StringBuilder builder = new StringBuilder();

	for (int i = 0; i < configuration.length; i++) {
	    for (int j = 0; j < configuration[0].length; j++) {
		if (configuration[i][j] == SquareType.EMPTY) {
		    builder.append("-");
		} else {
		    builder.append(configuration[i][j]);
		}
	    }
	    builder.append("\n");
	}
	String result = builder.toString();
	System.out.println(result);
    }

    public SquareType getSquare(int y, int x) {
	return this.configuration[y][x];
    }

    public int getPolyHeight() {
	return configuration.length;
    }

    public int getPolyWidth() {
	return configuration[0].length;
    }
}
