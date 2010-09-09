input metauml;

beginfig(1);

  Class.A("Dict")
         ()
         (); 

  Class.B("IWordList")
         ()
         ("+load(filename: String):void",
      "+searchFront(request: String):Words []");

  topToBottom(45)(A, B);

  drawObjects(A, B);

  clink(aggregationUni)(A, B)

endfig;

end
