package se.liu.alfsj019.tetris;

import com.google.gson.Gson;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;

import javax.swing.*;
import java.awt.*;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;
import java.util.Random;

public class Board
{
    private SquareType[][] squares;
    private SquareType[][] oldSquares;
    private int width;
    private int height;
    private boolean paused = false;
    private TetrominoMaker tetrominoMaker = new TetrominoMaker();
    private Poly falling = null;
    private Point fallingPolyCoord = null;
    private boolean gameOver = false;
    private ArrayList<Point> fallingPolyOccupiedCoords  = new ArrayList<>();
    private ArrayList<Point> visitedCoords  = new ArrayList<>();
    private ArrayList<BoardListener> boardListeners = new ArrayList<>();
    private static final int MARGIN = 3;
    private static final int DOUBLE_MARGIN = MARGIN * 2;
    private BoardToTextConverter converter = new BoardToTextConverter();
    private final static Random randomNumberGenerator = new Random();
    private Direction latestMovementDirection = null;
    private ArrayList<Text> textsOnBoard = new ArrayList<>();
    private Dimension frameSize = null;
    private Map<Integer, Integer> scoreMap = Map.of(0, 0, 1, 100, 2, 300, 3, 500, 4, 800);
    private int currentScore = 0;
    private String playerName = null;
    private HighscoreList highscoreList  = new HighscoreList(new ArrayList<>());
    private int clearedLinesTotal = 0;
    private int currentLevel = 1;
    private FallHandler fallHandler = new DefaultFallHandler();
    private int powerUpTimer = 4;
    private String powerUpString = "NONE";

    public Board(final int width, final int height) {

	this.width = width;
	this.height = height;
	this.squares = new SquareType[height + DOUBLE_MARGIN][width + DOUBLE_MARGIN];
	this.oldSquares = new SquareType[height + DOUBLE_MARGIN][width + DOUBLE_MARGIN];
	this.gameOver = false;

	this.initiateTexts();
	this.loadHighscores();

	for (int i = 0; i < height + DOUBLE_MARGIN; i++) {
	    for (int j = 0; j < width + DOUBLE_MARGIN; j++) {
		if ((i < MARGIN) || (j < MARGIN) || (i >= height + MARGIN) || (j >= width + MARGIN)) {
		    this.squares[i][j] = SquareType.OUTSIDE;
		} else {
		    this.squares[i][j] = SquareType.EMPTY;
		}
	    }
	}

	this.copyOverSquares();
	this.notifyListeners();
    }


    /* === Getters === */

    public SquareType getSquare(int y, int x) {
	return this.squares[MARGIN + y][MARGIN + x];
    }
    public SquareType getOldSquare(int y, int x) {
	return this.oldSquares[MARGIN + y][MARGIN + x];
    }

    public int getWidth() {
	return width;
    }

    public int getHeight() {
	return height;
    }

    public Poly getFalling() {
	return falling;
    }

    public Point getFallingPolyCoord() {
	return fallingPolyCoord;
    }

    public String getPlayerName() {
	return playerName;
    }

    public ArrayList<Text> getTextsOnBoard() {
	return this.textsOnBoard;
    }

    public int getCurrentLevel() {
	return currentLevel;
    }

    public ArrayList<Point> getFallingPolyOccupiedCoords() {
	return fallingPolyOccupiedCoords;
    }

    public ArrayList<Point> getVisitedCoords() {
	return visitedCoords;
    }

    public Direction getLatestMovementDirection() {
	return latestMovementDirection;
    }

    public boolean isGameOver() {
	return gameOver;
    }


    /* === Setters === */

    public void setFrameSize(Dimension dimension) {
	this.frameSize = dimension;
    }

    public void setFallingPolyCoord(final int x, final int y) {
	Point newCoord = new Point(x, y);
	this.fallingPolyCoord = newCoord;
    }

    public void setSquare(int y, int x, SquareType squareType) {
	this.squares[y + MARGIN][x + MARGIN] = squareType;
    }

    public void setOldSquare(int y, int x, SquareType squareType) {
	this.oldSquares[y + MARGIN][x + MARGIN] = squareType;
    }

    public void setPlayerName(final String playerName) {
	this.playerName = playerName;
    }

    public void setGameOver() {
	this.gameOver = true;
	this.createGameOverText();
    }


    /* === Text related functions === */

    public void initiateTexts() {
	this.createScoreText();
	this.createLevelText();
	this.createPowerUpText();
    }

    public void createScoreText() {
	this.textsOnBoard.add(new Text(5, 25, "SCORE: 0", Color.WHITE, 25));
    }
    public void createLevelText() {
	this.textsOnBoard.add(new Text(190, 25, "LEVEL: 1", Color.WHITE, 25));
    }

