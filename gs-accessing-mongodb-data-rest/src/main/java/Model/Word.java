package Model;

import java.util.HashMap;
import java.util.Map;

public class Word {
	int id;
	int level;
	int timesAnsCorr;
	int timesAnsWrong;
	Language lang;
	Map<String, Boolean> word;
	
	public Word(int id, int level, int timesAnsCorr, int timesAnsWrong,
			Language lang) {
		setId(id);
		setLevel(level);
		setTimesAnsCorr(timesAnsCorr);
		setTimesAnsWrong(timesAnsWrong);
		setLang(lang);
		word = new HashMap<String, Boolean>();
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getLevel() {
		return level;
	}
	public void setLevel(int level) {
		this.level = level;
	}
	public int getTimesAnsCorr() {
		return timesAnsCorr;
	}
	public void setTimesAnsCorr(int timesAnsCorr) {
		this.timesAnsCorr = timesAnsCorr;
	}
	public int getTimesAnsWrong() {
		return timesAnsWrong;
	}
	public void setTimesAnsWrong(int timesAnsWrong) {
		this.timesAnsWrong = timesAnsWrong;
	}
	public Language getLang() {
		return lang;
	}
	public void setLang(Language lang) {
		this.lang = lang;
	}
	@Override
	public String toString() {
		return "Word [id=" + id + ", level=" + level + ", timesAnsCorr="
				+ timesAnsCorr + ", timesAnsWrong=" + timesAnsWrong + ", lang="
				+ lang + ", word=" + word + "]";
	}

	public void setWord(Map<String, Boolean> word) {
		this.word = word;
	}
	
	public Map<String, Boolean> getWord() {
		return word;
	}
}
