/**
 * Config.java implements methods, which are needed to change programm settings
 */
package config;

import java.io.File;
import java.util.LinkedList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import dict.Dict;

public class Config {

  private static final String CONFIG_FILE = "config.xml";
  private static int id;
  private LinkedList<utils.LoadedWordList> wordList;
  
  private Dict dictObject;

  public Config(){
    }
    
  public Config(Dict dicts) {
    this.setDictObject(dicts);
    }
    
  
  public void setUpTestConfig() {
    try {
      DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
      DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
   
      // Create XML roots
      Document doc = docBuilder.newDocument();
      Element rootElement = doc.createElement("wordlists");
      doc.appendChild(rootElement);
      
      Element wl = doc.createElement("wordlist");
      rootElement.appendChild(wl);
      
      // Value list attribute
      Attr attr = doc.createAttribute("id");
      attr.setValue(Integer.toString(id));
      wl.setAttributeNode(attr);
      id++;
      
      // Word List file path
      Element wlFilePath = doc.createElement("wlfilepath");
      wlFilePath.appendChild(doc.createTextNode("tests/test.dwa"));
      wl.appendChild(wlFilePath);
      
      // Word List description
      Element wlFileDesc = doc.createElement("wlfiledesc");
      wlFileDesc.appendChild(doc.createTextNode("Testinis failas"));
      wl.appendChild(wlFileDesc);
      
      // write the content into XML file
      TransformerFactory transformerFactory = TransformerFactory.newInstance();
      Transformer transformer = transformerFactory.newTransformer();
      transformer.setOutputProperty(OutputKeys.INDENT, "yes");
      transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");

      DOMSource source = new DOMSource(doc);
      StreamResult result =  new StreamResult(new File(CONFIG_FILE));
      transformer.transform(source, result);
      }
    catch(ParserConfigurationException pce) {
      pce.printStackTrace();
      }
    catch(TransformerException tfe) {
      tfe.printStackTrace();
      };

    }
  
  public void save(LinkedList<utils.LoadedWordList> wordLists) {
    try {
      DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
      DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
   
      // Create XML roots
      Document doc = docBuilder.newDocument();
      Element rootElement = doc.createElement("wordlists");
      doc.appendChild(rootElement);
      
      Element wl;
      
      Attr attr;
      Element wlFilePath;
      Element wlFileDesc;
      
      for (int i = 0; i < wordLists.size(); i++) {
		// Value list attribute
	    wl = doc.createElement("wordlist");
        rootElement.appendChild(wl);
        
		attr = doc.createAttribute("id");
		attr.setValue(Integer.toString(wordLists.get(i).getId()));
		wl.setAttributeNode(attr);
		// Word List file path
		wlFilePath = doc.createElement("wlfilepath");
		wlFilePath.appendChild(doc.createTextNode(wordLists.get(i).getWordListPath()));
		wl.appendChild(wlFilePath);
		// Word List description
		wlFileDesc = doc.createElement("wlfiledesc");
		wlFileDesc.appendChild(doc.createTextNode(wordLists.get(i).getWordListName()));
		wl.appendChild(wlFileDesc);
      }
      
	// write the content into XML file
      TransformerFactory transformerFactory = TransformerFactory.newInstance();
      Transformer transformer = transformerFactory.newTransformer();
      transformer.setOutputProperty(OutputKeys.INDENT, "yes");
      transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");

      DOMSource source = new DOMSource(doc);
      StreamResult result =  new StreamResult(new File(CONFIG_FILE));
      transformer.transform(source, result);
      }
    catch(ParserConfigurationException pce) {
      pce.printStackTrace();
      }
    catch(TransformerException tfe) {
      tfe.printStackTrace();
      };

    }

  public LinkedList<utils.LoadedWordList> load() {
    try {
 
      File fXmlFile = new File(CONFIG_FILE);
      DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
      DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
      Document doc = dBuilder.parse(fXmlFile);
      doc.getDocumentElement().normalize();
   
      doc.getDocumentElement().getNodeName();
      NodeList nList = doc.getElementsByTagName("wordlist");
      wordList = new LinkedList<utils.LoadedWordList>();

      for (int temp = 0; temp < nList.getLength(); temp++) {
        Node nNode = nList.item(temp);
        if (nNode.getNodeType() == Node.ELEMENT_NODE) {
          Element eElement = (Element) nNode;
          
          wordList.add(new utils.LoadedWordList(
        		  Integer.parseInt(eElement.getAttribute("id")), 
        		  new wordlists.GSFMemory(), 
        		  getTagValue("wlfiledesc",eElement), 
        		  getTagValue("wlfilepath",eElement)
    		  ));
//          wordList[temp].setWordList(new wordlists.GSFMemory());
//          wordList[temp].setId(Integer.parseInt(eElement.getAttribute("id")));
//          wordList[temp].setWordListName(getTagValue("wlfiledesc",eElement));
//          wordList[temp].setWordListPath(getTagValue("wlfilepath",eElement));
          
          // FIXME: Check if IWordListFileRead before executing..
//          System.out.println(wordList.get(0).getWordListName());
          try {
            ((wordlists.IWordListFileRead) (wordList.get(temp)).getWordList()
              ).load(
              		getTagValue("wlfilepath",eElement)
              		);
      		} catch(Exception e) {
		        System.out.println("Couldn't load: "+e.getMessage());
		        wordList.removeLast();
      		  };
      		  
          /* FIXME: Object must be created outside the class
           * wordList = new wordlists.DWAMemory();
           * wordList.load(getTagValue("wlfilepath",eElement));
           */

          }
        }
      }
    catch (Exception e) {
      System.out.println("Error: " + e.getMessage());
//      e.printStackTrace();
      System.out.println("System is now exiting. :/");
      System.exit(0);
      }
    
    return this.wordList;
    }
 
  private static String getTagValue(String sTag, Element eElement){
    NodeList nlList = (eElement.getElementsByTagName(sTag).item(0)).getChildNodes();
    Node nValue = (Node) nlList.item(0); 
 
    return nValue.getNodeValue();
    }

	/**
	 * @param dictObject the dictObject to set
	 */
	public void setDictObject(Dict dictObject) {
		this.dictObject = dictObject;
	}
	
	/**
	 * @return the dictObject
	 */
	public Dict getDictObject() {
		return dictObject;
	}
	
	public void loadNewDict(File file, String name) {
		int id;
		if (wordList.size() == 0) {
			id = 0;
		} else {
			id = wordList.getLast().getId()+1;
		}
		wordList.add(new utils.LoadedWordList(
      		  id, 
      		  new wordlists.GSFMemory(), 
      		  name,
      		  file.getAbsolutePath()
  		  ));
		try {
			((wordlists.IWordListFileRead) (wordList.getLast()).getWordList()
        		).load(
    				file.getAbsolutePath()
        		);
			save(wordList);
		} catch (Exception e) {
			System.out.println("Error:"+e.getMessage());
		}
	}

  }
