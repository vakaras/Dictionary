/**
 * The XGUI class contains methods needed for graphical user interface on
 * computer platform.
 */
package services;

import java.io.File;
import java.util.LinkedList;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import utils.Word;
import dict.Dict;

public class XGUI extends javax.swing.JFrame implements IService {

	private static final long serialVersionUID = 1L;
    private LinkedList<utils.LoadedWordList> wordList;
    private Dict dict;

    /** Constructors */
    public XGUI(LinkedList<utils.LoadedWordList> theDicts, Dict dictObject) {
      //initComponents();
      this.wordList = theDicts;
      this.dict = dictObject;
      //this.setVisible(true);
      }
      
    public XGUI() {
      //initComponents();
      }

    // Variables declaration
    private LinkedList<Word> matchFound;
    private javax.swing.JMenu jFileMenu;
    private javax.swing.JMenu jHelpMenu;
    private javax.swing.JMenu jDictMenu;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JButton jSearchButton;
    private javax.swing.JTextField jSearchField;
    private javax.swing.JMenuItem jExitMenuItem;
    private javax.swing.JMenuItem jAddWordItem;
    private javax.swing.JMenuItem jEraseWordItem;
    private javax.swing.JMenuItem jUpdateWordItem;
    private javax.swing.JMenuItem jRemoveSelectedDict;
    private javax.swing.JMenuItem jAddNewDict;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextArea jWordDef;
    private javax.swing.JComboBox jDictBox;
	private javax.swing.JFileChooser jChooseDictFile;

	/** This method is called from within the constructor to
     * initialize the form.
     */
    private void initComponents() {

        jSearchField = new javax.swing.JTextField();
        jSearchButton = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jWordDef = new javax.swing.JTextArea();
        jDictBox = new javax.swing.JComboBox();
        jMenuBar1 = new javax.swing.JMenuBar();
        jFileMenu = new javax.swing.JMenu();
        jHelpMenu = new javax.swing.JMenu();
        jExitMenuItem = new javax.swing.JMenuItem();
        jDictMenu = new javax.swing.JMenu();
        jAddWordItem = new javax.swing.JMenuItem();
        jEraseWordItem = new javax.swing.JMenuItem();
        jUpdateWordItem = new javax.swing.JMenuItem();
        jRemoveSelectedDict = new javax.swing.JMenuItem();
        jAddNewDict = new javax.swing.JMenuItem();
        jChooseDictFile = new javax.swing.JFileChooser();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Universal Dictionary");

        jSearchField.setText("");

        jExitMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent event) {
              stop();
              }

        });
        
        jAddWordItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent event) {
              launchAddWordWindow();
              }
        });
        
        jEraseWordItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent event) {
              launchEraseWordWindow();
              }
        });
        
        jUpdateWordItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent event) {
              launchUpdateWordWindow();
              }
        });
        
        jRemoveSelectedDict.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent event) {
              removeSelectedDict();
              }
        });
        
        jAddNewDict.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent event) {
              addNewDict();
              }
        });

        jSearchButton.setText("Search");
        jSearchButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                searchInDict();
            }
        });

        jFileMenu.setText("File");
        jMenuBar1.add(jFileMenu);
        
        jRemoveSelectedDict.setText("Remove selected dictionary");
        jFileMenu.add(jRemoveSelectedDict);
        
        jAddNewDict.setText("Import new dictionary");
        jFileMenu.add(jAddNewDict);
        
        jDictMenu.setText("Dictionary");
        jMenuBar1.add(jDictMenu);

        jExitMenuItem.setText("Exit");
        jFileMenu.add(jExitMenuItem);
        
        jAddWordItem.setText("Add word");
        jDictMenu.add(jAddWordItem);
        
        jEraseWordItem.setText("Delete word");
        jDictMenu.add(jEraseWordItem);
        
        jUpdateWordItem.setText("Update word");
        jDictMenu.add(jUpdateWordItem);
        
        setJMenuBar(jMenuBar1);

        jWordDef.setColumns(20);
        jWordDef.setRows(5);
        jWordDef.setText("");

        jWordDef.setLineWrap(true);
        jWordDef.setWrapStyleWord(true);
        jScrollPane1.setViewportView(jWordDef);
        
        setModelDictList();
        
        jSearchField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
              searchInDict();
              }
        });

        jSearchField.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
