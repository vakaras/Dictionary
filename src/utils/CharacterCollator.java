package utils;

import java.text.Collator;

/**
 * Class implementing Character collator.
 */
public class CharacterCollator {

  private static CharacterCollator characterCollator = null;
  private Collator stringCollator = null;

  private CharacterCollator () {
    this.stringCollator = new Collator();
    }
  
  public CharacterCollator getInstance() {
    if (this.characterCollator == null)
      this.characterCollator = new CharacterCollator();
    return this.characterCollator;
    }
  
  public int compare(Character c1, Character c2) {
    return this.stringCollator.compare(c1.toString(), c2.toString());
    }

  }
