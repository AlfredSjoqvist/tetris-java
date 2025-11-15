package se.liu.alfsj019.tetris;

public class Highscore
{
    private int score;
    private String player;

    public Highscore(final int score, final String player) {
	this.score = score;
	this.player = player;
    }

    public int getScore() {
	return score;
    }

    public String getPlayer() {
	return player;
    }
}
