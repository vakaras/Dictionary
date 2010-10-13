package utils;

import java.lang.Character;
import java.text.Collator;
import java.util.Comparator;

/**
 * Class implementing Character collator.
 */
public class CharacterCollator implements Comparator<Object> {

  private static CharacterCollator characterCollator = null;
  private Collator stringCollator = null;

  private CharacterCollator () {
    this.stringCollator = Collator.getInstance();
    this.stringCollator.setStrength(Collator.IDENTICAL);
    }
  
  public static CharacterCollator getInstance() {
    if (characterCollator == null)
      characterCollator = new CharacterCollator();
    return characterCollator;
    }
  
  public int compare(Character c1, Character c2) {
    return this.stringCollator.compare(c1.toString(), c2.toString());
    }

  public int compare(Object c1, Object c2) {
    return this.compare((Character) c1, (Character) c2);
    }

  }
