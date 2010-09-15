fresh: clean src

src: Dict.class

run: Dict.class
	java Dict

Dict.class:
	javac Dict.java wordlists/WordListFactory.java

clean:
	rm -f *.class */*.class
