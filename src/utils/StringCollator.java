package utils;

import java.util.Comparator;
import java.lang.Math;
import java.lang.Character;

import utils.CharacterCollator;

/**
 * Class implementing String collator based on Character collator.
 */
public class StringCollator implements Comparator<Object> {

  private static StringCollator stringCollator = null;
  private CharacterCollator characterCollator = null;

  private StringCollator () {
    this.characterCollator = CharacterCollator.getInstance();
    }
  
  public static StringCollator getInstance() {
    if (stringCollator == null)
      stringCollator = new StringCollator();
    return stringCollator;
    }

  public int compare(String s1, String s2) {
    // TODO: Check if works.
    int length = Math.min(s1.length(), s2.length());

    for (int i = 0; i < length; i++) {
      Character c1 = new Character(s1.charAt(i));
      Character c2 = new Character(s2.charAt(i));
      if (this.characterCollator.compare(c1, c2) != 0) {
        return this.characterCollator.compare(c1, c2);
        }
      }
    
    if (s1.length() < s2.length()) {
      return -1;
      }
    else if (s1.length() > s2.length()) {
      return 1;
      }
    return 0;
    }
  
  public int compare(Object o1, Object o2) {
    return this.compare((String) o1, (String) o2);
    }
  
  }