    public void createPowerUpText() {
	this.textsOnBoard.add(new Text(5, 50, "POWER: NONE", Color.WHITE, 25));
    }

    public void createGameOverText() {
	int y = (int)(this.frameSize.getHeight() / 2);
	int x = (int)(this.frameSize.getWidth() / 2 - 110);
	this.textsOnBoard.add(new Text(x, y, "GAME OVER :(", Color.WHITE, 30));
    }

    public Text createPauseText() {

	int y = (int)(this.frameSize.getHeight() / 2);
	int x = (int)(this.frameSize.getWidth() / 2 - 70);

	return new Text(x, y, "PAUSED", Color.WHITE, 30);
    }


    public void updateScoreText() {
	for (Text text: textsOnBoard) {
	    if (text.getText().startsWith("SCORE:")) {
		String scoreString = String.valueOf(this.currentScore);
		text.setText("SCORE: " + scoreString);
		break;
	    }
	}
    }

    public void updateLevelText() {
	for (Text text: textsOnBoard) {
	    if (text.getText().startsWith("LEVEL:")) {
		String levelString = String.valueOf(this.currentLevel);
		text.setText("LEVEL: " + levelString);
		break;
	    }
	}
    }

    public void updatePowerUpText() {
	for (Text text: textsOnBoard) {
	    if (text.getText().startsWith("POWER:")) {
		text.setText("POWER: " + this.powerUpString);
		break;
	    }
	}
    }


    /* === Game state related functions === */

    public void restart() {
	this.textsOnBoard.clear();
	this.initiateTexts();
	this.currentLevel = 1;
	this.gameOver = false;
	this.falling = null;
	this.emptyBoard();
	this.currentScore = 0;
	this.playerName = null;
    }

    public void pause() {
	if (this.gameOver) {
	    this.paused = false;
	} else {
	    this.paused = !this.paused;
	}
	if (this.paused) {
	    textsOnBoard.add(this.createPauseText());
	} else {
	    for (Text text: textsOnBoard) {
		if (text.getText().equals("PAUSED")) {
		    textsOnBoard.remove(text);
		    break;
		}
	    }
	}
	this.notifyListeners();
    }

    public void tick() {

	if (!this.gameOver) {
	    if (!this.paused) {
		if (this.falling == null) {
		    this.createFalling();
		}

		this.moveFallingPoly(Direction.DOWN);
		this.handleCollision();
		this.drawFalling();
	    }
	}
    }

    public void updateCurrentLevel() {
	switch ((int)(this.clearedLinesTotal/10)) {
	    case 0:
		this.currentLevel = 1;
		break;
	    case 1:
		this.currentLevel = 2;
		break;
	    case 2:
		this.currentLevel = 3;
		break;
	    case 3:
		this.currentLevel = 4;
		break;
	    case 4:
		this.currentLevel = 5;
		break;
	    case 5:
		this.currentLevel = 6;
		break;
	    case 6:
		this.currentLevel = 7;
		break;
	    case 7:
		this.currentLevel = 8;
		break;
	    case 8:
		this.currentLevel = 9;
		break;
	    case 9:
		this.currentLevel = 10;
		break;
	}
    }



    /* === Movement related functions === */

    public void moveWithKey(Direction direction) {
	if (!this.paused) {
	    this.moveFallingPoly(direction);
	    this.handleCollision();
	    this.drawFalling();
	}
    }

    public void rotateWithKey(Direction direction) {
	if (!this.paused) {
	    this.rotateFallingPoly(direction);
	    this.drawFalling();
	}
    }

    public void moveFallingPoly(Direction direction) {

	if (!gameOver) {
	    switch (direction) {
		case LEFT:
		    this.fallingPolyCoord.setLocation(this.fallingPolyCoord.getX() - 1, this.fallingPolyCoord.getY());
		    break;
		case RIGHT:
		    this.fallingPolyCoord.setLocation(this.fallingPolyCoord.getX() + 1, this.fallingPolyCoord.getY());
		    break;
		case DOWN:
		    this.fallingPolyCoord.setLocation(this.fallingPolyCoord.getX(), this.fallingPolyCoord.getY() + 1);
		    break;
		case UP:
		    this.fallingPolyCoord.setLocation(this.fallingPolyCoord.getX(), this.fallingPolyCoord.getY() - 1);
		    this.cementFallingPoly();

	    }
	}
	this.updateFallingPolyOccupied();
	this.latestMovementDirection = direction;
    }

