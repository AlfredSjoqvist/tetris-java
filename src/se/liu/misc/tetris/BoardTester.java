package se.liu.alfsj019.tetris;

public class BoardTester
{
    public static void main(String[] args) {
	Board testBoard = new Board(10, 20);
	TetrisViewer display = new TetrisViewer(testBoard);
	display.show();
    }
}
