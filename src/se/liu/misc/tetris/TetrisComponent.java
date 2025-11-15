package se.liu.alfsj019.tetris;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.Map;

public class TetrisComponent extends JComponent implements BoardListener
{
    private final Board board;
    private final Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    private static final int TILE_SIZE = 30;
    private final static EnumMap<SquareType, Color> SQUARE_TO_COLOR_MAP = new EnumMap<>(Map.of(
	    SquareType.EMPTY, Color.BLACK,
	    SquareType.I, Color.CYAN,
	    SquareType.J, Color.BLUE,
	    SquareType.L, Color.ORANGE,
	    SquareType.O, Color.YELLOW,
	    SquareType.S, Color.GREEN,
	    SquareType.T, Color.MAGENTA,
	    SquareType.Z, Color.RED
    ));

    public TetrisComponent(final Board board) {
	this.board = board;
	this.getInputMap().put(KeyStroke.getKeyStroke("LEFT"), "moveLeft");
	this.getInputMap().put(KeyStroke.getKeyStroke("RIGHT"), "moveRight");
	this.getInputMap().put(KeyStroke.getKeyStroke("SPACE"), "moveDown");
	this.getInputMap().put(KeyStroke.getKeyStroke("UP"), "rotateLeft");
	this.getInputMap().put(KeyStroke.getKeyStroke("DOWN"), "rotateRight");
	this.getInputMap().put(KeyStroke.getKeyStroke("P"), "pause");
	this.getActionMap().put("moveLeft", new MoveAction(Direction.LEFT, this.board));
	this.getActionMap().put("moveRight", new MoveAction(Direction.RIGHT, this.board));
	this.getActionMap().put("moveDown", new MoveAction(Direction.DOWN, this.board));
	this.getActionMap().put("rotateLeft", new RotateAction(Direction.LEFT, this.board));
	this.getActionMap().put("rotateRight", new RotateAction(Direction.RIGHT, this.board));
	this.getActionMap().put("pause", new PauseAction(this.board));
    }



    public class PauseAction extends AbstractAction {
	private final Board innerBoardPause;
	public PauseAction(final Board innerBoardPause) {
	    this.innerBoardPause = innerBoardPause;
	}
	@Override public void actionPerformed(final ActionEvent e) {
	    this.innerBoardPause.pause();
	}
    }

    public class MoveAction extends AbstractAction {
	private final Direction direction;
	private final Board innerBoardMove;

	public MoveAction(final Direction direction, final Board innerBoardMove) {
	    this.direction = direction;
	    this.innerBoardMove = innerBoardMove;
	}

	@Override public void actionPerformed(final ActionEvent e) {
	    innerBoardMove.moveWithKey(direction);
	}
    }
    public class RotateAction extends AbstractAction {
	private final Direction direction;
	private final Board innerBoardRotate;
	public RotateAction(final Direction direction, final Board innerBoardRotate) {
	    this.direction = direction;
	    this.innerBoardRotate = innerBoardRotate;
	}

	@Override public void actionPerformed(final ActionEvent e) {
	    innerBoardRotate.rotateWithKey(direction);
	}
    }

    public Dimension getPreferredSize() {

	int width = this.board.getWidth();
	int height = this.board.getHeight();

	Dimension dimension = new Dimension(width*TILE_SIZE, height*TILE_SIZE);
	return dimension;

    }

    public void boardChanged() {
	this.repaint();
    }

    @Override protected void paintComponent(Graphics g) {

	super.paintComponent(g);
	final Graphics2D g2d = (Graphics2D) g;

	int width = this.board.getWidth();
	int height = this.board.getHeight();

	for (int x = 0; x < width; x++) {
	    for (int y = 0; y < height; y++) {
		g2d.setColor(SQUARE_TO_COLOR_MAP.get(this.board.getSquare(y, x)));
		g2d.fillRect(x*TILE_SIZE, y*TILE_SIZE, TILE_SIZE, TILE_SIZE);
	    }
	}

	ArrayList<Text> textsOnBoard = this.board.getTextsOnBoard();

	for (Text text: textsOnBoard) {
	    text.draw(g2d);
	}


    }


}
