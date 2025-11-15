package se.liu.alfsj019.tetris;

import java.util.Comparator;

public class ScoreComparator implements Comparator<Highscore>
{
    @Override public int compare(final Highscore o1, final Highscore o2) {
	if (o1.getScore() > o2.getScore()) {
	    return o1.getScore();
	} else {
	    return o2.getScore();
	}
    }
}
