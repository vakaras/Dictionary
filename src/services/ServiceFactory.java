/**
 * The ServicesFactory implemnts factory, which produces instances, which
 * implements IService inteface.
 */
package services;
import wordlists.*;

public class ServiceFactory {

    private static IService startedService;
    private Object dictObject;
    public wordlists.IWordList[] wordList;

    public ServiceFactory(){
      }
    
    public ServiceFactory(wordlists.IWordList[] dicts) {
      this.wordList = dicts;
      }
    
    public void newService (String service) {

      if(service.equals("DBUS")) {
        startedService = new DBUS();
        }
      else if(service.equals("MobileGUI")) {
        startedService = new MobileGUI();
        }
      else {
        startedService = new XGUI(wordList);
        }
      
      startedService.run();
      
      }

  }
