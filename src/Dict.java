/**
 * @author: Vytautas Astrauskas
 * @author: Egidijus Lukauskas
 *
 */

import services.ServiceFactory;
import wordlists.WordListFactory;
import config.Config;

import java.util.LinkedList;
import utils.Word;

/**
 * The Dict class implements an application, which allows user to get
 * word definitions from various dictionaries.
 */
class Dict {

  Dict() {
    System.out.println("Running GUI.");
    startService();
    }
  
  private wordlists.WordListFactory w;
  public wordlists.IWordList[] wordList;
  
  public static void main(String[] services) {
    System.out.println("Program starting.");

    System.out.println("Arguments (" + services.length + "):");
    for (String i: services) {
      System.out.println(i);
      }

    if (services.length == 1 && services[0].equals("test")) {
      System.out.println("Runing unit tests.");
      test();
      }
    else {
      new Dict();
      }
    
    }
    
  public void startService() {
    w = new wordlists.WordListFactory();

    config.Config cfg = new config.Config(this);
    //cfg.save();
    wordList = cfg.load();

    services.ServiceFactory s = new services.ServiceFactory(wordList);
    s.newService("XGUI");
    }

  public static void test() {
    junit.textui.TestRunner.run(tests.TestDWAMemory.class);
    junit.textui.TestRunner.run(tests.TestGSFMemory.class);
    junit.textui.TestRunner.run(tests.TestGSFFile.class);
    }
    
  private LinkedList<Word> result;
  public LinkedList<Word> search(String word, int count, int dicts) {
      try {
        result = wordList[dicts].search(word, count);
      } catch (Exception e) {
      
        }
      return result;
    }

  }
