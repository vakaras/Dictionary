/**
 * 
 */
package utils;

import wordlists.IWordList;

/**
 * @author egils
 *
 */
public class LoadedWordList {
	private int id;
    private wordlists.IWordList wordList;
    private String wordListName;
    private String wordListPath;

	public void setWordListName(String wordListName) {
		this.wordListName = wordListName;
	}

	public String getWordListName() {
		return wordListName;
	}
	
	public void setWordList(wordlists.IWordList wordList) {
		this.wordList = wordList;
	}
	
	public wordlists.IWordList getWordList() {
		return wordList;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getId() {
		return id;
	}
	
	public LoadedWordList(int id, IWordList wordList, String name, String path) {
		setId(id);
		setWordList(wordList);
		setWordListName(name);
		setWordListPath(path);
	}
	
	public LoadedWordList() {
	}

	public void setWordListPath(String wordListPath) {
		this.wordListPath = wordListPath;
//		System.out.println("Loaded: "+this.wordListPath);
	}

	public String getWordListPath() {
		return wordListPath;
	}
}