    public void rotateFallingPoly(Direction direction) {
	while (true) {

	    Poly rotatedPoly = new Poly(new SquareType[this.falling.getPolyHeight()][this.falling.getPolyWidth()], this.falling.getType());

	    int rotations = 0;

	    if (direction == Direction.RIGHT) {
		rotations = 1;
	    }
	    if (direction == Direction.LEFT) {
		rotations = 3;
	    }


	    for (int i = 0; i < rotations; i++) {
		for (int r = 0; r < this.falling.getPolyHeight(); r++) {
		    for (int c = 0; c < this.falling.getPolyWidth(); c++) {
			rotatedPoly.configuration[c][this.falling.getPolyWidth() - 1 - r] = this.falling.configuration[r][c];
		    }
		}
	    }

	    this.falling = rotatedPoly;
	    this.updateFallingPolyOccupied();

	    if (this.fallHandler.hasCollided(this)) {
		direction = direction.getOpposite();
		continue;
	    }

	    return;
	}
    }


    /* === Highscore related functions === */

    public void loadHighscores () {

	try (FileReader out = new FileReader("highscores.json")) {
	    Gson gson = new Gson();
	    this.highscoreList = gson.fromJson(out, HighscoreList.class);
	} catch (JsonIOException ignored) {
	    if (JOptionPane.showConfirmDialog(null, "Try again?", "java.JsonIOException", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
		this.loadHighscores();
	    } else {
		this.highscoreList = new HighscoreList(new ArrayList<>());
	    }
	} catch (JsonSyntaxException ignored) {
	    if (JOptionPane.showConfirmDialog(null, "Try again?", "java.JsonSyntaxException", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
		this.loadHighscores();
	    } else {
		this.highscoreList = new HighscoreList(new ArrayList<>());
	    }
	} catch (FileNotFoundException ignored) {
	    if (JOptionPane.showConfirmDialog(null, "Try again?", "java.FileNotFoundException (Highscore file not found)", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
		this.loadHighscores();
	    } else {
		this.highscoreList = new HighscoreList(new ArrayList<>());
	    }
	} catch (IOException ignored) {
	    if (JOptionPane.showConfirmDialog(null, "Try again?", "java.IOException", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
		this.loadHighscores();
	    } else {
		this.highscoreList = new HighscoreList(new ArrayList<>());
	    }
	}

	if (this.highscoreList == null) {
	    this.highscoreList = new HighscoreList(new ArrayList<>());
	}
    }

    public void showScoreScreen() {

	Highscore thisHighscore = new Highscore(this.currentScore, this.playerName);

	try {
	    this.highscoreList.addHighscore(thisHighscore);
	} catch (FileNotFoundException ignored) {
	    if (JOptionPane.showConfirmDialog(null, "Try again?", "java.FileNotFoundException (Highscore file not found)", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
		this.showScoreScreen();
	    } else {
		this.highscoreList = new HighscoreList(new ArrayList<>());
	    }
	} catch (IOException ignored) {
	    if (JOptionPane.showConfirmDialog(null, "Try again?", "java.IOException", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
		this.showScoreScreen();
	    } else {
		this.highscoreList = new HighscoreList(new ArrayList<>());
	    }
	}

	this.emptyBoard();
	this.textsOnBoard.clear();
	int spacing = 39;

	this.textsOnBoard.add(new Text((int)(this.frameSize.getWidth() / 2 - 110), 80, "HIGH SCORES:", Color.WHITE, 30));
	for (int i = 0; i < this.highscoreList.getHighscoreList().size(); i++) {

	    Highscore highscore = this.highscoreList.getHighscoreList().get(i);
	    String scoreString = String.valueOf(highscore.getScore());
	    String highScoreString = scoreString + "    " + highscore.getPlayer();

	    this.textsOnBoard.add(new Text((int)(this.frameSize.getWidth() / 2 - 110), 120 + i * spacing, highScoreString, Color.WHITE, 30));
	}

	this.notifyListeners();

    }

    /* === Board related functions === */

    public void addBoardListener(BoardListener b) {
	boardListeners.add(b);
    }

    private void notifyListeners() {

	for (BoardListener boardListener: boardListeners) {
	    boardListener.boardChanged();
	}
    }

    public void copyOverSquares() {
	for (int i = 0; i < height; i++) {
	    for (int j = 0; j < width; j++) {
		this.setOldSquare(i, j, this.getSquare(i, j));
	    }
	}
    }

    public void emptyBoard() {
	for (int y = 0; y < this.height; y++) {
	    for (int x = 0; x < this.width; x++) {
		this.setSquare(y, x, SquareType.EMPTY);
		this.setOldSquare(y, x, SquareType.EMPTY);

	    }
	}
    }

    public void updateFallingPolyOccupied() {

	ArrayList<Point> occupiedCoords = new ArrayList<>();

	for (int y = 0; y < this.falling.getPolyHeight(); y++) {
	    for (int x = 0; x < this.falling.getPolyWidth(); x++) {
		if (this.falling.getSquare(y, x) != SquareType.EMPTY) {
		    Point occupiedCoord = new Point(x + (int)this.fallingPolyCoord.getX(), y + (int)this.fallingPolyCoord.getY());
		    occupiedCoords.add(occupiedCoord);

		}
	    }
	}
	this.fallingPolyOccupiedCoords = occupiedCoords;
    }

    public void cementFallingPoly() {
	this.visitedCoords = new ArrayList<>();
	this.removeFullLines();
	this.copyOverSquares();
	this.createFalling();
	this.powerUpTimer -= 1;
	if (this.fallHandler.hasCollided(this)) {
	    this.setGameOver();
	}
    }

    public void removeFullLines() {

	int numberOfFullLines = 0;

	boolean fullLine = true;

	for (int y = 0; y < this.height; y++) {
	    fullLine = true;
	    for (int x = 0; x < this.width; x++) {
		if (this.getSquare(y, x) == SquareType.EMPTY) {
		    fullLine = false;
		}
	    }
	    if (fullLine) {
		numberOfFullLines += 1;
		this.clearedLinesTotal += 1;
		for (int reverseY = y; reverseY > 0; reverseY -= 1) {
		    for (int x = 0; x < this.width; x++) {
			this.setSquare(reverseY, x, this.getSquare(reverseY - 1, x));
		    }
		}
	    }
	}

	int scoreToBeAdded = this.scoreMap.get(numberOfFullLines);
	this.currentScore += scoreToBeAdded;
	this.updateCurrentLevel();
	this.updateScoreText();
	this.updateLevelText();

    }

    public void compressSquares(ArrayList<Point> topCoords) {

	boolean pullDown = false;

	for (Point topCoord:topCoords) {
	    pullDown = false;
	    for (int y = this.height-1; y >= (int)topCoord.getY(); y -= 1) {
		if (this.getSquare(y, (int)topCoord.getX()) == SquareType.EMPTY) {
		    pullDown = true;
		    for (int restoreY = this.height-1; restoreY > y; restoreY -= 1) {
			this.setSquare(restoreY, (int)topCoord.getX(), this.getOldSquare(restoreY, (int)topCoord.getX()));
		    }
		}
		if (pullDown) {
		    if (y != (int)topCoord.getY()) {
			this.setSquare(y, (int) topCoord.getX(), this.getSquare(y - 1, (int) topCoord.getX()));
		    } else {
			this.setSquare(y, (int) topCoord.getX(), SquareType.EMPTY);
		    }
		}
	    }
	    for (int y = 0; y <= (int)topCoord.getY(); y++) {
		this.setOldSquare(y, (int)topCoord.getX(), SquareType.EMPTY);
		this.setSquare(y, (int)topCoord.getX(), SquareType.EMPTY);
	    }
	}



    }


    public void handleCollision() {
	if (this.fallHandler.hasCollided(this)) { moveFallingPoly(this.latestMovementDirection.getOpposite()); }
    }

    public void createFalling() {

	Random randomNumberGenerator = new Random();

	if (this.powerUpTimer == 0) {
	    this.activatePowerup();
	} else {
	    this.deactivatePowerup();
	}

	this.falling = tetrominoMaker.getPoly(randomNumberGenerator.nextInt(7));
	this.fallingPolyCoord = new Point(3, 0);
	this.updateFallingPolyOccupied();
    }

    public void activatePowerup() {
	if (randomNumberGenerator.nextInt(2) == 0) {
	    this.fallHandler = new FallThrough();
	    this.powerUpString = "GHOST";
	} else {
	    this.fallHandler = new HeavyFall();
	    this.powerUpString = "HEAVY";
	}
	this.powerUpTimer = 4;
	this.updatePowerUpText();
    }

    public void deactivatePowerup() {
	this.fallHandler = new DefaultFallHandler();
	this.powerUpString = "NONE";
	this.updatePowerUpText();
    }

    public void drawFalling() {

	/**
	 * This method draws the currently falling poly on the board.
	 * Note that the falling poly isn't hard-coded into the
	 * implementation of the board but instead drawn from a
	 * coordinate.
	 */

	if (!this.gameOver) {
	    this.visitedCoords.addAll(this.fallingPolyOccupiedCoords);

	    for (int y = 0; y < this.height; y++) {
		for (int x = 0; x < this.width; x++) {
		    Point currentCoord = new Point(x, y);

		    if (this.fallingPolyOccupiedCoords.contains(currentCoord)) {
			this.setSquare(y, x, this.falling.getType());
		    } else if (this.visitedCoords.contains(currentCoord)) {
			this.setSquare(y, x, this.getOldSquare(y, x));
		    }
		}
	    }
	}

	    this.notifyListeners();
    }

    public void scramble() {
	for (int i = 0; i < height; i++) {
	    for (int j = 0; j < width; j++) {
		SquareType squareType = SquareType.values()[randomNumberGenerator.nextInt(8)];
		this.setSquare(i, j, squareType);
	    }
	}

	this.notifyListeners();
    }
}
