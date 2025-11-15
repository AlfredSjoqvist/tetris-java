package se.liu.alfsj019.tetris;

import javax.swing.*;
import java.awt.*;

public class OldTetrisViewer
{
    private final Board board;

    public OldTetrisViewer(final Board board) {
	this.board = board;
    }

    public void show() {

	int boardWidth = this.board.getWidth();
	int boardHeight = this.board.getHeight();

	BoardToTextConverter converter = new BoardToTextConverter();
	this.board.drawFalling();
	String testBoardString = converter.convertToText(this.board);

	JTextArea textArea = new JTextArea("Tetris", boardHeight, boardWidth);
	textArea.setText(testBoardString);

	JFrame window = new JFrame();
	window.setLayout(new BorderLayout());
	window.add(textArea, BorderLayout.CENTER);
	textArea.setFont(new Font("Monospaced", Font.PLAIN, 20));
	window.pack();
	window.setVisible(true);
    }
}
