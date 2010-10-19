package wordlists;

import java.util.Arrays;
import java.util.Collection;

/**
 * Word lists manager.
 */
public class Manager {

  /**
   * @return Collection of [<String_class_name>, <String_file_extension>]
   */
  public static Collection getWordListsWithFileExtensions() {
    return Arrays.asList(new Object[][] {
      { "wordlists.DWAFile", ".dwa" },
      { "wordlists.DWAMemory", ".dwa" },
      { "wordlists.GSFFile", ".gsf" },
      { "wordlists.GSFMemory", ".dwa" },
      { "wordlists.GSFMemory", ".gsf" },
      });
    }

  /**
   * @return Collection of [<String_class_name>, <String_file_extension>]
   */
  public static Collection getChangeableWordListsWithFileExtensions() {
    return Arrays.asList(new Object[][] {
      { "wordlists.DWAMemory", ".dwa" },
      { "wordlists.GSFMemory", ".dwa" },
      });
    }

  }
