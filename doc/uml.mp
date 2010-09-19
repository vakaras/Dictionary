% Pastaba: Ubuntu saugyklose esanti MetaUML versija yra pasenusi (2.3).
% Norint sukompiliuoti šį failą reikalinga atsinaujinti rankiniu būdu
% į 2.5 versiją.
% Note: In case to compile this file manual update of MetaUML package
% from 2.3 to 2.5 is needed.

input metauml;

beginfig(1);

  % TODO: Add words learning helper.

  Interface.IWL("IWordList")
      ("+search(word: String, count: int): List<Word>");

  Interface.IWLW("IWordListFileWrite")
      ("+save(filename: String): void");

  Interface.IWLR("IWordListFileRead")
      ("+load(filename: String): void");

  Interface.IWLC("IWordListChange")
      ("+addWord(word: String, definition: String): String",
      "+updateWord(word: String, definition: String): String",
      "+eraseWord(word: String): String");

  AbstractClass.WL("WordList")
      ()
      ();

  Class.WLa("DWAMemory")
      ()
      ();

  Class.WLb("GSFMemory")
      ()
      ();

  Class.WLc("DWAFile")
      ()
      ();

  Class.WLd("GSFFile")
      ()
      ();

  Class.WLF("WordListFactory")
      ()
      ("+newWordList(className: String, args:Map<String,String>):IWordList",
      "+changeType(dict: WordList, instanceIWordListWrite: String): void");

  Class.SF("ServiceFactory")
      ()
      ("+newService(className: String, args: String[]): IService");

  Class.C("Config")
      ()
      ("+load(): void",
      "+save(): void",
      "+getWordLists(): Map<String, Map<String, String> >",
      "+addWordList(identifier:String, wordList: Map<String,String>): void",
      "+removeWordList(identifier: String): void");

  Class.D("Dict")
      ()
      ("+main(services: String[]): void",
      "+addWordList(identifier:String, wordList: Map<String,String>): void",
      "+removeWordList(identifier: String): void",
      "+getWordListsIdentifiers(): String[]",
      "+searchAll(word: String): Map<String, Map<String, String> >",
      "+searchConcrete(identifier: String, word:String):Map<String,String>",
      "+searchSmart(word: String): Map<String, String>",
      "+updateWord(identifier:String,word:String, definition:String):void",
      "+eraseWord(identifier: String, word: String): String",
      "+addWord(identifier:String, word:String, definition: String):String"
      );

  Interface.IS("IService")
      ("+start(): void",
      "+stop(): void");

  Class.Sa("MobileGUI")
      ()
      ();

  Class.Sb("XGUI")
      ()
      ();

  Class.Sc("DBUS")
      ()
      ();

  IWL.nw = (0, 0);
  WL.nw = IWL.sw + (0, -50);
  WLa.nw = IWL.s + (10, -10);
  WLb.nw = WLa.sw + (0, -10);
  WLc.nw = WLb.sw + (0, -10);
  WLd.nw = WLc.sw + (0, -10);
  IWLC.nw = WLa.ne + (30, 0);
  IWLW.nw = IWLC.sw + (0, -10);
  IWLR.nw = IWLW.sw + (0, -10);

  drawObjects(IWL, WL, WLa, WLb, WLc, WLd, IWLR, IWLC, IWLW);

  link(realization)(WL.n -- IWL.s);
  link(inheritance)(WLa.w -- WL.e);
  link(inheritance)(WLb.w -- WL.e);
  link(inheritance)(WLc.w -- WL.e);
  link(inheritance)(WLd.w -- WL.e);
  link(realization)(WLa.e -- IWLC.w);
  link(realization)(WLb.e -- IWLC.w);
  link(realization)(WLa.e -- IWLW.w);
  link(realization)(WLb.e -- IWLW.w);
  link(realization)(WLa.e -- IWLR.w);
  link(realization)(WLb.e -- IWLR.w);
  link(realization)(WLc.e -- IWLR.w);
  link(realization)(WLd.e -- IWLR.w);


  D.nw = WL.sw + (0, -80);
  WLF.nw = D.s + (10, -10);
  SF.nw = WLF.sw + (0, -10);
  C.nw = SF.sw + (0, -10);

  drawObjects(D, WLF, SF, C);

  link(associationUni)(D.nw -- WL.s);
  item(iAssoc)("wordLists")(obj.ne = WL.s);
  item(iAssoc)("*")(obj.nw = WL.s);

  link(compositionUni)(WLF.w -- D.s + (-20, 0));
  link(composition)(SF.w -- D.s + (-25, 0));
  link(compositionUni)(C.w -- D.s + (-30, 0));

  IS.nw = D.sw + (20, -50);
  Sa.nw = IS.sw + (-20, -30);
  Sb.nw = Sa.s + (20, -10);
  Sc.nw = Sb.s + (20, -10);
 
  drawObjects(IS, Sa, Sb, Sc);

  link(association)(D.sw + (30, 0) -- IS.n);
  item(iAssoc)("services")(obj.sw = IS.n);
  item(iAssoc)("1..*")(obj.se = IS.n);
  item(iAssoc)("dict")(obj.nw = D.sw + (30, 0));
  item(iAssoc)("1")(obj.ne = D.sw + (30, 0));

  link(realization)(Sa.n -- IS.s);
  link(realization)(Sb.n -- IS.se);
  link(realization)(Sc.n -- IS.se);


endfig;

end
