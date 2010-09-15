/**
 * @author: Vytautas Astrauskas
 * @author: Egidijus Lukauskas
 *
 */

import services.ServiceFactory;

/**
 * The Dict class implements an application, which allows user to get
 * word definitions from various dictionaries.
 */
class Dict {

  public static void main(String[] services) {
    System.out.println("Program starting.");

    wordlists.WordListFactory w = new wordlists.WordListFactory();
    ServiceFactory s = new ServiceFactory();

    System.out.println("Program closed.");
    }

  }
