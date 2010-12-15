/**
 * The ServicesFactory implements factory, which produces instances, which
 * implements IService interface.
 */
package services;
import java.util.LinkedList;

import wordlists.*;
import dict.Dict;

@SuppressWarnings("unused")
public class ServiceFactory {

    private static IService startedService;
    //private Object dictObject;
    private LinkedList<utils.LoadedWordList> wordList;
    private Dict theDict = null;

    public ServiceFactory(){
      }
    
    ServiceFactory(LinkedList<utils.LoadedWordList> dicts) {
      this.wordList = dicts;
      }
    
    public ServiceFactory(LinkedList<utils.LoadedWordList> dicts, Dict theDict) {
        this.wordList = dicts;
        this.theDict = theDict;
        }
    
    public void newService (String service) {

      if(service.equals("DBUS")) {
        startedService = new DBUS();
        }
      else if(service.equals("MobileGUI")) {
        startedService = new MobileGUI();
        }
      else {
        startedService = new XGUI(wordList, theDict);
        }
      
      startedService.run();
      
      }

  }
