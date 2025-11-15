package se.liu.alfsj019.tetris;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.util.Map;

public class GameTimer
{
    private static Map<Integer, Integer> levelDelayMap = Map.of(1, 600, 2, 550, 3, 500, 4, 450, 5, 400,
								6, 350, 7, 300, 8, 250, 9, 200, 10, 150);

    public static void main(String[] args) {

	Board testBoard = new Board(10, 20);
	TetrisViewer display = new TetrisViewer(testBoard);

	display.showIntroIcon();
	
	final Action doOneStep = new AbstractAction() {
	    public void actionPerformed(ActionEvent e) {
		testBoard.tick();
		if (display.getBoard().isGameOver()) {

		    if (display.getBoard().getPlayerName() == null) {
			display.writeNamePrompt();
		    }
		}
	    }
	};


	Timer clockTimer = new Timer(levelDelayMap.get(1), doOneStep);
	clockTimer.setCoalesce(true);
	clockTimer.start();
	display.show();
	testBoard.createFalling();

	final Action changeLevel = new AbstractAction() {
	    public void actionPerformed(ActionEvent e) {
		clockTimer.setDelay(levelDelayMap.get(display.getBoard().getCurrentLevel()));
	    }
	};

	Timer delayTimer = new Timer(500, changeLevel);
	delayTimer.setCoalesce(true);
	delayTimer.start();

    }
}
