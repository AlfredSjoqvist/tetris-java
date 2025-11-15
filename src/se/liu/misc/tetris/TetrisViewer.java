package se.liu.alfsj019.tetris;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.concurrent.TimeUnit;

public class TetrisViewer
{
    private final Board board;
    private final JFrame window  = new JFrame();
    private final JMenuBar menuBar  = new JMenuBar();

    public TetrisViewer(final Board board) {
	this.board = board;
    }

    public void showExitPrompt() {

	this.board.pause();
	Object[] options = {"Yes", "No"};
	int optionChosen = JOptionPane.showOptionDialog(this.window, "Exit game?", "Are you sure you want to exit?",
							JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[1]);
	if (optionChosen == 0) {
	    System.exit(0);
	}
	this.board.pause();

    }

    public void initMenuBar() {
	final JMenu edit = new JMenu("Options");

	JMenuItem restartButton = new JMenuItem("Restart");
	JMenuItem exitButton = new JMenuItem("Exit");

	exitButton.addActionListener(ev -> this.showExitPrompt());
	restartButton.addActionListener(ev -> this.board.restart());

	edit.add(restartButton);
	edit.add(exitButton);
	this.menuBar.add(edit);
	this.window.setJMenuBar(this.menuBar);
    }

    public void writeNamePrompt() {

	String playerName = JOptionPane.showInputDialog(this.window,"Please enter name");

	this.board.setPlayerName(playerName);
	this.board.showScoreScreen();
    }


    public void showIntroIcon() {
	final JFrame frame = new JFrame("Intro Icon");
	frame.setLayout(new GridLayout(1,1));
	frame.add(new IconPainter());
	frame.setSize(660, 1000);
	frame.setVisible(true);

	try {TimeUnit.SECONDS.sleep(2);}
	catch (InterruptedException e) {}

	frame.dispose();
    }

    public void show() {

	TetrisComponent tetrisComponent = new TetrisComponent(this.board);
	this.board.addBoardListener(tetrisComponent);
	this.window.setLayout(new BorderLayout());
	this.window.add(tetrisComponent, BorderLayout.CENTER);

	this.initMenuBar();

	this.window.pack();
	this.window.setVisible(true);
	this.board.setFrameSize(this.window.getSize());
	this.window.addWindowListener(new WindowAdapter() {
	    @Override
	    public void windowClosing(WindowEvent windowEvent) {
		System.exit(0);
	    }
	});
    }

    public Board getBoard() {
	return board;
    }

    public void update() {
	TetrisComponent tetrisComponent = new TetrisComponent(this.board);
    }
}
