package se.liu.alfsj019.tetris;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;

public class HighscoreList
{
    private ArrayList<Highscore> highscoreList;

    public HighscoreList(final ArrayList<Highscore> highscoreList) {
	this.highscoreList = highscoreList;
    }

    public ArrayList<Highscore> getHighscoreList() {
	return highscoreList;
    }

    public void addHighscore(Highscore highscore) throws IOException, FileNotFoundException {
	ScoreComparator scoreComparator = new ScoreComparator();

	int iterationLength = this.highscoreList.size();
	boolean addEnd = true;

	for (int i = 0; i < iterationLength; i++) {
	    Highscore currentHighscore = this.highscoreList.get(i);
	    if (scoreComparator.compare(highscore, currentHighscore) == highscore.getScore()) {
		this.highscoreList.add(i, highscore);
		addEnd = false;
		break;
	    }
	}

	if (addEnd) {
	    this.highscoreList.add(highscore);
	}

	this.saveHighscores();
    }

    public void saveHighscores() throws IOException, FileNotFoundException {

	Gson gson = new GsonBuilder().setPrettyPrinting().create();
	String highScoreJson = gson.toJson(this);

	OutputStream os = new FileOutputStream("highscores_temp.json");
	PrintWriter out = new PrintWriter(os);
	out.print(highScoreJson);
	out.close();

	Files.deleteIfExists(Path.of("highscores.json"));
	File newFile = new File("highscores_temp.json");

	if (!newFile.renameTo(new File("highscores.json"))) {
	    throw new IOException("Could not rename file" + newFile.getName());
	}

    }
}

