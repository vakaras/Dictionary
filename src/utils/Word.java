package utils;

import java.lang.Character;
import java.lang.Integer;


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

  /**
   * Increments word identifier.
   *
   * In case two words with same identifier was found, this function
   * can increase identifier. (By adding a number, or if it already exists
   * by increasing it.)
   *
   * @param word – word to increase.
   * @return increased word.
   */
  public static String increaseWordIdentifier(String word) {
    if (word.length() > 2 && word.charAt(word.length()-2) == ' ' &&
          Character.isDigit(word.charAt(word.length()-1))) {
      char digit = word.charAt(word.length()-1);
      String number = new String(new char[] {digit});
      number = (new Integer(Integer.parseInt(number)+1)
          ).toString();
      word = word.substring(0, word.length()-1) + number;
      }
    else {
      word = word + " 1";
      }
    return word;
    }

  /**
   * Clears word identifier.
   *
   * In case increased word was read from file, it must be cleared before
   * using. 
   *
   * @param word – word, which may be increased.
   * @return cleared word.
   */
  public static String clearWordIdentifier(String word) {
    if (word.length() > 2 && word.charAt(word.length()-2) == ' ' &&
          Character.isDigit(word.charAt(word.length()-1))) {
      word = word.substring(0, word.length()-2);
      }
    return word;
    }
  
  }
