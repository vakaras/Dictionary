package utils;

/**
 * Class implementing simple tuple – (word, description).
 */
public class Word {

  private String word;
  private String description;

  public Word(String word, String description) {
    this.word = word;
    this.description = description;
    }
  
  public String getWord() {
    return this.word;
    }
  
  public String getDescription() {
    return this.description;
    }
  
  }
