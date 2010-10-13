/**
 * @author: Vytautas Astrauskas
 * @author: Egidijus Lukauskas
 *
 */

import services.ServiceFactory;
import wordlists.WordListFactory;
import config.Config;

/**
 * The Dict class implements an application, which allows user to get
 * word definitions from various dictionaries.
 */
class Dict {

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
      wordlists.WordListFactory w = new wordlists.WordListFactory();
      ServiceFactory s = new ServiceFactory();
      }

    System.out.println("Program closed.");
    }

  public static void test() {
    junit.textui.TestRunner.run(tests.TestDWAMemory.class);
    junit.textui.TestRunner.run(tests.TestGSFMemory.class);
    junit.textui.TestRunner.run(tests.TestGSFFile.class);
    }

  }