//                jPrintedText.setText(jSearchField.getText());
            }
        });	

        /** 
         * JFrom Layout Scheme
         */
        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 465, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jSearchField, javax.swing.GroupLayout.PREFERRED_SIZE, 224, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jDictBox, 0, 169, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jSearchButton)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jSearchField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jSearchButton)
                    .addComponent(jDictBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 196, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
        setVisible(true);
    }

    public void run() {
      initComponents();
      }

    public void stop() {
      System.out.println("System is now exiting. Bye :)");
      System.exit(0);
      }
    
    private void setModelDictList(){
    	String[] wordListai = new String[wordList.size()];
        for (int i = 0; i < wordList.size(); i++){
        	wordListai[i]=wordList.get(i).getWordListName();
        }
        
        jDictBox.setModel(new javax.swing.DefaultComboBoxModel(wordListai));
        jDictBox.revalidate();
        jDictBox.repaint();
    }
    
    private void launchAddWordWindow() {
    	String word = JOptionPane.showInputDialog(null, "Enter the word:");
    	String description = JOptionPane.showInputDialog(null, "Enter word description:");
    	try {
			(wordList.get(jDictBox.getSelectedIndex()).getWordList()).addWord(word, description);
			((wordlists.GSFMemory) wordList.get(jDictBox.getSelectedIndex()).getWordList()).save(wordList.get(jDictBox.getSelectedIndex()).getWordListPath());
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Error: "+e.getMessage());
		}
	}
    
    private void launchEraseWordWindow() {
    	String word = JOptionPane.showInputDialog(null, "Enter the word (full match needed):");
    	try {
			((wordlists.GSFMemory) wordList.get(jDictBox.getSelectedIndex()).getWordList()).eraseWord(word);
			((wordlists.GSFMemory) wordList.get(jDictBox.getSelectedIndex()).getWordList()).save(wordList.get(jDictBox.getSelectedIndex()).getWordListPath());
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Can't find word: "+word);
		}
	}

    private void launchUpdateWordWindow() {
    	String word = JOptionPane.showInputDialog(null, "Enter the word (full match needed):");
    	String description = JOptionPane.showInputDialog(null, "Enter word description:");

    	try {
			((wordlists.GSFMemory) wordList.get(jDictBox.getSelectedIndex()).getWordList()).updateWord(word, description);
			((wordlists.GSFMemory) wordList.get(jDictBox.getSelectedIndex()).getWordList()).save(wordList.get(jDictBox.getSelectedIndex()).getWordListPath());
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Can't find word: "+word);
		}
	}
    
	private void removeSelectedDict() {
		wordList.remove(jDictBox.getSelectedIndex());
		setModelDictList();
		dict.saveConfig(wordList);
	}
	
	private void addNewDict() {
		int returnVal = jChooseDictFile.showOpenDialog(this);
		if (returnVal == JFileChooser.APPROVE_OPTION) {
             File file = jChooseDictFile.getSelectedFile();
             String dictName = JOptionPane.showInputDialog(null, "Enter dictionary name:");
             dict.loadNewDict(file, dictName);
             setModelDictList();
//             System.out.println(file.getAbsolutePath()+" "+dictName);
        };
	}
	
    private void searchInDict() {
      try {
//        matchFound = (wordList[jDictBox.getSelectedIndex()].getWordList()).search(jSearchField.getText(), 4);
        matchFound = dict.search(jSearchField.getText(), 4, jDictBox.getSelectedIndex());
        
        String definitions = "";
        for(int temp = 0; temp < matchFound.size(); temp++) {
          definitions += matchFound.get(temp).getWord() + " - "
                      + matchFound.get(temp).getDescription() + "\n";
          }
        jWordDef.setText(definitions);
      } catch (Exception e) {
    	  JOptionPane.showMessageDialog(null, "Error: "+e.getMessage());
        };
      };
    // End of variables declaration
}
