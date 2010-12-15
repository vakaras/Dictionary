/**
 * @author: Vytautas Astrauskas
 * @author: Egidijus Lukauskas
 *
 */
package dict;
import java.io.File;
import java.util.LinkedList;

import utils.Word;

/**
 * The Dict class implements an application, which allows user to get
 * word definitions from various dictionaries.
 */
public class Dict {

  public Dict() {
    System.out.println("Running GUI.");
    startService();
    }
  private config.Config cfg; 
  
  public LinkedList<utils.LoadedWordList> wordList;
  
  public void startService() {

    cfg = new config.Config(this);
//    cfg.setUpTestConfig();
    wordList = cfg.load();
    
    services.ServiceFactory s = new services.ServiceFactory(wordList, this);
    s.newService("XGUI");
    }

//  public static void test() {
//    junit.textui.TestRunner.run(tests.TestDWAMemory.class);
//    junit.textui.TestRunner.run(tests.TestGSFMemory.class);
//    junit.textui.TestRunner.run(tests.TestGSFFile.class);
//    }
    
  private LinkedList<Word> result;
  public LinkedList<Word> search(String word, int count, int dicts) {
      try {
        result = (wordList.get(dicts).getWordList()).search(word, count);
      } catch (Exception e) {
      
        }
      return result;
    }
  
  public void saveConfig(LinkedList<utils.LoadedWordList> wordList) {
	  cfg.save(wordList);
  }
  
  public void loadNewDict(File file, String name) {
	  cfg.loadNewDict(file, name);
  }

  }
